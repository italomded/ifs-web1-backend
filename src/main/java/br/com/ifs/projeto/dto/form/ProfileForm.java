package br.com.ifs.projeto.dto.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import br.com.ifs.projeto.model.Profile;
import lombok.Getter;

@Getter
public class ProfileForm {

	@NotNull @NotBlank
	private String name;
	// acho q vai precisar de um regex aqui

	public Profile toProfile() {
		Profile profile = new Profile();
		profile.setName(name);
		profile.setStatus(true);
		return profile;
	}
	
}
