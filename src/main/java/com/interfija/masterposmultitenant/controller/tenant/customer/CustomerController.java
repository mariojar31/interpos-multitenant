package com.interfija.masterposmultitenant.controller.tenant.customer;

import com.interfija.masterposmultitenant.dto.customer.CustomerDTO;
import com.interfija.masterposmultitenant.services.customer.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    // Obtener cliente por ID
    @GetMapping("/{id}")
    public ResponseEntity<CustomerDTO> getCustomer(@PathVariable Long id) {
        Optional<CustomerDTO> customerOpt = customerService.getCustomer(id);
        return customerOpt.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Listado básico de clientes (por visibilidad)
    @GetMapping("/basic")
    public ResponseEntity<List<CustomerDTO>> getCustomerBasicList(@RequestParam(defaultValue = "true") boolean visible) {
        List<CustomerDTO> customers = customerService.getCustomerBasicList(visible);
        return ResponseEntity.ok(customers);
    }

    // Listado básico de clientes por empresa
    @GetMapping("/basic/by-company/{companyId}")
    public ResponseEntity<List<CustomerDTO>> getCustomersBasicListByCompany(
            @PathVariable Long companyId,
            @RequestParam(defaultValue = "true") boolean visible) {
        List<CustomerDTO> customers = customerService.getCustomersBasicList(companyId, visible);
        return ResponseEntity.ok(customers);
    }

    // Listado resumen de clientes
    @GetMapping("/summary")
    public ResponseEntity<List<CustomerDTO>> getCustomerSummaryList(@RequestParam(defaultValue = "true") boolean visible) {
        List<CustomerDTO> customers = customerService.getCustomerSummaryList(visible);
        return ResponseEntity.ok(customers);
    }

    // Listado resumen de clientes por empresa
    @GetMapping("/summary/by-company/{companyId}")
    public ResponseEntity<List<CustomerDTO>> getCustomerSummaryListByCompany(
            @PathVariable Long companyId,
            @RequestParam(defaultValue = "true") boolean visible) {
        List<CustomerDTO> customers = customerService.getCustomerSummaryList(companyId, visible);
        return ResponseEntity.ok(customers);
    }

    // Crear o actualizar cliente
    @PostMapping
    public ResponseEntity<String> saveCustomer(@RequestBody CustomerDTO customerDTO) {
        boolean success = customerService.saveCustomer(customerDTO);
        if (success) {
            return ResponseEntity.ok("Cliente guardado correctamente.");
        } else {
            return ResponseEntity.status(500).body("Error guardando el cliente.");
        }
    }

    // Eliminar cliente
    @DeleteMapping
    public ResponseEntity<String> deleteCustomer(@RequestBody CustomerDTO customerDTO) {
        boolean success = customerService.deleteCustomer(customerDTO);
        if (success) {
            return ResponseEntity.ok("Cliente eliminado correctamente.");
        } else {
            return ResponseEntity.status(500).body("Error eliminando el cliente.");
        }
    }
}
