package com.interfija.masterposmultitenant.dto.invoice;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.interfija.masterposmultitenant.dto.employee.EmployeeDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

/**
 * Representa el empleado asociado al producto asociado a la factura.
 *
 * @author Steven Arzuza.
 */
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class InvoiceProductEmployeeDTO {

    /**
     * Identificador único del empleado asociado al producto asociado a la factura
     */
    private Long idInvoiceProductEmployee;

    /**
     * Información del empleado asociado al producto asociado a la factura.
     */
    private EmployeeDTO employeeDTO;

    /**
     * Identificador único asociado del producto de la factura
     */
    private Long invoiceProductId;

    /**
     * Crea una nueva instancia de {@code InvoiceEmployeeDto} con la información del empleado asociado al producto proporcionada.
     *
     * @param employeeDTO El empleado asociado al producto asociado a la factura.
     */
    public InvoiceProductEmployeeDTO(EmployeeDTO employeeDTO) {
        this.employeeDTO = employeeDTO;
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
        InvoiceProductEmployeeDTO that = (InvoiceProductEmployeeDTO) obj;
        return Objects.equals(idInvoiceProductEmployee, that.idInvoiceProductEmployee);
    }

    /**
     * Devuelve el código hash de este objeto.
     *
     * @return el código hash basado en el identificador del empleado asociado al producto asociado a la factura.
     */
    @Override
    public int hashCode() {
        return Objects.hash(idInvoiceProductEmployee);
    }

    /**
     * Devuelve una representación en forma de cadena del nombre completo del empleado asociado al producto.
     *
     * @return el nombre completo del empleado asociado al producto (primer nombre y apellido).
     */
    @Override
    public String toString() {
        if (employeeDTO != null) {
            return employeeDTO.toString();
        }
        return "";
    }

}
