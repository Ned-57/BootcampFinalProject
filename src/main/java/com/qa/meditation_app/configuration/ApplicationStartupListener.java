package com.qa.meditation_app.configuration;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.qa.meditation_app.data.entity.Meditation;
import com.qa.meditation_app.data.repository.MeditationRepository;

@Profile("dev")
@Configuration
public class ApplicationStartupListener implements ApplicationListener<ApplicationReadyEvent> {

	private MeditationRepository meditationRepository;

	@Autowired // dependency injection
	public ApplicationStartupListener(MeditationRepository meditationRepository) {
		this.meditationRepository = meditationRepository;
	}

	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {
		meditationRepository.saveAll(List.of(new Meditation(LocalDate.of(2022, 01, 31), "10:52", 10, false),
				new Meditation(LocalDate.of(2022, 01, 30), "09:00", 45, true),
				new Meditation(LocalDate.of(2022, 02, 01), "14:00", 12, false)));
	}

}
