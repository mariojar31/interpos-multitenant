package com.interfija.masterposmultitenant.mappers.invoice;

import com.interfija.masterposmultitenant.dto.floor.TableDTO;
import com.interfija.masterposmultitenant.dto.invoice.InvoiceDTO;
import com.interfija.masterposmultitenant.entities.tenant.cash.CashEntity;
import com.interfija.masterposmultitenant.entities.tenant.invoice.InvoiceEntity;
import com.interfija.masterposmultitenant.mappers.base.BaseMapper;
import com.interfija.masterposmultitenant.mappers.base.GenericMapper;
import com.interfija.masterposmultitenant.mappers.other.PaymentFormMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class InvoiceMapper extends BaseMapper implements GenericMapper<InvoiceEntity, InvoiceDTO> {

    private final PaymentFormMapper paymentFormMapper;

    @Autowired
    public InvoiceMapper(PaymentFormMapper paymentFormMapper) {
        this.paymentFormMapper = paymentFormMapper;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public InvoiceDTO toDto(InvoiceEntity entity) {
        if (entity == null) {
            return null;
        }

        TableDTO tableDTO;
        if (entity.getTableName() != null) {
            tableDTO = TableDTO.builder()
                    .name(entity.getTableName())
                    .build();
        } else {
            tableDTO = null;
        }

        return InvoiceDTO.builder()
                .idInvoice(entity.getIdInvoice())
                .date(entity.getDate())
                .dateTerm(entity.getDateTerm())
                .daysTerm(entity.getDaysTerm())
                .discountType(entity.getDiscountType())
                .valueDiscount(entity.getValueDiscount())
                .valueServiceChange(entity.getValueServiceChange())
                .commissionType(entity.getCommissionType())
                .valueCommission(entity.getValueCommission())
                .tableDTO(tableDTO)
                .observation(entity.getObservation())
                .cashId(entity.getCashEntity().getIdCash())
                .paymentFormDTO(paymentFormMapper.toDto(entity.getPaymentFormEntity()))
                .customersList(new ArrayList<>())
                .employeesList(new ArrayList<>())
                .paymentsList(new ArrayList<>())
                .productsList(new ArrayList<>())
                //.isDelivery(entity.getDelivery())
                .build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public InvoiceEntity toEntity(InvoiceDTO dto) {
        if (dto == null) {
            return null;
        }

        return InvoiceEntity.builder()
                .idInvoice(dto.getIdInvoice())
                .date(dto.getDate())
                .dateTerm(dto.getDateTerm())
                .daysTerm(dto.getDaysTerm())
                .paymentFormEntity(paymentFormMapper.toEntity(dto.getPaymentFormDTO()))
                .discountType(defaultNullString(dto.getDiscountType()))
                .valueDiscount(defaultBigDecimal(dto.getValueDiscount()))
                .valueServiceChange(defaultBigDecimal(dto.getValueServiceChange()))
                .commissionType(dto.getCommissionType())
                .valueCommission(defaultBigDecimal(dto.getValueCommission()))
                .subtotal(dto.getSubtotal())
                .totalDiscount(dto.getTotalDiscount())
                .totalServiceChange(dto.getTotalServiceChange())
                .totalTax(dto.getTotalTax())
                .totalRetention(dto.getTotalRetention())
                .total(dto.getTotal())
                .totalCommission(dto.getTotalCommission())
                .tableName(dto.getTableDTO() != null ? dto.getTableDTO().toString() : null)
                .observation(defaultNullString(dto.getObservation()))
                .cashEntity(new CashEntity(dto.getCashId()))
                .customers(new ArrayList<>())
                .employees(new ArrayList<>())
                .payments(new ArrayList<>())
                .products(new ArrayList<>())
                .taxes(new ArrayList<>())
                //.isDelivery(entity.getDelivery())
                .build();
    }

    public void updateExistingEntity(InvoiceEntity entity, InvoiceDTO dto) {
        entity.setTableName(dto.getTableDTO() != null ? dto.toString() : null);
        entity.setDate(dto.getDate());
        entity.setDateTerm(dto.getDateTerm());
        entity.setDaysTerm(dto.getDaysTerm());
        entity.setPaymentFormEntity(paymentFormMapper.toEntity(dto.getPaymentFormDTO()));
        entity.setDiscountType(defaultNullString(dto.getDiscountType()));
        entity.setValueDiscount(defaultBigDecimal(dto.getValueDiscount()));
        entity.setValueServiceChange(defaultBigDecimal(dto.getValueServiceChange()));
        entity.setObservation(defaultNullString(dto.getObservation()));
        entity.setCommissionType(dto.getCommissionType());
        entity.setValueCommission(defaultBigDecimal(dto.getValueCommission()));
        entity.setSubtotal(dto.getSubtotal());
        entity.setTotalDiscount(dto.getTotalDiscount());
        entity.setTotalServiceChange(dto.getTotalServiceChange());
        entity.setTotalTax(dto.getTotalTax());
        entity.setTotalRetention(dto.getTotalRetention());
        entity.setTotal(dto.getTotal());
        entity.setTotalCommission(dto.getTotalCommission());
    }

}
