package com.interfija.masterposmultitenant.services.license;

import com.interfija.masterposmultitenant.dto.license.*;
import com.interfija.masterposmultitenant.utils.HttpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;

public class LicenseService {
    private static final Logger logger = LoggerFactory.getLogger(LicenseService.class);
    private final String BASE_API_URL;

    public LicenseService(String baseApiUrl) {
        this.BASE_API_URL = baseApiUrl;
    }

    public record ActivationResponse(LicenseDTO license, String message){}

    public LicenseResponseDTO requestLicense(LicenseRequestDTO licenseDto) {
        try {
            String url = BASE_API_URL + "/activate";
            LicenseResponseDTO response = HttpUtil.post(url, licenseDto, LicenseResponseDTO.class);

            if (response == null) {
                logger.error("Error en la solicitud de licencia: No se recibió respuesta del servidor");
            }

            return response;
        } catch (Exception e) {
            logger.error("Error en la solicitud de licencia: ", e);
            return new LicenseResponseDTO(e.getMessage(),null);
        }
    }

    public LicenseVerificationDTO verifyLicense(String licenseKey, String macAddress, String motherboardSerial) {
        try {
            LicenseVerificationRequestDTO verificationRequest = new LicenseVerificationRequestDTO(licenseKey, macAddress, motherboardSerial);
            String url = BASE_API_URL + "/validate";
            LicenseVerificationDTO response = HttpUtil.post(url, verificationRequest, LicenseVerificationDTO.class);

            if (response == null) {
                logger.error("Error en la verificación: No se recibió respuesta del servidor");
                return new LicenseVerificationDTO("Error de conexión",false,"500");
            }

            return response;
        } catch (Exception e) {
            logger.error("Error en la verificación: ", e);
            return new LicenseVerificationDTO("No se pudo verificar la licencia",false,"500");
        }
    }

    public LicenseDTO requestTrialLicense(String macAddress, Long companyNit, int activationDays) {
        try {
            TrialLicenseRequestDTO trialLicense = new TrialLicenseRequestDTO(macAddress, LocalDate.now().toString(), companyNit, activationDays);
            String url = BASE_API_URL + "/trial";
            LicenseDTO response = HttpUtil.post(url, trialLicense, LicenseDTO.class);

            if (response == null) {
                logger.error("Error en la solicitud de licencia de prueba: No se recibió respuesta del servidor");
            }

            return response;
        } catch (Exception e) {
            logger.error("Error en la solicitud de licencia de prueba: ", e);
            return null;
        }
    }

    public LicenseDTO getLicenseInfo(String licenseKey){
        try {
            String url = BASE_API_URL + "/licensekey/"+licenseKey;
            LicenseDTO response = HttpUtil.get(url, LicenseDTO.class);

            if (response == null) {
                logger.error("Error requesting license: No response received");
            }

            return response;
        } catch (Exception e) {
            logger.error("Error requesting license: ", e);
            return null;
        }
    }


}
