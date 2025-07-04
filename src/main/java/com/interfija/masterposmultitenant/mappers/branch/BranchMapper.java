package com.interfija.masterposmultitenant.mappers.branch;

import com.interfija.masterposmultitenant.dto.branch.BranchDTO;
import com.interfija.masterposmultitenant.dto.company.CompanyDTO;
import com.interfija.masterposmultitenant.entities.tenant.branch.BranchEntity;
import com.interfija.masterposmultitenant.entities.tenant.company.CompanyEntity;
import com.interfija.masterposmultitenant.mappers.base.BaseMapper;
import com.interfija.masterposmultitenant.mappers.base.GenericMapper;
import com.interfija.masterposmultitenant.mappers.other.MunicipalityMapper;
import com.interfija.masterposmultitenant.mappers.other.TypeOrganizationMapper;
import com.interfija.masterposmultitenant.mappers.other.TypeRegimeMapper;
import com.interfija.masterposmultitenant.mappers.other.TypeResponsibilityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BranchMapper extends BaseMapper implements GenericMapper<BranchEntity, BranchDTO> {

    private final MunicipalityMapper municipalityMapper;
    private final TypeRegimeMapper typeRegimeMapper;
    private final TypeOrganizationMapper typeOrganizationMapper;
    private final TypeResponsibilityMapper typeResponsibilityMapper;
    private final BranchConfigurationMapper branchConfigurationMapper;

    @Autowired
    public BranchMapper(MunicipalityMapper municipalityMapper, TypeRegimeMapper typeRegimeMapper,
                        TypeOrganizationMapper typeOrganizationMapper, TypeResponsibilityMapper typeResponsibilityMapper,
                        BranchConfigurationMapper branchConfigurationMapper) {
        this.municipalityMapper = municipalityMapper;
        this.typeRegimeMapper = typeRegimeMapper;
        this.typeOrganizationMapper = typeOrganizationMapper;
        this.typeResponsibilityMapper = typeResponsibilityMapper;
        this.branchConfigurationMapper = branchConfigurationMapper;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BranchDTO toDto(BranchEntity entity) {
        if (entity == null) {
            return null;
        }

        CompanyDTO companyDTO = CompanyDTO.builder()
                .idCompany(entity.getCompanyEntity().getIdCompany())
                .name(entity.getCompanyEntity().getName())
                .build();

        return BranchDTO.builder()
                .idBranch(entity.getIdBranch())
                .name(entity.getName())
                .address(entity.getAddress())
                .municipalityDTO(municipalityMapper.toDto(entity.getMunicipalityEntity()))
                .mail(entity.getMail())
                .phone(entity.getPhone())
                .image(entity.getImage())
                .typeRegimeDTO(typeRegimeMapper.toDto(entity.getTypeRegimeEntity()))
                .typeOrganizationDTO(typeOrganizationMapper.toDto(entity.getTypeOrganizationEntity()))
                .typeResponsibilityDTO(typeResponsibilityMapper.toDto(entity.getTypeResponsibilityEntity()))
                .companyDTO(companyDTO)
                .visible(entity.getVisible())
                .branchConfigurationDTO(branchConfigurationMapper.toDto(entity.getBranchConfiguration()))
                .build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BranchEntity toEntity(BranchDTO dto) {
        if (dto == null) {
            return null;
        }

        CompanyEntity companyEntity = CompanyEntity.builder()
                .idCompany(dto.getCompanyDTO().getIdCompany())
                .build();

        BranchEntity branchEntity = BranchEntity.builder()
                .idBranch(dto.getIdBranch())
                .name(dto.getName())
                .address(defaultNullString(dto.getAddress()))
                .municipalityEntity(municipalityMapper.toEntity(dto.getMunicipalityDTO()))
                .mail(defaultNullString(dto.getMail()))
                .phone(defaultNullString(dto.getPhone()))
                .image(dto.getImage())
                .typeRegimeEntity(typeRegimeMapper.toEntity(dto.getTypeRegimeDTO()))
                .typeOrganizationEntity(typeOrganizationMapper.toEntity(dto.getTypeOrganizationDTO()))
                .typeResponsibilityEntity(typeResponsibilityMapper.toEntity(dto.getTypeResponsibilityDTO()))
                .companyEntity(companyEntity)
                .visible(dto.isVisible())
                .build();

        branchEntity.setBranchConfiguration(branchConfigurationMapper.toEntity(dto.getBranchConfigurationDTO(), branchEntity));

        return branchEntity;
    }

}
