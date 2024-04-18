package com.tipico.service.impl;

import com.tipico.model.dto.OfferDto;
import com.tipico.model.dto.OfferRequest;
import com.tipico.model.entity.Campaign;
import com.tipico.model.entity.Offer;
import com.tipico.repository.CampaignRepository;
import com.tipico.repository.OfferRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class OfferServiceImplTest {

    @Mock
    private OfferRepository offerRepository;

    @Mock
    private CampaignRepository campaignRepository;

    @InjectMocks
    private OfferServiceImpl offerService;

    @Test
    void findById_WhenIdExists_ReturnsOffer() {
        Long id = 1L;
        var expectedOffer = new Offer();
        when(offerRepository.findById(id)).thenReturn(Optional.of(expectedOffer));

        Offer result = offerService.findById(id);
        assertEquals(expectedOffer, result);
    }

    @Test
    void findById_WhenIdDoesNotExist_ThrowsException() {
        Long id = 1L;
        when(offerRepository.findById(id)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> offerService.findById(id));
        assertEquals("No data found", exception.getMessage());
    }

    @Test
    void findByUid_WhenUidExists_ReturnsOffer() {
        UUID uid = UUID.randomUUID();
        Offer expectedOffer = new Offer();
        when(offerRepository.findByUid(uid)).thenReturn(Optional.of(expectedOffer));

        Offer result = offerService.findByUid(uid);
        assertEquals(expectedOffer, result);
    }

    @Test
    void findByUid_WhenUidDoesNotExist_ThrowsException() {
        UUID uid = UUID.randomUUID();
        when(offerRepository.findByUid(uid)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> offerService.findByUid(uid));
        assertEquals("No data found", exception.getMessage());
    }

    @Test
    void findRelevantOffers_WhenOffersAndCampaignsMatch_ReturnsNonEmptyList() {
        OfferRequest request = new OfferRequest();
        request.setCompanyName("Company");

        // Assuming Campaign ID and creating Offers with it
        Long campaignId = 1L;
        Offer offer = new Offer();
        offer.setCampaignId(campaignId);  // Set non-null Campaign ID

        List<Offer> offers = List.of(offer);
        when(offerRepository.findByCompanyNameIgnoreCaseAndExpirationDateIsAfter(eq("Company"), any(LocalDate.class)))
                .thenReturn(offers);

        Campaign mockCampaign = new Campaign();
        mockCampaign.setId(campaignId);  // Ensure ID matches with offers' Campaign ID
        mockCampaign.setConditions(Collections.emptyList());  // Ensure conditions are not null

        List<Campaign> campaigns = List.of(mockCampaign);
        when(campaignRepository.findAllRelevant(any(), any(LocalDate.class)))
                .thenReturn(campaigns);

        List<OfferDto> result = offerService.findRelevantOffers(request);
        assertFalse(result.isEmpty());
    }
}
