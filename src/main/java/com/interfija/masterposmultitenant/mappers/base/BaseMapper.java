package com.interfija.masterposmultitenant.mappers.base;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Clase base para proporcionar funcionalidades comunes de mapeo de datos.
 * <p>
 * Esta clase contiene métodos utilitarios que pueden ser reutilizados
 * por otros mapeadores en la aplicación.
 * </p>
 *
 * @author Steven Arzuza.
 */
public abstract class BaseMapper {

    /**
     * Devuelve {@code null} si la cadena proporcionada está vacía,
     * de lo contrario, devuelve la misma cadena.
     *
     * @param string la cadena a evaluar.
     * @return {@code null} si la cadena está vacía, de lo contrario, la misma cadena.
     */
    protected String defaultNullString(String string) {
        return (string == null || string.isEmpty()) ? null : string;
    }

    /**
     * Devuelve {@code BigDecimal.ZERO} si el valor proporcionado es {@code null},
     * de lo contrario, devuelve el mismo valor.
     *
     * @param bigDecimal el número a evaluar.
     * @return {@code BigDecimal.ZERO} si el número es null, de lo contrario, el mismo número.
     */
    protected BigDecimal defaultBigDecimal(BigDecimal bigDecimal) {
        return bigDecimal == null ? BigDecimal.ZERO : bigDecimal;
    }

    /**
     * Devuelve {@code null} si la fecha proporcionada es {@code null} o si representa un valor predeterminado,
     * de lo contrario, devuelve la misma fecha.
     *
     * @param dateTime el objeto {@code LocalDateTime} a evaluar.
     * @return {@code null} si la fecha es {@code null} o si es un valor predeterminado,
     * de lo contrario, la misma fecha.
     */
    protected LocalDateTime defaultNullDateTime(LocalDateTime dateTime) {
        return (dateTime == null || dateTime.equals(LocalDateTime.MIN)) ? null : dateTime;
    }

}
