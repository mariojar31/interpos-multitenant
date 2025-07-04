package com.interfija.masterposmultitenant.mappers.other;

import com.interfija.masterposmultitenant.dto.other.TypeElectronicDocumentDTO;
import com.interfija.masterposmultitenant.entities.tenant.other.TypeElectronicDocumentEntity;
import com.interfija.masterposmultitenant.mappers.base.GenericMapper;
import org.springframework.stereotype.Component;

@Component
public class TypeElectronicDocumentMapper implements GenericMapper<TypeElectronicDocumentEntity, TypeElectronicDocumentDTO> {

    /**
     * {@inheritDoc}
     */
    @Override
    public TypeElectronicDocumentDTO toDto(TypeElectronicDocumentEntity entity) {
        if (entity == null) {
            return null;
        }

        return TypeElectronicDocumentDTO.builder()
                .idTypeElectronicDocument(entity.getIdTypeElectronicDocument())
                .name(entity.getName())
                .apiElectronicDocumentId(entity.getApiElectronicDocumentId())
                .build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TypeElectronicDocumentEntity toEntity(TypeElectronicDocumentDTO dto) {
        if (dto == null) {
            return null;
        }

        return TypeElectronicDocumentEntity.builder()
                .idTypeElectronicDocument(dto.getIdTypeElectronicDocument())
                .name(dto.getName())
                .apiElectronicDocumentId(dto.getApiElectronicDocumentId())
                .build();
    }

}
