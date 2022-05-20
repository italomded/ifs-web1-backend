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
import br.com.ifs.projeto.dto.form.UserForm;
import br.com.ifs.projeto.model.User;
import br.com.ifs.projeto.service.UserService;

@RestController
@RequestMapping("user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	// somente para adm
	@GetMapping
	public ResponseEntity<List<User>> getAll() {
		List<User> userList = userService.getAll();
		return ResponseEntity.ok(userList);
	}
	
	// somente para adm e o proprio usuario
	@GetMapping("{id}")
	public ResponseEntity<User> getOne(@PathVariable String id) {
		User user = userService.getOne(Long.parseLong(id));
		if (user != null) {
			return ResponseEntity.ok(user);
		} else {
			return ResponseEntity.badRequest().build();
		}
	}
	
	// logado
	@PostMapping
	public ResponseEntity<?> create(UserForm form) {
		Long id = userService.create(form);
		if (id == null) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorDTO("login", "Not unique"));
		} else {
			return ResponseEntity.created(URI.create("user/" + id)).body(new CreatedDTO(id));
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

}
