package com.interfija.masterposmultitenant.dto.company;

import com.interfija.masterposmultitenant.dto.other.IdentificationTypeDTO;
import com.interfija.masterposmultitenant.dto.other.MunicipalityDTO;
import com.interfija.masterposmultitenant.dto.other.TaxDTO;
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
 * Representa la información de un empresa.
 *
 * @author Steven Arzuza.
 */
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CompanyDTO {

    /**
     * Identificador único de la empresa.
     */
    private Long idCompany;

    /**
     * Tipo de identificación de la empresa (por ejemplo, cédula, pasaporte).
     */
    private IdentificationTypeDTO identificationTypeDTO;

    /**
     * Número de identificación de la empresa.
     */
    private String identificationNumber;

    /**
     * Dirección de la empresa.
     */
    private String address;

    /**
     * Municipio donde reside la empresa.
     */
    private MunicipalityDTO municipalityDTO;

    /**
     * Primer nombre de la empresa.
     */
    private String name;

    /**
     * Correo electrónico de la empresa.
     */
    private String mail;

    /**
     * Teléfono de contacto de la empresa.
     */
    private String phone;

    /**
     * Icono representativo de la empresa.
     */
    private byte[] image;

    /**
     * Certificado de la firma digital de la empresa.
     */
    private byte[] certificate;

    /**
     * Contraseña de la firma digital de la empresa.
     */
    private String passwordCertificate;

    /**
     * Indica si el empresa es visible o no en la plataforma.
     */
    private boolean visible;

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
     * Responsabilidad de impuesto.
     */
    private TaxDTO taxDTO;

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
        CompanyDTO that = (CompanyDTO) obj;
        return Objects.equals(idCompany, that.idCompany);
    }

    /**
     * Devuelve el código hash de este objeto.
     *
     * @return el código hash basado en el identificador de la empresa.
     */
    @Override
    public int hashCode() {
        return Objects.hash(idCompany);
    }

    /**
     * Devuelve una representación en forma de cadena del nombre completo de la empresa.
     *
     * @return el nombre completo de la empresa (primer nombre y apellido).
     */
    @Override
    public String toString() {
        return name == null ? "" : name;
    }

}
