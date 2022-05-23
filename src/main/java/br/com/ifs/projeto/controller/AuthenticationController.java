package br.com.ifs.projeto.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.ifs.projeto.dto.TokenDTO;
import br.com.ifs.projeto.dto.form.LoginForm;
import br.com.ifs.projeto.service.AuthenticationService;

@RestController
@RequestMapping("authentication")
public class AuthenticationController {

	@Autowired
	private AuthenticationService authenticationService;
	
	@PostMapping
	public ResponseEntity<TokenDTO> login(@RequestBody @Valid LoginForm form) {
		String token = authenticationService.login(form);
		if (token != null) {
			return ResponseEntity.ok(new TokenDTO("Bearer", token));
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
	}
	
}
