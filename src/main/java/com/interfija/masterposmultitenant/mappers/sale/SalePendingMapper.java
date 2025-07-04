package com.interfija.masterposmultitenant.mappers.sale;

import com.interfija.masterposmultitenant.dto.branch.BranchDTO;
import com.interfija.masterposmultitenant.dto.sale.SalePendingDTO;
import com.interfija.masterposmultitenant.entities.tenant.branch.BranchEntity;
import com.interfija.masterposmultitenant.entities.tenant.sale.SalePendingEntity;
import com.interfija.masterposmultitenant.mappers.base.BaseMapper;
import com.interfija.masterposmultitenant.mappers.base.GenericMapper;
import com.interfija.masterposmultitenant.mappers.floor.TableMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SalePendingMapper extends BaseMapper implements GenericMapper<SalePendingEntity, SalePendingDTO> {

    private final TableMapper tableMapper;

    @Autowired
    public SalePendingMapper(TableMapper tableMapper) {
        this.tableMapper = tableMapper;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SalePendingDTO toDto(SalePendingEntity entity) {
        if (entity == null) {
            return null;
        }

        BranchDTO branchDTO = BranchDTO.builder()
                .idBranch(entity.getBranchEntity().getIdBranch())
                .name(entity.getBranchEntity().getName())
                .build();

        return SalePendingDTO.builder()
                .idSalePending(entity.getIdSalePending())
                .date(entity.getDate())
                .employeeName(entity.getEmployeeName())
                .customerName(entity.getCustomerName())
                .customerPhone(entity.getCustomerPhone())
                .customerAddress(entity.getCustomerAddress())
                .domiciliaryName(entity.getDomiciliaryName())
                //.invoice(entity.getInvoice())
                .total(entity.getTotal())
                .delivery(entity.getDelivery())
                .tableDTO(tableMapper.toDto(entity.getTableEntity()))
                .branchDTO(branchDTO)
                .build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SalePendingEntity toEntity(SalePendingDTO dto) {
        if (dto == null) {
            return null;
        }

        BranchEntity branchEntity;
        if (dto.getBranchDTO() != null) {
            branchEntity = BranchEntity.builder()
                    .idBranch(dto.getBranchDTO().getIdBranch())
                    .name(dto.getBranchDTO().getName())
                    .build();
        } else {
            branchEntity = null;
        }

        return SalePendingEntity.builder()
                .idSalePending(dto.getIdSalePending())
                .date(dto.getDate())
                .employeeName(defaultNullString(dto.getEmployeeName()))
                .customerName(defaultNullString(dto.getCustomerName()))
                .customerPhone(defaultNullString(dto.getCustomerPhone()))
                .customerAddress(defaultNullString(dto.getCustomerAddress()))
                .domiciliaryName(defaultNullString(dto.getDomiciliaryName()))
                .invoice(dto.getInvoice())
                .total(defaultBigDecimal(dto.getTotal()))
                .delivery(dto.isDelivery())
                .tableEntity(tableMapper.toEntity(dto.getTableDTO()))
                .branchEntity(branchEntity)
                .build();
    }

}
