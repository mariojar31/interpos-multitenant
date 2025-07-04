package com.interfija.masterposmultitenant.mappers.company;

import com.interfija.masterposmultitenant.dto.company.CompanyDTO;
import com.interfija.masterposmultitenant.entities.tenant.company.CompanyEntity;
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
public class CompanyMapper extends BaseMapper implements GenericMapper<CompanyEntity, CompanyDTO> {

    private final IdentificationTypeMapper identificationTypeMapper;
    private final MunicipalityMapper municipalityMapper;
    private final TypeRegimeMapper typeRegimeMapper;
    private final TypeOrganizationMapper typeOrganizationMapper;
    private final TypeResponsibilityMapper typeResponsibilityMapper;
    private final TaxMapper taxMapper;

    @Autowired
    public CompanyMapper(IdentificationTypeMapper identificationTypeMapper, MunicipalityMapper municipalityMapper,
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
    public CompanyDTO toDto(CompanyEntity entity) {
        if (entity == null) {
            return null;
        }

        return CompanyDTO.builder()
                .idCompany(entity.getIdCompany())
                .identificationNumber(entity.getIdentificationNumber())
                .identificationTypeDTO(identificationTypeMapper.toDto(entity.getIdentificationTypeEntity()))
                .name(entity.getName())
                .address(entity.getAddress())
                .municipalityDTO(municipalityMapper.toDto(entity.getMunicipalityEntity()))
                .mail(entity.getMail())
                .phone(entity.getPhone())
                .image(entity.getImage())
                .certificate(entity.getCertificate())
                .passwordCertificate(entity.getPasswordCertificate())
                .typeRegimeDTO(typeRegimeMapper.toDto(entity.getTypeRegimeEntity()))
                .typeOrganizationDTO(typeOrganizationMapper.toDto(entity.getTypeOrganizationEntity()))
                .typeResponsibilityDTO(typeResponsibilityMapper.toDto(entity.getTypeResponsibilityEntity()))
                .taxDTO(taxMapper.toDto(entity.getTaxEntity()))
                .visible(entity.getVisible())
                .build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CompanyEntity toEntity(CompanyDTO dto) {
        if (dto == null) {
            return null;
        }

        return CompanyEntity.builder()
                .idCompany(dto.getIdCompany())
                .identificationNumber(dto.getIdentificationNumber())
                .identificationTypeEntity(identificationTypeMapper.toEntity(dto.getIdentificationTypeDTO()))
                .name(dto.getName())
                .address(defaultNullString(dto.getAddress()))
                .municipalityEntity(municipalityMapper.toEntity(dto.getMunicipalityDTO()))
                .mail(defaultNullString(dto.getMail()))
                .phone(defaultNullString(dto.getPhone()))
                .image(dto.getImage())
                .certificate(dto.getCertificate())
                .passwordCertificate(dto.getPasswordCertificate())
                .typeRegimeEntity(typeRegimeMapper.toEntity(dto.getTypeRegimeDTO()))
                .typeOrganizationEntity(typeOrganizationMapper.toEntity(dto.getTypeOrganizationDTO()))
                .typeResponsibilityEntity(typeResponsibilityMapper.toEntity(dto.getTypeResponsibilityDTO()))
                .taxEntity(taxMapper.toEntity(dto.getTaxDTO()))
                .visible(dto.isVisible())
                .build();
    }

}
