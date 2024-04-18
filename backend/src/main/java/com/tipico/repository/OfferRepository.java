package com.tipico.repository;

import com.tipico.model.entity.Offer;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OfferRepository extends JpaBaseRepository<Offer, Long> {
    Optional<Offer> findByUid(UUID uid);
    List<Offer> findByCompanyNameIgnoreCaseAndExpirationDateIsAfter(String companyName, LocalDate date);
}
