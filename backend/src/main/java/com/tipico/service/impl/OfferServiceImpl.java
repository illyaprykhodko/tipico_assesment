package com.tipico.service.impl;

import com.tipico.model.dto.OfferDto;
import com.tipico.model.dto.OfferRequest;
import com.tipico.model.entity.Offer;
import com.tipico.repository.CampaignRepository;
import com.tipico.repository.OfferRepository;
import com.tipico.service.OfferService;
import lombok.RequiredArgsConstructor;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.SpelParserConfiguration;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OfferServiceImpl implements OfferService {
    private final OfferRepository offerRepository;
    private final CampaignRepository campaignRepository;

    private static SpelParserConfiguration config = new SpelParserConfiguration(true, true);
    private static ExpressionParser parser = new SpelExpressionParser(config);

    @Override
    public Offer findById(Long id) {
        return offerRepository.findById(id).orElseThrow(() -> new RuntimeException("No data found"));
    }

    @Override
    public Offer findByUid(UUID uid) {
        return offerRepository.findByUid(uid).orElseThrow(() -> new RuntimeException("No data found"));
    }

    @Override
    public List<OfferDto> findRelevantOffers(OfferRequest offerRequest) {
        var now = LocalDate.now();
        var offers = offerRepository.
                findByCompanyNameIgnoreCaseAndExpirationDateIsAfter(offerRequest.getCompanyName(), now);

        var campaigns = offers.
                stream().
                map(o -> o.getCampaignId()).
                collect(Collectors.toList());

        var relevantCampaigns= campaignRepository.findAllRelevant(campaigns, now);

        List<OfferDto> eligibleOffers = new ArrayList<>();
        for (var campaign : relevantCampaigns) {
            boolean conditionsMet = true;
            if (!campaign.getConditions().isEmpty()) {
                for (var condition : campaign.getConditions()) {
                    Expression expression = parser.parseExpression(condition.getExpression());
                    EvaluationContext context = new StandardEvaluationContext(offerRequest);

                    boolean result  = expression.getValue(context, Boolean.class);

                    conditionsMet = conditionsMet & result;
                }
            }
            if (conditionsMet) {
                var offer = offers.stream().filter(o -> o.getCampaignId().equals(campaign.getId())).findFirst();
                if (offer.isPresent()) {
                    eligibleOffers.add(OfferDto.builder().
                            campaignName(campaign.getName()).
                            amount(campaign.getAmount()).
                            expirationDate(offer.get().getExpirationDate()).build());
                }
            }
        }
        return eligibleOffers;
    }
}
