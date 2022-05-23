package br.com.ifs.projeto.dto.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import br.com.ifs.projeto.model.ModelSystem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SystemForm {

	@NotBlank @NotNull
	private String name;
	
	public ModelSystem toSystem() {
		ModelSystem system = new ModelSystem();
		system.setName(name);
		system.setStatus(true);
		return system;
	}
	
}
