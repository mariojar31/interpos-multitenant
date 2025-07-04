package com.interfija.masterposmultitenant.dto.license;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TrialLicenseRequestDTO {
    private String macAddress;
    private String activationDate;
    private Long companyNit;
    private int activationDays;
}
