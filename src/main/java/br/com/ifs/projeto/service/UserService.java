package br.com.ifs.projeto.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ifs.projeto.dto.form.UserForm;
import br.com.ifs.projeto.model.User;
import br.com.ifs.projeto.repository.UserRepository;

@Service
public class UserService {
	
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

	public Long create(UserForm form) {
		User user = form.toUser();
		//DataIntegrityViolationException
		userRepository.save(user);
		return user.getId();
	}

	public Boolean delete(Long id) {
		User user = this.getOne(id);
		if (user != null) {
			userRepository.delete(user);
			return true;
		} else {
			return false;
		}
	}
	
	public Boolean changeStatus(Long id) {
		User user = this.getOne(id);
		if (user == null) {
			return false;
		}
		user.setStatus(!user.getStatus());
		userRepository.save(user);
		return true;
	}
	
}
