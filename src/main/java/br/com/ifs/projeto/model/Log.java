package br.com.ifs.projeto.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name="logs")
public class Log {
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private Long user_id;
	
	@Column(nullable = false, length = 500)
	private String text;
	
	@Column(nullable = false)
	private LocalDate date;
	
}
