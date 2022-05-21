package br.com.ifs.projeto.dto.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;

@Getter
public class SimpleActionForm {

	@NotNull @NotBlank
	private Long MainResourceId;
	
	@NotNull @NotBlank
	private Long SubResourceId;
	
}
