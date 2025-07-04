package com.interfija.masterposmultitenant.mappers.other;

import com.interfija.masterposmultitenant.dto.other.MunicipalityDTO;
import com.interfija.masterposmultitenant.entities.tenant.other.MunicipalityEntity;
import com.interfija.masterposmultitenant.mappers.base.GenericMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MunicipalityMapper implements GenericMapper<MunicipalityEntity, MunicipalityDTO> {

    private final DepartmentMapper departmentMapper;

    @Autowired
    public MunicipalityMapper(DepartmentMapper departmentMapper) {
        this.departmentMapper = departmentMapper;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MunicipalityDTO toDto(MunicipalityEntity entity) {
        if (entity == null) {
            return null;
        }

        return MunicipalityDTO.builder()
                .idMunicipality(entity.getIdMunicipality())
                .name(entity.getName())
                .code(entity.getCode())
                .departmentDTO(departmentMapper.toDto(entity.getDepartmentEntity()))
                .build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MunicipalityEntity toEntity(MunicipalityDTO dto) {
        if (dto == null) {
            return null;
        }

        return MunicipalityEntity.builder()
                .idMunicipality(dto.getIdMunicipality())
                .name(dto.getName())
                .code(dto.getCode())
                .departmentEntity(departmentMapper.toEntity(dto.getDepartmentDTO()))
                .build();
    }

}
