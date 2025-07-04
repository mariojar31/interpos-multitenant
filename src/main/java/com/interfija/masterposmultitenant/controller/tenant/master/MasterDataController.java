package com.interfija.masterposmultitenant.controller.tenant.master;
import com.interfija.masterposmultitenant.dto.other.*;
import com.interfija.masterposmultitenant.master.MasterDataEnum;
import com.interfija.masterposmultitenant.services.master.MasterDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Controlador REST para exponer los datos maestros del sistema.
 * Autor: Steven Arzuza
 */
@RestController
@RequestMapping("/api/master-data")
public class MasterDataController {

    private final MasterDataService masterDataService;

    @Autowired
    public MasterDataController(MasterDataService masterDataService) {
        this.masterDataService = masterDataService;
    }

    @GetMapping("/barcode-types")
    public ResponseEntity<List<BarcodeTypeDTO>> getBarcodeTypes() {
        return ResponseEntity.ok(masterDataService.findAllBarcodeTypes());
    }

    @GetMapping("/departments")
    public ResponseEntity<List<DepartmentDTO>> getDepartments() {
        return ResponseEntity.ok(masterDataService.findAllDepartments());
    }

    @GetMapping("/municipalities")
    public ResponseEntity<List<MunicipalityDTO>> getMunicipalities() {
        return ResponseEntity.ok(masterDataService.findAllMunicipalities());
    }

    @GetMapping("/municipalities/by-department")
    public ResponseEntity<List<MunicipalityDTO>> getMunicipalitiesByDepartment(
            @RequestParam Short departmentId) {
        return ResponseEntity.ok(masterDataService.findAllMunicipalitiesByDepartmentId(departmentId));
    }

    @GetMapping("/type-organizations")
    public ResponseEntity<List<TypeOrganizationDTO>> getTypeOrganizations() {
        return ResponseEntity.ok(masterDataService.findAllTypeOrganizations());
    }

    @GetMapping("/type-regimes")
    public ResponseEntity<List<TypeRegimeDTO>> getTypeRegimes() {
        return ResponseEntity.ok(masterDataService.findAllTypeRegimes());
    }

    @GetMapping("/type-responsibilities")
    public ResponseEntity<List<TypeResponsibilityDTO>> getTypeResponsibilities() {
        return ResponseEntity.ok(masterDataService.findAllTypeResponsibilities());
    }

    @GetMapping("/type-taxes")
    public ResponseEntity<List<TypeTaxDTO>> getTypeTaxes() {
        return ResponseEntity.ok(masterDataService.findAllTypeTaxes());
    }

    @GetMapping("/taxes")
    public ResponseEntity<List<TaxDTO>> getTaxes() {
        return ResponseEntity.ok(masterDataService.findAllTaxes());
    }

    @GetMapping("/type-units")
    public ResponseEntity<List<TypeUnitDTO>> getTypeUnits() {
        return ResponseEntity.ok(masterDataService.findAllTypeUnits());
    }

    @GetMapping("/identification-types")
    public ResponseEntity<List<IdentificationTypeDTO>> getIdentificationTypes() {
        return ResponseEntity.ok(masterDataService.findAllIdentificationsType());
    }

    @GetMapping("/type-payments")
    public ResponseEntity<List<TypePaymentDTO>> getTypePayments() {
        return ResponseEntity.ok(masterDataService.findAllTypePayments());
    }

    @GetMapping("/payment-forms")
    public ResponseEntity<List<PaymentFormDTO>> getPaymentForms() {
        return ResponseEntity.ok(masterDataService.findAllPaymentsForm());
    }

    @PostMapping("/grouped")
    public ResponseEntity<Map<MasterDataEnum, List<?>>> getGroupedMasterData(
            @RequestBody List<MasterDataEnum> keys) {
        return ResponseEntity.ok(masterDataService.findGroupedMasterData(keys));
    }
}