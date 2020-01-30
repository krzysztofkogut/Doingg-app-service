package com.example.doingg.model;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Instant;


@RunWith(SpringRunner.class)
@SpringBootTest
public class OfferTest {

    @Test
    public void notNull_getQuestion() {
        Offer offer = new Offer();
        offer.setQuestion("Test");
        offer.setCity("NYC");
        offer.setPhone("999888777");
        offer.setWage("10");
        offer.setExpirationDateTime(Instant.now());

        Assert.assertNotNull(offer.getQuestion());
    }

    @Test
    public void notNull_getCity() {
        Offer offer = new Offer();
        offer.setQuestion("Test");
        offer.setCity("NYC");
        offer.setPhone("999888777");
        offer.setWage("10");
        offer.setExpirationDateTime(Instant.now());

        Assert.assertNotNull(offer.getCity());
    }

    @Test
    public void notNull_getPhone() {
        Offer offer = new Offer();
        offer.setQuestion("Test");
        offer.setCity("NYC");
        offer.setPhone("999888777");
        offer.setWage("10");
        offer.setExpirationDateTime(Instant.now());

        Assert.assertNotNull(offer.getPhone());
    }

    @Test
    public void notNull_getWage() {
        Offer offer = new Offer();
        offer.setQuestion("Test");
        offer.setCity("NYC");
        offer.setPhone("999888777");
        offer.setWage("10");
        offer.setExpirationDateTime(Instant.now());

        Assert.assertNotNull(offer.getWage());
    }

    @Test
    public void notNull_getExpirationDateTime() {
        Offer offer = new Offer();
        offer.setQuestion("Test");
        offer.setCity("NYC");
        offer.setPhone("999888777");
        offer.setWage("10");
        offer.setExpirationDateTime(Instant.now());

        Assert.assertNotNull(offer.getExpirationDateTime());
    }

    @Test
    public void compare_getQuestion() {
        Offer offer = new Offer();
        offer.setQuestion("Test");
        offer.setCity("NYC");
        offer.setPhone("999888777");
        offer.setWage("10");
        offer.setExpirationDateTime(Instant.now());

        Assert.assertEquals(offer.getQuestion(),"Test");
    }

    @Test
    public void compare_getCity() {
        Offer offer = new Offer();
        offer.setQuestion("Test");
        offer.setCity("NYC");
        offer.setPhone("999888777");
        offer.setWage("10");
        offer.setExpirationDateTime(Instant.now());

        Assert.assertEquals(offer.getCity(),"NYC");
    }

    @Test
    public void compare_getPhone() {
        Offer offer = new Offer();
        offer.setQuestion("Test");
        offer.setCity("NYC");
        offer.setPhone("999888777");
        offer.setWage("10");
        offer.setExpirationDateTime(Instant.now());
        System.out.println(offer.getExpirationDateTime());

        Assert.assertEquals(offer.getPhone(),"999888777");
    }

    @Test
    public void compare_getWage() {
        Offer offer = new Offer();
        offer.setQuestion("Test");
        offer.setCity("NYC");
        offer.setPhone("999888777");
        offer.setWage("10");
        offer.setExpirationDateTime(Instant.now());

        Assert.assertEquals(offer.getWage(),"10");
    }

    @Test
    public void compare_getExpirationDateTime() {
        Offer offer = new Offer();
        offer.setQuestion("Test");
        offer.setCity("NYC");
        offer.setPhone("999888777");
        offer.setWage("10");
        offer.setExpirationDateTime(Instant.parse("2020-01-18T21:46:17.854744Z"));

        Assert.assertEquals(offer.getExpirationDateTime(),Instant.parse("2020-01-18T21:46:17.854744Z"));
    }
}