package com.interfija.masterposmultitenant.dto.license;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LicenseVerificationDTO {
    private String message;
    private boolean valid;
    private String code;
}
