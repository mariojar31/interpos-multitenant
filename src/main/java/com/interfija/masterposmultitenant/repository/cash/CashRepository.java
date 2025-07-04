package com.interfija.masterposmultitenant.repository.cash;

import com.interfija.masterposmultitenant.entities.tenant.cash.CashEntity;
import com.interfija.masterposmultitenant.repository.cash.projections.CashPaymentProjection;
import com.interfija.masterposmultitenant.repository.cash.projections.CashSummaryProjection;
import com.interfija.masterposmultitenant.repository.cash.projections.CashTaxProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CashRepository extends JpaRepository<CashEntity, Long> {

    @Query("SELECT c FROM CashEntity c WHERE c.terminalEntity.idTerminal = :terminalId AND endDate IS NULL")
    Optional<CashEntity> findOpenCashByTerminalId(@Param("terminalId") Long terminalId);

    @Query(value = """
            SELECT
                cas.id_cash AS idCash,
                cas.sequence AS sequence,
                cas.start_date AS startDate,
                cas.end_date AS endDate,
                SUM(inpa.total_received) AS total
            FROM cash AS cas
            LEFT JOIN invoice AS inv ON cas.id_cash = inv.cash_id
            LEFT JOIN invoice_payment AS inpa ON inv.id_invoice = inpa.invoice_id
            WHERE cas.terminal_id = (:terminalId)
            GROUP BY cas.id_cash, cas.start_date, cas.end_date
            ORDER BY cas.sequence DESC
            """, nativeQuery = true)
    List<CashSummaryProjection> findCashSummaries(@Param("terminalId") Long terminalId);

    @Query(value = """
            SELECT
                inpm.id_invoice_payment AS invoicePaymentId,
                inpm.total_received AS totalReceived,
                typa.id_type_payment AS typePaymentId,
                typa.name AS typePaymentName,
                inv.date AS invoiceDate,
                inv.id_invoice AS invoiceId,
                inv.cash_id AS cashId
            FROM invoice_payment inpm
            INNER JOIN type_payment typa ON inpm.type_payment_id = typa.id_type_payment
            INNER JOIN invoice inv ON inv.id_invoice = inpm.invoice_id
            WHERE inv.cash_id IN (:cashIds)
            """, nativeQuery = true)
    List<CashPaymentProjection> findCashPayments(@Param("cashIds") List<Long> cashIds);

    @Query(value = """
            SELECT
                tyta.id_type_tax AS typeTaxId,
                CONCAT(ta.name, ' ', tyta.name) AS typeTaxName,
                tyta.rate AS typeTaxRate,
                inv.id_invoice AS invoiceId,
                inv.cash_id AS cashId,
                SUM(
                    (inli.sale_price -
                        CASE
                            WHEN inli.value_discount >= 100 THEN inli.value_discount
                            ELSE (inli.sale_price * inli.value_discount / 100)
                        END
                    ) * inli.quantity
                ) AS saleSum,
                SUM(
                    ((inli.sale_price -
                        CASE
                            WHEN inli.value_discount >= 100 THEN inli.value_discount
                            ELSE (inli.sale_price * inli.value_discount / 100)
                        END
                    ) * inli.quantity * tyta.rate / 100)
                ) AS taxSum
            FROM invoice_product_tax inlita
            INNER JOIN invoice_product inli ON inli.id_invoice_product = inlita.invoice_product_id
            INNER JOIN type_tax tyta ON tyta.id_type_tax = inlita.type_tax_id
            INNER JOIN tax ta ON ta.id_tax = tyta.tax_id
            INNER JOIN invoice inv ON inv.id_invoice = inli.invoice_id
            WHERE inv.cash_id IN (:cashIds) AND ta.code != '06'
            GROUP BY tyta.id_type_tax
            """, nativeQuery = true)
    List<CashTaxProjection> findCashTaxes(@Param("cashIds") List<Long> cashIds);

}
