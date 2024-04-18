package com.tipico.model.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class OfferRequest {
    private String companyName;
    private LocalDate registrationDate;
    private BigDecimal depositAmount;
    private boolean firstDeposit;
    //@Size(min = 2, max = 2)
    private String countryCode;
}
