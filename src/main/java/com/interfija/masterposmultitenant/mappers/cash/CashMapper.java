package com.interfija.masterposmultitenant.mappers.cash;

import com.interfija.masterposmultitenant.dto.cash.CashDTO;
import com.interfija.masterposmultitenant.entities.tenant.cash.CashEntity;
import com.interfija.masterposmultitenant.mappers.base.GenericMapper;
import com.interfija.masterposmultitenant.mappers.floor.TerminalMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CashMapper implements GenericMapper<CashEntity, CashDTO> {

    private final TerminalMapper terminalMapper;

    @Autowired
    public CashMapper(TerminalMapper terminalMapper) {
        this.terminalMapper = terminalMapper;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CashDTO toDto(CashEntity entity) {
        if (entity == null) {
            return null;
        }

        return CashDTO.builder()
                .idCash(entity.getIdCash())
                .sequence(entity.getSequence())
                .startDate(entity.getStartDate())
                .endDate(entity.getEndDate())
                .closed(entity.getIsClosed())
                .terminalDTO(terminalMapper.toDto(entity.getTerminalEntity()))
                .build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CashEntity toEntity(CashDTO dto) {
        if (dto == null) {
            return null;
        }

        return CashEntity.builder()
                .idCash(dto.getIdCash())
                .sequence(dto.getSequence())
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .isClosed(dto.isClosed())
                .terminalEntity(terminalMapper.toEntity(dto.getTerminalDTO()))
                .build();
    }

}
