package com.interfija.masterposmultitenant.controller.tenant.electronicdocuments;

import com.interfija.masterposmultitenant.dto.branch.BranchDTO;
import com.interfija.masterposmultitenant.dto.company.CompanyDTO;
import com.interfija.masterposmultitenant.dto.electronicdocuments.ResolutionDTO;
import com.interfija.masterposmultitenant.dto.invoice.InvoiceDTO;
import com.interfija.masterposmultitenant.services.electronicdocuments.SendElectronicDocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tenant/electronic-documents")
public class SendElectronicDocumentController {

    private final SendElectronicDocumentService sendElectronicDocumentService;

    @Autowired
    public SendElectronicDocumentController(SendElectronicDocumentService sendElectronicDocumentService) {
        this.sendElectronicDocumentService = sendElectronicDocumentService;
    }

    /**
     * Endpoint para enviar un documento electrónico.
     *
     * Recibe el InvoiceDTO, CompanyDTO, BranchDTO, ResolutionDTO y el tipo de documento electrónico.
     * En un caso real, normalmente estos datos deberían recibirse en un DTO compuesto o separado.
     */
    @PostMapping("/send")
    public ResponseEntity<String> sendElectronicDocument(
            @RequestBody SendDocumentRequest request) {
        try {
            boolean success = sendElectronicDocumentService.sendDocument(
                    request.getInvoiceDTO(),
                    request.getCompanyDTO(),
                    request.getBranchDTO(),
                    request.getResolutionDTO(),
                    request.getIdTypeElectronicDocument()
            );
            if (success) {
                return ResponseEntity.ok("Documento electrónico enviado exitosamente.");
            } else {
                return ResponseEntity.status(500).body("Error enviando el documento electrónico.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Excepción: " + e.getMessage());
        }
    }

    // DTO interno para recibir el payload del envío
    public static class SendDocumentRequest {
        private InvoiceDTO invoiceDTO;
        private CompanyDTO companyDTO;
        private BranchDTO branchDTO;
        private ResolutionDTO resolutionDTO;
        private Short idTypeElectronicDocument;

        // Getters y setters

        public InvoiceDTO getInvoiceDTO() {
            return invoiceDTO;
        }

        public void setInvoiceDTO(InvoiceDTO invoiceDTO) {
            this.invoiceDTO = invoiceDTO;
        }

        public CompanyDTO getCompanyDTO() {
            return companyDTO;
        }

        public void setCompanyDTO(CompanyDTO companyDTO) {
            this.companyDTO = companyDTO;
        }

        public BranchDTO getBranchDTO() {
            return branchDTO;
        }

        public void setBranchDTO(BranchDTO branchDTO) {
            this.branchDTO = branchDTO;
        }

        public ResolutionDTO getResolutionDTO() {
            return resolutionDTO;
        }

        public void setResolutionDTO(ResolutionDTO resolutionDTO) {
            this.resolutionDTO = resolutionDTO;
        }

        public Short getIdTypeElectronicDocument() {
            return idTypeElectronicDocument;
        }

        public void setIdTypeElectronicDocument(Short idTypeElectronicDocument) {
            this.idTypeElectronicDocument = idTypeElectronicDocument;
        }
    }
}
