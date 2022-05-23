package br.com.ifs.projeto.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name="transactions")
public class Transaction implements Model {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, length = 50)
	private String name;
	
	@Column(nullable = false)
	private Boolean status;
	
	@Column(nullable = false, length = 1000)
	private String url;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private ModelService service;
	
	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "transactions")
	private Set<Profile> profiles = new HashSet<>();

}
