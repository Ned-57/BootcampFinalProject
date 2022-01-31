package com.qa.meditation_app.data.entity;

import java.time.LocalDate;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "meditation")
public class Meditation {

	@Id // For primary key
	@GeneratedValue(strategy = GenerationType.IDENTITY) // automatically increment IDs upon creation
	private Long id;

	@NotNull
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) // Should be formatted as date
	@JsonFormat(pattern = "dd/MM/yyyy") // How the date should be formatted
	private LocalDate date;

	@NotNull
	@Length(min = 5) // e.g. 00:00
	private String timeOfDay;

	@NotNull
	@Min(1)
	private Integer duration; // time in minutes

	@NotNull
	private boolean guided;

	public Meditation() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public String getTimeOfDay() {
		return timeOfDay;
	}

	public void setTimeOfDay(String timeOfDay) {
		this.timeOfDay = timeOfDay;
	}

	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	public boolean isGuided() {
		return guided;
	}

	public void setGuided(boolean guided) {
		this.guided = guided;
	}

	@Override
	public String toString() {
		return "Meditation [id=" + id + ", date=" + date + ", timeOfDay=" + timeOfDay + ", duration=" + duration
				+ ", guided=" + guided + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(date, duration, guided, id, timeOfDay);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Meditation other = (Meditation) obj;
		return Objects.equals(date, other.date) && Objects.equals(duration, other.duration) && guided == other.guided
				&& Objects.equals(id, other.id) && Objects.equals(timeOfDay, other.timeOfDay);
	}
}
