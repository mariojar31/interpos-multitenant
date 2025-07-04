package com.interfija.masterposmultitenant.mappers.other;

import com.interfija.masterposmultitenant.dto.other.DepartmentDTO;
import com.interfija.masterposmultitenant.entities.tenant.other.DepartmentEntity;
import com.interfija.masterposmultitenant.mappers.base.GenericMapper;
import org.springframework.stereotype.Component;

@Component
public class DepartmentMapper implements GenericMapper<DepartmentEntity, DepartmentDTO> {

    /**
     * {@inheritDoc}
     */
    @Override
    public DepartmentDTO toDto(DepartmentEntity entity) {
        if (entity == null) {
            return null;
        }

        return DepartmentDTO.builder()
                .idDepartment(entity.getIdDepartment())
                .name(entity.getName())
                .code(entity.getCode())
                .build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DepartmentEntity toEntity(DepartmentDTO dto) {
        if (dto == null) {
            return null;
        }

        return DepartmentEntity.builder()
                .idDepartment(dto.getIdDepartment())
                .name(dto.getName())
                .code(dto.getCode())
                .build();
    }

}
