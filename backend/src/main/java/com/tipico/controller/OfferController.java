package com.tipico.controller;

import com.tipico.model.dto.OfferDto;
import com.tipico.model.dto.OfferRequest;
import com.tipico.service.OfferService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "Offer", description = "Offer endpoint")
@RequestMapping("/offer")
@Validated
public class OfferController {
    private final OfferService offerService;

    @RequestMapping(
            method = RequestMethod.POST,
            consumes = {"application/json"},
            produces = {"application/json"}
    )
    public ResponseEntity<List<OfferDto>> searchOffers(@Parameter(required = true) @Valid @RequestBody OfferRequest offerRequest) {
        var offers = offerService.findRelevantOffers(offerRequest);
        return new ResponseEntity<>(offers, HttpStatus.OK);
    }
}
