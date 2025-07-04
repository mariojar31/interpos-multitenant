package com.interfija.masterposmultitenant.mappers.invoice;

import com.interfija.masterposmultitenant.dto.customer.CustomerDTO;
import com.interfija.masterposmultitenant.dto.invoice.InvoiceCustomerDTO;
import com.interfija.masterposmultitenant.entities.tenant.invoice.InvoiceCustomerEntity;
import com.interfija.masterposmultitenant.entities.tenant.invoice.InvoiceEntity;
import com.interfija.masterposmultitenant.mappers.base.BaseMapper;
import com.interfija.masterposmultitenant.mappers.base.GenericMapper;
import com.interfija.masterposmultitenant.mappers.other.IdentificationTypeMapper;
import com.interfija.masterposmultitenant.mappers.other.MunicipalityMapper;
import com.interfija.masterposmultitenant.mappers.other.TypeOrganizationMapper;
import com.interfija.masterposmultitenant.mappers.other.TypeRegimeMapper;
import com.interfija.masterposmultitenant.mappers.other.TypeResponsibilityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InvoiceCustomerMapper extends BaseMapper implements GenericMapper<InvoiceCustomerEntity, InvoiceCustomerDTO> {

    private final IdentificationTypeMapper identificationTypeMapper;
    private final MunicipalityMapper municipalityMapper;
    private final TypeRegimeMapper typeRegimeMapper;
    private final TypeOrganizationMapper typeOrganizationMapper;
    private final TypeResponsibilityMapper typeResponsibilityMapper;

    @Autowired
    public InvoiceCustomerMapper(IdentificationTypeMapper identificationTypeMapper, MunicipalityMapper municipalityMapper,
                                 TypeRegimeMapper typeRegimeMapper, TypeOrganizationMapper typeOrganizationMapper,
                                 TypeResponsibilityMapper typeResponsibilityMapper) {
        this.identificationTypeMapper = identificationTypeMapper;
        this.municipalityMapper = municipalityMapper;
        this.typeRegimeMapper = typeRegimeMapper;
        this.typeOrganizationMapper = typeOrganizationMapper;
        this.typeResponsibilityMapper = typeResponsibilityMapper;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public InvoiceCustomerDTO toDto(InvoiceCustomerEntity entity) {
        if (entity == null) {
            return null;
        }

        CustomerDTO customerDTO = CustomerDTO.builder()
                .identificationNumber(entity.getIdentificationNumber())
                .identificationTypeDTO(identificationTypeMapper.toDto(entity.getIdentificationTypeEntity()))
                .names(entity.getNames())
                .lastNames(entity.getLastNames())
                .address(entity.getAddress())
                .phone(entity.getPhone())
                .municipalityDTO(municipalityMapper.toDto(entity.getMunicipalityEntity()))
                .typeRegimeDTO(typeRegimeMapper.toDto(entity.getTypeRegimeEntity()))
                .typeOrganizationDTO(typeOrganizationMapper.toDto(entity.getTypeOrganizationEntity()))
                .typeResponsibilityDTO(typeResponsibilityMapper.toDto(entity.getTypeResponsibilityEntity()))
                .build();

        return InvoiceCustomerDTO.builder()
                .idInvoiceCustomer(entity.getIdInvoiceCustomer())
                .customerDTO(customerDTO)
                .invoiceId(entity.getInvoiceEntity().getIdInvoice())
                .build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public InvoiceCustomerEntity toEntity(InvoiceCustomerDTO dto) {
        if (dto == null) {
            return null;
        }

        CustomerDTO customerDTO = dto.getCustomerDTO();

        return InvoiceCustomerEntity.builder()
                .idInvoiceCustomer(dto.getIdInvoiceCustomer())
                .identificationNumber(customerDTO.getIdentificationNumber())
                .identificationTypeEntity(identificationTypeMapper.toEntity(customerDTO.getIdentificationTypeDTO()))
                .names(customerDTO.getNames())
                .lastNames(defaultNullString(customerDTO.getLastNames()))
                .address(defaultNullString(customerDTO.getAddress()))
                .phone(defaultNullString(customerDTO.getPhone()))
                .municipalityEntity(municipalityMapper.toEntity(customerDTO.getMunicipalityDTO()))
                .typeRegimeEntity(typeRegimeMapper.toEntity(customerDTO.getTypeRegimeDTO()))
                .typeOrganizationEntity(typeOrganizationMapper.toEntity(customerDTO.getTypeOrganizationDTO()))
                .typeResponsibilityEntity(typeResponsibilityMapper.toEntity(customerDTO.getTypeResponsibilityDTO()))
                .invoiceEntity(new InvoiceEntity(dto.getInvoiceId()))
                .build();
    }

    public void updateExistingEntity(InvoiceCustomerEntity entity, InvoiceCustomerDTO dto) {

    }

}
