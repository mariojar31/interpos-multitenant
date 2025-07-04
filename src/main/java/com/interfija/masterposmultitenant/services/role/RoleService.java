package com.interfija.masterposmultitenant.services.role;

import com.interfija.masterposmultitenant.dto.role.PermissionDTO;
import com.interfija.masterposmultitenant.dto.role.RoleDTO;
import com.interfija.masterposmultitenant.dto.role.RolePermissionDTO;
import com.interfija.masterposmultitenant.entities.tenant.role.PermissionEntity;
import com.interfija.masterposmultitenant.entities.tenant.role.RoleEntity;
import com.interfija.masterposmultitenant.entities.tenant.role.RolePermissionEntity;
import com.interfija.masterposmultitenant.mappers.role.PermissionMapper;
import com.interfija.masterposmultitenant.mappers.role.RoleMapper;
import com.interfija.masterposmultitenant.mappers.role.RolePermissionMapper;
import com.interfija.masterposmultitenant.repository.role.PermissionRepository;
import com.interfija.masterposmultitenant.repository.role.RolePermissionRepository;
import com.interfija.masterposmultitenant.repository.role.RoleRepository;
import com.interfija.masterposmultitenant.services.base.BaseService;
import com.interfija.masterposmultitenant.utils.ActionEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import static com.interfija.masterposmultitenant.utils.ActionEnum.DELETE;
import static com.interfija.masterposmultitenant.utils.ActionEnum.INSERT;

/**
 * Clase que gestiona las operaciones relacionadas con los roles y sus permisos dentro del sistema.
 * Esta clase maneja las instancias de acceso a la base de datos para los roles y sus permisos,
 * permitiendo realizar operaciones como obtener y actualizar roles y sus permisos asociados.
 *
 * @author Steven Arzuza.
 */
@Service
public class RoleService extends BaseService {

    /**
     * Objeto que maneja las operaciones de acceso a la base de datos para roles.
     */
    private final RoleRepository roleRepository;

    /**
     * Mapper para convertir entre entidades {@link RoleEntity} y {@link RoleDTO} relacionados.
     */
    private final RoleMapper roleMapper;

    /**
     * Objeto que maneja las operaciones de acceso a la base de datos para los permisos.
     */
    private final PermissionRepository permissionRepository;

    /**
     * Mapper para convertir entre entidades {@link PermissionEntity} y {@link PermissionDTO} relacionados.
     */
    private final PermissionMapper permissionMapper;

    /**
     * Objeto que maneja las operaciones de acceso a la base de datos para los roles con sus permisos.
     */
    private final RolePermissionRepository rolePermissionRepository;

    /**
     * Mapper para convertir entre entidades {@link RolePermissionEntity} y {@link RolePermissionDTO} relacionados.
     */
    private final RolePermissionMapper rolePermissionMapper;

    /**
     * Constructor que inicializa los objetos necesarios para la gestión de roles.
     * Inicializa la fuente de datos, el DAO de roles y los datos maestros.
     */
    @Autowired
    public RoleService(RoleRepository roleRepository, RoleMapper roleMapper,
                       PermissionRepository permissionRepository, PermissionMapper permissionMapper,
                       RolePermissionRepository rolePermissionRepository, RolePermissionMapper rolePermissionMapper) {
        setLogger(RoleService.class);
        this.roleRepository = roleRepository;
        this.roleMapper = roleMapper;
        this.permissionRepository = permissionRepository;
        this.permissionMapper = permissionMapper;
        this.rolePermissionRepository = rolePermissionRepository;
        this.rolePermissionMapper = rolePermissionMapper;
    }

    /**
     * Obtiene la lista de todos los roles disponibles.
     *
     * @return una lista de objetos RoleDTO que representan todos los roles.
     */
    @Transactional(readOnly = true)
    public List<RoleDTO> getRolesList() {
        List<RoleEntity> rolesList = roleRepository.findAll();
        return roleMapper.toDtoList(rolesList);
    }

    /**
     * Obtiene la lista de roles que coinciden con los criterios especificados.
     *
     * @param roleDTO los criterios utilizados para filtrar la lista de roles.
     * @return una lista de objetos RoleDTO que coinciden con los criterios dados.
     */
    @Transactional(readOnly = true)
    public List<RoleDTO> getRolesList(RoleDTO roleDTO) {
        short roleId = roleDTO.getIdRole();

        // Si el rol es 1, solo se excluye el 1
        List<Short> excludedIds = (roleId > 1)
                ? LongStream.rangeClosed(1, roleId)
                .mapToObj(i -> (short) i)
                .collect(Collectors.toList())
                : List.of((short) 1);

        List<RoleEntity> rolesList = roleRepository.findAllExcludingIds(excludedIds);
        return roleMapper.toDtoList(rolesList);
    }

    /**
     * Obtiene la lista de permisos asociados a un rol específico.
     *
     * @param roleDTO el rol para el cual se recuperarán los permisos.
     * @return una lista de objetos PermissionDTO que representan los permisos del rol dado.
     */
    @Transactional(readOnly = true)
    public List<PermissionDTO> getPermissionsList(RoleDTO roleDTO) {
        try {
            return rolePermissionRepository.findAllPermissionsByRoleId((long) roleDTO.getIdRole())
                    .stream()
                    .map(permissionMapper::toDto)
                    .toList();
        } catch (Exception e) {
            logger.error("No se pudo obtener los permisos del rol -> {}.", e.getMessage());
            return Collections.emptyList();
        }
    }

    /**
     * Obtiene la lista de permisos asociados a un rol específico.
     *
     * @param roleDTO el rol para el cual se recuperarán los permisos.
     * @return una lista de objetos PermissionDTO que representan los permisos del rol dado.
     */
    @Transactional(readOnly = true)
    public List<PermissionDTO> getPermissionsList(Long branchId, RoleDTO roleDTO) {
        try {
            return rolePermissionRepository.findAllPermissionsByBranchIdAndRoleId(branchId, (long) roleDTO.getIdRole())
                    .stream()
                    .map(permissionMapper::toDto)
                    .toList();
        } catch (Exception e) {
            logger.error("No se pudo obtener los permisos del rol por sucursal -> {}.", e.getMessage());
            return Collections.emptyList();
        }
    }

    /**
     * Obtiene un mapa de los permisos por rol registrados en el sistema.
     *
     * @param idRole identificador único del rol.
     * @return Un mapa de objetos PermissionDTO que representan los tipos de permisos registrados para el rol.
     */
    @Transactional(readOnly = true)
    public Map<Short, PermissionDTO> getPermissionsMap(Long idRole) {
        try {
            List<PermissionEntity> permissionList = rolePermissionRepository.findAllPermissionsByRoleId(idRole);
            Map<Short, PermissionDTO> permissionMap = new HashMap<>();
            for (PermissionEntity permission : permissionList) {
                permissionMap.put(permission.getIdPermission(), permissionMapper.toDto(permission));
            }
            return permissionMap;
        } catch (Exception e) {
            logger.error("No se pudo obtener el mapa de permisos del rol -> {}.", e.getMessage());
            return Collections.emptyMap();
        }
    }

    /**
     * Inserta o elimina los permisos del rol registrado en el sistema.
     *
     * @param rolePermissionsList lista de permisos asociados al rol.
     * @return {@code true} si la operación fue exitosa, {@code false} en caso contrario.
     */
    @Transactional
    public boolean updatePermissions(List<RolePermissionDTO> rolePermissionsList) {
        Map<ActionEnum, List<RolePermissionDTO>> groupedByAction = rolePermissionsList.stream()
                .collect(Collectors.groupingBy(RolePermissionDTO::getAction));

        try {
            if (groupedByAction.containsKey(INSERT)) {
                List<RolePermissionEntity> insertList = rolePermissionMapper.toEntityList(groupedByAction.get(INSERT));
                rolePermissionRepository.saveAll(insertList);
            }
        } catch (Exception e) {
            logger.error("No se pudo insertar los permisos -> {}", e.getMessage());
            return false;
        }

        try {
            if (groupedByAction.containsKey(DELETE)) {
                List<RolePermissionEntity> deleteList = rolePermissionMapper.toEntityList(groupedByAction.get(DELETE));
                rolePermissionRepository.deleteAll(deleteList);
            }
        } catch (Exception e) {
            logger.error("No se pudo eliminar los permisos -> {}", e.getMessage());
            return false;
        }

        return true;
    }

}
