package com.qa.user_app.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.qa.meditation_app.controller.MeditationController;
import com.qa.meditation_app.service.MeditationService;

@WebMvcTest(MeditationController.class)
public class MeditationControllerWebIntegrationTest {

	@Autowired
	private MeditationController controller;

	// Controller depends on Service
	@MockBean
	private MeditationService meditationService;

	// Data for tests

	@BeforeEach
	public void init() {

	}

	@Test
	public void getAllMeditationsTest() {

	}
}
