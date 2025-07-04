package com.interfija.masterposmultitenant.dto.customer;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.interfija.masterposmultitenant.dto.branch.BranchDTO;
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

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Representa la información de un cliente.
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
public class CustomerDTO {

    /**
     * Identificador único del cliente.
     */
    private Long idCustomer;

    /**
     * Tipo de identificación del cliente (por ejemplo, cédula, pasaporte).
     */
    private IdentificationTypeDTO identificationTypeDTO;

    /**
     * Número de identificación del cliente.
     */
    private String identificationNumber;

    /**
     * Dirección del cliente.
     */
    private String address;

    /**
     * Municipio donde reside el cliente.
     */
    private MunicipalityDTO municipalityDTO;

    /**
     * Primer nombre del cliente.
     */
    private String names;

    /**
     * Apellido del cliente.
     */
    private String lastNames;

    /**
     * Correo electrónico del cliente.
     */
    private String mail;

    /**
     * Teléfono de contacto del cliente.
     */
    private String phone;

    /**
     * Indica si el cliente es visible o no en la plataforma.
     */
    private boolean visible;

    /**
     * Fecha en la que se registró la deuda del cliente.
     */
    private LocalDateTime debtDate;

    /**
     * Monto de deuda actual del cliente.
     */
    private BigDecimal amountDebt;

    /**
     * Monto máximo de deuda permitido para el cliente.
     */
    private BigDecimal maxDebt;

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
     * Sucursal asociada al cliente
     */
    private BranchDTO branchDTO;

    /**
     * Crea una nueva instancia de {@code CustomerDTO} con la información del cliente proporcionada.
     *
     * @param customerDTO Objeto con los datos del cliente.
     */
    public CustomerDTO(CustomerDTO customerDTO) {
        if (customerDTO == null) {
            return;
        }

        this.idCustomer = customerDTO.idCustomer;
        this.identificationTypeDTO = customerDTO.identificationTypeDTO;
        this.identificationNumber = customerDTO.identificationNumber;
        this.address = customerDTO.address;
        this.municipalityDTO = customerDTO.municipalityDTO;
        this.names = customerDTO.names;
        this.lastNames = customerDTO.lastNames;
        this.mail = customerDTO.mail;
        this.phone = customerDTO.phone;
        this.visible = customerDTO.visible;
        this.debtDate = customerDTO.debtDate;
        this.amountDebt = customerDTO.amountDebt;
        this.maxDebt = customerDTO.maxDebt;
        this.typeRegimeDTO = customerDTO.typeRegimeDTO;
        this.typeOrganizationDTO = customerDTO.typeOrganizationDTO;
        this.typeResponsibilityDTO = customerDTO.typeResponsibilityDTO;
        this.taxDTO = customerDTO.taxDTO;
        this.branchDTO = customerDTO.branchDTO;
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
        CustomerDTO that = (CustomerDTO) obj;
        return Objects.equals(idCustomer, that.idCustomer);
    }

    /**
     * Devuelve el código hash de este objeto.
     *
     * @return el código hash basado en el identificador del cliente.
     */
    @Override
    public int hashCode() {
        return Objects.hash(idCustomer);
    }

    /**
     * Devuelve una representación en forma de cadena del nombre completo del cliente.
     *
     * @return el nombre completo del cliente (primer nombre y apellido).
     */
    @Override
    public String toString() {
        return (names == null ? "" : names) + (lastNames == null ? "" : " " + lastNames);
    }

}
