package com.interfija.masterposmultitenant.mappers.invoice;

import com.interfija.masterposmultitenant.dto.invoice.InvoiceProductEmployeeDTO;
import com.interfija.masterposmultitenant.entities.tenant.invoice.InvoiceProductEmployeeEntity;
import com.interfija.masterposmultitenant.entities.tenant.invoice.InvoiceProductEntity;
import com.interfija.masterposmultitenant.mappers.base.BaseMapper;
import com.interfija.masterposmultitenant.mappers.base.GenericMapper;
import com.interfija.masterposmultitenant.mappers.employee.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InvoiceProductEmployeeMapper extends BaseMapper implements GenericMapper<InvoiceProductEmployeeEntity, InvoiceProductEmployeeDTO> {

    private final EmployeeMapper employeeMapper;

    @Autowired
    public InvoiceProductEmployeeMapper(EmployeeMapper employeeMapper) {
        this.employeeMapper = employeeMapper;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public InvoiceProductEmployeeDTO toDto(InvoiceProductEmployeeEntity entity) {
        if (entity == null) {
            return null;
        }

        return InvoiceProductEmployeeDTO.builder()
                .idInvoiceProductEmployee(entity.getIdInvoiceProductEmployee())
                .employeeDTO(employeeMapper.toDto(entity.getEmployeeEntity()))
                .invoiceProductId(entity.getInvoiceProductEntity().getIdInvoiceProduct())
                .build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public InvoiceProductEmployeeEntity toEntity(InvoiceProductEmployeeDTO dto) {
        if (dto == null) {
            return null;
        }

        return InvoiceProductEmployeeEntity.builder()
                .idInvoiceProductEmployee(dto.getIdInvoiceProductEmployee())
                .employeeEntity(employeeMapper.toEntity(dto.getEmployeeDTO()))
                .invoiceProductEntity(new InvoiceProductEntity(dto.getInvoiceProductId()))
                .build();
    }

    public void updateExistingEntity(InvoiceProductEmployeeEntity entity, InvoiceProductEmployeeDTO dto) {

    }

    public void updateExistingEntity(InvoiceProductEmployeeEntity entity, InvoiceProductEmployeeEntity entity1) {

    }

}
