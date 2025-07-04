package com.interfija.masterposmultitenant.mappers.customer;

import com.interfija.masterposmultitenant.dto.branch.BranchDTO;
import com.interfija.masterposmultitenant.dto.company.CompanyDTO;
import com.interfija.masterposmultitenant.dto.customer.CustomerDTO;
import com.interfija.masterposmultitenant.entities.tenant.branch.BranchEntity;
import com.interfija.masterposmultitenant.entities.tenant.customer.CustomerEntity;
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

@Component
public class CustomerMapper extends BaseMapper implements GenericMapper<CustomerEntity, CustomerDTO> {

    private final IdentificationTypeMapper identificationTypeMapper;
    private final MunicipalityMapper municipalityMapper;
    private final TypeRegimeMapper typeRegimeMapper;
    private final TypeOrganizationMapper typeOrganizationMapper;
    private final TypeResponsibilityMapper typeResponsibilityMapper;
    private final TaxMapper taxMapper;

    @Autowired
    public CustomerMapper(IdentificationTypeMapper identificationTypeMapper, MunicipalityMapper municipalityMapper,
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
    public CustomerDTO toDto(CustomerEntity entity) {
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

        return CustomerDTO.builder()
                .idCustomer(entity.getIdCustomer())
                .identificationNumber(entity.getIdentificationNumber())
                .identificationTypeDTO(identificationTypeMapper.toDto(entity.getIdentificationTypeEntity()))
                .names(entity.getNames())
                .lastNames(entity.getLastNames())
                .address(entity.getAddress())
                .municipalityDTO(municipalityMapper.toDto(entity.getMunicipalityEntity()))
                .mail(entity.getMail())
                .phone(entity.getPhone())
                .debtDate(entity.getDebtDate())
                .amountDebt(entity.getAmountDebt())
                .maxDebt(entity.getMaxDebt())
                .typeRegimeDTO(typeRegimeMapper.toDto(entity.getTypeRegimeEntity()))
                .typeOrganizationDTO(typeOrganizationMapper.toDto(entity.getTypeOrganizationEntity()))
                .typeResponsibilityDTO(typeResponsibilityMapper.toDto(entity.getTypeResponsibilityEntity()))
                .taxDTO(taxMapper.toDto(entity.getTaxEntity()))
                .branchDTO(branchDTO)
                .visible(entity.getVisible())
                .build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CustomerEntity toEntity(CustomerDTO dto) {
        if (dto == null) {
            return null;
        }

        BranchEntity branchEntity = BranchEntity.builder()
                .idBranch(dto.getBranchDTO().getIdBranch())
                .build();

        return CustomerEntity.builder()
                .idCustomer(dto.getIdCustomer())
                .identificationNumber(dto.getIdentificationNumber())
                .identificationTypeEntity(identificationTypeMapper.toEntity(dto.getIdentificationTypeDTO()))
                .names(dto.getNames())
                .lastNames(defaultNullString(defaultNullString(dto.getLastNames())))
                .address(defaultNullString(defaultNullString(dto.getAddress())))
                .municipalityEntity(municipalityMapper.toEntity(dto.getMunicipalityDTO()))
                .mail(defaultNullString(defaultNullString(dto.getMail())))
                .phone(defaultNullString(defaultNullString(dto.getPhone())))
                .debtDate(dto.getDebtDate())
                .amountDebt(defaultBigDecimal(dto.getAmountDebt()))
                .maxDebt(defaultBigDecimal(dto.getMaxDebt()))
                .typeRegimeEntity(typeRegimeMapper.toEntity(dto.getTypeRegimeDTO()))
                .typeOrganizationEntity(typeOrganizationMapper.toEntity(dto.getTypeOrganizationDTO()))
                .typeResponsibilityEntity(typeResponsibilityMapper.toEntity(dto.getTypeResponsibilityDTO()))
                .taxEntity(taxMapper.toEntity(dto.getTaxDTO()))
                .branchEntity(branchEntity)
                .visible(dto.isVisible())
                .build();
    }

}
