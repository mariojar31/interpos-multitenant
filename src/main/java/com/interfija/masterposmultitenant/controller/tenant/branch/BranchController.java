package com.interfija.masterposmultitenant.controller.tenant.branch;

import com.interfija.masterposmultitenant.dto.branch.BranchDTO;
import com.interfija.masterposmultitenant.services.branch.BranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controlador REST para manejar las operaciones relacionadas con las sucursales.
 */
@RestController
@RequestMapping("/api/branches")
public class BranchController {

    private final BranchService branchService;

    @Autowired
    public BranchController(BranchService branchService) {
        this.branchService = branchService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<BranchDTO> getBranch(@PathVariable Long id) {
        Optional<BranchDTO> branch = branchService.getBranch(id);
        return branch.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/basic")
    public ResponseEntity<List<BranchDTO>> getBranchBasicList(@RequestParam boolean visible) {
        List<BranchDTO> branches = branchService.getBranchBasicList(visible);
        return ResponseEntity.ok(branches);
    }

    @GetMapping("/basic/company/{companyId}")
    public ResponseEntity<List<BranchDTO>> getBranchBasicListByCompany(
            @PathVariable Long companyId,
            @RequestParam boolean visible) {
        List<BranchDTO> branches = branchService.getBranchBasicList(companyId, visible);
        return ResponseEntity.ok(branches);
    }

    @GetMapping("/summary")
    public ResponseEntity<List<BranchDTO>> getBranchSummaryList(@RequestParam boolean visible) {
        List<BranchDTO> branches = branchService.getBranchSummaryList(visible);
        return ResponseEntity.ok(branches);
    }

    @GetMapping("/summary/company/{companyId}")
    public ResponseEntity<List<BranchDTO>> getBranchSummaryListByCompany(
            @PathVariable Long companyId,
            @RequestParam boolean visible) {
        List<BranchDTO> branches = branchService.getBranchSummaryList(companyId, visible);
        return ResponseEntity.ok(branches);
    }

    @PostMapping
    public ResponseEntity<Void> saveBranch(@RequestBody BranchDTO branchDTO) {
        boolean success = branchService.saveBranch(branchDTO);
        return success ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteBranch(@RequestBody BranchDTO branchDTO) {
        boolean success = branchService.deleteBranch(branchDTO);
        return success ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }
}

