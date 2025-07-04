package com.interfija.masterposmultitenant.master;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.interfija.masterposmultitenant.services.master.MasterDataService;
import com.interfija.masterposmultitenant.session.SpringContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

import static com.interfija.masterposmultitenant.master.MasterDataEnum.BARCODE_TYPES_LIST;
import static com.interfija.masterposmultitenant.master.MasterDataEnum.DEPARTMENTS_LIST;
import static com.interfija.masterposmultitenant.master.MasterDataEnum.IDENTIFICATION_TYPES_LIST;
import static com.interfija.masterposmultitenant.master.MasterDataEnum.MUNICIPALITIES_LIST;
import static com.interfija.masterposmultitenant.master.MasterDataEnum.PAYMENTS_FORM_LIST;
import static com.interfija.masterposmultitenant.master.MasterDataEnum.TAXES_LIST;
import static com.interfija.masterposmultitenant.master.MasterDataEnum.TYPE_TAXES_LIST;
import static com.interfija.masterposmultitenant.master.MasterDataEnum.TYPE_ORGANIZATIONS_LIST;
import static com.interfija.masterposmultitenant.master.MasterDataEnum.TYPE_PAYMENTS_LIST;
import static com.interfija.masterposmultitenant.master.MasterDataEnum.TYPE_REGIMES_LIST;
import static com.interfija.masterposmultitenant.master.MasterDataEnum.TYPE_RESPONSIBILITIES_LIST;
import static com.interfija.masterposmultitenant.master.MasterDataEnum.UNITS_LIST;

/**
 * Clase para gestionar una caché en memoria utilizando la librería Caffeine.
 * Permite el almacenamiento de datos en caché con tiempos de expiración dinámicos
 * dependiendo de la clave.
 *
 * @author Steven Arzuza.
 */
public class MasterData {

    private static volatile MasterData instance;
    private final Logger logger;
    private final Cache<String, Object> cachedData;
    private final MasterDataService masterDataService;

    /**
     * Constructor privado para implementar el patrón Singleton.
     * Inicializa la caché con expiración dinámica y carga los datos iniciales.
     */
    private MasterData() {
        this.logger = LoggerFactory.getLogger(MasterData.class);
        this.masterDataService = SpringContext.getInstance().getBean(MasterDataService.class);
        this.cachedData = Caffeine.newBuilder()
                .maximumSize(1000)
                .expireAfterWrite(Duration.ofMinutes(15))
                .build();
    }

    /**
     * Obtiene la instancia única de la clase (patrón Singleton).
     *
     * @return Instancia única de MasterData.
     */
    public static MasterData getInstance() {
        if (instance == null) {
            synchronized (MasterData.class) {
                if (instance == null) {
                    instance = new MasterData();
                }
            }
        }
        return instance;
    }

    /**
     * Obtiene una lista de la caché por su clave. Si no existe, la carga desde la base de datos.
     *
     * @param key   Clave de la lista en la caché.
     * @param clazz Clase del tipo esperado.
     * @param <T>   Tipo de los elementos de la lista.
     * @return Lista almacenada o cargada desde la base de datos.
     * @throws ClassCastException Si la lista no coincide con el tipo esperado.
     */
    @SuppressWarnings("unchecked")
    public <T> List<T> getList(MasterDataEnum key, Class<T> clazz) {
        Object rawData = cachedData.getIfPresent(key.getMaster());

        if (rawData instanceof List<?> rawList) {
            if (rawList.isEmpty() || clazz.isInstance(rawList.getFirst())) {
                return (List<T>) rawList;
            } else {
                throw new ClassCastException("El tipo de la lista no coincide con el esperado: " + clazz.getName());
            }
        }

        return loadAndCacheList(key, clazz);
    }

    /**
     * Carga y almacena una lista en la caché si aún no está presente.
     *
     * @param key   Clave de la lista.
     * @param clazz Clase del tipo esperado.
     * @param <T>   Tipo de los elementos de la lista.
     * @return Lista cargada y almacenada en la caché.
     */
    @SuppressWarnings("unchecked")
    private <T> List<T> loadAndCacheList(MasterDataEnum key, Class<T> clazz) {
        try {
            // Mapa para claves individuales
            Map<MasterDataEnum, Supplier<List<T>>> queryMap = new HashMap<>();
            queryMap.put(TYPE_TAXES_LIST, () -> (List<T>) masterDataService.findAllTypeTaxes());
            queryMap.put(TAXES_LIST, () -> (List<T>) masterDataService.findAllTaxes());
            queryMap.put(UNITS_LIST, () -> (List<T>) masterDataService.findAllTypeUnits());
            queryMap.put(BARCODE_TYPES_LIST, () -> (List<T>) masterDataService.findAllBarcodeTypes());
            queryMap.put(TYPE_REGIMES_LIST, () -> (List<T>) masterDataService.findAllTypeRegimes());
            queryMap.put(TYPE_ORGANIZATIONS_LIST, () -> (List<T>) masterDataService.findAllTypeOrganizations());
            queryMap.put(TYPE_RESPONSIBILITIES_LIST, () -> (List<T>) masterDataService.findAllTypeResponsibilities());
            queryMap.put(DEPARTMENTS_LIST, () -> (List<T>) masterDataService.findAllDepartments());
            queryMap.put(MUNICIPALITIES_LIST, () -> (List<T>) masterDataService.findAllMunicipalities());
            queryMap.put(IDENTIFICATION_TYPES_LIST, () -> (List<T>) masterDataService.findAllIdentificationsType());
            queryMap.put(TYPE_PAYMENTS_LIST, () -> (List<T>) masterDataService.findAllTypePayments());
            queryMap.put(PAYMENTS_FORM_LIST, () -> (List<T>) masterDataService.findAllPaymentsForm());

            // Grupos que se deben consultar en conjunto usando una sola transacción
            Map<Set<MasterDataEnum>, List<MasterDataEnum>> groupedKeys = Map.of(
                    Set.of(TYPE_TAXES_LIST, TAXES_LIST, UNITS_LIST, BARCODE_TYPES_LIST),
                    List.of(TYPE_TAXES_LIST, TAXES_LIST, UNITS_LIST, BARCODE_TYPES_LIST),

                    Set.of(TYPE_REGIMES_LIST, TYPE_ORGANIZATIONS_LIST, TYPE_RESPONSIBILITIES_LIST),
                    List.of(TYPE_REGIMES_LIST, TYPE_ORGANIZATIONS_LIST, TYPE_RESPONSIBILITIES_LIST)
            );

            // Consultar en grupo si la clave está en un grupo
            for (Map.Entry<Set<MasterDataEnum>, List<MasterDataEnum>> entry : groupedKeys.entrySet()) {
                if (entry.getKey().contains(key)) {
                    Map<MasterDataEnum, List<?>> groupedData =
                            masterDataService.findGroupedMasterData(entry.getValue());

                    for (MasterDataEnum k : entry.getValue()) {
                        List<?> data = groupedData.get(k);
                        if (data != null) {
                            cachedData.put(k.getMaster(), loadList(data, k.getMaster()));
                        }
                    }

                    return getList(key, clazz);
                }
            }

            // Clave no agrupada: consultar individualmente
            if (queryMap.containsKey(key)) {
                cachedData.put(key.getMaster(), loadList(queryMap.get(key).get(), key.getMaster()));
                return getList(key, clazz);
            }

            throw new IllegalArgumentException("Clave de lista no reconocida: " + key.getMaster());

        } catch (Exception e) {
            logger.error("Error al cargar la lista '{}' desde la base de datos: {}", key.getMaster(), e.getMessage(), e);
            return List.of();
        }
    }

    /**
     * Válida y carga una lista de datos. Si la lista está vacía, retorna una lista vacía.
     *
     * @param list     Lista a cargar.
     * @param listName Nombre de la lista (para fines de depuración).
     * @param <T>      Tipo de los elementos de la lista.
     * @return Lista cargada o lista vacía en caso de error.
     */
    private <T> List<T> loadList(List<T> list, String listName) {
        try {
            if (list == null || list.isEmpty()) {
                throw new Exception("Lista vacía: " + listName);
            }
            return list;
        } catch (Exception e) {
            logger.error("No se pudo cargar la lista de {}: {}", listName, e.getMessage());
            return List.of();
        }
    }

    /**
     * Agrega una lista a la caché bajo la clave proporcionada.
     *
     * @param key  Clave de la lista.
     * @param list Lista de elementos a almacenar.
     * @param <T>  Tipo de los elementos de la lista.
     */
    public <T> void putList(String key, List<T> list) {
        cachedData.put(key, list);
    }

    /**
     * Verifica si existe una lista con la clave especificada en la caché.
     *
     * @param key Clave de la lista.
     * @return true si existe la lista, false en caso contrario.
     */
    public boolean containsList(String key) {
        return cachedData.getIfPresent(key) != null;
    }

}
