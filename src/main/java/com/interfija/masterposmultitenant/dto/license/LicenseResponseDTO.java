package com.interfija.masterposmultitenant.dto.license;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LicenseResponseDTO {
    private String message;
    private LicenseDTO data;
}
