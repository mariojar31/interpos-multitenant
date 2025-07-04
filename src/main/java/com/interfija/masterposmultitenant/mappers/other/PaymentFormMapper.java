package com.interfija.masterposmultitenant.mappers.other;

import com.interfija.masterposmultitenant.dto.other.PaymentFormDTO;
import com.interfija.masterposmultitenant.entities.tenant.other.PaymentFormEntity;
import com.interfija.masterposmultitenant.mappers.base.GenericMapper;
import org.springframework.stereotype.Component;

@Component
public class PaymentFormMapper implements GenericMapper<PaymentFormEntity, PaymentFormDTO> {

    /**
     * {@inheritDoc}
     */
    @Override
    public PaymentFormDTO toDto(PaymentFormEntity entity) {
        if (entity == null) {
            return null;
        }

        return PaymentFormDTO.builder()
                .idPaymentForm(entity.getIdPaymentForm())
                .name(entity.getName())
                .code(entity.getCode())
                .build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PaymentFormEntity toEntity(PaymentFormDTO dto) {
        if (dto == null) {
            return null;
        }

        return PaymentFormEntity.builder()
                .idPaymentForm(dto.getIdPaymentForm())
                .name(dto.getName())
                .code(dto.getCode())
                .build();
    }

}
