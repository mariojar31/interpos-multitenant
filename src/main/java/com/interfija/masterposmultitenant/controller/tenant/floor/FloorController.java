package com.interfija.masterposmultitenant.controller.tenant.floor;

import com.interfija.masterposmultitenant.dto.floor.FloorDTO;
import com.interfija.masterposmultitenant.dto.floor.TableDTO;
import com.interfija.masterposmultitenant.dto.floor.TerminalDTO;
import com.interfija.masterposmultitenant.services.floor.FloorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tenant/floors")
public class FloorController {

    private final FloorService floorService;

    @Autowired
    public FloorController(FloorService floorService) {
        this.floorService = floorService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<FloorDTO> getFloorBasic(@PathVariable Long id) {
        Optional<FloorDTO> floor = floorService.getFloorBasic(id);
        return floor.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/full")
    public ResponseEntity<FloorDTO> getFloorFull(@PathVariable Long id) {
        Optional<FloorDTO> floor = floorService.getFloorFull(id);
        return floor.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<FloorDTO>> getFloorsBasic(@RequestParam(defaultValue = "true") boolean visible) {
        return ResponseEntity.ok(floorService.getFloorBasicList(visible));
    }

    @GetMapping("/branch/{branchId}")
    public ResponseEntity<List<FloorDTO>> getFloorsByBranch(@PathVariable Long branchId,
                                                            @RequestParam(defaultValue = "true") boolean visible) {
        return ResponseEntity.ok(floorService.getFloorBasicList(branchId, visible));
    }

    @PostMapping
    public ResponseEntity<String> saveFloor(@RequestBody FloorDTO floorDTO) {
        boolean success = floorService.saveFloor(floorDTO);
        return success ?
                ResponseEntity.ok("Piso guardado correctamente.") :
                ResponseEntity.status(500).body("Error al guardar el piso.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteFloor(@PathVariable Long id) {
        FloorDTO dto = FloorDTO.builder().idFloor(id).build();
        boolean success = floorService.deleteFloor(dto);
        return success ?
                ResponseEntity.ok("Piso eliminado correctamente.") :
                ResponseEntity.status(500).body("Error al eliminar el piso.");
    }

    @GetMapping("/{floorId}/tables")
    public ResponseEntity<List<TableDTO>> getTables(@PathVariable Long floorId,
                                                    @RequestParam(defaultValue = "true") boolean visible) {
        return ResponseEntity.ok(floorService.getTablesList(floorId, visible));
    }

    @GetMapping("/{floorId}/terminals")
    public ResponseEntity<List<TerminalDTO>> getTerminals(@PathVariable Long floorId,
                                                          @RequestParam(defaultValue = "true") boolean visible,
                                                          @RequestParam(defaultValue = "false") boolean fullMapping) {
        return ResponseEntity.ok(floorService.getTerminalsList(floorId, visible, fullMapping));
    }
}
