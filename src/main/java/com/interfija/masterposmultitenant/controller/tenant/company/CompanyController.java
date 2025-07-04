package com.interfija.masterposmultitenant.controller.tenant.company;

import com.interfija.masterposmultitenant.dto.company.CompanyDTO;
import com.interfija.masterposmultitenant.services.company.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controlador REST para gestionar operaciones relacionadas con empresas.
 */
@RestController
@RequestMapping("/api/company")
public class CompanyController {

    private final CompanyService companyService;

    @Autowired
    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping("/{idCompany}")
    public ResponseEntity<CompanyDTO> getCompany(@PathVariable Long idCompany) {
        Optional<CompanyDTO> company = companyService.getCompany(idCompany);
        return company.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/basic")
    public ResponseEntity<List<CompanyDTO>> getBasicList(@RequestParam(defaultValue = "true") boolean visible) {
        return ResponseEntity.ok(companyService.getCompanyBasicList(visible));
    }

    @GetMapping("/summary")
    public ResponseEntity<List<CompanyDTO>> getSummaryList(@RequestParam(defaultValue = "true") boolean visible) {
        return ResponseEntity.ok(companyService.getCompanySummaryList(visible));
    }

    @PostMapping("/save")
    public ResponseEntity<Void> saveCompany(@RequestBody CompanyDTO companyDTO) {
        boolean saved = companyService.saveCompany(companyDTO);
        return saved ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteCompany(@RequestBody CompanyDTO companyDTO) {
        boolean deleted = companyService.deleteCompany(companyDTO);
        return deleted ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }
}
