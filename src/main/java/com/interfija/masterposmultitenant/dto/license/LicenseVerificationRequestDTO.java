package com.interfija.masterposmultitenant.dto.license;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LicenseVerificationRequestDTO {
    private String licenseKey;
    private String macAddress;
    private String motherboardSerial;
}
