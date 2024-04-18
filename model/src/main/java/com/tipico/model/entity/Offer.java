package com.tipico.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "offer")
@Setter
@Getter
public class Offer extends AbstractEntity<Long> {
    @Id
    private Long id;

    @Column(name = "offer_uid")
    private UUID uid;

    @Column(name = "expiration_date")
    private LocalDate expirationDate;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "campaign_id")
    private Long campaignId;
}
