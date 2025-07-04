package com.interfija.masterposmultitenant.entities.tenant.cash;

import com.interfija.masterposmultitenant.entities.tenant.floor.TerminalEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "cash")
public class CashEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cash", nullable = false)
    private Long idCash;

    @Column(name = "sequence", nullable = false)
    private Integer sequence;

    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    @Column(name = "is_closed", nullable = false)
    private Boolean isClosed;

    @ManyToOne
    @JoinColumn(name = "terminal_id", referencedColumnName = "id_terminal", nullable = false)
    private TerminalEntity terminalEntity;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public CashEntity(Long idCash) {
        this.idCash = idCash;
    }

}
