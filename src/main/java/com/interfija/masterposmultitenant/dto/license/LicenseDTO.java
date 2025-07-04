package com.interfija.masterposmultitenant.dto.license;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LicenseDTO {
    private Long idLicense;
    private String licenseKey;
    private String expirationDate;
    private boolean perpetual;
    private String macAddress;
    private String motherboardSerial;
    private Long activationDays;
}
