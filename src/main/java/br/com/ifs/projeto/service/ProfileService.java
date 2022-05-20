package br.com.ifs.projeto.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ifs.projeto.dto.form.ProfileForm;
import br.com.ifs.projeto.model.Profile;
import br.com.ifs.projeto.repository.ProfileRepository;

@Service
public class ProfileService {

	@Autowired
	private ProfileRepository profileRepository;

	public List<Profile> getAll() {
		return profileRepository.findAll();
	}

	public Profile getOne(Long id) {
		Optional<Profile> optProfile = profileRepository.findById(id);
		if (optProfile.isPresent()) {
			return optProfile.get();
		} else {
			return null;
		}
	}

	public Long create(ProfileForm form) {
		Profile profile = form.toProfile();
		profileRepository.save(profile);
		return profile.getId();
	}

	public Boolean delete(Long id) {
		Profile profile = this.getOne(id);
		if (profile != null) {
			profileRepository.delete(profile);
			return true;
		} else {
			return false;
		}	
	}
}
