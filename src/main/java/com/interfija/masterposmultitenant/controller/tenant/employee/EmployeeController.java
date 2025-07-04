package com.interfija.masterposmultitenant.controller.tenant.employee;

import com.interfija.masterposmultitenant.dto.employee.EmployeeDTO;
import com.interfija.masterposmultitenant.services.employee.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tenant/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable Long id) {
        Optional<EmployeeDTO> employeeOpt = employeeService.getEmployee(id);
        return employeeOpt.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/company/{companyId}")
    public ResponseEntity<List<EmployeeDTO>> getEmployeesByCompany(
            @PathVariable Long companyId,
            @RequestParam(defaultValue = "true") boolean visible) {
        List<EmployeeDTO> employees = employeeService.getEmployeeSummaryList(companyId, visible);
        return ResponseEntity.ok(employees);
    }

    @PostMapping
    public ResponseEntity<String> saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        boolean saved = employeeService.saveEmployee(employeeDTO);
        if (saved) {
            return ResponseEntity.ok("Empleado guardado correctamente");
        } else {
            return ResponseEntity.status(500).body("Error guardando el empleado");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable Long id) {
        Optional<EmployeeDTO> employeeOpt = employeeService.getEmployee(id);
        if (employeeOpt.isPresent()) {
            boolean deleted = employeeService.deleteEmployee(employeeOpt.get());
            if (deleted) {
                return ResponseEntity.ok("Empleado eliminado correctamente");
            } else {
                return ResponseEntity.status(500).body("Error eliminando el empleado");
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
