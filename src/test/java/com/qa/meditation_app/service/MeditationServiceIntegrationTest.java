package com.qa.meditation_app.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.qa.meditation_app.data.entity.Meditation;
import com.qa.meditation_app.data.repository.MeditationRepository;

//Signifies test
@SpringBootTest
@Transactional
public class MeditationServiceIntegrationTest {

	@Autowired
	private MeditationService meditationService;

	@Autowired
	private MeditationRepository meditationRepository;

	private List<Meditation> medsInDb;
	private Meditation gotMed;

	@BeforeEach
	public void init() {
		// Add some objects to a list
		List<Meditation> meds = (List.of(new Meditation(1L, LocalDate.of(2022, 01, 31), "10:52", 10, false),
				new Meditation(2L, LocalDate.of(2022, 02, 01), "09:00", 45, true),
				new Meditation(3L, LocalDate.of(2022, 02, 02), "14:00", 12, false)));
		// instantiate medsInDb as an ArrayList
		medsInDb = new ArrayList<>();
		// Add the objects to the repository, and to the ArrayList
		medsInDb.addAll(meditationRepository.saveAll(meds));
		// Meditation to get by id
		gotMed = new Meditation(1L, LocalDate.of(2022, 01, 31), "10:52", 10, false);
	}

	@Test
	public void getAllMedsTest() {
		assertThat(medsInDb).isEqualTo(meditationService.getAll());
	}

	@Test
	public void getMeditationById() {
		assertThat(gotMed).isEqualTo(meditationService.getById(1L));
	}

}
