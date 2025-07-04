package com.interfija.masterposmultitenant.controller.tenant.category;

import com.interfija.masterposmultitenant.dto.category.CategoryDTO;
import com.interfija.masterposmultitenant.services.category.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controlador REST para operaciones relacionadas con categorías.
 */
@RestController
@RequestMapping("/api/category")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/{idCategory}")
    public ResponseEntity<CategoryDTO> getCategory(@PathVariable Long idCategory) {
        Optional<CategoryDTO> category = categoryService.getCategory(idCategory);
        return category.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/basic")
    public ResponseEntity<List<CategoryDTO>> getBasicList(@RequestParam(defaultValue = "true") boolean visible) {
        return ResponseEntity.ok(categoryService.getCategoryBasicList(visible));
    }

    @GetMapping("/basic/company/{companyId}")
    public ResponseEntity<List<CategoryDTO>> getBasicListByCompany(
            @PathVariable Long companyId,
            @RequestParam(defaultValue = "true") boolean visible) {
        return ResponseEntity.ok(categoryService.getCategoryBasicList(companyId, visible));
    }

    @GetMapping("/summary")
    public ResponseEntity<List<CategoryDTO>> getSummaryList(@RequestParam(defaultValue = "true") boolean visible) {
        return ResponseEntity.ok(categoryService.getCategorySummaryList(visible));
    }

    @GetMapping("/summary/company/{companyId}")
    public ResponseEntity<List<CategoryDTO>> getSummaryListByCompany(
            @PathVariable Long companyId,
            @RequestParam(defaultValue = "true") boolean visible) {
        return ResponseEntity.ok(categoryService.getCategorySummaryList(companyId, visible));
    }

    @PostMapping("/save")
    public ResponseEntity<Void> saveCategory(@RequestBody CategoryDTO categoryDTO) {
        boolean result = categoryService.saveCategory(categoryDTO);
        return result ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteCategory(@RequestBody CategoryDTO categoryDTO) {
        boolean result = categoryService.deleteCategory(categoryDTO);
        return result ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }
}
