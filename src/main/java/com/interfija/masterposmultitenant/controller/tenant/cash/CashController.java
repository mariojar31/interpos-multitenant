package com.interfija.masterposmultitenant.controller.tenant.cash;

import com.interfija.masterposmultitenant.dto.cash.CashDTO;
import com.interfija.masterposmultitenant.dto.cash.CashSummaryDTO;
import com.interfija.masterposmultitenant.dto.floor.TerminalDTO;
import com.interfija.masterposmultitenant.services.cash.CashService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controlador REST para operaciones relacionadas con cajas.
 */
@RestController
@RequestMapping("/api/cash")
public class CashController {

    private final CashService cashService;

    @Autowired
    public CashController(CashService cashService) {
        this.cashService = cashService;
    }

    @GetMapping("/{idCash}")
    public ResponseEntity<CashDTO> getCashById(
            @PathVariable long idCash,
            @RequestParam(defaultValue = "false") boolean fullMapping) {
        Optional<CashDTO> cash = cashService.getCashById(idCash, fullMapping);
        return cash.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/open/{idTerminal}")
    public ResponseEntity<CashDTO> getOpenCashByTerminalId(
            @PathVariable long idTerminal,
            @RequestParam(defaultValue = "false") boolean fullMapping) {
        Optional<CashDTO> cash = cashService.getOpenCashByTerminalId(idTerminal, fullMapping);
        return cash.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/summaries/{idTerminal}")
    public ResponseEntity<List<CashSummaryDTO>> getCashSummaries(@PathVariable long idTerminal) {
        List<CashSummaryDTO> summaries = cashService.getCashSummaries(idTerminal);
        return ResponseEntity.ok(summaries);
    }

    @PostMapping("/insert")
    public ResponseEntity<CashDTO> insertCash(@RequestParam int sequence, @RequestBody TerminalDTO terminalDTO) {
        Optional<CashDTO> newCash = cashService.insertCash(sequence, terminalDTO);
        return newCash.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @PostMapping("/close")
    public ResponseEntity<CashDTO> closeCash(@RequestBody CashDTO cashDTO) {
        Optional<CashDTO> newCash = cashService.closeCash(cashDTO);
        return newCash.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.badRequest().build());
    }
}
