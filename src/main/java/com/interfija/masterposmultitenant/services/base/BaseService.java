package com.interfija.masterposmultitenant.services.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Clase base que proporciona métodos comunes para realizar operaciones con bases de datos,
 * incluyendo transacciones y validación de datos.
 */
public class BaseService {

    /**
     * Logger para registrar eventos y mensajes de depuración.
     */
    protected Logger logger;

    /**
     * Constructor que inicializa las instancias de DataSource y MasterData.
     */
    public BaseService() {
    }

    /**
     * Establece un objeto de tipo Logger para manejar los mensajes de actividad de la clase.
     *
     * @param classForLogger clase que requiere el logger.
     */
    public void setLogger(Class<?> classForLogger) {
        this.logger = LoggerFactory.getLogger(classForLogger);
    }

    /**
     * Concatena múltiples cadenas en una sola, separadas por un espacio.
     * <p>
     * Ignora valores {@code null} y omite espacios adicionales.
     * </p>
     *
     * @param delimiter Delimitador para las cadenas a concatenar.
     * @param strings   Las cadenas a concatenar.
     * @return Una cadena resultante de la concatenación de los valores no nulos,
     * separados por un espacio. Si no hay valores válidos, devuelve una cadena vacía.
     */
    protected String concatStrings(char delimiter, String... strings) {
        return Arrays.stream(strings)
                .filter(Objects::nonNull)
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.joining(String.valueOf(delimiter)));
    }

    /**
     * Método auxiliar para evitar advertencias de conversión genérica.
     */
    @SuppressWarnings("unchecked")
    protected <T> List<T> cast(List<?> list) {
        return (List<T>) list;
    }

    /**
     * Válida y filtra una lista de datos según los predicados proporcionados.
     *
     * @param <T>           El tipo de los elementos en la lista.
     * @param dataList      La lista de datos a validar y filtrar.
     * @param nameExtractor Una función que extrae el nombre o identificador de cada elemento.
     * @param validations   Un mapa donde la clave es la descripción de la validación y el valor es un
     *                      predicado para evaluar cada elemento.
     * @return Una lista filtrada que solo contiene elementos que pasan todas las validaciones.
     */
    protected <T> List<T> validateAndFilterData(List<T> dataList, Function<T, String> nameExtractor,
                                                Map<String, Predicate<T>> validations) {
        return dataList.stream()
                .filter(item -> {
                    boolean isValid = true;
                    for (Map.Entry<String, Predicate<T>> entry : validations.entrySet()) {
                        if (!entry.getValue().test(item)) {
                            logger.warn("{} {}", nameExtractor.apply(item), entry.getKey());
                            isValid = false;
                        }
                    }
                    return isValid;
                })
                .toList();
    }

    /**
     * Válida un elemento utilizando los predicados proporcionados.
     * Si el elemento no cumple alguna validación, se registra un mensaje de advertencia en el log.
     *
     * @param <T>           El tipo del elemento a validar.
     * @param item          El elemento que se validará.
     * @param nameExtractor Una función que extrae un identificador o nombre del elemento para fines de registro.
     * @param validations   Un mapa donde la clave es la descripción de la validación y el valor es un predicado
     *                      que determina si el elemento cumple con la condición.
     * @return {@code true} si el elemento pasa todas las validaciones, de lo contrario {@code false}.
     */
    protected <T> boolean validateData(T item, Function<T, Object> nameExtractor, Map<String, Function<T, Boolean>> validations) {
        boolean isValid = true;
        for (Map.Entry<String, Function<T, Boolean>> entry : validations.entrySet()) {
            if (!entry.getValue().apply(item)) {
                logger.warn("{} {}", nameExtractor.apply(item), entry.getKey());
                isValid = false;
            }
        }
        return isValid;
    }

    /**
     * Válida un elemento utilizando los predicados proporcionados.
     * Si el elemento no cumple alguna validación, se registra un mensaje de advertencia en el log.
     *
     * @param <T>           El tipo del elemento a validar.
     * @param dataList      La lista de datos a validar.
     * @param nameExtractor Una función que extrae un identificador o nombre del elemento para fines de registro.
     * @param validations   Un mapa donde la clave es la descripción de la validación y el valor es un predicado
     *                      que determina si el elemento cumple con la condición.
     * @return {@code true} si el elemento pasa todas las validaciones, de lo contrario {@code false}.
     */
    protected <T> boolean validateDataList(List<T> dataList, Function<T, String> nameExtractor,
                                           Map<String, Function<T, Boolean>> validations) {
        boolean isValid = true;
        for (T t : dataList) {
            for (Map.Entry<String, Function<T, Boolean>> entry : validations.entrySet()) {
                if (!entry.getValue().apply(t)) {
                    logger.warn("{} {}", nameExtractor.apply(t), entry.getKey());
                    isValid = false;
                }
            }
        }
        return isValid;
    }

    protected <D, E> void mapAndAddEntities(List<D> dtoList, Function<D, E> mapper, Consumer<E> adder) {
        dtoList.forEach(dto -> adder.accept(mapper.apply(dto)));
    }

    protected <D, E, K> void updateChildEntities(List<E> currentEntities, List<D> dtoList,
                                                 Function<D, K> dtoIdExtractor, Function<E, K> entityIdExtractor, BiConsumer<E, D> updater,
                                                 Function<D, E> entityCreator, Consumer<E> remover, Consumer<E> adder) {

        // Mapear IDs actuales
        Map<K, E> currentMap = currentEntities.stream()
                .collect(Collectors.toMap(entityIdExtractor, Function.identity()));

        // Nuevos IDs del DTO
        Set<K> incomingIds = dtoList.stream()
                .map(dtoIdExtractor)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        // Eliminar los que no están
        currentMap.values().stream()
                .filter(e -> !incomingIds.contains(entityIdExtractor.apply(e)))
                .forEach(remover);

        // Agregar o actualizar
        for (D dto : dtoList) {
            K id = dtoIdExtractor.apply(dto);
            if (id == null) {
                E newEntity = entityCreator.apply(dto);
                adder.accept(newEntity);
            } else {
                E existing = currentMap.get(id);
                if (existing != null) {
                    updater.accept(existing, dto);
                }
            }
        }
    }

    protected <E, K> void updateChildEntities(List<E> currentEntities, List<E> entityList,
                                                 Function<E, K> entityIdExtractor, BiConsumer<E, E> updater,
                                                 Consumer<E> remover, Consumer<E> adder) {

        // Mapear IDs actuales
        Map<K, E> currentMap = currentEntities.stream()
                .collect(Collectors.toMap(entityIdExtractor, Function.identity()));

        // Nuevos IDs del DTO
        Set<K> incomingIds = entityList.stream()
                .map(entityIdExtractor)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        // Eliminar los que no están
        currentMap.values().stream()
                .filter(e -> !incomingIds.contains(entityIdExtractor.apply(e)))
                .forEach(remover);

        // Agregar o actualizar
        for (E e : entityList) {
            K id = entityIdExtractor.apply(e);
            if (id == null) {
                adder.accept(e);
            } else {
                E existing = currentMap.get(id);
                if (existing != null) {
                    updater.accept(existing, e);
                }
            }
        }
    }

}
