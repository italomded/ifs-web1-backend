package br.com.ifs.projeto.controller;

import java.net.URI;
import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.ifs.projeto.dto.CreatedDTO;
import br.com.ifs.projeto.dto.ErrorDTO;
import br.com.ifs.projeto.dto.ProfileDTO;
import br.com.ifs.projeto.dto.form.ProfileForm;
import br.com.ifs.projeto.dto.form.ProfileTransactionForm;
import br.com.ifs.projeto.dto.form.ProfileUserForm;
import br.com.ifs.projeto.model.Profile;
import br.com.ifs.projeto.service.ProfileService;

@RestController
@RequestMapping("profile")
public class ProfileController {

	@Autowired
	private ProfileService profileService;
	
	@GetMapping
	public ResponseEntity<List<ProfileDTO>> getAll() {
		List<Profile> profileList = profileService.getAll();
		List<ProfileDTO> responseList = profileList.stream().map(ProfileDTO::new).toList();
		return ResponseEntity.ok(responseList);
	}
	
	@GetMapping("{id}")
	public ResponseEntity<ProfileDTO> getOne(@PathVariable String id) {
		Profile profile = profileService.getOne(Long.parseLong(id));
		if (profile != null) {
			profile.getUsers().forEach(u -> System.out.println(u.getUser().getId()));
			return ResponseEntity.ok(new ProfileDTO(profile));
		} else {
			return ResponseEntity.badRequest().build();
		}
	}
	
	@Transactional
	@PostMapping
	public ResponseEntity<?> create(@RequestBody @Valid ProfileForm form) {
		Long idCreated = profileService.create(form);
		if (idCreated == null) {
			return ResponseEntity.badRequest().body(new ErrorDTO("name", "Invalid name"));
		} else {
			return ResponseEntity.created(URI.create("profile/" + idCreated)).body(new CreatedDTO(idCreated));
		}
	}
	
	
	@DeleteMapping("{id}")
	public ResponseEntity<?> delete(@PathVariable String id) {
		Boolean deleted = profileService.delete(Long.parseLong(id));
		if (deleted) {
			return ResponseEntity.ok().build();
		} else {
			return ResponseEntity.badRequest().build();
		}
	}
	
	//
	
	@PutMapping("user")
	public ResponseEntity<?> addRole(@RequestBody @Valid ProfileUserForm form) {
		Boolean added = profileService.addRole(form);
		if (added) {
			return ResponseEntity.ok().build();
		} else {
			return ResponseEntity.badRequest().build();
		}
	}
	
	@DeleteMapping("user")
	public ResponseEntity<?> removerRole(@Valid @RequestBody ProfileUserForm form) {
		Boolean removed = profileService.removeRole(form);
		if (removed) {
			return ResponseEntity.ok().build();
		} else {
			return ResponseEntity.badRequest().build();
		}
	}
	
	// for enable or disable a profile
	@PutMapping("{id}")
	public ResponseEntity<?> changeStatus(@PathVariable String id) {
		Boolean changed = profileService.changeStatus(Long.parseLong(id));
		if (changed) {
			return ResponseEntity.ok().build();
		} else {
			return ResponseEntity.badRequest().build();
		}
	}
	
	@PutMapping("transaction")
	public ResponseEntity<?> addTransaction(@RequestBody @Valid ProfileTransactionForm form) {
		Boolean added = profileService.addTransaction(form);
		if (added) {
			return ResponseEntity.ok().build();
		} else {
			return ResponseEntity.badRequest().build();
		}
	}
	
	@DeleteMapping("transaction")
	public ResponseEntity<?> removeTransaction(@RequestBody @Valid ProfileTransactionForm form) {
		Boolean removed = profileService.removeTransaction(form);
		if (removed) {
			return ResponseEntity.ok().build();
		} else {
			return ResponseEntity.badRequest().build();
		}
	}
	
}
