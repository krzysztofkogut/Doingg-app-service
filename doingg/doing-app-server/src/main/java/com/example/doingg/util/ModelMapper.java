package com.example.doingg.util;

import com.example.doingg.model.Offer;
import com.example.doingg.model.User;
import com.example.doingg.payload.OfferResponse;
import com.example.doingg.payload.UserSummary;

import java.time.Instant;

public class ModelMapper {

    public static OfferResponse mapOfferToOfferResponse(Offer offer, User creator, Long userBid) {
        OfferResponse offerResponse = new OfferResponse();
        offerResponse.setId(offer.getId());
        offerResponse.setQuestion(offer.getQuestion());
        offerResponse.setCity(offer.getCity());
        offerResponse.setWage(offer.getWage());
        offerResponse.setPhone(offer.getPhone());
        offerResponse.setCreationDateTime(offer.getCreatedAt());
        offerResponse.setExpirationDateTime(offer.getExpirationDateTime());
        Instant now = Instant.now();
        offerResponse.setExpired(offer.getExpirationDateTime().isBefore(now));

        UserSummary creatorSummary = new UserSummary(creator.getId(), creator.getUsername(), creator.getName());
        offerResponse.setCreatedBy(creatorSummary);

        if(userBid != null) {
            Long totalVotes = offerResponse.getTotalBids();
            offerResponse.setTotalBids(totalVotes+1);
        }

        return offerResponse;
    }

}
