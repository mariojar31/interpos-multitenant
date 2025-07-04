package com.interfija.masterposmultitenant.mappers.branch;

import com.interfija.masterposmultitenant.dto.branch.BranchConfigurationDTO;
import com.interfija.masterposmultitenant.dto.branch.BranchDTO;
import com.interfija.masterposmultitenant.entities.tenant.branch.BranchConfigurationEntity;
import com.interfija.masterposmultitenant.entities.tenant.branch.BranchEntity;
import com.interfija.masterposmultitenant.mappers.base.BaseMapper;
import com.interfija.masterposmultitenant.mappers.base.GenericMapper;
import org.springframework.stereotype.Component;

@Component
public class BranchConfigurationMapper extends BaseMapper implements GenericMapper<BranchConfigurationEntity, BranchConfigurationDTO> {

    /**
     * {@inheritDoc}
     */
    @Override
    public BranchConfigurationDTO toDto(BranchConfigurationEntity entity) {
        if (entity == null) {
            return null;
        }

        BranchDTO branchDTO = BranchDTO.builder()
                .idBranch(entity.getBranchEntity().getIdBranch())
                .name(entity.getBranchEntity().getName())
                .build();

        return BranchConfigurationDTO.builder()
                .idBranchConfiguration(entity.getIdBranchConfiguration())
                .prefixInvoice(entity.getPrefixInvoice())
                .headerInvoice(entity.getHeaderInvoice())
                .footerInvoice(entity.getFooterInvoice())
                .codeOverride(entity.getCodeOverride())
                .valueServiceChange(entity.getValueServiceChange())
                .branchDTO(branchDTO)
                .build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BranchConfigurationEntity toEntity(BranchConfigurationDTO dto) {
        if (dto == null) {
            return null;
        }

        BranchEntity branchEntity = BranchEntity.builder()
                .idBranch(dto.getBranchDTO().getIdBranch())
                .build();

        return BranchConfigurationEntity.builder()
                .idBranchConfiguration(dto.getIdBranchConfiguration())
                .prefixInvoice(defaultNullString(dto.getPrefixInvoice()))
                .headerInvoice(defaultNullString(dto.getHeaderInvoice()))
                .footerInvoice(defaultNullString(dto.getFooterInvoice()))
                .codeOverride(defaultNullString(dto.getCodeOverride()))
                .valueServiceChange(dto.getValueServiceChange())
                .branchEntity(branchEntity)
                .build();
    }

    public BranchConfigurationEntity toEntity(BranchConfigurationDTO dto, BranchEntity branchEntity) {
        if (dto == null) {
            return null;
        }

        return BranchConfigurationEntity.builder()
                .idBranchConfiguration(dto.getIdBranchConfiguration())
                .prefixInvoice(defaultNullString(dto.getPrefixInvoice()))
                .headerInvoice(defaultNullString(dto.getHeaderInvoice()))
                .footerInvoice(defaultNullString(dto.getFooterInvoice()))
                .codeOverride(defaultNullString(dto.getCodeOverride()))
                .valueServiceChange(dto.getValueServiceChange())
                .branchEntity(branchEntity)
                .build();
    }

}
