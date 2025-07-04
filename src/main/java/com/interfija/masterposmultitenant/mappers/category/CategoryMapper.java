package com.interfija.masterposmultitenant.mappers.category;

import com.interfija.masterposmultitenant.dto.branch.BranchDTO;
import com.interfija.masterposmultitenant.dto.category.CategoryDTO;
import com.interfija.masterposmultitenant.dto.company.CompanyDTO;
import com.interfija.masterposmultitenant.entities.tenant.branch.BranchEntity;
import com.interfija.masterposmultitenant.entities.tenant.category.CategoryEntity;
import com.interfija.masterposmultitenant.mappers.base.GenericMapper;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper implements GenericMapper<CategoryEntity, CategoryDTO> {

    /**
     * {@inheritDoc}
     */
    @Override
    public CategoryDTO toDto(CategoryEntity entity) {
        if (entity == null) {
            return null;
        }

        CompanyDTO companyDTO = CompanyDTO.builder()
                .idCompany(entity.getBranchEntity().getCompanyEntity().getIdCompany())
                .name(entity.getBranchEntity().getCompanyEntity().getName())
                .build();

        BranchDTO branchDTO = BranchDTO.builder()
                .idBranch(entity.getBranchEntity().getIdBranch())
                .name(entity.getBranchEntity().getName())
                .companyDTO(companyDTO)
                .build();

        CategoryDTO parentDTO;
        if (entity.getParent() != null) {
            parentDTO = CategoryDTO.builder()
                    .idCategory(entity.getParent().getIdCategory())
                    .name(entity.getParent().getName())
                    .build();
        } else {
            parentDTO = null;
        }

        return CategoryDTO.builder()
                .idCategory(entity.getIdCategory())
                .name(entity.getName())
                .code(entity.getCode())
                .image(entity.getImage())
                .parentDTO(parentDTO)
                .visible(entity.getVisible())
                .branchDTO(branchDTO)
                .build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CategoryEntity toEntity(CategoryDTO dto) {
        if (dto == null) {
            return null;
        }

        BranchEntity branchEntity;
        if (dto.getBranchDTO() != null) {
            branchEntity = BranchEntity.builder()
                    .idBranch(dto.getBranchDTO().getIdBranch())
                    .build();
        } else {
            branchEntity = null;
        }

        CategoryEntity parent;
        if (dto.getParentDTO() != null) {
            parent = CategoryEntity.builder()
                    .idCategory(dto.getParentDTO().getIdCategory())
                    .build();
        } else {
            parent = null;
        }

        return CategoryEntity.builder()
                .idCategory(dto.getIdCategory())
                .name(dto.getName())
                .code(dto.getCode())
                .image(dto.getImage())
                .parent(parent)
                .visible(dto.isVisible())
                .branchEntity(branchEntity)
                .build();
    }

}
