package com.interfija.masterposmultitenant.services.master;

import com.interfija.masterposmultitenant.dto.other.DepartmentDTO;
import com.interfija.masterposmultitenant.dto.other.IdentificationTypeDTO;
import com.interfija.masterposmultitenant.dto.other.MunicipalityDTO;
import com.interfija.masterposmultitenant.dto.other.PaymentFormDTO;
import com.interfija.masterposmultitenant.dto.other.TaxDTO;
import com.interfija.masterposmultitenant.dto.other.TypeOrganizationDTO;
import com.interfija.masterposmultitenant.dto.other.TypePaymentDTO;
import com.interfija.masterposmultitenant.dto.other.TypeRegimeDTO;
import com.interfija.masterposmultitenant.dto.other.TypeResponsibilityDTO;
import com.interfija.masterposmultitenant.dto.other.TypeTaxDTO;
import com.interfija.masterposmultitenant.dto.other.TypeUnitDTO;
import com.interfija.masterposmultitenant.dto.other.BarcodeTypeDTO;
import com.interfija.masterposmultitenant.mappers.other.BarcodeTypeMapper;
import com.interfija.masterposmultitenant.mappers.other.DepartmentMapper;
import com.interfija.masterposmultitenant.mappers.other.IdentificationTypeMapper;
import com.interfija.masterposmultitenant.mappers.other.MunicipalityMapper;
import com.interfija.masterposmultitenant.mappers.other.PaymentFormMapper;
import com.interfija.masterposmultitenant.mappers.other.TaxMapper;
import com.interfija.masterposmultitenant.mappers.other.TypeOrganizationMapper;
import com.interfija.masterposmultitenant.mappers.other.TypePaymentMapper;
import com.interfija.masterposmultitenant.mappers.other.TypeRegimeMapper;
import com.interfija.masterposmultitenant.mappers.other.TypeResponsibilityMapper;
import com.interfija.masterposmultitenant.mappers.other.TypeTaxMapper;
import com.interfija.masterposmultitenant.mappers.other.TypeUnitMapper;
import com.interfija.masterposmultitenant.master.MasterDataEnum;
import com.interfija.masterposmultitenant.repository.other.BarcodeTypeRepository;
import com.interfija.masterposmultitenant.repository.other.DepartmentRepository;
import com.interfija.masterposmultitenant.repository.other.IdentificationTypeRepository;
import com.interfija.masterposmultitenant.repository.other.MunicipalityRepository;
import com.interfija.masterposmultitenant.repository.other.PaymentFormRepository;
import com.interfija.masterposmultitenant.repository.other.TaxRepository;
import com.interfija.masterposmultitenant.repository.other.TypeOrganizationRepository;
import com.interfija.masterposmultitenant.repository.other.TypePaymentRepository;
import com.interfija.masterposmultitenant.repository.other.TypeRegimeRepository;
import com.interfija.masterposmultitenant.repository.other.TypeResponsibilityRepository;
import com.interfija.masterposmultitenant.repository.other.TypeTaxRepository;
import com.interfija.masterposmultitenant.repository.other.TypeUnitRepository;
import com.interfija.masterposmultitenant.services.base.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Service
public class MasterDataService extends BaseService {

    private final BarcodeTypeRepository barcodeTypeRepository;
    private final BarcodeTypeMapper barcodeTypeMapper;
    private final DepartmentRepository departmentRepository;
    private final DepartmentMapper departmentMapper;
    private final MunicipalityRepository municipalityRepository;
    private final MunicipalityMapper municipalityMapper;
    private final TypeOrganizationRepository typeOrganizationRepository;
    private final TypeOrganizationMapper typeOrganizationMapper;
    private final TypeRegimeRepository typeRegimeRepository;
    private final TypeRegimeMapper typeRegimeMapper;
    private final TypeResponsibilityRepository typeResponsibilityRepository;
    private final TypeResponsibilityMapper typeResponsibilityMapper;
    private final TypeTaxRepository typeTaxRepository;
    private final TypeTaxMapper typeTaxMapper;
    private final TaxRepository taxRepository;
    private final TaxMapper taxMapper;
    private final TypeUnitRepository typeUnitRepository;
    private final TypeUnitMapper typeUnitMapper;
    private final IdentificationTypeRepository identificationTypeRepository;
    private final IdentificationTypeMapper identificationTypeMapper;
    private final TypePaymentRepository typePaymentRepository;
    private final TypePaymentMapper typePaymentMapper;
    private final PaymentFormRepository paymentFormRepository;
    private final PaymentFormMapper paymentFormMapper;

    @Autowired
    public MasterDataService(BarcodeTypeRepository barcodeTypeRepository, BarcodeTypeMapper barcodeTypeMapper,
                             DepartmentRepository departmentRepository, DepartmentMapper departmentMapper,
                             MunicipalityRepository municipalityRepository, MunicipalityMapper municipalityMapper,
                             TypeOrganizationRepository typeOrganizationRepository, TypeOrganizationMapper typeOrganizationMapper,
                             TypeRegimeRepository typeRegimeRepository, TypeRegimeMapper typeRegimeMapper,
                             TypeResponsibilityRepository typeResponsibilityRepository, TypeResponsibilityMapper typeResponsibilityMapper,
                             TypeTaxRepository typeTaxRepository, TypeTaxMapper typeTaxMapper,
                             TaxRepository taxRepository, TaxMapper taxMapper,
                             TypeUnitRepository typeUnitRepository, TypeUnitMapper typeUnitMapper,
                             IdentificationTypeRepository identificationTypeRepository, IdentificationTypeMapper identificationTypeMapper,
                             TypePaymentRepository typePaymentRepository, TypePaymentMapper typePaymentMapper,
                             PaymentFormRepository paymentFormRepository, PaymentFormMapper paymentFormMapper) {

        setLogger(MasterDataService.class);
        this.barcodeTypeRepository = barcodeTypeRepository;
        this.barcodeTypeMapper = barcodeTypeMapper;
        this.departmentRepository = departmentRepository;
        this.departmentMapper = departmentMapper;
        this.municipalityRepository = municipalityRepository;
        this.municipalityMapper = municipalityMapper;
        this.typeOrganizationRepository = typeOrganizationRepository;
        this.typeOrganizationMapper = typeOrganizationMapper;
        this.typeRegimeRepository = typeRegimeRepository;
        this.typeRegimeMapper = typeRegimeMapper;
        this.typeResponsibilityRepository = typeResponsibilityRepository;
        this.typeResponsibilityMapper = typeResponsibilityMapper;
        this.typeTaxRepository = typeTaxRepository;
        this.typeTaxMapper = typeTaxMapper;
        this.taxRepository = taxRepository;
        this.taxMapper = taxMapper;
        this.typeUnitRepository = typeUnitRepository;
        this.typeUnitMapper = typeUnitMapper;
        this.identificationTypeRepository = identificationTypeRepository;
        this.identificationTypeMapper = identificationTypeMapper;
        this.typePaymentRepository = typePaymentRepository;
        this.typePaymentMapper = typePaymentMapper;
        this.paymentFormRepository = paymentFormRepository;
        this.paymentFormMapper = paymentFormMapper;
    }

    @Transactional(readOnly = true)
    public List<BarcodeTypeDTO> findAllBarcodeTypes() {
        try {
            return barcodeTypeMapper.toDtoList(barcodeTypeRepository.findAll());
        } catch (Exception e) {
            logger.error("No se pudo obtener los tipos de código de barras -> {}.", e.getMessage());
            return Collections.emptyList();
        }
    }

    @Transactional(readOnly = true)
    public List<DepartmentDTO> findAllDepartments() {
        try {
            return departmentMapper.toDtoList(departmentRepository.findAll());
        } catch (Exception e) {
            logger.error("No se pudo obtener los departamentos -> {}.", e.getMessage());
            return Collections.emptyList();
        }
    }

    @Transactional(readOnly = true)
    public List<MunicipalityDTO> findAllMunicipalities() {
        try {
            return municipalityMapper.toDtoList(municipalityRepository.findAll());
        } catch (Exception e) {
            logger.error("No se pudo obtener los municipios -> {}.", e.getMessage());
            return Collections.emptyList();
        }
    }

    @Transactional(readOnly = true)
    public List<MunicipalityDTO> findAllMunicipalitiesByDepartmentId(Short departmentId) {
        try {
            return municipalityMapper.toDtoList(municipalityRepository.findByDepartmentId(departmentId));
        } catch (Exception e) {
            logger.error("No se pudo obtener los municipios por departamento {} -> {}.", departmentId, e.getMessage());
            return Collections.emptyList();
        }
    }

    @Transactional(readOnly = true)
    public List<TypeOrganizationDTO> findAllTypeOrganizations() {
        try {
            return typeOrganizationMapper.toDtoList(typeOrganizationRepository.findAll());
        } catch (Exception e) {
            logger.error("No se pudo obtener los tipos de organización -> {}.", e.getMessage());
            return Collections.emptyList();
        }
    }

    @Transactional(readOnly = true)
    public List<TypeRegimeDTO> findAllTypeRegimes() {
        try {
            return typeRegimeMapper.toDtoList(typeRegimeRepository.findAll());
        } catch (Exception e) {
            logger.error("No se pudo obtener los tipos de régimen -> {}.", e.getMessage());
            return Collections.emptyList();
        }
    }

    @Transactional(readOnly = true)
    public List<TypeResponsibilityDTO> findAllTypeResponsibilities() {
        try {
            return typeResponsibilityMapper.toDtoList(typeResponsibilityRepository.findAll());
        } catch (Exception e) {
            logger.error("No se pudo obtener los tipos de responsabilidad -> {}.", e.getMessage());
            return Collections.emptyList();
        }
    }

    @Transactional(readOnly = true)
    public List<TypeTaxDTO> findAllTypeTaxes() {
        try {
            return typeTaxMapper.toDtoList(typeTaxRepository.findAll());
        } catch (Exception e) {
            logger.error("No se pudo obtener los tipos de impuestos -> {}.", e.getMessage());
            return Collections.emptyList();
        }
    }

    @Transactional(readOnly = true)
    public List<TaxDTO> findAllTaxes() {
        try {
            return taxMapper.toDtoList(taxRepository.findAll());
        } catch (Exception e) {
            logger.error("No se pudo obtener los impuestos -> {}.", e.getMessage());
            return Collections.emptyList();
        }
    }

    @Transactional(readOnly = true)
    public List<TypeUnitDTO> findAllTypeUnits() {
        try {
            return typeUnitMapper.toDtoList(typeUnitRepository.findAll());
        } catch (Exception e) {
            logger.error("No se pudo obtener los tipos de unidades -> {}.", e.getMessage());
            return Collections.emptyList();
        }
    }

    @Transactional(readOnly = true)
    public List<IdentificationTypeDTO> findAllIdentificationsType() {
        try {
            return identificationTypeMapper.toDtoList(identificationTypeRepository.findAll());
        } catch (Exception e) {
            logger.error("No se pudo obtener los tipos de identificación -> {}.", e.getMessage());
            return Collections.emptyList();
        }
    }

    @Transactional(readOnly = true)
    public List<TypePaymentDTO> findAllTypePayments() {
        try {
            return typePaymentMapper.toDtoList(typePaymentRepository.findAll());
        } catch (Exception e) {
            logger.error("No se pudo obtener los tipos de pago -> {}.", e.getMessage());
            return Collections.emptyList();
        }
    }

    @Transactional(readOnly = true)
    public List<PaymentFormDTO> findAllPaymentsForm() {
        try {
            return paymentFormMapper.toDtoList(paymentFormRepository.findAll());
        } catch (Exception e) {
            logger.error("No se pudo obtener las formas de pago -> {}.", e.getMessage());
            return Collections.emptyList();
        }
    }

    @Transactional(readOnly = true)
    public Map<MasterDataEnum, List<?>> findGroupedMasterData(List<MasterDataEnum> keys) {
        Map<MasterDataEnum, List<?>> result = new EnumMap<>(MasterDataEnum.class);

        for (MasterDataEnum key : keys) {
            switch (key) {
                case TYPE_TAXES_LIST -> result.put(key, findAllTypeTaxes());
                case TAXES_LIST -> result.put(key, findAllTaxes());
                case UNITS_LIST -> result.put(key, findAllTypeUnits());
                case BARCODE_TYPES_LIST -> result.put(key, findAllBarcodeTypes());
                case TYPE_REGIMES_LIST -> result.put(key, findAllTypeRegimes());
                case TYPE_ORGANIZATIONS_LIST -> result.put(key, findAllTypeOrganizations());
                case TYPE_RESPONSIBILITIES_LIST -> result.put(key, findAllTypeResponsibilities());
                case DEPARTMENTS_LIST -> result.put(key, findAllDepartments());
                case MUNICIPALITIES_LIST -> result.put(key, findAllMunicipalities());
                case IDENTIFICATION_TYPES_LIST -> result.put(key, findAllIdentificationsType());
                case TYPE_PAYMENTS_LIST -> result.put(key, findAllTypePayments());
                case PAYMENTS_FORM_LIST -> result.put(key, findAllPaymentsForm());
                default -> throw new IllegalArgumentException("Clave de lista no soportada: " + key.getMaster());
            }
        }

        return result;
    }

}
