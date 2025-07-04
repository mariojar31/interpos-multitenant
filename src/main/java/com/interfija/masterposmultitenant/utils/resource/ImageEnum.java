package com.interfija.masterposmultitenant.utils.resource;

/**
 * Enum que representa las imágenes asociadas a diferentes funcionalidades o entidades en la aplicación.
 * Cada constante en este enum corresponde a una imagen específica utilizada en la interfaz de usuario.
 *
 * @author Steven Arzuza.
 */
public enum ImageEnum {

    /**
     * Icono asociado a los productos.
     */
    PRODUCTS("products.png"),

    /**
     * Icono asociado a las categorías.
     */
    CATEGORIES("category.png"),

    /**
     * Icono asociado a los informes o reportes.
     */
    REPORTS("reports.png"),

    /**
     * Icono del deslizador del menú en modo oscuro.
     */
    MENU_SLIDER_DARK("menuSliderDark.png"),

    /**
     * Icono del deslizador del menú en modo claro.
     */
    MENU_SLIDER_LIGHT("menuSliderWhite.png"),

    /**
     * Icono para la acción de editar.
     */
    EDIT("edit.png"),

    /**
     * Icono para la acción de eliminar.
     */
    DELETE("delete.png"),

    /**
     * Icono para la acción de información.
     */
    INFO("info.png"),

    SALE("sale.png"),

    EDIT_SALE("editSale.png"),

    ELECTRONIC_DOCUMENT("electronicDocument.png"),

    REPORT_SALE("reportSales.png"),

    CASH("cash.png"),

    TOOLS("tools.png"),

    CUSTOMER("customer.png"),

    EMPLOYEE("employee.png"),

    MAINTENANCE("maintenance.png"),

    PRINTER("preTicket.png"),

    CLOSE_SESSION("closeSession.png"),

    CONFIGURATION("setting.png"),

    MOVE_TABLE("moveTable.png");

    /**
     * Nombre del archivo
     */
    final String imageEnum;

    /**
     * Constructor privado reservado para la clase de tipo enum
     */
    ImageEnum(String fileEnum) {
        this.imageEnum = fileEnum;
    }

    /**
     * Obtiene el nombre de la imagen asociada a la constante.
     *
     * @return Nombre de la imagen como cadena de texto.
     */
    public String getImage() {
        return imageEnum;
    }

}
