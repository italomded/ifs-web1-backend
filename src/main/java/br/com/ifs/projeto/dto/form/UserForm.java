package br.com.ifs.projeto.dto.form;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import br.com.ifs.projeto.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserForm {
	
	@NotNull @NotBlank
	private String name;
	
	@NotNull @NotBlank
	private String login;
	
	@NotNull @NotBlank
	private String password;

	public User toUser() {
		User user = new User();
		user.setName(name);
		user.setLogin(login);
		user.setPassword(password);
		user.setRegister(LocalDate.now());
		user.setStatus(true);
		return user;
	}

}
