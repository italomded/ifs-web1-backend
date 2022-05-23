package br.com.ifs.projeto.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ifs.projeto.dto.form.UserForm;
import br.com.ifs.projeto.model.Profile;
import br.com.ifs.projeto.model.User;
import br.com.ifs.projeto.model.UserAndProfile;
import br.com.ifs.projeto.model.UserAndProfileKey;
import br.com.ifs.projeto.repository.ProfileRepository;
import br.com.ifs.projeto.repository.UserAndProfileRepository;
import br.com.ifs.projeto.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ProfileRepository profileRepository;
	
	@Autowired
	private UserAndProfileRepository userAndProfileRepository;
	
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
		Set<UserAndProfile> UnP = userAndProfileRepository.findByUser_Id(id);
		if (optUser.isPresent()) {
			User user = optUser.get();
			user.setProfiles(UnP);
			return user;
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
			List<UserAndProfileKey> upId = new ArrayList<>();
			user.getProfiles().removeIf(up -> {
				upId.add(up.getId());
				Profile profile = up.getProfile();
				profile.getUsers().removeIf(up2 -> up2.getId() == up.getId());
				profileRepository.save(profile);
				return true;
			});
			upId.forEach(up -> userAndProfileRepository.deleteById(up));
			userRepository.deleteById(user.getId());
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
