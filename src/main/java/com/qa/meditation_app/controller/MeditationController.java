package com.qa.meditation_app.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.qa.meditation_app.data.entity.Meditation;
import com.qa.meditation_app.service.MeditationService;

@RestController
@RequestMapping(path = "/meditation")
public class MeditationController {

	private MeditationService meditationService;

	@Autowired
	public MeditationController(MeditationService meditationService) {
		this.meditationService = meditationService;
	}

	@GetMapping
	public ResponseEntity<List<Meditation>> getMeditations() {
		ResponseEntity<List<Meditation>> meditations = ResponseEntity.ok(meditationService.getAll());
		return meditations;
	}

	// get by id
	@GetMapping(path = "/{id}")
	public ResponseEntity<Meditation> getMeditationById(@PathVariable("id") long id) {
		Meditation savedMeditation = meditationService.getById(id);
		ResponseEntity<Meditation> response = ResponseEntity.status(HttpStatus.OK).body(savedMeditation);
		return response;
	}

	@PostMapping
	public ResponseEntity<Meditation> createMeditation(@Valid @RequestBody Meditation meditation) {
		Meditation savedMeditation = meditationService.create(meditation);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Location", "/meditation/" + String.valueOf(savedMeditation.getId()));

		ResponseEntity<Meditation> response = new ResponseEntity<Meditation>(savedMeditation, headers,
				HttpStatus.CREATED);
		return response;
	}

	// Update
	@PutMapping("/{id}")
	public ResponseEntity<Meditation> updateMeditation(@PathVariable("id") long id,
			@Valid @RequestBody Meditation meditation) {
		Meditation updatedMeditation = meditationService.update(meditation, id);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Location", "/user/" + String.valueOf(updatedMeditation.getId()));
		ResponseEntity<Meditation> response = new ResponseEntity<Meditation>(updatedMeditation, headers,
				HttpStatus.CREATED);
		return response;
	}

	// Delete
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteMeditation(@PathVariable("id") long id) {
		meditationService.delete(id);
		return ResponseEntity.accepted().build();

	}
}
