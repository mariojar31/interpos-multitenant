package com.interfija.masterposmultitenant.dto.branch;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.interfija.masterposmultitenant.dto.company.CompanyDTO;
import com.interfija.masterposmultitenant.dto.other.MunicipalityDTO;
import com.interfija.masterposmultitenant.dto.other.TypeOrganizationDTO;
import com.interfija.masterposmultitenant.dto.other.TypeRegimeDTO;
import com.interfija.masterposmultitenant.dto.other.TypeResponsibilityDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

/**
 * Representa una sucursal.
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
public class BranchDTO {

    /**
     * Identificador único de la sucursal.
     */
    private Long idBranch;

    /**
     * Municipio donde reside la sucursal.
     */
    private MunicipalityDTO municipalityDTO;

    /**
     * Nombre de la sucursal.
     */
    private String name;

    /**
     * Dirección de la sucursal.
     */
    private String address;

    /**
     * Correo electrónico de la sucursal.
     */
    private String mail;

    /**
     * Teléfono de contacto de la sucursal.
     */
    private String phone;

    /**
     * Icono representativo de la sucursal.
     */
    private byte[] image;

    /**
     * Tipo de regimen.
     */
    private TypeRegimeDTO typeRegimeDTO;

    /**
     * Tipo de organización.
     */
    private TypeOrganizationDTO typeOrganizationDTO;

    /**
     * Tipo de responsabilidad.
     */
    private TypeResponsibilityDTO typeResponsibilityDTO;

    /**
     * Indica si la sucursal es visible o no.
     */
    private boolean visible;

    /**
     * Configuración de la sucursal.
     */
    private BranchConfigurationDTO branchConfigurationDTO;

    /**
     * Empresa asociada a la sucursal
     */
    private CompanyDTO companyDTO;

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
        BranchDTO that = (BranchDTO) obj;
        return Objects.equals(idBranch, that.idBranch);
    }

    /**
     * Devuelve el código hash de este objeto.
     *
     * @return el código hash basado en el identificador de la sucursal.
     */
    @Override
    public int hashCode() {
        return Objects.hash(idBranch);
    }

    /**
     * Devuelve una representación en forma de cadena del nombre de la sucursal.
     *
     * @return el nombre de la sucursal.
     */
    @Override
    public String toString() {
        return name == null ? "" : name;
    }

}
