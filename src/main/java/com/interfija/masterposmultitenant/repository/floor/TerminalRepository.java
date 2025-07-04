package com.interfija.masterposmultitenant.repository.floor;

import com.interfija.masterposmultitenant.entities.tenant.floor.TerminalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TerminalRepository extends JpaRepository<TerminalEntity, Long> {

    Optional<TerminalEntity> findByName(String name);

    @Query("SELECT t.idTerminal, t.name FROM TerminalEntity t WHERE t.floorEntity.idFloor = :floorId AND t.visible = :visible")
    List<TerminalEntity> findSimpleMappingByFloorId(@Param("floorId") Long floorId, @Param("visible") Boolean visible);

    @Query("SELECT t FROM TerminalEntity t WHERE t.floorEntity.idFloor = :floorId AND t.visible = :visible")
    List<TerminalEntity> findByFloorId(@Param("floorId") Long floorId, @Param("visible") Boolean visible);
}
