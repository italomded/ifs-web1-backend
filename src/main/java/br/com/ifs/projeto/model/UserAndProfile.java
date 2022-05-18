package br.com.ifs.projeto.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name="user_profile")
public class UserAndProfile {

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	private User user_id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Profile profile_id;

	@Column(nullable = false)
	private LocalDate start;
	
	@Column
	private LocalDate end;
	
}
