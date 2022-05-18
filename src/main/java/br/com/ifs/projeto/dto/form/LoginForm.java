package br.com.ifs.projeto.dto.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;

@Getter
public class LoginForm {
	
	@NotNull @NotBlank
	private String login;
	
	@NotNull @NotBlank
	private String password;

}
