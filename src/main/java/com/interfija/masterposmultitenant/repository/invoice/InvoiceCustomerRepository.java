package com.interfija.masterposmultitenant.repository.invoice;

import com.interfija.masterposmultitenant.entities.tenant.invoice.InvoiceCustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceCustomerRepository extends JpaRepository<InvoiceCustomerEntity, Long> {

}
