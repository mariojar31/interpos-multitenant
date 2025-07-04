package com.interfija.masterposmultitenant.services.cash;

import com.interfija.masterposmultitenant.dto.cash.CashDTO;
import com.interfija.masterposmultitenant.dto.cash.CashPaymentDTO;
import com.interfija.masterposmultitenant.dto.cash.CashSummaryDTO;
import com.interfija.masterposmultitenant.dto.cash.CashTaxDTO;
import com.interfija.masterposmultitenant.dto.floor.TerminalDTO;
import com.interfija.masterposmultitenant.dto.other.TypePaymentDTO;
import com.interfija.masterposmultitenant.dto.other.TypeTaxDTO;
import com.interfija.masterposmultitenant.entities.tenant.cash.CashEntity;
import com.interfija.masterposmultitenant.mappers.cash.CashMapper;
import com.interfija.masterposmultitenant.repository.cash.CashRepository;
import com.interfija.masterposmultitenant.repository.cash.projections.CashPaymentProjection;
import com.interfija.masterposmultitenant.repository.cash.projections.CashTaxProjection;
import com.interfija.masterposmultitenant.services.base.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * Clase que gestiona las operaciones relacionadas con las cajas dentro del sistema.
 * Esta clase maneja las instancias de acceso a la base de datos para las cajas,
 * permitiendo realizar operaciones como obtener, crear y actualizar cajas.
 *
 * @author Steven Arzuza.
 */
@Service
public class CashService extends BaseService {

    /**
     * Objeto que maneja las operaciones de acceso a la base de datos para caja.
     */
    private final CashRepository cashRepository;

    /**
     * Mapper para convertir entre entidades {@link CashEntity} y {@link CashDTO} relacionados.
     */
    private final CashMapper cashMapper;

    /**
     * Constructor que inicializa los objetos necesarios para la gestión de caja.
     * Inicializa la fuente de datos, el DAO de caja y los datos maestros.
     */
    @Autowired
    public CashService(CashRepository cashRepository, CashMapper cashMapper) {
        setLogger(CashService.class);
        this.cashRepository = cashRepository;
        this.cashMapper = cashMapper;
    }

    /**
     * Obtiene la caja abierta registrada en el sistema a partir del ID de terminal.
     *
     * @param idTerminal  Identificador de la terminal asociada a la caja.
     * @param fullMapping indica si se deben cargar las relaciones asociadas al objeto.
     * @return un {@link Optional} que contiene un {@link CashDTO} si se encuentra la caja, o vacío si no existe.
     */
    @Transactional(readOnly = true)
    public Optional<CashDTO> getOpenCashByTerminalId(long idTerminal, boolean fullMapping) {
        return getCash(() -> cashRepository.findOpenCashByTerminalId(idTerminal), fullMapping, "terminal");
    }

    /**
     * Obtiene una caja registrada en el sistema a partir del ID de caja.
     *
     * @param idCash      Identificador de la caja a consultar.
     * @param fullMapping indica si se deben cargar las relaciones asociadas al objeto.
     * @return un {@link Optional} que contiene un {@link CashDTO} si se encuentra la caja, o vacío si no existe.
     */
    @Transactional(readOnly = true)
    public Optional<CashDTO> getCashById(long idCash, boolean fullMapping) {
        return getCash(() -> cashRepository.findById(idCash), fullMapping, "id");
    }

    /**
     * Método auxiliar reutilizable para obtener una entidad de caja y mapearla a un {@link CashDTO}.
     *
     * @param entitySupplier proveedor de la entidad {@link CashEntity} (por ID o por terminal).
     * @param fullMapping    indica si se deben cargar las relaciones asociadas al DTO.
     * @param source         descripción de la fuente para fines de log en caso de error (por ejemplo, "id" o "terminal").
     * @return un {@link Optional} que contiene un {@link CashDTO} si se encuentra, o vacío si no.
     */
    private Optional<CashDTO> getCash(Supplier<Optional<CashEntity>> entitySupplier, boolean fullMapping, String source) {
        try {
            Optional<CashEntity> optionalCashEntity = entitySupplier.get();
            if (optionalCashEntity.isEmpty()) {
                return Optional.empty();
            }

            CashDTO cashDTO = cashMapper.toDto(optionalCashEntity.get());
            if (fullMapping) {
                mapCash(List.of(cashDTO));
            }

            return Optional.of(cashDTO);
        } catch (Exception e) {
            logger.error("No se pudo obtener la caja por {} -> {}", source, e.getMessage(), e);
            return Optional.empty();
        }
    }

    /**
     * Asocia a cada {@link CashDTO} de la lista sus impuestos y pagos correspondientes.
     * <p>
     * Este método obtiene las listas de {@link CashTaxDTO} y {@link CashPaymentDTO} asociadas
     * a los IDs de caja, y las asigna a cada objeto {@link CashDTO} según corresponda.
     *
     * @param cashList lista de objetos {@link CashDTO} a los que se les asignarán sus impuestos y pagos.
     */
    private void mapCash(List<CashDTO> cashList) {
        List<Long> cashIds = cashList.stream()
                .map(CashDTO::getIdCash)
                .toList();

        List<CashTaxDTO> cashTaxList = getCashTax(cashIds);
        List<CashPaymentDTO> cashPaymentList = getCashPayments(cashIds);

        for (CashDTO cash : cashList) {
            List<CashTaxDTO> taxList = cashTaxList.stream()
                    .filter(tax -> tax.getCashId().equals(cash.getIdCash()))
                    .toList();
            cash.setCashTaxesList(taxList);

            List<CashPaymentDTO> paymentList = cashPaymentList.stream()
                    .filter(payment -> payment.getCashId().equals(cash.getIdCash()))
                    .toList();
            cash.setCashPaymentList(paymentList);
        }
    }

    /**
     * Obtiene una lista de pagos de las cajas en el sistema.
     *
     * @param cashIds identificadores de las cajas a buscar.
     * @return Lista de objetos CashPaymentDTO que representan los pagos de las cajas registradas.
     */
    private List<CashPaymentDTO> getCashPayments(List<Long> cashIds) {
        List<CashPaymentProjection> projections = cashRepository.findCashPayments(cashIds);

        return projections.stream()
                .map(p -> {
                    TypePaymentDTO typePaymentDTO = TypePaymentDTO.builder()
                            .idTypePayment(p.getTypePaymentId())
                            .name(p.getTypePaymentName())
                            .build();

                    return CashPaymentDTO.builder()
                            .typePaymentDTO(typePaymentDTO)
                            .total(p.getTotalReceived())
                            .date(p.getInvoiceDate())
                            .invoiceId(p.getInvoiceId())
                            .cashId(p.getCashId())
                            .build();
                })
                .toList();
    }

    /**
     * Obtiene una lista de impuestos de las cajas en el sistema.
     *
     * @param cashIds identificadores de las cajas a buscar.
     * @return Lista de objetos CashTaxDTO que representan los impuestos de las cajas registradas.
     */
    private List<CashTaxDTO> getCashTax(List<Long> cashIds) {
        List<CashTaxProjection> projections = cashRepository.findCashTaxes(cashIds);

        return projections.stream()
                .map(p -> {
                    TypeTaxDTO typeTax = TypeTaxDTO.builder()
                            .idTypeTax(p.getTypeTaxId())
                            .name(p.getTypeTaxName())
                            .rate(p.getTypeTaxRate())
                            .build();

                    return CashTaxDTO.builder()
                            .neto(p.getSaleSum())
                            .total(p.getTaxSum())
                            .typeTaxDTO(typeTax)
                            .invoiceId(p.getInvoiceId())
                            .cashId(p.getCashId())
                            .build();
                }).toList();
    }

    /**
     * Cierra el registro de caja actual y crea un nuevo registro de caja con el siguiente número de secuencia.
     * <p>
     * Este método inicia una transacción para cerrar la caja actual y, si tiene éxito,
     * inserta un nuevo registro de caja con el inicio del siguiente turno.
     *
     * @param cashDTO Objeto CashDTO que representa la caja a cerrar.
     * @return Un Optional que contiene el nuevo objeto CashDTO si la operación fue exitosa,
     * o un Optional vacío si no lo fue.
     */
    @Transactional
    public Optional<CashDTO> closeCash(CashDTO cashDTO) {
        cashDTO.setEndDate(LocalDateTime.now());
        CashEntity cashEntity;
        try {
            cashEntity = cashMapper.toEntity(cashDTO);
            cashRepository.save(cashEntity);
        } catch (Exception e) {
            logger.error("No se pudo cerrar la caja -> {}.", e.getMessage());
            return Optional.empty();
        }

        if (cashEntity.getIdCash() == null) {
            return Optional.empty();
        }

        return insertCash(cashDTO.getSequence(), cashDTO.getTerminalDTO());
    }

    /**
     * Inserta un nuevo registro de caja en la base de datos.
     */
    @Transactional
    public Optional<CashDTO> insertCash(int sequence, TerminalDTO terminalDTO) {
        CashDTO cashDTO = CashDTO.builder()
                .sequence(sequence + 1)
                .startDate(LocalDateTime.now())
                .terminalDTO(terminalDTO)
                .build();

        CashEntity cashEntity;
        try {
            cashEntity = cashMapper.toEntity(cashDTO);
            cashRepository.save(cashEntity);
        } catch (Exception e) {
            logger.error("No se pudo insertar la caja -> {}.", e.getMessage());
            return Optional.empty();
        }

        if (cashEntity.getIdCash() == null) {
            return Optional.empty();
        } else {
            cashDTO.setIdCash(cashEntity.getIdCash());
            return Optional.of(cashDTO);
        }
    }

    /**
     * Obtiene una lista de resumenes de las cajas en el sistema.
     *
     * @param idTerminal identificador de la terminal para buscar las cajas.
     * @return Lista de objetos CashSummaryDTO que representan los resumenes de las cajas registradas.
     */
    @Transactional(readOnly = true)
    public List<CashSummaryDTO> getCashSummaries(long idTerminal) {
        return cashRepository.findCashSummaries(idTerminal).stream()
                .map(p -> CashSummaryDTO.builder()
                        .idCash(p.getIdCash())
                        .sequence(p.getSequence())
                        .startDate(p.getStartDate())
                        .endDate(p.getEndDate())
                        .total(p.getTotal())
                        .build())
                .collect(Collectors.toList());
    }

}
