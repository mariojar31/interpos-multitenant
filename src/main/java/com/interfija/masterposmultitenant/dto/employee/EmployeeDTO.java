package com.interfija.masterposmultitenant.dto.employee;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.interfija.masterposmultitenant.dto.branch.BranchDTO;
import com.interfija.masterposmultitenant.dto.other.IdentificationTypeDTO;
import com.interfija.masterposmultitenant.dto.other.MunicipalityDTO;
import com.interfija.masterposmultitenant.dto.role.RoleDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Representa un empleado.
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
public class EmployeeDTO {

    /**
     * Identificador único del empleado.
     */
    private Long idEmployee;

    /**
     * Tipo de identificación del empleado (por ejemplo, cédula, pasaporte).
     */
    private IdentificationTypeDTO identificationTypeDTO;

    /**
     * Número de identificación del empleado.
     */
    private String identificationNumber;

    /**
     * Dirección del empleado.
     */
    private String address;

    /**
     * Municipio donde reside el empleado.
     */
    private MunicipalityDTO municipalityDTO;

    /**
     * Nombre del empleado.
     */
    private String names;

    /**
     * Apellido del empleado.
     */
    private String lastNames;

    /**
     * Correo electrónico del empleado.
     */
    private String mail;

    /**
     * Teléfono de contacto del empleado.
     */
    private String phone;

    /**
     * Indica si el empleado es visible o no en la plataforma.
     */
    private boolean visible;

    /**
     * Contraseña del empleado.
     */
    private String password;

    /**
     * Rol del empleado, representado por un {@link RoleDTO}.
     */
    private RoleDTO roleDTO;

    /**
     * Fecha de creación de la cuenta del empleado.
     */
    private LocalDateTime dateCreated;

    /**
     * Fecha de la última actualización de la cuenta del empleado.
     */
    private LocalDateTime dateUpdated;

    /**
     * Identificador único de la sucursal asociada al empleado
     */
    private BranchDTO branchDTO;

    /**
     * Compara este objeto con otro para determinar si son iguales.
     * La comparación se basa en el identificador del empleado.
     *
     * @param obj el objeto a comparar.
     * @return {@code true} si ambos objetos tienen el mismo identificador de empleado, {@code false} en caso contrario.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        EmployeeDTO that = (EmployeeDTO) obj;
        return Objects.equals(idEmployee, that.idEmployee);
    }

    /**
     * Devuelve el código hash de este objeto basado en el identificador del empleado.
     *
     * @return el código hash del identificador del empleado.
     */
    @Override
    public int hashCode() {
        return Objects.hash(idEmployee);
    }

    /**
     * Devuelve una representación en forma de cadena del nombre completo del empleado,
     * que incluye el primer nombre y el apellido.
     *
     * @return el nombre completo del empleado.
     */
    @Override
    public String toString() {
        return (names == null ? "" : names) + (lastNames == null ? "" : " " + lastNames);
    }

}
