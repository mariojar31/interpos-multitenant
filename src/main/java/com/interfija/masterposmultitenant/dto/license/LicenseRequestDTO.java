package com.interfija.masterposmultitenant.dto.license;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LicenseRequestDTO {
    private String macAddress;
    private String motherboardSerial;
    private String uuidLicense;
    private String terminal;
    private Long companyNit;
}
