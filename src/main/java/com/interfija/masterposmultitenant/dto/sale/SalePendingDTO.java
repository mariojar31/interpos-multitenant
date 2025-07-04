package com.interfija.masterposmultitenant.dto.sale;

import com.interfija.masterposmultitenant.dto.branch.BranchDTO;
import com.interfija.masterposmultitenant.dto.customer.CustomerDTO;
import com.interfija.masterposmultitenant.dto.employee.EmployeeDTO;
import com.interfija.masterposmultitenant.dto.floor.TableDTO;
import com.interfija.masterposmultitenant.dto.invoice.InvoiceCustomerDTO;
import com.interfija.masterposmultitenant.dto.invoice.InvoiceDTO;
import com.interfija.masterposmultitenant.dto.invoice.InvoiceEmployeeDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Representa la venta pendiente.
 *
 * @author Steven Arzuza.
 */
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SalePendingDTO {

    /**
     * Identificador único de la venta pendiente.
     */
    private String idSalePending;

    /**
     * Fecha y hora de la venta.
     */
    private LocalDateTime date;

    /**
     * Nombre del empleado de la venta.
     */
    private String employeeName;

    /**
     * Nombre del cliente de la venta.
     */
    private String customerName;

    /**
     * Teléfono de contacto del cliente de la venta.
     */
    private String customerPhone;

    /**
     * Dirección del cliente de la venta.
     */
    private String customerAddress;

    /**
     * Nombre del domiciliario de la venta.
     */
    private String domiciliaryName;

    /**
     * Información de la venta en formato JSON.
     */
    private String invoice;

    /**
     * Total de la venta.
     */
    private BigDecimal total;

    /**
     * Indica si la venta es por domicilio.
     */
    private boolean delivery;

    /**
     * Mesa asociada a la venta.
     */
    private TableDTO tableDTO;

    /**
     * Sucursal asociada a la venta.
     */
    private BranchDTO branchDTO;

    /**
     * Constructor que inicializa una venta pendiente a partir de un objeto {@link InvoiceDTO}.
     *
     * @param invoiceDTO Objeto {@link InvoiceDTO} que contiene los datos relacionados con la venta.
     *                   Si el parámetro es {@code null}, no se realiza ninguna acción.
     */
    public SalePendingDTO(InvoiceDTO invoiceDTO) {
        if (invoiceDTO == null) {
            return;
        }

        this.date = invoiceDTO.getDate();
        this.total = invoiceDTO.getTotal();
        setEmployee(invoiceDTO.getEmployee().map(InvoiceEmployeeDTO::getEmployeeDTO).orElse(null));
        setCustomer(invoiceDTO.getCustomer().map(InvoiceCustomerDTO::getCustomerDTO).orElse(null));
        setDomiciliary(invoiceDTO.getDomiciliary().isEmpty() ? null : invoiceDTO.getDomiciliary().get().getEmployeeDTO());
        setTable(invoiceDTO.getTableDTO());
    }

    /**
     * Establece el empleado responsable de la orden.
     *
     * @param employeeDTO Objeto {@link EmployeeDTO} que representa al empleado.
     *                    Si es {@code null}, no se realiza ninguna acción.
     */
    public void setEmployee(EmployeeDTO employeeDTO) {
        if (employeeDTO == null) {
            return;
        }

        this.employeeName = employeeDTO.toString();
    }

    /**
     * Establece los datos del cliente asociados a la orden.
     *
     * @param customerDTO Objeto {@link CustomerDTO} que contiene los datos del cliente.
     *                    Si es {@code null}, no se realiza ninguna acción.
     */
    public void setCustomer(CustomerDTO customerDTO) {
        if (customerDTO == null) {
            return;
        }

        this.customerName = customerDTO.toString();
        this.customerPhone = customerDTO.getPhone();
        this.customerAddress = customerDTO.getAddress();
    }

    /**
     * Establece el domiciliario asignado para la entrega de la orden.
     *
     * @param employeeDTO Objeto {@link EmployeeDTO} que representa al domiciliario.
     *                    Si es {@code null}, no se realiza ninguna acción.
     */
    public void setDomiciliary(EmployeeDTO employeeDTO) {
        if (employeeDTO == null) {
            return;
        }

        this.domiciliaryName = employeeDTO.toString();
    }

    /**
     * Establece la mesa asociada a la orden.
     *
     * @param tableDTO Objeto {@link TableDTO} que contiene la información de la mesa.
     *                 Si es {@code null}, no se realiza ninguna acción.
     */
    public void setTable(TableDTO tableDTO) {
        if (tableDTO == null) {
            return;
        }

        this.tableDTO = tableDTO;
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
        SalePendingDTO that = (SalePendingDTO) obj;
        return Objects.equals(idSalePending, that.idSalePending);
    }

    /**
     * Devuelve el código hash de este objeto.
     *
     * @return el código hash basado en el identificador de la venta pendiente.
     */
    @Override
    public int hashCode() {
        return Objects.hash(idSalePending);
    }

}
