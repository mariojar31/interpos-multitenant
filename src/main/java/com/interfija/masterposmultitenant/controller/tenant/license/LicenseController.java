//package com.interfija.masterposmultitenant.controller.tenant.license;
//
//import com.interfija.masterposmultitenant.dto.license.*;
//import com.interfija.masterposmultitenant.services.license.LicenseService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
///**
// * Controlador REST para gestionar licencias del sistema.
// * Actúa como puente con el servidor de licencias.
// */
//@RestController
//@RequestMapping("/api/tenant/license")
//public class LicenseController {
//
//    private final LicenseService licenseService;
//
//    @Autowired
//    public LicenseController(LicenseService licenseService) {
//        this.licenseService = licenseService;
//    }
//
//    /**
//     * Activa una licencia.
//     *
//     * @param licenseRequest Datos de solicitud de licencia.
//     * @return Licencia activada o mensaje de error.
//     */
//    @PostMapping("/activate")
//    public ResponseEntity<LicenseResponseDTO> requestLicense(@RequestBody LicenseRequestDTO licenseRequest) {
//        LicenseResponseDTO response = licenseService.requestLicense(licenseRequest);
//        return ResponseEntity.ok(response);
//    }
//
//    /**
//     * Verifica si una licencia es válida.
//     *
//     * @param licenseKey Clave de la licencia.
//     * @param macAddress Dirección MAC.
//     * @param motherboardSerial Serial de la motherboard.
//     * @return Resultado de la verificación.
//     */
//    @GetMapping("/validate")
//    public ResponseEntity<LicenseVerificationDTO> verifyLicense(
//            @RequestParam String licenseKey,
//            @RequestParam String macAddress,
//            @RequestParam String motherboardSerial
//    ) {
//        LicenseVerificationDTO response = licenseService.verifyLicense(licenseKey, macAddress, motherboardSerial);
//        return ResponseEntity.ok(response);
//    }
//
//    /**
//     * Solicita una licencia de prueba.
//     *
//     * @param macAddress Dirección MAC.
//     * @param companyNit NIT de la empresa.
//     * @param days Número de días de prueba.
//     * @return Licencia temporal generada.
//     */
//    @PostMapping("/trial")
//    public ResponseEntity<LicenseDTO> requestTrialLicense(
//            @RequestParam String macAddress,
//            @RequestParam Long companyNit,
//            @RequestParam int days
//    ) {
//        LicenseDTO response = licenseService.requestTrialLicense(macAddress, companyNit, days);
//        return response != null ? ResponseEntity.ok(response) : ResponseEntity.internalServerError().build();
//    }
//
//    /**
//     * Obtiene la información completa de una licencia.
//     *
//     * @param licenseKey Clave de licencia.
//     * @return Información de la licencia.
//     */
//    @GetMapping("/{licenseKey}")
//    public ResponseEntity<LicenseDTO> getLicenseInfo(@PathVariable String licenseKey) {
//        LicenseDTO response = licenseService.getLicenseInfo(licenseKey);
//        return response != null ? ResponseEntity.ok(response) : ResponseEntity.notFound().build();
//    }
//}
