package com.example.doingg.repository;


import com.example.doingg.model.Offer;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Instant;


@RunWith(SpringRunner.class)
@SpringBootTest
public class OfferRepositoryTest {

    @Autowired
    private OfferRepository offerRepository;

    @Test
    public void saveTest() {
        Offer offer = new Offer();
        offer.setQuestion("Test");
        offer.setCity("NYC");
        offer.setPhone("999888777");
        offer.setWage("10");
        offer.setExpirationDateTime(Instant.now());

        offerRepository.save(offer);
        offerRepository.delete(offer);
    }

    @Test
    public void findById() {
        Offer offer = new Offer();
        offer.setQuestion("Test");
        offer.setCity("NYC");
        offer.setPhone("999888777");
        offer.setWage("10");
        offer.setExpirationDateTime(Instant.now());

        offerRepository.save(offer);
        Assert.assertNotNull(offerRepository.findById(offer.getId()));
        offerRepository.delete(offer);
    }
}