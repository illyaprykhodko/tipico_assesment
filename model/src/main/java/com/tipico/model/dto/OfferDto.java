package com.tipico.model.dto;


import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
public class OfferDto {
    private UUID uid;
    private LocalDate expirationDate;
    private String campaignName;
   // @JsonSerialize(using = MoneySerializer.class)
    private BigDecimal amount;
}
