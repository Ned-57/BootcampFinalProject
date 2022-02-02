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
	private Meditation newMed;
	private Long nextNewId;

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
		// Meditation to create
		newMed = new Meditation(LocalDate.of(2022, 02, 03), "06:30", 90, false);
		// Generates new element id from last element's id
		nextNewId = medsInDb.get(medsInDb.size() - 1).getId() + 1;
	}

	@Test
	public void getAllMedsTest() {
		assertThat(medsInDb).isEqualTo(meditationService.getAll());
	}

	@Test
	public void getMeditationById() {
		assertThat(gotMed).isEqualTo(meditationService.getById(1L));
	}

	@Test
	public void createMeditationTest() {
		// Expected
		Meditation expected = new Meditation(nextNewId, newMed.getDate(), newMed.getTimeOfDay(), newMed.getDuration(),
				newMed.isGuided());

		assertThat(expected).isEqualTo(meditationService.create(newMed));
	}

	@Test
	public void updateMeditationTest() {
		Meditation updatedMed = new Meditation(2L, LocalDate.of(2025, 12, 25), "00:00", 120, true);
		assertThat(updatedMed).isEqualTo(meditationService.update(updatedMed, 2));
	}

	@Test
	public void deleteMeditationTest() {
		Meditation med = medsInDb.get(1);

		assertThat(meditationService.delete(med.getId())).isEqualTo(med);
	}
}
