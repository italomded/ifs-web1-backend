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
import br.com.ifs.projeto.dto.ModelServiceDTO;
import br.com.ifs.projeto.dto.form.ServiceForm;
import br.com.ifs.projeto.model.ModelService;
import br.com.ifs.projeto.service.ServiceService;

@RestController
@RequestMapping("service")
public class ServiceController {

	@Autowired
	private ServiceService serviceService;

	@GetMapping()
	public ResponseEntity<List<ModelServiceDTO>> getAll() {
		List<ModelService> list = serviceService.getAll();
		List<ModelServiceDTO> responseList = list.stream().map(ModelServiceDTO::new).toList();
		return ResponseEntity.ok(responseList);
	}

	@GetMapping("{id}")
	public ResponseEntity<ModelServiceDTO> getOne(@PathVariable String id) {
		ModelService service = serviceService.getOne(Long.parseLong(id));
		if (service != null) {
			return ResponseEntity.ok(new ModelServiceDTO(service));
		} else {
			return ResponseEntity.badRequest().build();
		}

	}

	@Transactional
	@PostMapping
	public ResponseEntity<CreatedDTO> create(@RequestBody @Valid ServiceForm form) {
		Long idCreated = serviceService.create(form);
		if (idCreated != null) {
			// CreatedDTO
			return ResponseEntity.created(URI.create("service/" + idCreated)).body(new CreatedDTO(idCreated));
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

	// for enable or disable a service
	@PutMapping("{id}")
	public ResponseEntity<?> changeStatus(@PathVariable String id) {
		Boolean changed = serviceService.changeStatus(Long.parseLong(id));
		if (changed) {
			return ResponseEntity.ok().build();
		} else {
			return ResponseEntity.badRequest().build();
		}
	}

}
