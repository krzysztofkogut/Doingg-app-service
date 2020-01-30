package com.example.doingg.controller;

import com.example.doingg.model.*;
import com.example.doingg.payload.*;
import com.example.doingg.repository.OfferRepository;
import com.example.doingg.repository.UserRepository;
import com.example.doingg.repository.BidRepository;
import com.example.doingg.security.CurrentUser;
import com.example.doingg.security.UserPrincipal;
import com.example.doingg.service.OfferService;
import com.example.doingg.util.AppConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/offers")
public class OfferController {

    @Autowired
    private OfferRepository offerRepository;

    @Autowired
    private BidRepository bidRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OfferService offerService;

    private static final Logger logger = LoggerFactory.getLogger(OfferController.class);

    @GetMapping
    public PagedResponse<OfferResponse> getOffers(@CurrentUser UserPrincipal currentUser,
                                                  @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                                                  @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
        return offerService.getAllOffers(currentUser, page, size);
    }

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> createOffer(@Valid @RequestBody OfferRequest offerRequest) {
        Offer offer = offerService.createOffer(offerRequest);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{offerId}")
                .buildAndExpand(offer.getId()).toUri();

        return ResponseEntity.created(location)
                .body(new ApiResponse(true, "Offer Created Successfully"));
    }


    @GetMapping("/{offerId}")
    public OfferResponse getOfferById(@CurrentUser UserPrincipal currentUser,
                                      @PathVariable Long offerId) {
        return offerService.getOfferById(offerId, currentUser);
    }

    @PostMapping("/{offerId}/bids")
    @PreAuthorize("hasRole('USER')")
    public OfferResponse castBid(@CurrentUser UserPrincipal currentUser,
                                 @PathVariable Long offerId) {
        return offerService.castBidAndGetUpdatedOffer(offerId, currentUser);
    }

}
