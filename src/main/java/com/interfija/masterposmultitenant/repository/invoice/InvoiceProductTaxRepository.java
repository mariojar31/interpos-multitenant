package com.interfija.masterposmultitenant.repository.invoice;

import com.interfija.masterposmultitenant.entities.tenant.invoice.InvoiceProductTaxEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceProductTaxRepository extends JpaRepository<InvoiceProductTaxEntity, Long> {

}
