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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.ifs.projeto.dto.CreatedDTO;
import br.com.ifs.projeto.dto.TransactionDTO;
import br.com.ifs.projeto.dto.form.TransactionForm;
import br.com.ifs.projeto.model.Transaction;
import br.com.ifs.projeto.service.TransactionService;

@RestController
@RequestMapping("transaction")
public class TransactionController {

	@Autowired
	private TransactionService transactionService;

	@GetMapping()
	public ResponseEntity<List<TransactionDTO>> getAll() {
		List<Transaction> list = transactionService.getAll();
		List<TransactionDTO> responseList = list.stream().map(TransactionDTO::new).toList();
		return ResponseEntity.ok(responseList);
	}

	@GetMapping("{id}")
	public ResponseEntity<TransactionDTO> getOne(@PathVariable String id) {
		Transaction transaction = transactionService.getOne(Long.parseLong(id));
		if (transaction != null) {
			return ResponseEntity.ok(new TransactionDTO(transaction));
		} else {
			return ResponseEntity.badRequest().build();
		}
	}

	@Transactional
	@PostMapping
	public ResponseEntity<CreatedDTO> create(@Valid TransactionForm form) {
		Long idCreated = transactionService.create(form);
		if (idCreated != null) {
			// CreatedDTO
			return ResponseEntity.created(URI.create("transaction/" + idCreated)).body(new CreatedDTO(idCreated));
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

	// for enable or disable a transaction
	@PutMapping("{id}")
	public ResponseEntity<?> changeStatus(@PathVariable String id) {
		Boolean changed = transactionService.changeStatus(Long.parseLong(id));
		if (changed) {
			return ResponseEntity.ok().build();
		} else {
			return ResponseEntity.badRequest().build();
		}
	}

}
