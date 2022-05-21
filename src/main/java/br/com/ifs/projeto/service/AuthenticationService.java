package br.com.ifs.projeto.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import br.com.ifs.projeto.config.security.TokenService;
import br.com.ifs.projeto.dto.form.LoginForm;
import br.com.ifs.projeto.model.User;

@Service
public class AuthenticationService {

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private TokenService tokenService;
	
	@Autowired
	private UserService userService;
	
	public String login(LoginForm form) {
		User user = userService.getUserByLogin(form.getLogin());
		if (user == null) {
			return null;
		} else if (!user.getStatus()) {
			return null;
		}
		try {
			UsernamePasswordAuthenticationToken loginData = new UsernamePasswordAuthenticationToken(form.getLogin(), form.getPassword());
			Authentication authentication = authenticationManager.authenticate(loginData);
			return tokenService.generateToken(authentication);
		} catch (BadCredentialsException exception) {
			return null;
		}
	}
	
}
