package com.interfija.masterposmultitenant.mappers.floor;

import com.interfija.masterposmultitenant.dto.floor.FloorDTO;
import com.interfija.masterposmultitenant.dto.floor.TerminalDTO;
import com.interfija.masterposmultitenant.entities.tenant.floor.FloorEntity;
import com.interfija.masterposmultitenant.entities.tenant.floor.TerminalEntity;
import com.interfija.masterposmultitenant.mappers.base.BaseMapper;
import com.interfija.masterposmultitenant.mappers.base.GenericMapper;
import org.springframework.stereotype.Component;

@Component
public class TerminalMapper extends BaseMapper implements GenericMapper<TerminalEntity, TerminalDTO> {

    /**
     * {@inheritDoc}
     */
    @Override
    public TerminalDTO toDto(TerminalEntity entity) {
        if (entity == null) {
            return null;
        }

        FloorDTO floorDTO = FloorDTO.builder()
                .idFloor(entity.getFloorEntity().getIdFloor())
                .name(entity.getFloorEntity().getName())
                .build();

        return TerminalDTO.builder()
                .idTerminal(entity.getIdTerminal())
                .code(entity.getCode())
                .name(entity.getName())
                .sequence(entity.getSequence())
                .floorDTO(floorDTO)
                .visible(entity.getVisible())
                .build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TerminalEntity toEntity(TerminalDTO dto) {
        if (dto == null) {
            return null;
        }

        FloorEntity floorEntity = FloorEntity.builder()
                .idFloor(dto.getFloorDTO().getIdFloor())
                .build();

        return TerminalEntity.builder()
                .idTerminal(dto.getIdTerminal())
                .name(dto.getName())
                .code(defaultNullString(dto.getCode()))
                .sequence(dto.getSequence() == 0 ? 1 : dto.getSequence())
                .visible(dto.isVisible())
                .floorEntity(floorEntity)
                .build();
    }

    public void updateExistingEntity(TerminalEntity entity, TerminalDTO dto) {
        entity.setName(dto.getName());
        entity.setCode(defaultNullString(dto.getCode()));
        entity.setSequence(dto.getSequence() == 0 ? 1 : dto.getSequence());
        entity.setVisible(dto.isVisible());
    }

}
