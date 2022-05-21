package br.com.ifs.projeto.dto;

import java.time.LocalDate;
import java.util.List;

import br.com.ifs.projeto.model.User;
import lombok.Getter;

@Getter
public class UserDTO {

	private Long id;
	private String name;
	private String login;
	private Boolean status;
	private LocalDate register;
	private List<ModelDTO> profiles;
	
	public UserDTO(User user) {
		this.id = user.getId();
		this.name = user.getName();
		this.login = user.getLogin();
		this.status = user.getStatus();
		this.register = user.getRegister();
		this.profiles = user.getProfiles().stream().map(p -> new ModelDTO(p, "user")).toList();
	}
	
}
