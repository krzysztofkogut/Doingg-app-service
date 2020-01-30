package com.example.doingg.repository;

import com.example.doingg.model.Offer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OfferRepository extends JpaRepository<Offer, Long> {

    Optional<Offer> findById(Long offerId);

    Page<Offer> findByCreatedBy(Long userId, Pageable pageable);

    long countByCreatedBy(Long userId);

    List<Offer> findByIdIn(List<Long> offerIds);

    List<Offer> findByIdIn(List<Long> offerIds, Sort sort);
}
