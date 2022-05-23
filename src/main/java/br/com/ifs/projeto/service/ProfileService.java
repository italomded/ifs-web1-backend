package br.com.ifs.projeto.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import br.com.ifs.projeto.dto.form.ProfileForm;
import br.com.ifs.projeto.dto.form.ProfileTransactionForm;
import br.com.ifs.projeto.dto.form.ProfileUserForm;
import br.com.ifs.projeto.model.Profile;
import br.com.ifs.projeto.model.Transaction;
import br.com.ifs.projeto.model.User;
import br.com.ifs.projeto.model.UserAndProfile;
import br.com.ifs.projeto.model.UserAndProfileKey;
import br.com.ifs.projeto.repository.ProfileRepository;
import br.com.ifs.projeto.repository.TransactionRepository;
import br.com.ifs.projeto.repository.UserAndProfileRepository;
import br.com.ifs.projeto.repository.UserRepository;

@Service
public class ProfileService {

	@Autowired
	private ProfileRepository profileRepository;
	
	@Autowired
	private TransactionRepository transactionRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserAndProfileRepository userAndProfileRepository;

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
		if (profile.getName().equals("EMPTY")) {
			return null;
		}
		profileRepository.save(profile);
		return profile.getId();
	}

	public Boolean delete(Long id) {
		Profile profile = this.getOne(id);
		if (profile != null) {
			List<UserAndProfileKey> upId = new ArrayList<>();
			profile.getUsers().removeIf(up -> {
				upId.add(up.getId());
				User user = up.getUser();
				user.getProfiles().removeIf(up2 -> up2.getId() == up.getId());
				userRepository.save(user);
				return true;
			});
			upId.forEach(up -> userAndProfileRepository.deleteById(up));
			profile.getTransactions().removeIf(t -> {
				t.getProfiles().removeIf(p -> p.getId() == profile.getId());
				return true;
			});
			profileRepository.deleteById(profile.getId());
			return true;
		} else {
			return false;
		}	
	}

	public Boolean addTransaction(ProfileTransactionForm form) {
		Optional<Profile> optProfile = profileRepository.findById(form.getProfileId());
		Optional<Transaction> optTransaction = transactionRepository.findById(form.getTransactionId());
		
		if (optProfile.isEmpty() || optTransaction.isEmpty()) {
			return false;
		}
		
		Profile profile = optProfile.get();
		Transaction transaction = optTransaction.get();
		
		transaction.getProfiles().add(profile);
		profile.getTransactions().add(transaction);
		
		try {
			profileRepository.save(profile);
			return true;
		} catch (DataIntegrityViolationException exception) {
			return false;
		}
	}

	public Boolean removeTransaction(ProfileTransactionForm form) {
		Optional<Profile> optProfile = profileRepository.findById(form.getProfileId());
		Optional<Transaction> optTransaction = transactionRepository.findById(form.getTransactionId());
		
		if (optProfile.isEmpty() || optTransaction.isEmpty()) {
			return false;
		}
		
		Profile profile = optProfile.get();
		Transaction transaction = optTransaction.get();
		
		transaction.getProfiles().removeIf(p -> p.getId() == profile.getId());
		profile.getTransactions().removeIf(t -> t.getId() == transaction.getId());

		profileRepository.save(profile);
		return true;
	}

	public Boolean addRole(ProfileUserForm form) {
		Optional<Profile> optProfile = profileRepository.findById(form.getProfileId());
		Optional<User> optUser = userRepository.findById(form.getUserId());
		
		if (optProfile.isEmpty() || optUser.isEmpty()) {
			return false;
		}
		
		Profile profile = optProfile.get();
		User user = optUser.get();
		
		Optional<UserAndProfile> optUP = userAndProfileRepository.exist(user.getId(), profile.getId());
		if (optUP.isPresent()) {
			UserAndProfile userAndProfile = optUP.get();
			if (userAndProfile.getEnd() != null) {
				userAndProfile.setEnd(null);
				userAndProfile.setStart(LocalDate.now());
				userAndProfileRepository.save(userAndProfile);
				return true;
			} else {
				return false;
			}
		}
		
		UserAndProfile userAndProfile = new UserAndProfile();
		userAndProfile.setUserAndProfileId(profile.getId(), user.getId());
		userAndProfile.setProfile(profile);
		userAndProfile.setUser(user);
		userAndProfile.setStart(LocalDate.now());
		
		user.getProfiles().add(userAndProfile);
		
		userAndProfileRepository.save(userAndProfile);
		return true;
	}

	public Boolean removeRole(ProfileUserForm form) {
		Optional<Profile> optProfile = profileRepository.findById(form.getProfileId());
		Optional<User> optUser = userRepository.findById(form.getUserId());
		
		if (optProfile.isEmpty() || optUser.isEmpty()) {
			return false;
		}
		
		Profile profile = optProfile.get();
		User user = optUser.get();
		
		Optional<UserAndProfile> optUP = userAndProfileRepository.exist(user.getId(), profile.getId());
		if (optUP.isEmpty()) {
			return false;
		}
		
		profile.getUsers().removeIf(u -> u.getProfile().getId() == profile.getId());
		user.getProfiles().removeIf(p -> p.getUser().getId() == user.getId());
		
		UserAndProfile userAndProfile = optUP.get();
		userAndProfile.setEnd(LocalDate.now());
		
		profileRepository.save(profile);
		userRepository.save(user);
		userAndProfileRepository.save(userAndProfile);
		return true;
	}

	public Boolean changeStatus(Long id) {
		Profile profile = this.getOne(id);
		if (profile == null) {
			return false;
		}
		Boolean status = profile.getStatus();
		status = !status;
		profile.setStatus(status);
		profileRepository.save(profile);
		return true;
	}

}
