package com.qa.meditation_app.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.qa.meditation_app.data.entity.Meditation;
import com.qa.meditation_app.data.repository.MeditationRepository;

@ExtendWith(MockitoExtension.class)
public class MeditationServiceUnitTest {

	@Mock
	private MeditationRepository meditationRepository;

	@InjectMocks
	private MeditationService meditationService;

	private List<Meditation> meds;
	private Long medId;
	private Meditation medWithId;
	private Meditation medWithoutId;

	@BeforeEach
	public void init() {
		meds = new ArrayList<>();
		meds.addAll(List.of(new Meditation(1L, LocalDate.of(2022, 01, 31), "10:52", 10, false),
				new Meditation(2L, LocalDate.of(2022, 02, 01), "09:00", 45, true),
				new Meditation(3L, LocalDate.of(2022, 02, 02), "14:00", 12, false)));
		medId = 1L;
		medWithId = new Meditation(1L, LocalDate.of(2022, 01, 31), "10:52", 10, false);
		medWithoutId = new Meditation(LocalDate.of(2022, 01, 31), "10:52", 10, false);

	}

	@Test
	public void getAllMedsTest() {
		when(meditationRepository.findAll()).thenReturn(meds);

		assertThat(meditationService.getAll()).isEqualTo(meds);

		verify(meditationRepository).findAll();
	}

	@Test
	public void getMeditationById() {
		when(meditationRepository.existsById(medId)).thenReturn(true);
		when(meditationRepository.findById(medId)).thenReturn(Optional.of(medWithId));

		assertThat(meditationService.getById(medId)).isEqualTo(medWithId);

		verify(meditationRepository).findById(medId);
		verify(meditationRepository).existsById(medId);

	}

	@Test
	public void createMeditationTest() {
		when(meditationRepository.save(medWithoutId)).thenReturn(medWithId);

		assertThat(meditationService.create(medWithoutId)).isEqualTo(medWithId);

		verify(meditationRepository).save(medWithoutId);
	}

	@Test
	public void updateMeditationTest() {
		when(meditationRepository.existsById(medId)).thenReturn(true);
		when(meditationRepository.getById(medId)).thenReturn(medWithId);
		when(meditationRepository.save(medWithId)).thenReturn(medWithId);

		assertThat(meditationService.update(medWithoutId, medId)).isEqualTo(medWithId);

		verify(meditationRepository).existsById(medId);
		verify(meditationRepository).getById(medId);
		verify(meditationRepository).save(medWithId);
	}

	@Test
	public void deleteMeditationTest() {
		when(meditationRepository.existsById(medId)).thenReturn(true);
//		when(meditationRepository.getById(medId)).thenReturn(null);

		assertThat(meditationService.delete(medId)).isEqualTo(null);

		verify(meditationRepository).existsById(medId);
//		verify(meditationRepository).getById(medId);
	}
}
