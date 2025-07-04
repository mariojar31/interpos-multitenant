package com.interfija.masterposmultitenant.services.sale;

import com.interfija.masterposmultitenant.dto.invoice.InvoiceDTO;
import com.interfija.masterposmultitenant.dto.product.ProductBranchDTO;
import com.interfija.masterposmultitenant.dto.sale.SalePendingDTO;
import com.interfija.masterposmultitenant.entities.tenant.sale.SalePendingEntity;
import com.interfija.masterposmultitenant.mappers.sale.SalePendingMapper;
import com.interfija.masterposmultitenant.repository.product.ProductBranchRepository;
import com.interfija.masterposmultitenant.repository.sale.SalePendingRepository;
import com.interfija.masterposmultitenant.services.base.BaseService;
import com.interfija.masterposmultitenant.services.invoice.InvoiceService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Clase que gestiona las operaciones relacionadas con las ventas y sus permisos dentro del sistema.
 * Esta clase maneja las instancias de acceso a la base de datos para las ventas y sus permisos,
 * permitiendo realizar operaciones como obtener y actualizar ventas y sus permisos asociados.
 *
 * @author Steven Arzuza.
 */
@Service
public class SalesService extends BaseService {

    /**
     * Objeto que maneja las operaciones de acceso a la base de datos para las ventas pendientes.
     */
    private final SalePendingRepository salePendingRepository;

    private final SalePendingMapper salePendingMapper;

    private final ProductBranchRepository stockRepository;

    /**
     * Objeto que maneja las operaciones de servicio a los datos para las facturas.
     */
    @Getter
    private final InvoiceService invoiceService;

    /**
     * Constructor que inicializa el modelo de ventas con la fuente de datos, el DAO de ventas
     * y los datos maestros.
     */
    @Autowired
    public SalesService(SalePendingRepository salePendingRepository, SalePendingMapper salePendingMapper,
                        ProductBranchRepository stockRepository, InvoiceService invoiceService) {
        setLogger(SalesService.class);
        this.salePendingRepository = salePendingRepository;
        this.salePendingMapper = salePendingMapper;
        this.stockRepository = stockRepository;
        this.invoiceService = invoiceService;
    }

    /**
     * Gestiona el proceso de guardar una factura, incluyendo datos del cliente, pagos, productos,
     * empleados y la actualización de los niveles de stock.
     */
    @Transactional
    public boolean saveSale(InvoiceDTO invoiceDTO, List<ProductBranchDTO> productStocksList) {
        boolean success = invoiceService.saveInvoice(invoiceDTO);
        if (!success) {
            return false;
        }

        return updateProductStock(productStocksList);
    }

    /**
     * Actualiza los niveles de existencias del producto según los detalles de la factura.
     *
     * @param productStocksList Lista de objetos InvoiceProductDTO que representan los productos de la factura.
     * @return true si las actualizaciones de existencias se realizan correctamente, false en caso contrario.
     */
    private boolean updateProductStock(List<ProductBranchDTO> productStocksList) {
        for (ProductBranchDTO productBranchDTO : productStocksList) {
            Long idStock = productBranchDTO.getIdProductBranch();
            try {
                stockRepository.updateStockProduct(productBranchDTO.getQuantity(), idStock);
            } catch (Exception e) {
                logger.error("No se pudo actualizar el stock con id '{}' -> {}.", idStock, e.getMessage());
                return false;
            }
        }
        return true;
    }

    /**
     * Elimina una factura en la base de datos.
     *
     * @param invoiceDTO Objeto {@code InvoiceDTO} que representa la factura a eliminar factura.
     * @return {@code true} si la operación fue exitosa, {@code false} en caso contrario.
     */
    @Transactional
    public boolean deleteInvoice(InvoiceDTO invoiceDTO) {
        boolean success = invoiceService.deleteInvoice(invoiceDTO);
        if (!success) {
            return false;
        }

        List<ProductBranchDTO> productStocksList = new ArrayList<>();
        invoiceDTO.getProductsList().forEach(invoiceProductDTO -> {
            ProductBranchDTO productBranchDTO = invoiceProductDTO.getProductBranchDTO();
            productBranchDTO.setIdProductBranch(invoiceProductDTO.getProductBranchDTO().getIdProductBranch());
            productBranchDTO.setQuantity(invoiceProductDTO.getQuantity().multiply(BigDecimal.valueOf(-1)));
            productStocksList.add(productBranchDTO);
        });

        return updateProductStock(productStocksList);
    }

    /**
     * Obtiene la cantidad de ventas pendientes para una sucursal específica.
     *
     * @param branchId el identificador de la sucursal para la cual se desea obtener la cantidad de ventas pendientes.
     * @return la cantidad total de ventas pendientes en la sucursal especificada.
     */
    @Transactional(readOnly = true)
    public int getSalePendingCount(long branchId) {
        return salePendingRepository.findSalePendingCountByBranchId(branchId);
    }

    /**
     * Busca las ventas pendientes en la base de datos.
     *
     * @return Una lista de objetos {@code SalePendingDTO} que representan las ventas pendientes.
     */
    @Transactional(readOnly = true)
    public List<SalePendingDTO> getSalesPendingList(long branchId) {
        List<SalePendingEntity> salePendingList = salePendingRepository.findAllByBranchId(branchId);
        return salePendingMapper.toDtoList(salePendingList);
    }

    /**
     * Busca las ventas pendientes en la base de datos.
     *
     * @return Una lista de objetos {@code SalePendingDTO} que representan las ventas pendientes.
     */
    @Transactional(readOnly = true)
    public List<SalePendingDTO> getSalesPendingByTablesList(long branchId) {
        List<SalePendingEntity> salePendingList = salePendingRepository.findAllByTables(branchId);
        return salePendingMapper.toDtoList(salePendingList);
    }

    /**
     * Busca una factura pendiente en la base de datos.
     *
     * @param idSalePending identificador único de la factura.
     * @return Un objeto {@code String} que representan la factura pendiente en formato JSON.
     */
    @Transactional(readOnly = true)
    public Optional<String> getInvoiceSalePending(String idSalePending) {
        return salePendingRepository.findInvoiceById(idSalePending);
    }

    /**
     * Inserta o actualiza una venta pendiente en la base de datos.
     *
     * @param salePendingDTO Objeto que contiene la información de la venta.
     * @return {@code true} si la operación fue exitosa, {@code false} en caso contrario.
     */
    @Transactional
    public boolean saveSalePending(SalePendingDTO salePendingDTO) {
        if (salePendingDTO.getIdSalePending() == null) {
            salePendingDTO.setIdSalePending(UUID.randomUUID().toString());
            return insertSalePending(salePendingDTO);
        } else {
            return updateSalePending(salePendingDTO);
        }
    }

    @Transactional
    protected boolean insertSalePending(SalePendingDTO salePendingDTO) {
        try {
            SalePendingEntity salePendingEntity = salePendingMapper.toEntity(salePendingDTO);
            salePendingRepository.save(salePendingEntity);
            return salePendingEntity.getIdSalePending() != null;
        } catch (Exception e) {
            logger.error("No se pudo insertar la venta pendiente -> {}.", e.getMessage());
            return false;
        }
    }

    @Transactional
    protected boolean updateSalePending(SalePendingDTO salePendingDTO) {
        try {
            SalePendingEntity salePendingEntity = salePendingMapper.toEntity(salePendingDTO);
            salePendingRepository.save(salePendingEntity);
            return true;
        } catch (Exception e) {
            logger.error("No se pudo actualizar la venta pendiente -> {}.", e.getMessage());
            return false;
        }
    }

    @Transactional
    public boolean deleteSalePending(SalePendingDTO salePendingDTO) {
        try {
            salePendingRepository.delete(salePendingMapper.toEntity(salePendingDTO));
            return true;
        } catch (Exception e) {
            logger.error("No se pudo eliminar la venta pendiente -> {}.", e.getMessage());
            return false;
        }
    }

}
