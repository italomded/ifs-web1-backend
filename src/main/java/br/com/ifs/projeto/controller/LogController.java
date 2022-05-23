package br.com.ifs.projeto.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.ifs.projeto.model.Log;
import br.com.ifs.projeto.service.LogService;

@RestController
@RequestMapping("/log")
public class LogController {

	@Autowired
	private LogService logService;
	
	@GetMapping
	public ResponseEntity<Page<Log>> getAll(@PageableDefault(page = 0, size = 5) Pageable pageable) {
		Page<Log> list = logService.getAll(pageable);
		return ResponseEntity.ok(list);
	}
	
	@GetMapping("{id}")
	public ResponseEntity<Log> getOne(@PathVariable String id) {
		Log log = logService.getOne(Long.parseLong(id));
		if (log != null) {
			return ResponseEntity.ok(log);
		} else {
			return ResponseEntity.badRequest().build();
		}
	}
	
	@DeleteMapping
	public ResponseEntity<List<Log>> clear() {
		Boolean cleared = logService.clear();
		if (cleared) {
			return ResponseEntity.ok().build();
		} else {
			return ResponseEntity.badRequest().build();
		}
	}
	
}
