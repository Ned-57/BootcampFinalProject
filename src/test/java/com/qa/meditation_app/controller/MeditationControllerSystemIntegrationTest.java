package com.qa.meditation_app.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.meditation_app.data.entity.Meditation;
import com.qa.meditation_app.data.repository.MeditationRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional
public class MeditationControllerSystemIntegrationTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private MeditationRepository meditationRepository;

	private List<Meditation> medsInDb;
	private Long nextNewId;
	private Meditation gotMed;

	@BeforeEach
	public void init() {
		// Add some objects to a list
		List<Meditation> meds = (List.of(new Meditation(1L, LocalDate.of(2022, 01, 31), "10:52", 10, false),
				new Meditation(2L, LocalDate.of(2022, 02, 01), "09:00", 45, true),
				new Meditation(3L, LocalDate.of(2022, 02, 02), "14:00", 12, false)));
		// Instantiate medsInDb as an ArrayList
		medsInDb = new ArrayList<>();
		// Add the objects to the repository, and to the ArrayList
		medsInDb.addAll(meditationRepository.saveAll(meds));
		// Meditation to get by id
		gotMed = new Meditation(1L, LocalDate.of(2022, 01, 31), "10:52", 10, false);
		// Calculates new element id from last element's id
		nextNewId = medsInDb.get(medsInDb.size() - 1).getId() + 1;
	}

	@Test
	public void getAllMeditationsTest() throws Exception {
		// Mock HTTP request
		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.request(HttpMethod.GET, "/meditation");
		// Set the content type
		mockRequest.accept(MediaType.APPLICATION_JSON);
		// Create expected JSON String from medsInDb
		String meds = objectMapper.writeValueAsString(medsInDb);
		// Result matchers to compare medsInDb JSON with the result of the mock request
		ResultMatcher statusMatcher = MockMvcResultMatchers.status().isOk();
		ResultMatcher contentMatcher = MockMvcResultMatchers.content().json(meds);
		// Send the mock request and expect back the result matchers
		mockMvc.perform(mockRequest).andExpect(statusMatcher).andExpect(contentMatcher);
	}

}
