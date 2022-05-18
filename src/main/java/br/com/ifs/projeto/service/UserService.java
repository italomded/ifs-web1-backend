package br.com.ifs.projeto.service;

import java.util.List;
import java.util.Optional;

import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import br.com.ifs.projeto.config.security.TokenService;
import br.com.ifs.projeto.dto.form.CreateUserForm;
import br.com.ifs.projeto.dto.form.LoginForm;
import br.com.ifs.projeto.model.User;
import br.com.ifs.projeto.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private TokenService tokenService;
	
	@Autowired
	private UserRepository userRepository;
	
	public User getUserByLogin(String login) {
		Optional<User> optUser = userRepository.findByLogin(login);
		if (optUser.isPresent()) {
			return optUser.get();
		} else {
			return null;
		}
	}

	public String login(LoginForm form) {
		UsernamePasswordAuthenticationToken loginData = new UsernamePasswordAuthenticationToken(form.getLogin(), form.getPassword());
		try {
			Authentication authentication = authenticationManager.authenticate(loginData);
			return tokenService.generateToken(authentication);
		} catch (BadCredentialsException exception) {
			return null;
		}
	}

	public List<User> getAll() {
		return userRepository.findAll();
	}
	
	public User getOne(Long id) {
		Optional<User> optUser = userRepository.findById(id);
		if (optUser.isPresent()) {
			return optUser.get();
		} else {
			return null;
		}
	}

	public Long create(CreateUserForm form) {
		User user = form.toUser();
		try {
			userRepository.save(user);
		} catch (ConstraintViolationException exception) {
			// criar filtro para essa exceção
			return null;
		}
		return user.getId();
	}
	
}
