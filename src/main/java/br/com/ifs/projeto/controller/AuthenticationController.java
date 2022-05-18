package br.com.ifs.projeto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.ifs.projeto.dto.TokenDTO;
import br.com.ifs.projeto.dto.form.LoginForm;
import br.com.ifs.projeto.service.UserService;

@RestController
@RequestMapping("authentication")
public class AuthenticationController {

	@Autowired
	private UserService userService;
	
	@PostMapping
	public ResponseEntity<TokenDTO> login(LoginForm form) {
		String token = userService.login(form);
		if (token != null) {
			return ResponseEntity.ok(new TokenDTO("Bearer", token));
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
	}
	
}
