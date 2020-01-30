package com.example.doingg.service;

import com.example.doingg.model.Offer;
import com.example.doingg.model.User;
import com.example.doingg.payload.OfferLength;
import com.example.doingg.payload.OfferRequest;
import com.example.doingg.repository.OfferRepository;
import com.example.doingg.repository.UserRepository;
import com.example.doingg.security.UserPrincipal;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class OfferServiceTest {

    @Autowired
    private OfferService offerService;

    @Autowired
    private OfferRepository offerRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void createOffer() {
        OfferRequest offerRequest = new OfferRequest();
        offerRequest.setQuestion("Test");
        offerRequest.setCity("LA");
        offerRequest.setPhone("111222333");
        offerRequest.setWage("32");
        OfferLength offerLength = new OfferLength();
        offerLength.setDays(2);
        offerLength.setHours(1);
        offerRequest.setOfferLength(offerLength);

        Offer offer = offerService.createOffer(offerRequest);

        Assert.assertNotNull(offer);
        offerRepository.delete(offer);
    }

    @Test
    public void getAllOffers() {
        User user = new User();
        user.setEmail("usertest@gmail.com");
        user.setPassword("user123");
        user.setName("User");
        user.setUsername("User1");
        UserPrincipal userPrincipal = UserPrincipal.create(user);

        userRepository.save(user);
        Assert.assertNotNull(offerService.getAllOffers(userPrincipal,1,1));
        userRepository.delete(user);
    }
}