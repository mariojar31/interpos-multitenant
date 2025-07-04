package com.interfija.masterposmultitenant.mappers.employee;

import com.interfija.masterposmultitenant.dto.branch.BranchDTO;
import com.interfija.masterposmultitenant.dto.company.CompanyDTO;
import com.interfija.masterposmultitenant.dto.employee.EmployeeDTO;
import com.interfija.masterposmultitenant.entities.tenant.branch.BranchEntity;
import com.interfija.masterposmultitenant.entities.tenant.employee.EmployeeEntity;
import com.interfija.masterposmultitenant.mappers.base.BaseMapper;
import com.interfija.masterposmultitenant.mappers.base.GenericMapper;
import com.interfija.masterposmultitenant.mappers.other.MunicipalityMapper;
import com.interfija.masterposmultitenant.mappers.other.IdentificationTypeMapper;
import com.interfija.masterposmultitenant.mappers.role.RoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmployeeMapper extends BaseMapper implements GenericMapper<EmployeeEntity, EmployeeDTO> {

    private final IdentificationTypeMapper identificationTypeMapper;

    private final RoleMapper roleMapper;

    private final MunicipalityMapper municipalityMapper;

    @Autowired
    public EmployeeMapper(RoleMapper roleMapper, IdentificationTypeMapper identificationTypeMapper,
                          MunicipalityMapper municipalityMapper) {
        this.roleMapper = roleMapper;
        this.identificationTypeMapper = identificationTypeMapper;
        this.municipalityMapper = municipalityMapper;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EmployeeDTO toDto(EmployeeEntity entity) {
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

        return EmployeeDTO.builder()
                .idEmployee(entity.getIdEmployee())
                .identificationTypeDTO(identificationTypeMapper.toDto(entity.getIdentificationTypeEntity()))
                .identificationNumber(entity.getIdentificationNumber())
                .names(entity.getNames())
                .lastNames(entity.getLastNames())
                .address(entity.getAddress())
                .phone(entity.getPhone())
                .mail(entity.getMail())
                .password(entity.getPassword())
                .roleDTO(roleMapper.toDto(entity.getRoleEntity()))
                .municipalityDTO(municipalityMapper.toDto(entity.getMunicipalityEntity()))
                .visible(entity.getVisible())
                .branchDTO(branchDTO)
                .build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EmployeeEntity toEntity(EmployeeDTO dto) {
        if (dto == null) {
            return null;
        }

        BranchEntity branchEntity = BranchEntity.builder()
                .idBranch(dto.getBranchDTO().getIdBranch())
                .build();

        return EmployeeEntity.builder()
                .idEmployee(dto.getIdEmployee())
                .identificationTypeEntity(identificationTypeMapper.toEntity(dto.getIdentificationTypeDTO()))
                .identificationNumber(dto.getIdentificationNumber())
                .names(dto.getNames())
                .lastNames(defaultNullString(dto.getLastNames()))
                .address(defaultNullString(dto.getAddress()))
                .phone(defaultNullString(dto.getPhone()))
                .mail(defaultNullString(dto.getMail()))
                .password(defaultNullString(dto.getPassword()))
                .roleEntity(roleMapper.toEntity(dto.getRoleDTO()))
                .municipalityEntity(municipalityMapper.toEntity(dto.getMunicipalityDTO()))
                .visible(dto.isVisible())
                .branchEntity(branchEntity)
                .build();
    }

}
