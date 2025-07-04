package com.interfija.masterposmultitenant.services.product;

import com.interfija.masterposmultitenant.dto.branch.BranchDTO;
import com.interfija.masterposmultitenant.dto.other.TypeUnitDTO;
import com.interfija.masterposmultitenant.dto.product.ProductBranchStockDTO;
import com.interfija.masterposmultitenant.mappers.product.ProductBranchMapper;
import com.interfija.masterposmultitenant.repository.product.ProductBranchRepository;
import com.interfija.masterposmultitenant.repository.product.projections.ProductBranchStockProjection;
import com.interfija.masterposmultitenant.services.base.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * Clase que gestiona las operaciones relacionadas con las sucursales de los productos dentro del sistema.
 * Esta clase maneja las instancias de acceso a la base de datos para las sucursales de los productos,
 * permitiendo realizar operaciones como obtener y actualizar productos asociados.
 *
 * @author Steven Arzuza.
 */
@Service
public class ProductBranchService extends BaseService {

    private final ProductBranchRepository productBranchRepository;

    private final ProductBranchMapper productBranchMapper;

    @Autowired
    public ProductBranchService(ProductBranchRepository productBranchRepository, ProductBranchMapper productBranchMapper) {
        setLogger(ProductBranchService.class);
        this.productBranchRepository = productBranchRepository;
        this.productBranchMapper = productBranchMapper;
    }

    @Transactional(readOnly = true)
    public List<ProductBranchStockDTO> getStockByProductId(long productId) {
        List<ProductBranchStockProjection> projections = productBranchRepository.findStockByProductId(productId);

        return projections.stream()
                .map(p -> {
                    TypeUnitDTO typeUnitDTO = TypeUnitDTO.builder()
                            .idTypeUnit(p.getTypeUnitId())
                            .name(p.getTypeUnitName())
                            .abbreviation(p.getTypeUnitAbbreviation())
                            .baseValue(p.getTypeUnitBaseValue())
                            .build();


                    BranchDTO branchDTO = BranchDTO.builder()
                            .idBranch(p.getBranchId())
                            .name(p.getBranchName())
                            .build();

                    return ProductBranchStockDTO.builder()
                            .idProductBranch(p.getIdProductBranch())
                            .quantity(p.getQuantity())
                            .typeUnitDTO(typeUnitDTO)
                            .branchDTO(branchDTO)
                            .build();
                })
                .toList();
    }

    @Transactional
    public boolean updateStock(BigDecimal quantity, long idProductBranch) {
        try {
            boolean updated = productBranchRepository.updateStockProduct(quantity, idProductBranch) > 0;
            if (!updated) {
                throw new Exception("no se modifico ninguna fila");
            }
            return true;
        } catch (Exception e) {
            logger.error("No se pudo actualizar el stock con id '{}' -> {}.", idProductBranch, e.getMessage());
            return false;
        }
    }

    @Transactional
    public boolean transferStock(BigDecimal quantity, long idProductBranchCurrent, long idProductBranchDestination) {
        try {
            boolean updatedCurrent = productBranchRepository.updateStockProduct(quantity.multiply(BigDecimal.valueOf(-1)), idProductBranchCurrent) > 0;
            if (!updatedCurrent) {
                throw new Exception("no se modifico ninguna fila en el actual");
            }

            boolean updatedDestination = productBranchRepository.updateStockProduct(quantity, idProductBranchDestination) > 0;
            if (!updatedDestination) {
                throw new Exception("no se modifico ninguna fila en el destino");
            }
            return true;
        } catch (Exception e) {
            logger.error("No se pudo transpasar el stock con id '{}' a id '{}' -> {}.",
                    idProductBranchCurrent, idProductBranchDestination, e.getMessage());
            return false;
        }
    }

}
