package com.example.doingg.service;

import com.example.doingg.exception.BadRequestException;
import com.example.doingg.exception.ResourceNotFoundException;
import com.example.doingg.model.*;
import com.example.doingg.payload.PagedResponse;
import com.example.doingg.payload.OfferRequest;
import com.example.doingg.payload.OfferResponse;
import com.example.doingg.payload.BidRequest;
import com.example.doingg.repository.OfferRepository;
import com.example.doingg.repository.UserRepository;
import com.example.doingg.repository.BidRepository;
import com.example.doingg.security.UserPrincipal;
import com.example.doingg.util.AppConstants;
import com.example.doingg.util.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class OfferService {

    @Autowired
    private OfferRepository offerRepository;

    @Autowired
    private BidRepository bidRepository;

    @Autowired
    private UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(OfferService.class);

    public PagedResponse<OfferResponse> getAllOffers(UserPrincipal currentUser, int page, int size) {
        validatePageNumberAndSize(page, size);

        // Retrieve Offers
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt");
        Page<Offer> offers = offerRepository.findAll(pageable);

        if(offers.getNumberOfElements() == 0) {
            return new PagedResponse<>(Collections.emptyList(), offers.getNumber(),
                    offers.getSize(), offers.getTotalElements(), offers.getTotalPages(), offers.isLast());
        }

        // Map Offers to OfferResponses containing bid counts and offer creator details
        List<Long> offerIds = offers.map(Offer::getId).getContent();
        Map<Long, Long> offerUserBidMap = getOfferUserBidMap(currentUser, offerIds);
        Map<Long, User> creatorMap = getOfferCreatorMap(offers.getContent());

        List<OfferResponse> offerResponses = offers.map(offer -> {
            return ModelMapper.mapOfferToOfferResponse(offer,
                    creatorMap.get(offer.getCreatedBy()),
                    offerUserBidMap == null ? null : offerUserBidMap.getOrDefault(offer.getId(), null));
        }).getContent();

        return new PagedResponse<>(offerResponses, offers.getNumber(),
                offers.getSize(), offers.getTotalElements(), offers.getTotalPages(), offers.isLast());
    }

    public PagedResponse<OfferResponse> getOffersCreatedBy(String username, UserPrincipal currentUser, int page, int size) {
        validatePageNumberAndSize(page, size);

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));

        // Retrieve all offers created by the given username
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt");
        Page<Offer> offers = offerRepository.findByCreatedBy(user.getId(), pageable);

        if (offers.getNumberOfElements() == 0) {
            return new PagedResponse<>(Collections.emptyList(), offers.getNumber(),
                    offers.getSize(), offers.getTotalElements(), offers.getTotalPages(), offers.isLast());
        }

        // Map Offers to OfferResponses containing bid counts and offer creator details
        List<Long> offerIds = offers.map(Offer::getId).getContent();
        Map<Long, Long> offerUserBidMap = getOfferUserBidMap(currentUser, offerIds);

        List<OfferResponse> offerResponses = offers.map(offer -> {
            return ModelMapper.mapOfferToOfferResponse(offer,
                    user,
                    offerUserBidMap == null ? null : offerUserBidMap.getOrDefault(offer.getId(), null));
        }).getContent();

        return new PagedResponse<>(offerResponses, offers.getNumber(),
                offers.getSize(), offers.getTotalElements(), offers.getTotalPages(), offers.isLast());
    }

    public PagedResponse<OfferResponse> getOffersBidBy(String username, UserPrincipal currentUser, int page, int size) {
        validatePageNumberAndSize(page, size);

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));

        // Retrieve all offerIds in which the given username has voted
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt");
        Page<Long> userBidOfferIds = bidRepository.findBidOfferIdsByUserId(user.getId(), pageable);

        if (userBidOfferIds.getNumberOfElements() == 0) {
            return new PagedResponse<>(Collections.emptyList(), userBidOfferIds.getNumber(),
                    userBidOfferIds.getSize(), userBidOfferIds.getTotalElements(),
                    userBidOfferIds.getTotalPages(), userBidOfferIds.isLast());
        }

        // Retrieve all offer details from the bid offerIds.
        List<Long> offerIds = userBidOfferIds.getContent();

        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
        List<Offer> offers = offerRepository.findByIdIn(offerIds, sort);

        // Map Offers to OfferResponses containing bid counts and offer creator details
        Map<Long, Long> offerUserBidMap = getOfferUserBidMap(currentUser, offerIds);
        Map<Long, User> creatorMap = getOfferCreatorMap(offers);

        List<OfferResponse> offerResponses = offers.stream().map(offer -> {
            return ModelMapper.mapOfferToOfferResponse(offer,
                    creatorMap.get(offer.getCreatedBy()),
                    offerUserBidMap == null ? null : offerUserBidMap.getOrDefault(offer.getId(), null));
        }).collect(Collectors.toList());

        return new PagedResponse<>(offerResponses, userBidOfferIds.getNumber(), userBidOfferIds.getSize(), userBidOfferIds.getTotalElements(), userBidOfferIds.getTotalPages(), userBidOfferIds.isLast());
    }


    public Offer createOffer(OfferRequest offerRequest) {
        Offer offer = new Offer();
        offer.setQuestion(offerRequest.getQuestion());
        offer.setCity(offerRequest.getCity());
        offer.setPhone(offerRequest.getPhone());
        offer.setWage(offerRequest.getWage());

        Instant now = Instant.now();
        Instant expirationDateTime = now.plus(Duration.ofDays(offerRequest.getOfferLength().getDays()))
                .plus(Duration.ofHours(offerRequest.getOfferLength().getHours()));

        offer.setExpirationDateTime(expirationDateTime);

        return offerRepository.save(offer);
    }

    public OfferResponse getOfferById(Long offerId, UserPrincipal currentUser) {
        Offer offer = offerRepository.findById(offerId).orElseThrow(
                () -> new ResourceNotFoundException("Offer", "id", offerId));

        // Retrieve offer creator details
        User creator = userRepository.findById(offer.getCreatedBy())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", offer.getCreatedBy()));

        // Retrieve vote done by logged in user
        Bid userBid = null;
        if(currentUser != null) {
            userBid = bidRepository.findByUserIdAndOfferId(currentUser.getId(), offerId);
        }

        return ModelMapper.mapOfferToOfferResponse(offer, creator, userBid != null ? userBid.getId(): null);
    }

    public OfferResponse castBidAndGetUpdatedOffer(Long offerId, UserPrincipal currentUser) {
        Offer offer = offerRepository.findById(offerId)
                .orElseThrow(() -> new ResourceNotFoundException("Offer", "id", offerId));

        if(offer.getExpirationDateTime().isBefore(Instant.now())) {
            throw new BadRequestException("Sorry! This Offer has already expired");
        }

        User user = userRepository.getOne(currentUser.getId());

        Bid bid = new Bid();
        bid.setOffer(offer);
        bid.setUser(user);

        try {
            bid = bidRepository.save(bid);
        } catch (DataIntegrityViolationException ex) {
            logger.info("User {} has already bid in Offer {}", currentUser.getId(), offerId);
            throw new BadRequestException("Sorry! You have already bid this offer");
        }

        //-- Bid Saved, Return the updated Offer Response now --

        // Retrieve offer creator details
        User creator = userRepository.findById(offer.getCreatedBy())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", offer.getCreatedBy()));

        return ModelMapper.mapOfferToOfferResponse(offer, creator, bid.getId());
    }


    private void validatePageNumberAndSize(int page, int size) {
        if(page < 0) {
            throw new BadRequestException("Page number cannot be less than zero.");
        }

        if(size > AppConstants.MAX_PAGE_SIZE) {
            throw new BadRequestException("Page size must not be greater than " + AppConstants.MAX_PAGE_SIZE);
        }
    }

    private Map<Long, Long> getOfferUserBidMap(UserPrincipal currentUser, List<Long> offerIds) {
        // Retrieve Votes done by the logged in user to the given offerIds
        Map<Long, Long> offerUserBidMap = null;
        if(currentUser != null) {
            List<Bid> userBids = bidRepository.findByUserIdAndOfferIdIn(currentUser.getId(), offerIds);

            offerUserBidMap = userBids.stream()
                    .collect(Collectors.toMap(bid -> bid.getOffer().getId(), bid -> bid.getId()));
        }
        return offerUserBidMap;
    }

    Map<Long, User> getOfferCreatorMap(List<Offer> offers) {
        // Get Offer Creator details of the given list of offers
        List<Long> creatorIds = offers.stream()
                .map(Offer::getCreatedBy)
                .distinct()
                .collect(Collectors.toList());

        List<User> creators = userRepository.findByIdIn(creatorIds);
        Map<Long, User> creatorMap = creators.stream()
                .collect(Collectors.toMap(User::getId, Function.identity()));

        return creatorMap;
    }
}
