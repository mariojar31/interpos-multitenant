package com.interfija.masterposmultitenant.mappers.other;

import com.interfija.masterposmultitenant.dto.other.TypePaymentDTO;
import com.interfija.masterposmultitenant.entities.tenant.other.TypePaymentEntity;
import com.interfija.masterposmultitenant.mappers.base.GenericMapper;
import org.springframework.stereotype.Component;

@Component
public class TypePaymentMapper implements GenericMapper<TypePaymentEntity, TypePaymentDTO> {

    /**
     * {@inheritDoc}
     */
    @Override
    public TypePaymentDTO toDto(TypePaymentEntity entity) {
        if (entity == null) {
            return null;
        }

        return TypePaymentDTO.builder()
                .idTypePayment(entity.getIdTypePayment())
                .name(entity.getName())
                .apiPaymentId(entity.getApiPaymentId())
                .build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TypePaymentEntity toEntity(TypePaymentDTO dto) {
        if (dto == null) {
            return null;
        }

        return TypePaymentEntity.builder()
                .idTypePayment(dto.getIdTypePayment())
                .name(dto.getName())
                .apiPaymentId(dto.getApiPaymentId())
                .build();
    }

}
