package com.example.doingg.model;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class BidTest {

    @Test
    public void notNull_getOffer() {
        Bid bid = new Bid();
        Offer offer = new Offer();
        User user = new User();
        bid.setOffer(offer);
        bid.setUser(user);

        Assert.assertNotNull(bid.getOffer());
    }

    @Test
    public void notNull_getUser() {
        Bid bid = new Bid();
        Offer offer = new Offer();
        User user = new User();
        bid.setOffer(offer);
        bid.setUser(user);

        Assert.assertNotNull(bid.getUser());
    }

    @Test
    public void compare_getOffer() {
        Bid bid = new Bid();
        Offer offer = new Offer();
        User user = new User();
        bid.setOffer(offer);
        bid.setUser(user);

        Assert.assertEquals(bid.getOffer(),offer);
    }

    @Test
    public void compare_getUser() {
        Bid bid = new Bid();
        Offer offer = new Offer();
        User user = new User();
        bid.setOffer(offer);
        bid.setUser(user);

        Assert.assertEquals(bid.getUser(),user);
    }

}