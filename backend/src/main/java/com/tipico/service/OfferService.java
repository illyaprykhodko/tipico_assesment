package com.tipico.service;

import com.tipico.model.dto.OfferDto;
import com.tipico.model.dto.OfferRequest;
import com.tipico.model.entity.Offer;

import java.util.List;
import java.util.UUID;

public interface OfferService {
    Offer findById(Long id);
    Offer findByUid(UUID uid);
    List<OfferDto> findRelevantOffers(OfferRequest offerRequest);
}
