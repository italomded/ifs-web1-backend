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

import br.com.ifs.projeto.dto.form.ServiceForm;
import br.com.ifs.projeto.model.ModelService;
import br.com.ifs.projeto.service.ServiceService;

@RestController
@RequestMapping("service")
public class ServiceController {
	
	@Autowired
	private ServiceService serviceService;
	
	@GetMapping()
	public ResponseEntity<List<ModelService>> getAll() {
		List<ModelService> list = serviceService.getAll();
		return ResponseEntity.ok(list);
	}
	
	
	@GetMapping("{id}")
	public ResponseEntity<ModelService> getOne(@PathVariable String id) {
		ModelService service = serviceService.getOne(Long.parseLong(id));
		if (service != null) {
			return ResponseEntity.ok(service);
		} else {
			return ResponseEntity.badRequest().build();
		}

	}
	
	@PostMapping
	public ResponseEntity<?> create(@Valid ServiceForm form) {
		Long idCreated = serviceService.create(form);
		if (idCreated != null) {
			//CreatedDTO
			return ResponseEntity.created(URI.create("service/" + idCreated)).body(idCreated);
		} else {
			return ResponseEntity.badRequest().build();
		}
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<?> delete(@PathVariable String id) {
		Boolean deleted = serviceService.delete(Long.parseLong(id));
		if (deleted) {
			return ResponseEntity.ok().build();
		} else {
			return ResponseEntity.badRequest().build();
		}
	}
	
	// metodo put faltando
	// adicionar transacao
	
}
