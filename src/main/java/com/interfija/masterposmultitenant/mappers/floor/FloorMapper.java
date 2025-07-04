package com.interfija.masterposmultitenant.mappers.floor;

import com.interfija.masterposmultitenant.dto.branch.BranchDTO;
import com.interfija.masterposmultitenant.dto.company.CompanyDTO;
import com.interfija.masterposmultitenant.dto.floor.FloorDTO;
import com.interfija.masterposmultitenant.entities.tenant.branch.BranchEntity;
import com.interfija.masterposmultitenant.entities.tenant.floor.FloorEntity;
import com.interfija.masterposmultitenant.mappers.base.GenericMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class FloorMapper implements GenericMapper<FloorEntity, FloorDTO> {

    /**
     * {@inheritDoc}
     */
    @Override
    public FloorDTO toDto(FloorEntity entity) {
        if (entity == null) {
            return null;
        }

        CompanyDTO companyDTO = CompanyDTO.builder()
                .idCompany(entity.getBranchEntity().getCompanyEntity().getIdCompany())
                .name(entity.getBranchEntity().getCompanyEntity().getName())
                .build();

        BranchDTO branchDTO = BranchDTO.builder()
                .idBranch(entity.getBranchEntity().getIdBranch())
                .name(entity.getBranchEntity().getName())
                .companyDTO(companyDTO)
                .build();

        return FloorDTO.builder()
                .idFloor(entity.getIdFloor())
                .name(entity.getName())
                .image(entity.getImage())
                .visible(entity.getVisible())
                .terminalsList(new ArrayList<>())
                .tablesList(new ArrayList<>())
                .branchDTO(branchDTO)
                .build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FloorEntity toEntity(FloorDTO dto) {
        if (dto == null) {
            return null;
        }

        BranchEntity branchEntity = BranchEntity.builder()
                .idBranch(dto.getBranchDTO().getIdBranch())
                .build();

        return FloorEntity.builder()
                .idFloor(dto.getIdFloor())
                .name(dto.getName())
                .image(dto.getImage())
                .visible(dto.isVisible())
                .terminals(new ArrayList<>())
                .tables(new ArrayList<>())
                .branchEntity(branchEntity)
                .build();
    }

    public void updateExistingEntity(FloorEntity entity, FloorDTO dto) {
        BranchEntity branchEntity = BranchEntity.builder()
                .idBranch(dto.getBranchDTO().getIdBranch())
                .build();

        entity.setName(dto.getName());
        entity.setImage(dto.getImage());
        entity.setVisible(dto.isVisible());
        entity.setBranchEntity(branchEntity);
    }

}
