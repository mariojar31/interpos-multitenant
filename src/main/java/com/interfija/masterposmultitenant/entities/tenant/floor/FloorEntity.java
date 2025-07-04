package com.interfija.masterposmultitenant.entities.tenant.floor;

import com.interfija.masterposmultitenant.entities.tenant.branch.BranchEntity;
import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

import static com.interfija.masterposmultitenant.utils.StandardMethod.nowUtc;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "floor")
public class FloorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_floor")
    private Long idFloor;

    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Lob
    @Column(name = "image", columnDefinition = "MEDIUMBLOB")
    @Basic(fetch = FetchType.LAZY)
    private byte[] image;

    @Column(name = "visible", columnDefinition = "BOOLEAN DEFAULT 1")
    private Boolean visible;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_id", referencedColumnName = "id_branch", nullable = false)
    private BranchEntity branchEntity;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        LocalDateTime now = nowUtc();
        this.createdAt = now;
        this.updatedAt = now;
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = nowUtc();
    }

    @OneToMany(mappedBy = "floorEntity", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    private List<TerminalEntity> terminals;

    @OneToMany(mappedBy = "floorEntity", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<TableEntity> tables;

    public void addTerminal(TerminalEntity terminalEntity) {
        terminalEntity.setFloorEntity(this);
        terminals.add(terminalEntity);
    }

    public void removeTerminal(TerminalEntity terminalEntity) {
        terminals.remove(terminalEntity);
    }

    public void addTable(TableEntity tableEntity) {
        tableEntity.setFloorEntity(this);
        tables.add(tableEntity);
    }

    public void removeTable(TableEntity tableEntity) {
        tables.remove(tableEntity);
    }

}
