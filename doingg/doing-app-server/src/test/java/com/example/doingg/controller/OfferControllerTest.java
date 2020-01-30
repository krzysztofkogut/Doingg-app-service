package com.example.doingg.controller;

import com.example.doingg.repository.OfferRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application.properties")
public class OfferControllerTest {

    @Autowired
    private OfferRepository offerRepository;

    @Autowired
    MockMvc mockMvc;

    @Test
    public void contextLoads() throws Exception {

        assertThat(offerRepository).isNotNull();

        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.get("/")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn();

        System.out.println(mvcResult.getResponse());

    }

}