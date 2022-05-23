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
import br.com.ifs.projeto.dto.ModelSystemDTO;
import br.com.ifs.projeto.dto.form.SystemForm;
import br.com.ifs.projeto.model.ModelSystem;
import br.com.ifs.projeto.service.SystemService;

@RestController
@RequestMapping("system")
public class SystemController {

	@Autowired
	private SystemService systemService;

	@GetMapping()
	public ResponseEntity<List<ModelSystemDTO>> getAll() {
		List<ModelSystem> list = systemService.getAll();
		List<ModelSystemDTO> responseList = list.stream().map(ModelSystemDTO::new).toList();
		return ResponseEntity.ok(responseList);
	}

	@GetMapping("{id}")
	public ResponseEntity<ModelSystemDTO> getOne(@PathVariable String id) {
		ModelSystem system = systemService.getOne(Long.parseLong(id));
		if (system != null) {
			return ResponseEntity.ok(new ModelSystemDTO(system));
		} else {
			return ResponseEntity.badRequest().build();
		}
	}

	@Transactional
	@PostMapping
	public ResponseEntity<CreatedDTO> create(@RequestBody @Valid SystemForm form) {
		Long idCreated = systemService.create(form);
		if (idCreated != null) {
			// CreatedDTO
			return ResponseEntity.created(URI.create("system/" + idCreated)).body(new CreatedDTO(idCreated));
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

	// for enable or disable a system
	@PutMapping("{id}")
	public ResponseEntity<?> changeStatus(@PathVariable String id) {
		Boolean changed = systemService.changeStatus(Long.parseLong(id));
		if (changed) {
			return ResponseEntity.ok().build();
		} else {
			return ResponseEntity.badRequest().build();
		}
	}

}
