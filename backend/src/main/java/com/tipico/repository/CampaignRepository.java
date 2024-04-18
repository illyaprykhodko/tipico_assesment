package com.tipico.repository;

import com.tipico.model.entity.Campaign;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CampaignRepository extends JpaBaseRepository<Campaign, Long> {
    @Query(value = "SELECT * FROM campaign WHERE id in (:ids) and start_date <= :date and end_date >= :date", nativeQuery = true)
    List<Campaign> findAllRelevant(List<Long> ids, LocalDate date);
}
