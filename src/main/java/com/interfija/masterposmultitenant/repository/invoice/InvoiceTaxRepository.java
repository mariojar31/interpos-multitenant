package com.interfija.masterposmultitenant.repository.invoice;

import com.interfija.masterposmultitenant.entities.tenant.invoice.InvoiceTaxEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceTaxRepository extends JpaRepository<InvoiceTaxEntity, Long> {

}
