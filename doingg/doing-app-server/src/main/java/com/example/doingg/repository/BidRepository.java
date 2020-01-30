package com.example.doingg.repository;

import com.example.doingg.model.Bid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BidRepository extends JpaRepository<Bid, Long> {
    @Query("SELECT v FROM Bid v where v.user.id = :userId and v.offer.id in :offerIds")
    List<Bid> findByUserIdAndOfferIdIn(@Param("userId") Long userId, @Param("offerIds") List<Long> offerIds);

    @Query("SELECT v FROM Bid v where v.user.id = :userId and v.offer.id = :offerId")
    Bid findByUserIdAndOfferId(@Param("userId") Long userId, @Param("offerId") Long offerId);

    @Query("SELECT COUNT(v.id) from Bid v where v.user.id = :userId")
    long countByUserId(@Param("userId") Long userId);

    @Query("SELECT v.offer.id FROM Bid v WHERE v.user.id = :userId")
    Page<Long> findBidOfferIdsByUserId(@Param("userId") Long userId, Pageable pageable);
}

