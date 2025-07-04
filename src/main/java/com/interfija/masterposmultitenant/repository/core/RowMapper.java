package com.interfija.masterposmultitenant.repository.core;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Interfaz que define un contrato para mapear una fila de un {@link ResultSet} a un objeto de tipo {@code T}.
 * Esta interfaz se utiliza comúnmente en frameworks de acceso a datos para convertir los resultados de una consulta SQL
 * en objetos Java correspondientes.
 *
 * @param <T> El tipo de objeto que será mapeado desde una fila del {@link ResultSet}.
 *
 * @author Steven Arzuza.
 */
public interface RowMapper<T> {

    /**
     * Mapea una fila del {@link ResultSet} a un objeto de tipo {@code T}.
     *
     * @param resultSet El {@link ResultSet} que contiene los resultados de la consulta SQL.
     * @return El objeto mapeado de tipo {@code T}.
     * @throws SQLException Sí ocurre un error al acceder a los datos del {@link ResultSet}.
     */
    T mapRow(ResultSet resultSet) throws SQLException;

    /**
     * Mapea una fila del {@link ResultSet} a un objeto de tipo {@code T} con los datos esenciales.
     * Este método solo extrae la información básica necesaria para la visualización rápida.
     *
     * @param resultSet El {@link ResultSet} que contiene los resultados de la consulta SQL.
     * @return El objeto mapeado de tipo {@code T} con los datos mínimos requeridos.
     * @throws SQLException Sí ocurre un error al acceder a los datos del {@link ResultSet}.
     */
    T mapSimpleRow(ResultSet resultSet) throws SQLException;

    /**
     * Mapea una fila del {@link ResultSet} a un objeto de tipo {@code T} con toda la información disponible.
     * Este método extrae todos los datos relevantes del objeto desde la base de datos.
     *
     * @param resultSet El {@link ResultSet} que contiene los resultados de la consulta SQL.
     * @return El objeto mapeado de tipo {@code T} con toda su información detallada.
     * @throws SQLException Sí ocurre un error al acceder a los datos del {@link ResultSet}.
     */
    T mapCompleteRow(ResultSet resultSet) throws SQLException;

}
