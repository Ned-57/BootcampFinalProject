package com.qa.meditation_app.service;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qa.meditation_app.data.entity.Meditation;
import com.qa.meditation_app.data.repository.MeditationRepository;

@Service
public class MeditationService {

	private MeditationRepository meditationRepository;

	@Autowired
	public MeditationService(MeditationRepository meditationRepository) {
		this.meditationRepository = meditationRepository;
	}

	public List<Meditation> getAll() {
		return meditationRepository.findAll();
	}

	public Meditation getById(long id) {
		if (meditationRepository.existsById(id)) {
			return meditationRepository.findById(id).get();
		}
		throw new EntityNotFoundException("Meditation with id " + id + " does not exist");
	}

	// to add: getByDate

	public Meditation create(Meditation meditation) {
		Meditation savedMeditation = meditationRepository.save(meditation);
		return savedMeditation;
	}

	public Meditation update(Meditation meditation, long id) {

		if (meditationRepository.existsById(id)) {

			Meditation meditationInDb = meditationRepository.getById(id);

			meditationInDb.setDate(meditation.getDate());
			meditationInDb.setTimeOfDay(meditation.getTimeOfDay());
			meditationInDb.setDuration(meditation.getDuration());
			meditationInDb.setGuided(meditation.isGuided());

			return meditationRepository.save(meditationInDb);
		} else {
			throw new EntityNotFoundException("Meditation with id " + id + " does not exist");
		}
	}

	public Meditation delete(long id) {
		if (meditationRepository.existsById(id)) {
			Meditation meditation = meditationRepository.getById(id);
			meditationRepository.deleteById(id);
			return meditation; // may not work (does work)
		}
		throw new EntityNotFoundException("Meditation with id " + id + " does not exist");
	}

}
