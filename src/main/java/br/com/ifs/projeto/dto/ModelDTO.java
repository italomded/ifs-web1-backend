package br.com.ifs.projeto.dto;

import br.com.ifs.projeto.model.Model;
import br.com.ifs.projeto.model.UserAndProfile;
import lombok.Getter;

@Getter
public class ModelDTO {

	private Long id;
	private String name;
	private Boolean status;

	public ModelDTO(Model model) {
		this.id = model.getId();
		this.name = model.getName();
		this.status = model.getStatus();
	}
	
	public ModelDTO(UserAndProfile model, String side) {
		if (side.equals("user")) {
			this.id = model.getProfile().getId();
			this.name = model.getProfile().getName();
			this.status = model.getProfile().getStatus();
		} else {
			this.id = model.getUser().getId();
			this.name = model.getUser().getName();
			this.status = model.getUser().getStatus();
		}
	}
}

