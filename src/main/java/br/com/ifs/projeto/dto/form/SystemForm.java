package br.com.ifs.projeto.dto.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import br.com.ifs.projeto.model.ModelSystem;
import lombok.Getter;

public class SystemForm {

@Getter
private Long id;
	
	@NotBlank @NotNull
	private String name;
	
	public ModelSystem toSystem() {
		ModelSystem system = new ModelSystem();
		system.setName(name);
		system.setStatus(true);
		return system;
	}
	
}
