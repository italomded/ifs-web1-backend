package br.com.ifs.projeto.dto.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import br.com.ifs.projeto.model.Profile;
import lombok.Getter;

@Getter
public class ProfileForm {

	@NotNull @NotBlank
	private String name;

	public Profile toProfile() {
		Profile profile = new Profile();
		profile.setStatus(true);
		if (name.startsWith("ROLE_")) {
			profile.setName(name);
		} else {
			profile.setName("ROLE_" + name);
		}
		return profile;
	}
	
}
