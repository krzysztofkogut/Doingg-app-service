package com.example.doingg.controller;

import com.example.doingg.exception.ResourceNotFoundException;
import com.example.doingg.model.User;
import com.example.doingg.payload.*;
import com.example.doingg.repository.OfferRepository;
import com.example.doingg.repository.UserRepository;
import com.example.doingg.repository.BidRepository;
import com.example.doingg.security.UserPrincipal;
import com.example.doingg.service.OfferService;
import com.example.doingg.security.CurrentUser;
import com.example.doingg.util.AppConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OfferRepository offerRepository;

    @Autowired
    private BidRepository bidRepository;

    @Autowired
    private OfferService offerService;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/user/me")
    @PreAuthorize("hasRole('USER')")
    public UserSummary getCurrentUser(@CurrentUser UserPrincipal currentUser) {
        UserSummary userSummary = new UserSummary(currentUser.getId(), currentUser.getUsername(), currentUser.getName());
        return userSummary;
    }

    @GetMapping("/user/checkUsernameAvailability")
    public UserIdentityAvailability checkUsernameAvailability(@RequestParam(value = "username") String username) {
        Boolean isAvailable = !userRepository.existsByUsername(username);
        return new UserIdentityAvailability(isAvailable);
    }

    @GetMapping("/user/checkEmailAvailability")
    public UserIdentityAvailability checkEmailAvailability(@RequestParam(value = "email") String email) {
        Boolean isAvailable = !userRepository.existsByEmail(email);
        return new UserIdentityAvailability(isAvailable);
    }

    @GetMapping("/users/{username}")
    public UserProfile getUserProfile(@PathVariable(value = "username") String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));

        long offerCount = offerRepository.countByCreatedBy(user.getId());
        long bidCount = bidRepository.countByUserId(user.getId());

        UserProfile userProfile = new UserProfile(user.getId(), user.getUsername(), user.getName(), user.getCreatedAt(), offerCount, bidCount);

        return userProfile;
    }

    @GetMapping("/users/{username}/offers")
    public PagedResponse<OfferResponse> getOfferssCreatedBy(@PathVariable(value = "username") String username,
                                                            @CurrentUser UserPrincipal currentUser,
                                                            @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                                                            @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
        return offerService.getOffersCreatedBy(username, currentUser, page, size);
    }


    @GetMapping("/users/{username}/bids")
    public PagedResponse<OfferResponse> getOffersBidBy(@PathVariable(value = "username") String username,
                                                       @CurrentUser UserPrincipal currentUser,
                                                       @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                                                       @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
        return offerService.getOffersBidBy(username, currentUser, page, size);
    }

}
