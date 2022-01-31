package com.qa.meditation_app.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.qa.meditation_app.data.entity.Meditation;

public interface MeditationRepository extends JpaRepository<Meditation, Long> {

}
