package com.interfija.masterposmultitenant.mappers.floor;

import com.interfija.masterposmultitenant.dto.floor.FloorDTO;
import com.interfija.masterposmultitenant.dto.floor.TableDTO;
import com.interfija.masterposmultitenant.entities.tenant.floor.FloorEntity;
import com.interfija.masterposmultitenant.entities.tenant.floor.TableEntity;
import com.interfija.masterposmultitenant.mappers.base.BaseMapper;
import com.interfija.masterposmultitenant.mappers.base.GenericMapper;
import org.springframework.stereotype.Component;

@Component
public class TableMapper extends BaseMapper implements GenericMapper<TableEntity, TableDTO> {

    /**
     * {@inheritDoc}
     */
    @Override
    public TableDTO toDto(TableEntity entity) {
        if (entity == null) {
            return null;
        }

        FloorDTO floorDTO = FloorDTO.builder()
                .idFloor(entity.getFloorEntity().getIdFloor())
                .name(entity.getFloorEntity().getName())
                .build();

        return TableDTO.builder()
                .idTable(entity.getIdTable())
                .code(entity.getCode())
                .name(entity.getName())
                .width(entity.getWidth())
                .height(entity.getHeight())
                .positionX(entity.getPositionX())
                .positionY(entity.getPositionY())
                .visible(entity.getVisible())
                .floorDTO(floorDTO)
                .build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TableEntity toEntity(TableDTO dto) {
        if (dto == null) {
            return null;
        }

        FloorEntity floorEntity = FloorEntity.builder()
                .idFloor(dto.getFloorDTO().getIdFloor())
                .build();

        return TableEntity.builder()
                .idTable(dto.getIdTable())
                .code(defaultNullString(dto.getCode()))
                .name(dto.getName())
                .width(dto.getWidth())
                .height(dto.getHeight())
                .positionX(dto.getPositionX())
                .positionY(dto.getPositionY())
                .visible(dto.isVisible())
                .floorEntity(floorEntity)
                .build();
    }

    public void updateExistingEntity(TableEntity entity, TableDTO dto) {
        entity.setCode(defaultNullString(dto.getCode()));
        entity.setName(dto.getName());
        entity.setWidth(dto.getWidth());
        entity.setHeight(dto.getHeight());
        entity.setPositionX(dto.getPositionX());
        entity.setPositionY(dto.getPositionY());
        entity.setVisible(dto.isVisible());
    }

}
