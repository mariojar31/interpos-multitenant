package com.interfija.masterposmultitenant.mappers.invoice;

import com.interfija.masterposmultitenant.dto.invoice.InvoicePaymentDTO;
import com.interfija.masterposmultitenant.dto.payment.PaymentDTO;
import com.interfija.masterposmultitenant.entities.tenant.invoice.InvoicePaymentEntity;
import com.interfija.masterposmultitenant.entities.tenant.invoice.InvoiceEntity;
import com.interfija.masterposmultitenant.mappers.base.GenericMapper;
import com.interfija.masterposmultitenant.mappers.other.TypePaymentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InvoicePaymentMapper implements GenericMapper<InvoicePaymentEntity, InvoicePaymentDTO> {

    private final TypePaymentMapper typePaymentMapper;

    @Autowired
    public InvoicePaymentMapper(TypePaymentMapper typePaymentMapper) {
        this.typePaymentMapper = typePaymentMapper;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public InvoicePaymentDTO toDto(InvoicePaymentEntity entity) {
        if (entity == null) {
            return null;
        }

        PaymentDTO paymentDTO = PaymentDTO.builder()
                .totalChange(entity.getTotalChange())
                .totalReceived(entity.getTotalReceived())
                .typePaymentDTO(typePaymentMapper.toDto(entity.getTypePaymentEntity()))
                .build();

        return InvoicePaymentDTO.builder()
                .idInvoicePayment(entity.getIdInvoicePayment())
                .paymentDTO(paymentDTO)
                .invoiceId(entity.getInvoiceEntity().getIdInvoice())
                .build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public InvoicePaymentEntity toEntity(InvoicePaymentDTO dto) {
        if (dto == null) {
            return null;
        }

        return InvoicePaymentEntity.builder()
                .idInvoicePayment(dto.getIdInvoicePayment())
                .typePaymentEntity(typePaymentMapper.toEntity(dto.getPaymentDTO().getTypePaymentDTO()))
                .totalChange(dto.getPaymentDTO().getTotalChange())
                .totalReceived(dto.getPaymentDTO().getTotalReceived())
                .invoiceEntity(new InvoiceEntity(dto.getInvoiceId()))
                .build();
    }

    public void updateExistingEntity(InvoicePaymentEntity entity, InvoicePaymentDTO dto) {

    }

}
