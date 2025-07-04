package com.interfija.masterposmultitenant.master;

import lombok.Getter;

/**
 * Enumeración que representa las claves utilizadas para identificar y gestionar
 * diferentes tipos de datos maestros dentro del sistema y para su correcto funcionamiento.
 *
 * @author Steven Arzuza.
 */
@Getter
public enum MasterDataEnum {

    /**
     * Prefijo para datos de tipo corto.
     */
    SHORT("short_"),

    /**
     * Prefijo para datos de tipo largo.
     */
    LONG("long_"),

    /**
     * Clave utilizada para gestionar la lista de categorías.
     */
    CATEGORIES_LIST("categoriesList"),

    /**
     * Clave utilizada para gestionar la lista de tipos de impuestos.
     */
    TYPE_TAXES_LIST("typeTaxesList"),

    /**
     * Clave utilizada para gestionar la lista de impuestos.
     */
    TAXES_LIST("taxesList"),

    /**
     * Clave utilizada para gestionar la lista de unidades de medida.
     */
    UNITS_LIST("unitsList"),

    /**
     * Clave utilizada para gestionar la lista de ubicaciones.
     */
    LOCATIONS_LIST("locationsList"),

    /**
     * Clave utilizada para gestionar la lista de productos.
     */
    PRODUCTS_LIST("productsList"),

    /**
     * Clave utilizada para gestionar la lista de proveedores.
     */
    SUPPLIERS_LIST("suppliersList"),

    /**
     * Clave utilizada para gestionar la lista de tipos de código de barras.
     */
    BARCODE_TYPES_LIST("barcodeTypesList"),

    /**
     * Clave utilizada para gestionar la lista de tipos de regimen.
     */
    TYPE_REGIMES_LIST("typeRegimesList"),

    /**
     * Clave utilizada para gestionar la lista de tipos de organización.
     */
    TYPE_ORGANIZATIONS_LIST("typeOrganizationsList"),

    /**
     * Clave utilizada para gestionar la lista de tipos de responsabilidades.
     */
    TYPE_RESPONSIBILITIES_LIST("typeResponsibilitiesList"),

    /**
     * Clave utilizada para gestionar la lista de tipos de identificaciones.
     */
    IDENTIFICATION_TYPES_LIST("identificationTypesList"),

    /**
     * Clave utilizada para gestionar la lista de los departamentos.
     */
    DEPARTMENTS_LIST("departmentsList"),

    /**
     * Clave utilizada para gestionar la lista de los municipios.
     */
    MUNICIPALITIES_LIST("municipalitiesList"),

    /**
     * Clave utilizada para gestionar la lista de los tipos de pagos.
     */
    TYPE_PAYMENTS_LIST("typePaymentList"),

    /**
     * Clave utilizada para gestionar la lista de las formas de pagos.
     */
    PAYMENTS_FORM_LIST("paymentFormList");

    /**
     * Nombre o clave identificadora del dato maestro.
     */
    final String master;

    /**
     * Constructor privado reservado para la clase de tipo enum.
     *
     * @param master Nombre o clave del dato maestro.
     */
    MasterDataEnum(String master) {
        this.master = master;
    }

}
