package br.com.ifs.projeto.service;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ifs.projeto.dto.form.TransactionForm;
import br.com.ifs.projeto.model.ModelService;
import br.com.ifs.projeto.model.Transaction;
import br.com.ifs.projeto.repository.ServiceRepository;
import br.com.ifs.projeto.repository.TransactionRepository;

@Service
public class TransactionService {

	@Autowired
	private TransactionRepository transactionRepository;
	
	@Autowired
	private ServiceRepository serviceRepository;
	
	public List<Transaction> getAll() {
		return transactionRepository.findAll();
	}
	
	public Transaction getOne(Long id) {
		Optional<Transaction> optTransaction = transactionRepository.findById(id);
		if (optTransaction.isPresent()) {
			return optTransaction.get();
		} else {
			return null;
		}
	}
	
	public Boolean delete(Long id) {
		Transaction transaction = this.getOne(id);
		if (transaction != null) {
			transactionRepository.delete(transaction);
			return true;
		} else {
			return false;
		}
	}

	public Long create(@Valid TransactionForm form) {
		Transaction transaction = form.toTransaction();
		Optional<ModelService> optService = serviceRepository.findById(form.getServiceId());
		if (optService.isPresent()) {
			ModelService service = optService.get();
			service.getTransactions().add(transaction);
			transaction.setService(service);
			transactionRepository.save(transaction);
			return transaction.getId();
		} else {
			return null;
		}
	}
	
}
