package com.interfija.masterposmultitenant.session;

import com.interfija.masterposmultitenant.dto.cash.CashDTO;
import com.interfija.masterposmultitenant.dto.category.CategoryDTO;
import com.interfija.masterposmultitenant.dto.customer.CustomerDTO;
import com.interfija.masterposmultitenant.dto.employee.EmployeeDTO;
import com.interfija.masterposmultitenant.dto.invoice.InvoiceDTO;
import com.interfija.masterposmultitenant.dto.invoice.InvoiceProductDTO;
import com.interfija.masterposmultitenant.dto.branch.BranchDTO;
import com.interfija.masterposmultitenant.dto.payment.PaymentDTO;
import com.interfija.masterposmultitenant.dto.product.ProductDTO;
import com.interfija.masterposmultitenant.dto.product.ProductBranchPriceDTO;
import com.interfija.masterposmultitenant.dto.session.SessionDTO;
import com.interfija.masterposmultitenant.dto.supplier.SupplierDTO;

/**
 * Enum que representa los diferentes atributos de sesión utilizados en la aplicación.
 *
 * @author Steven Arzuza.
 */
public enum AttributeSession {

    /**
     * Nombre del atributo para la caja.
     */
    CASH_NAME(CashDTO.class.getSimpleName()),

    /**
     * Nombre del atributo para la factura.
     */
    INVOICE_NAME(InvoiceDTO.class.getSimpleName()),

    /**
     * Nombre del atributo para la línea de una factura.
     */
    INVOICE_LINE_NAME(InvoiceProductDTO.class.getSimpleName()),

    /**
     * Nombre del atributo para el pago.
     */
    PAYMENT_NAME(PaymentDTO.class.getSimpleName()),

    /**
     * Nombre del atributo para el producto.
     */
    PRODUCT_NAME(ProductDTO.class.getSimpleName()),

    /**
     * Nombre del atributo para el precio del producto.
     */
    PRODUCT_PRICE_NAME(ProductBranchPriceDTO.class.getSimpleName()),

    /**
     * Nombre del atributo para el usuario.
     */
    CUSTOMER_NAME(CustomerDTO.class.getSimpleName()),

    /**
     * Nombre del atributo para el empleado.
     */
    EMPLOYEE_NAME("Employee"),

    /**
     * Nombre del atributo para el cajero.
     */
    CASHIER_NAME("Cashier"),

    /**
     * Nombre del atributo para el domiciliario.
     */
    DOMICILIARY_NAME("Domiciliary"),

    /**
     * Nombre del atributo para el local.
     */
    BRANCH_NAME(BranchDTO.class.getSimpleName()),

    /**
     * Nombre del atributo para la categoría.
     */
    CATEGORY_NAME(CategoryDTO.class.getSimpleName()),

    /**
     * Nombre del atributo para el proveedor.
     */
    SUPPLIER_NAME(SupplierDTO.class.getSimpleName()),

    /**
     * Nombre del atributo para la sesión de usuario.
     */
    EMPLOYEE_SESSION(EmployeeDTO.class.getSimpleName()),

    /**
     * Nombre del atributo para los datos de la sesión.
     */
    SESSION(SessionDTO.class.getSimpleName()),

    /**
     * Nombre del atributo para la alerta de confirmación.
     */
    CONFIRMATION_ALERT("confirmationAlert");

    /**
     * Nombre del atributo asociado a la constante.
     */
    final String nameAttribute;

    /**
     * Constructor privado reservado para la clase de tipo enum.
     * Se utiliza para asociar un nombre al atributo de cada constante en el enum.
     *
     * @param nameAttribute el nombre del atributo.
     */
    AttributeSession(String nameAttribute) {
        this.nameAttribute = nameAttribute;
    }

    /**
     * Obtiene el nombre del atributo asociado a la constante.
     *
     * @return Nombre del atributo como cadena de texto.
     */
    public String getAttribute() {
        return nameAttribute;
    }

}
