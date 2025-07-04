package com.interfija.masterposmultitenant.controller.tenant.role;

import com.interfija.masterposmultitenant.dto.role.PermissionDTO;
import com.interfija.masterposmultitenant.dto.role.RoleDTO;
import com.interfija.masterposmultitenant.dto.role.RolePermissionDTO;
import com.interfija.masterposmultitenant.services.role.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Controlador REST para la gestión de roles y permisos.
 * Expone endpoints para la obtención y actualización de roles y permisos asociados.
 *
 * Autor: OpenAI para Steven Arzuza
 */
@RestController
@RequestMapping("/api/tenant/roles")
public class RoleController {

    private final RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    /**
     * Obtiene todos los roles del sistema.
     */
    @GetMapping
    public List<RoleDTO> getAllRoles() {
        return roleService.getRolesList();
    }

    /**
     * Obtiene roles excluyendo algunos dependiendo del rol que llama.
     */
    @PostMapping("/filtered")
    public List<RoleDTO> getFilteredRoles(@RequestBody RoleDTO roleDTO) {
        return roleService.getRolesList(roleDTO);
    }

    /**
     * Obtiene los permisos de un rol específico.
     */
    @PostMapping("/permissions")
    public List<PermissionDTO> getPermissionsByRole(@RequestBody RoleDTO roleDTO) {
        return roleService.getPermissionsList(roleDTO);
    }

    /**
     * Obtiene los permisos de un rol específico para una sucursal.
     */
    @PostMapping("/permissions/{branchId}")
    public List<PermissionDTO> getPermissionsByBranchAndRole(@PathVariable Long branchId, @RequestBody RoleDTO roleDTO) {
        return roleService.getPermissionsList(branchId, roleDTO);
    }

    /**
     * Obtiene un mapa de permisos asociados a un rol.
     */
    @GetMapping("/{roleId}/permissions/map")
    public Map<Short, PermissionDTO> getPermissionsMap(@PathVariable Long roleId) {
        return roleService.getPermissionsMap(roleId);
    }

    /**
     * Actualiza (inserta/elimina) permisos asociados a un rol.
     */
    @PostMapping("/permissions/update")
    public boolean updateRolePermissions(@RequestBody List<RolePermissionDTO> rolePermissionsList) {
        return roleService.updatePermissions(rolePermissionsList);
    }
}
