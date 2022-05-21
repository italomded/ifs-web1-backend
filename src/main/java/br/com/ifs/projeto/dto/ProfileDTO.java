package br.com.ifs.projeto.dto;

import java.util.List;

import br.com.ifs.projeto.model.Profile;
import lombok.Getter;

@Getter
public class ProfileDTO {

	private Long id;
	private String name;
	private Boolean status;
	private List<ModelDTO> users;
	private List<ModelDTO> transactions;
	
	public ProfileDTO(Profile profile) {
		this.id = profile.getId();
		this.name = profile.getName();
		this.users = profile.getUsers().stream().map(u -> new ModelDTO(u, "profile")).toList();
		this.transactions = profile.getTransactions().stream().map(ModelDTO::new).toList();
	}
	
}
