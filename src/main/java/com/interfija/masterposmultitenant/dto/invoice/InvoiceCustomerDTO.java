package com.interfija.masterposmultitenant.dto.invoice;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.interfija.masterposmultitenant.dto.customer.CustomerDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

/**
 * Representa el cliente asociado a la factura.
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
public class InvoiceCustomerDTO {

    /**
     * Identificador único del cliente asociado a la factura
     */
    private Long idInvoiceCustomer;

    /**
     * Información del cliente asociado a la factura.
     */
    private CustomerDTO customerDTO;

    /**
     * Identificador único asociado a la factura
     */
    private Long invoiceId;

    /**
     * Crea una nueva instancia de {@code InvoiceCustomerDto} con la información del cliente proporcionada.
     *
     * @param customerDTO El cliente asociado a la factura.
     */
    public InvoiceCustomerDTO(CustomerDTO customerDTO) {
        this.customerDTO = customerDTO;
    }

    /**
     * Crea una nueva instancia de {@code InvoiceCustomerDTO} con la información del cliente de la factura proporcionada.
     *
     * @param invoiceCustomerDTO Objeto con los datos del cliente de la factura.
     */
    public InvoiceCustomerDTO(InvoiceCustomerDTO invoiceCustomerDTO) {
        this.idInvoiceCustomer = invoiceCustomerDTO.idInvoiceCustomer;
        this.customerDTO = new CustomerDTO(invoiceCustomerDTO.customerDTO);
        this.invoiceId = invoiceCustomerDTO.invoiceId;
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
        InvoiceCustomerDTO that = (InvoiceCustomerDTO) obj;
        return Objects.equals(idInvoiceCustomer, that.idInvoiceCustomer);
    }

    /**
     * Devuelve el código hash de este objeto.
     *
     * @return el código hash basado en el identificador del cliente asociado a la factura.
     */
    @Override
    public int hashCode() {
        return Objects.hash(idInvoiceCustomer);
    }

    /**
     * Devuelve una representación en forma de cadena del nombre completo del cliente.
     *
     * @return el nombre completo del cliente (primer nombre y apellido).
     */
    @Override
    public String toString() {
        if (customerDTO != null) {
            return customerDTO.toString();
        }
        return "";
    }
}
