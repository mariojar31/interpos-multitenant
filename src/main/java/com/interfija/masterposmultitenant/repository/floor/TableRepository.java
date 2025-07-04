package com.interfija.masterposmultitenant.repository.floor;

import com.interfija.masterposmultitenant.entities.tenant.floor.TableEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TableRepository extends JpaRepository<TableEntity, Long> {

    @Query("SELECT t FROM TableEntity t WHERE t.floorEntity.idFloor = :floorId AND t.visible = :visible")
    List<TableEntity> findByFloorId(@Param("floorId") Long floorId, @Param("visible") Boolean visible);

}
