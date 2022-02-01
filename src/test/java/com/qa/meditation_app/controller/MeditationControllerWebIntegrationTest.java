package com.qa.meditation_app.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.qa.meditation_app.data.entity.Meditation;
import com.qa.meditation_app.service.MeditationService;

@WebMvcTest(MeditationController.class)
public class MeditationControllerWebIntegrationTest {

	@Autowired
	private MeditationController controller;

	// Controller depends on Service, but not testing service
	@MockBean
	private MeditationService meditationService;

	// Data for tests
	private List<Meditation> meds;

	// Runs before each test
	@BeforeEach
	public void init() {
		// Initialise meds as an ArrayList
		meds = new ArrayList<>();
		// Add some objects to the ArrayList
		meds.addAll(List.of(new Meditation(LocalDate.of(2022, 01, 31), "10:52", 10, false),
				new Meditation(LocalDate.of(2022, 02, 01), "09:00", 45, true),
				new Meditation(LocalDate.of(2022, 02, 02), "14:00", 12, false)));

	}

	@Test
	public void getAllMeditationsTest() {
		// Expected return is a list of all meditations in the body, and an HTTP status
		// of OK
		ResponseEntity<List<Meditation>> expected = new ResponseEntity<List<Meditation>>(meds, HttpStatus.OK);

		// When meditationService.getAll method called, return meds
		when(meditationService.getAll()).thenReturn(meds);

		// The actual method we are testing
		ResponseEntity<List<Meditation>> actual = controller.getMeditations();

		// Verifies that the actual is the same as the expected
		assertThat(expected).isEqualTo(actual);

		// Verifies that the meditationService.getAll method was called by the
		// controller
		verify(meditationService).getAll();
	}

}
