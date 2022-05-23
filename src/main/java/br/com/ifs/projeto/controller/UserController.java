package br.com.ifs.projeto.controller;

import java.net.URI;
import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import br.com.ifs.projeto.dto.UserDTO;
import br.com.ifs.projeto.dto.form.UserForm;
import br.com.ifs.projeto.model.User;
import br.com.ifs.projeto.service.UserService;

@RestController
@RequestMapping("user")
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping
	public ResponseEntity<List<UserDTO>> getAll() {
		List<User> userList = userService.getAll();
		List<UserDTO> userResponse = userList.stream().map(UserDTO::new).toList();
		return ResponseEntity.ok(userResponse);
	}

	@GetMapping("{id}")
	public ResponseEntity<UserDTO> getOne(@PathVariable String id) {
		User user = userService.getOne(Long.parseLong(id));
		if (user != null) {
			return ResponseEntity.ok(new UserDTO(user));
		} else {
			return ResponseEntity.badRequest().build();
		}
	}

	@Transactional
	@PostMapping
	public ResponseEntity<CreatedDTO> create(@RequestBody @Valid UserForm form) {
		Long idCreated = userService.create(form);
		if (idCreated != null) {
			return ResponseEntity.created(URI.create("user/" + idCreated)).body(new CreatedDTO(idCreated));
		} else {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
	}

	@DeleteMapping("{id}")
	public ResponseEntity<?> delete(@PathVariable String id) {
		Boolean deleted = userService.delete(Long.parseLong(id));
		if (deleted) {
			return ResponseEntity.ok().build();
		} else {
			return ResponseEntity.badRequest().build();
		}
	}

	// for enable or disable a user
	@PutMapping("{id}")
	public ResponseEntity<?> changeStatus(@PathVariable String id) {
		Boolean changed = userService.changeStatus(Long.parseLong(id));
		if (changed) {
			return ResponseEntity.ok().build();
		} else {
			return ResponseEntity.badRequest().build();
		}
	}

}
