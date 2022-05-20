package br.com.ifs.projeto.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.ifs.projeto.dto.CreatedDTO;
import br.com.ifs.projeto.dto.ErrorDTO;
import br.com.ifs.projeto.dto.form.ProfileForm;
import br.com.ifs.projeto.model.Profile;
import br.com.ifs.projeto.service.ProfileService;

@RestController
@RequestMapping("profile")
public class ProfileController {

	@Autowired
	private ProfileService profileService;
	
	@GetMapping
	public ResponseEntity<List<Profile>> getAll() {
		List<Profile> profileList = profileService.getAll();
		return ResponseEntity.ok(profileList);
	}
	
	@GetMapping("{id}")
	public ResponseEntity<Profile> getOne(@PathVariable String id) {
		Profile profile = profileService.getOne(Long.parseLong(id));
		if (profile != null) {
			return ResponseEntity.ok(profile);
		} else {
			return ResponseEntity.badRequest().build();
		}
	}
	
	@PostMapping
	public ResponseEntity<?> create(ProfileForm form) {
		Long id = profileService.create(form);
		if (id == null) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorDTO("login", "Not unique"));
		} else {
			return ResponseEntity.created(URI.create("profile/" + id)).body(new CreatedDTO(id));
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
	

	// profile/user { id profile, id user, end date can be null }
	// put para adicionar role ao usuario
	// put para adicionar end date ao profile do usuario
	// delete para deletar role de usuario
	
	// profile/transaction
	// put para adicionar role a transacao
	// delete para remover role de transacao
	
}
