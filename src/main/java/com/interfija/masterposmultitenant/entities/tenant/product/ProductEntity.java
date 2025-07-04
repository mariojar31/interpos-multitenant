package com.interfija.masterposmultitenant.entities.tenant.product;

import com.interfija.masterposmultitenant.entities.tenant.category.CategoryEntity;
import com.interfija.masterposmultitenant.entities.tenant.other.BarcodeTypeEntity;
import com.interfija.masterposmultitenant.entities.tenant.other.TypeUnitEntity;
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
@Table(name = "product")
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_product")
    private Long idProduct;

    @Column(nullable = false, length = 100)
    private String reference;

    @Column(nullable = false, length = 100)
    private String barcode;

    @ManyToOne
    @JoinColumn(name = "barcode_type_id")
    private BarcodeTypeEntity barcodeTypeEntity;

    @Column(nullable = false, length = 100)
    private String name;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private CategoryEntity categoryEntity;

    @ManyToOne
    @JoinColumn(name = "type_unit_id")
    private TypeUnitEntity typeUnitEntity;

//    @ManyToOne
//    @JoinColumn(name = "attribute_id")
//    private Attribute attribute;

    @Lob
    @Column(name = "image", columnDefinition = "MEDIUMBLOB")
    private byte[] image;

    @Column(name = "variable_price", columnDefinition = "BOOLEAN DEFAULT 0")
    private Boolean variablePrice;

    @Column(name = "service", columnDefinition = "BOOLEAN DEFAULT 0")
    private Boolean service;

    @Column(name = "bundle", columnDefinition = "BOOLEAN DEFAULT 0")
    private Boolean bundle;

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

    @OneToMany(mappedBy = "productEntity", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ProductTaxEntity> taxes;

    @OneToMany(mappedBy = "productEntity", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ProductBranchEntity> branches;

    public void addTax(ProductTaxEntity tax) {
        taxes.add(tax);
        tax.setProductEntity(this);
    }

    public void removeTax(ProductTaxEntity tax) {
        taxes.remove(tax);
    }

    public void addBranch(ProductBranchEntity branch) {
        branches.add(branch);
        branch.setProductEntity(this);
    }

    public void removeBranch(ProductBranchEntity branch) {
        branches.remove(branch);
    }

}
