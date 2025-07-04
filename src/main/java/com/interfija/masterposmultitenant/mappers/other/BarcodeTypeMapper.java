package com.interfija.masterposmultitenant.mappers.other;

import com.interfija.masterposmultitenant.dto.other.BarcodeTypeDTO;
import com.interfija.masterposmultitenant.entities.tenant.other.BarcodeTypeEntity;
import com.interfija.masterposmultitenant.mappers.base.GenericMapper;
import org.springframework.stereotype.Component;

@Component
public class BarcodeTypeMapper implements GenericMapper<BarcodeTypeEntity, BarcodeTypeDTO> {

    /**
     * {@inheritDoc}
     */
    @Override
    public BarcodeTypeDTO toDto(BarcodeTypeEntity entity) {
        if (entity == null) {
            return null;
        }

        return BarcodeTypeDTO.builder()
                .idBarcodeType(entity.getIdBarcodeType())
                .name(entity.getName())
                .build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BarcodeTypeEntity toEntity(BarcodeTypeDTO dto) {
        if (dto == null) {
            return null;
        }

        return BarcodeTypeEntity.builder()
                .idBarcodeType(dto.getIdBarcodeType())
                .name(dto.getName())
                .build();
    }

}
