/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.interfija.masterposmultitenant.dto.other;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

/**
 * Representa una impresora.
 *
 * @author Steven Arzuza.
 */
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PrinterDTO {

    /**
     * Nombre de la impresora.
     */
    private String name;

    /**
     * Tipo de la impresora (por ejemplo, láser, inyección de tinta).
     */
    private String type;

    /**
     * Estado de la impresora (por ejemplo, en línea, fuera de línea).
     */
    private String status;

    /**
     * Tamaño de papel predeterminado de la impresora.
     */
    private String defaultPaperSize;

    /**
     * Resolución de impresión en puntos por pulgada (dpi).
     */
    private int dpi;

    /**
     * Indica si la impresora es a color.
     */
    private boolean isColor;

    /**
     * Indica si la impresora es dúplex (puede imprimir a ambos lados del papel).
     */
    private boolean isDuplex;

    /**
     * Número de páginas que la impresora puede imprimir por minuto.
     */
    private int pagesPerMinute;

    /**
     * Dirección IP de la impresora.
     */
    private String ipAddress;

    /**
     * Puerto de la impresora.
     */
    private int port;

    /**
     * Tipo de conexión de la impresora (por ejemplo, USB, red).
     */
    private String connectionType;

    /**
     * Lista de capacidades de la impresora (por ejemplo, impresión en color, impresión dúplex).
     */
    private List<String> capabilities;

    /**
     * Lenguaje de impresión que la impresora soporta.
     */
    private String printLanguage;

    /**
     * Localización o región de la impresora.
     */
    private String locale;

    /**
     * Fabricante de la impresora.
     */
    private String manufacturer;

    /**
     * Modelo de la impresora.
     */
    private String model;

    /**
     * Número de serie de la impresora.
     */
    private String serialNumber;

    /**
     * Determina si este objeto es igual a otro basado en el nombre de la impresora.
     *
     * @param obj el objeto a comparar.
     * @return {@code true} si ambos objetos tienen el mismo nombre de impresora, {@code false} en caso contrario.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        PrinterDTO that = (PrinterDTO) obj;
        return Objects.equals(name, that.name);
    }

    /**
     * Devuelve el código hash de este objeto basado en el nombre de la impresora.
     *
     * @return el código hash del nombre de la impresora.
     */
    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    /**
     * Devuelve una representación en forma de cadena del nombre de la impresora.
     *
     * @return el nombre de la impresora.
     */
    @Override
    public String toString() {
        return name;
    }

}
