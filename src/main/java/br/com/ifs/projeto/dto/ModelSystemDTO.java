package br.com.ifs.projeto.dto;

import java.util.List;

import br.com.ifs.projeto.model.ModelSystem;
import lombok.Getter;

@Getter
public class ModelSystemDTO {
	
	private Long id;
	private String name;
	private Boolean status;
	private List<ModelDTO> services;
	
	public ModelSystemDTO(ModelSystem system) {
		this.id = system.getId();
		this.name = system.getName();
		this.status = system.getStatus();
		this.services = system.getServices().stream().map(ModelDTO::new).toList();
	}

}
