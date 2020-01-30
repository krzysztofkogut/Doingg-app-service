package com.example.doingg;

import static org.assertj.core.api.Assertions.assertThat;

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


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application.properties")
public class OffersApplicationTests {

	@Autowired
	private OffersApplication offersApplication;

	@Autowired
	MockMvc mockMvc;

	@Test
	public void contextLoads() throws Exception {

		assertThat(offersApplication).isNotNull();

		MvcResult mvcResult = mockMvc.perform(
				MockMvcRequestBuilders.get("/api/")
						.accept(MediaType.APPLICATION_JSON))
		.andReturn();

		System.out.println(mvcResult.getResponse());
	}

}
