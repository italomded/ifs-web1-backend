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

import br.com.ifs.projeto.dto.form.TransactionForm;
import br.com.ifs.projeto.model.Transaction;
import br.com.ifs.projeto.service.TransactionService;

@RestController
@RequestMapping("transaction")
public class TransactionController {
	
	@Autowired
	private TransactionService transactionService;
	
	@GetMapping()
	public ResponseEntity<List<Transaction>> getAll() {
		List<Transaction> list = transactionService.getAll();
		return ResponseEntity.ok(list);
	}
	
	@GetMapping("{id}")
	public ResponseEntity<Transaction> getOne(@PathVariable String id) {
		Transaction transaction = transactionService.getOne(Long.parseLong(id));
		if (transaction != null) {
			return ResponseEntity.ok(transaction);
		} else {
			return ResponseEntity.badRequest().build();
		}
	}
	
	@PostMapping
	public ResponseEntity<Long> create(@Valid TransactionForm form) {
		Long idCreated = transactionService.create(form);
		if (idCreated != null) {
			//CreatedDTO
			return ResponseEntity.created(URI.create("transaction/" + idCreated)).body(idCreated);
		} else {
			return ResponseEntity.badRequest().build();
		}
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<?> delete(@PathVariable String id) {
		Boolean deleted = transactionService.delete(Long.parseLong(id));
		if (deleted) {
			return ResponseEntity.ok().build();
		} else {
			return ResponseEntity.badRequest().build();
		}
	}
	
	// faltando put
	// adicionar profiles

}
