package br.com.ifs.projeto.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name="services")
public class ModelService implements Model {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, length = 50)
	private String name;
	
	@Column(nullable = false, length = 1000)
	private String url;
	
	@Column(nullable = false)
	private Boolean status;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "service")
	private List<Transaction> transactions = new ArrayList<>();
	
	@ManyToOne(fetch = FetchType.LAZY)
	private ModelSystem system;
	
}
