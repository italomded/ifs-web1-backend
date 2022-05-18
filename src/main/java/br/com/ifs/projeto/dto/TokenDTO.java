package br.com.ifs.projeto.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class TokenDTO {

	private String type;
	private String token;
	
}
