package br.com.ifs.projeto.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.ifs.projeto.dto.form.SystemForm;
import br.com.ifs.projeto.model.ModelSystem;
import br.com.ifs.projeto.service.SystemService;

@RestController
@RequestMapping("system")
public class SystemController {
	
	@Autowired
	private SystemService systemService;
	
	@GetMapping()
	public ResponseEntity<List<ModelSystem>> getAll() {
		List<ModelSystem> list = systemService.getAll();
		return ResponseEntity.ok(list);
	}
	
	@GetMapping("{id}")
	public ResponseEntity<ModelSystem> getOne(@PathVariable String id) {
		ModelSystem system = systemService.getOne(Long.parseLong(id));
		if (system != null) {
			return ResponseEntity.ok(system);
		} else {
			return ResponseEntity.badRequest().build();
		}
	}
	
	@PostMapping
	public ResponseEntity<Long> create(@Valid SystemForm form) {
		Long idCreated = systemService.create(form);
		if (idCreated != null) {
			//CreatedDTO
			return ResponseEntity.created(URI.create("system/" + idCreated)).body(idCreated);
		} else {
			return ResponseEntity.badRequest().build();
		}
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<?> delete(@PathVariable String id) {
		Boolean deleted = systemService.delete(Long.parseLong(id));
		if (deleted) {
			return ResponseEntity.ok().build();
		} else {
			return ResponseEntity.badRequest().build();
		}
	}
	
	// metodo put faltando
	// adicionar servicos

}