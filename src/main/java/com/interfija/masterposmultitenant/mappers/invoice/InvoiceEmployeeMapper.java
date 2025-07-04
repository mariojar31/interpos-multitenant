package com.interfija.masterposmultitenant.mappers.invoice;

import com.interfija.masterposmultitenant.dto.invoice.InvoiceEmployeeDTO;
import com.interfija.masterposmultitenant.entities.tenant.invoice.InvoiceEmployeeEntity;
import com.interfija.masterposmultitenant.entities.tenant.invoice.InvoiceEntity;
import com.interfija.masterposmultitenant.mappers.base.BaseMapper;
import com.interfija.masterposmultitenant.mappers.base.GenericMapper;
import com.interfija.masterposmultitenant.mappers.employee.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InvoiceEmployeeMapper extends BaseMapper implements GenericMapper<InvoiceEmployeeEntity, InvoiceEmployeeDTO> {

    private final EmployeeMapper employeeMapper;

    @Autowired
    public InvoiceEmployeeMapper(EmployeeMapper employeeMapper) {
        this.employeeMapper = employeeMapper;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public InvoiceEmployeeDTO toDto(InvoiceEmployeeEntity entity) {
        if (entity == null) {
            return null;
        }

        return InvoiceEmployeeDTO.builder()
                .idInvoiceEmployee(entity.getIdInvoiceEmployee())
                .employeeDTO(employeeMapper.toDto(entity.getEmployeeEntity()))
                .invoiceId(entity.getInvoiceEntity().getIdInvoice())
                .build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public InvoiceEmployeeEntity toEntity(InvoiceEmployeeDTO dto) {
        if (dto == null) {
            return null;
        }

        return InvoiceEmployeeEntity.builder()
                .idInvoiceEmployee(dto.getIdInvoiceEmployee())
                .employeeEntity(employeeMapper.toEntity(dto.getEmployeeDTO()))
                .invoiceEntity(new InvoiceEntity(dto.getInvoiceId()))
                .build();
    }

    public void updateExistingEntity(InvoiceEmployeeEntity entity, InvoiceEmployeeDTO dto) {

    }

}
