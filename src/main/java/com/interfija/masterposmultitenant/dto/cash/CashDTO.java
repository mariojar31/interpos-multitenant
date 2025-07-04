package com.interfija.masterposmultitenant.dto.cash;

import com.interfija.masterposmultitenant.dto.base.DtoWithAction;
import com.interfija.masterposmultitenant.dto.floor.TerminalDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * Representa la información de una caja.
 *
 * @author Steven Arzuza.
 */
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CashDTO extends DtoWithAction {

    /**
     * Identificador único de la caja.
     */
    private Long idCash;

    /**
     * Secuencia de la caja.
     */
    private int sequence;

    /**
     * Fecha de apertura de la caja.
     */
    private LocalDateTime startDate;

    /**
     * Fecha cierre de la caja.
     */
    private LocalDateTime endDate;

    /**
     * Indica si la caja esta abierta o no.
     */
    private boolean closed;

    /**
     * Lista de pagos de la caja.
     */
    private List<CashPaymentDTO> cashPaymentList;

    /**
     * Lista de impuestos de la caja.
     */
    private List<CashTaxDTO> cashTaxesList;

    /**
     * Terminal asociada a la caja.
     */
    private TerminalDTO terminalDTO;

    /**
     * Calcula el total de impuestos sumando los impuestos de todos los pagos.
     *
     * @return Total de impuestos calculados.
     */
    public BigDecimal getTotalTax() {
        if (cashTaxesList == null) {
            return BigDecimal.ZERO;
        }

        return cashTaxesList.stream()
                .map(CashTaxDTO::getTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * Calcula el total final de la caja sumando los pagos.
     *
     * @return Total final de la caja.
     */
    public BigDecimal getTotal() {
        if (cashPaymentList == null) {
            return BigDecimal.ZERO;
        }

        return cashPaymentList.stream()
                .map(CashPaymentDTO::getTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * Determina si este objeto es igual a otro.
     *
     * @param obj el objeto a comparar.
     * @return {@code true} si ambos objetos son iguales, {@code false} en caso contrario.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        CashDTO that = (CashDTO) obj;
        return Objects.equals(idCash, that.idCash);
    }

    /**
     * Devuelve el código hash de este objeto.
     *
     * @return el código hash basado en el identificador de la caja.
     */
    @Override
    public int hashCode() {
        return Objects.hash(idCash);
    }

    /**
     * Devuelve una representación en forma de cadena del nombre del piso.
     *
     * @return el nombre del piso.
     */
    @Override
    public String toString() {
        return Long.toString(sequence);
    }

}
