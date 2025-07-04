package com.interfija.masterposmultitenant.dto.supplier;

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

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Representa un proveedor.
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
public class SupplierDTO {

    /**
     * Identificador único del proveedor.
     */
    private Long idSupplier;

    /**
     * Tipo de identificación del proveedor (por ejemplo, cédula, pasaporte).
     */
    private IdentificationTypeDTO identificationTypeDTO;

    /**
     * Número de identificación del proveedor.
     */
    private String identificationNumber;

    /**
     * Dirección del proveedor.
     */
    private String address;

    /**
     * Municipio donde reside el proveedor.
     */
    private MunicipalityDTO municipalityDTO;

    /**
     * Nombre del proveedor.
     */
    private String names;

    /**
     * Apellido del proveedor.
     */
    private String lastNames;

    /**
     * Correo electrónico del proveedor.
     */
    private String mail;

    /**
     * Teléfono de contacto del proveedor.
     */
    private String phone;

    /**
     * Indica si el proveedor es visible o no en la plataforma.
     */
    private boolean visible;

    /**
     * Fecha en la que se registró la deuda del proveedor.
     */
    private LocalDateTime debtDate;

    /**
     * Monto de deuda actual del proveedor.
     */
    private double amountDebt;

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
     * Cantidad de productos asociados al proveedor.
     */
    private long quantityProducts;

    /**
     * Sucursal asociada al proveedor
     */
    private BranchDTO branchDTO;

    /**
     * Compara este objeto con otro para determinar si son iguales.
     * La comparación se basa en el identificador del proveedor.
     *
     * @param obj el objeto a comparar.
     * @return {@code true} si ambos objetos tienen el mismo identificador de proveedor, {@code false} en caso contrario.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        SupplierDTO that = (SupplierDTO) obj;
        return Objects.equals(idSupplier, that.idSupplier);
    }

    /**
     * Devuelve el código hash de este objeto basado en el identificador del proveedor.
     *
     * @return el código hash del identificador de proveedor.
     */
    @Override
    public int hashCode() {
        return Objects.hash(idSupplier);
    }

    /**
     * Devuelve una representación en forma de cadena del nombre del proveedor.
     *
     * @return el nombre del proveedor.
     */
    @Override
    public String toString() {
        return (names == null ? "" : names) + (lastNames == null ? "" : " " + lastNames);
    }

}
