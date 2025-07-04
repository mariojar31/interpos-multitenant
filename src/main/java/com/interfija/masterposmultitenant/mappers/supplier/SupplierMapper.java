package com.interfija.masterposmultitenant.mappers.supplier;

import com.interfija.masterposmultitenant.dto.branch.BranchDTO;
import com.interfija.masterposmultitenant.dto.company.CompanyDTO;
import com.interfija.masterposmultitenant.dto.supplier.SupplierDTO;
import com.interfija.masterposmultitenant.entities.tenant.branch.BranchEntity;
import com.interfija.masterposmultitenant.entities.tenant.supplier.SupplierEntity;
import com.interfija.masterposmultitenant.mappers.base.BaseMapper;
import com.interfija.masterposmultitenant.mappers.base.GenericMapper;
import com.interfija.masterposmultitenant.mappers.other.IdentificationTypeMapper;
import com.interfija.masterposmultitenant.mappers.other.MunicipalityMapper;
import com.interfija.masterposmultitenant.mappers.other.TaxMapper;
import com.interfija.masterposmultitenant.mappers.other.TypeOrganizationMapper;
import com.interfija.masterposmultitenant.mappers.other.TypeRegimeMapper;
import com.interfija.masterposmultitenant.mappers.other.TypeResponsibilityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class SupplierMapper extends BaseMapper implements GenericMapper<SupplierEntity, SupplierDTO> {

    private final IdentificationTypeMapper identificationTypeMapper;
    private final MunicipalityMapper municipalityMapper;
    private final TypeRegimeMapper typeRegimeMapper;
    private final TypeOrganizationMapper typeOrganizationMapper;
    private final TypeResponsibilityMapper typeResponsibilityMapper;
    private final TaxMapper taxMapper;

    @Autowired
    public SupplierMapper(IdentificationTypeMapper identificationTypeMapper, MunicipalityMapper municipalityMapper,
                          TypeRegimeMapper typeRegimeMapper, TypeOrganizationMapper typeOrganizationMapper,
                          TypeResponsibilityMapper typeResponsibilityMapper, TaxMapper taxMapper) {
        this.identificationTypeMapper = identificationTypeMapper;
        this.municipalityMapper = municipalityMapper;
        this.typeRegimeMapper = typeRegimeMapper;
        this.typeOrganizationMapper = typeOrganizationMapper;
        this.typeResponsibilityMapper = typeResponsibilityMapper;
        this.taxMapper = taxMapper;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SupplierDTO toDto(SupplierEntity entity) {
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

        return SupplierDTO.builder()
                .idSupplier(entity.getIdSupplier())
                .identificationNumber(entity.getIdentificationNumber())
                .names(entity.getNames())
                .lastNames(entity.getLastNames())
                //.creditLimit(entity.getCreditLimit())
                .address(entity.getAddress())
                .mail(entity.getMail())
                .phone(entity.getPhone())
                .visible(entity.getVisible())
                .identificationTypeDTO(identificationTypeMapper.toDto(entity.getIdentificationTypeEntity()))
                .municipalityDTO(municipalityMapper.toDto(entity.getMunicipalityEntity()))
                .typeRegimeDTO(typeRegimeMapper.toDto(entity.getTypeRegimeEntity()))
                .typeOrganizationDTO(typeOrganizationMapper.toDto(entity.getTypeOrganizationEntity()))
                .typeResponsibilityDTO(typeResponsibilityMapper.toDto(entity.getTypeResponsibilityEntity()))
                .taxDTO(taxMapper.toDto(entity.getTaxEntity()))
                .branchDTO(branchDTO)
                .build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SupplierEntity toEntity(SupplierDTO dto) {
        if (dto == null) {
            return null;
        }

        BranchEntity branchEntity = BranchEntity.builder()
                .idBranch(dto.getBranchDTO().getIdBranch())
                .build();

        return SupplierEntity.builder()
                .idSupplier(dto.getIdSupplier())
                .identificationNumber(dto.getIdentificationNumber())
                .names(dto.getNames())
                .lastNames(defaultNullString(defaultNullString(dto.getLastNames())))
                .creditLimit(defaultBigDecimal(BigDecimal.valueOf(0.00)))//.creditLimit(defaultBigDecimal(dto.getCreditLimit()))
                .address(defaultNullString(defaultNullString(dto.getAddress())))
                .mail(defaultNullString(defaultNullString(dto.getMail())))
                .phone(defaultNullString(defaultNullString(dto.getPhone())))
                .visible(dto.isVisible())
                .identificationTypeEntity(identificationTypeMapper.toEntity(dto.getIdentificationTypeDTO()))
                .municipalityEntity(municipalityMapper.toEntity(dto.getMunicipalityDTO()))
                .typeRegimeEntity(typeRegimeMapper.toEntity(dto.getTypeRegimeDTO()))
                .typeOrganizationEntity(typeOrganizationMapper.toEntity(dto.getTypeOrganizationDTO()))
                .typeResponsibilityEntity(typeResponsibilityMapper.toEntity(dto.getTypeResponsibilityDTO()))
                .taxEntity(taxMapper.toEntity(dto.getTaxDTO()))
                .branchEntity(branchEntity)
                .build();
    }

}
