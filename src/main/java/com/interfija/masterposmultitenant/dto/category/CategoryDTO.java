package com.interfija.masterposmultitenant.dto.category;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.interfija.masterposmultitenant.dto.branch.BranchDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

/**
 * Representa la información de una categoría.
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
public class CategoryDTO {

    /**
     * Identificador único de la categoría.
     */
    private Long idCategory;

    /**
     * Nombre de la categoría.
     */
    private String name;

    /**
     * Código de la categoría.
     */
    private String code;

    /**
     * Icono representativo del categoría.
     */
    private byte[] image;

    /**
     * Cantidad de productos de la categoría.
     */
    private long quantityProducts;

    /**
     * Cantidad de categorias hijas de la categoría.
     */
    private long quantityChildren;

    /**
     * Indica si la categoría es visible o no.
     */
    private boolean visible;

    /**
     * Categoría principal (si aplica).
     */
    private CategoryDTO parentDTO;

    /**
     * Sucursal asociada a la categoría
     */
    private BranchDTO branchDTO;

    public CategoryDTO(CategoryDTO categoryDTO) {
        this.idCategory = categoryDTO.idCategory;
        this.name = categoryDTO.name;
        this.code = categoryDTO.code;
        this.quantityProducts = categoryDTO.quantityProducts;
        this.visible = categoryDTO.visible;
        this.branchDTO = categoryDTO.branchDTO;
        this.parentDTO = categoryDTO.parentDTO != null ? new CategoryDTO(categoryDTO.parentDTO) : null;
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
        CategoryDTO that = (CategoryDTO) obj;
        return Objects.equals(idCategory, that.idCategory);
    }

    /**
     * Devuelve el código hash de este objeto.
     *
     * @return el código hash basado en el identificador de la categoría.
     */
    @Override
    public int hashCode() {
        return Objects.hash(idCategory);
    }

    /**
     * Devuelve una representación en forma de cadena del nombre de la categoría.
     *
     * @return el nombre de la categoría.
     */
    @Override
    public String toString() {
        return name == null ? "" : name;
    }

}
