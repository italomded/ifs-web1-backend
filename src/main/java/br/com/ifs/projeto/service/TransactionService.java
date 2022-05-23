package br.com.ifs.projeto.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import br.com.ifs.projeto.dto.form.TransactionForm;
import br.com.ifs.projeto.model.ModelService;
import br.com.ifs.projeto.model.Profile;
import br.com.ifs.projeto.model.Transaction;
import br.com.ifs.projeto.model.User;
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
			User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			List<Profile> userProfiles = new ArrayList<>();
			user.getProfiles().forEach(p -> userProfiles.add(p.getProfile()));
			Transaction transaction = optTransaction.get();
			Set<Profile> tranProfiles = transaction.getProfiles();
			
			for (Profile uProfile : userProfiles) {
				for (Profile tProfile : tranProfiles) {
					if (tProfile.getId() == uProfile.getId()) {
						return transaction;
					}
				}
			}
			
			transaction.setUrl("ACCESS DENIED");
			return transaction;
		} else {
			return null;
		}
	}
	
	public Boolean delete(Long id) {
		Transaction transaction = this.getOne(id);
		if (transaction != null) {
			transaction.getProfiles().removeIf(p -> {
				p.getTransactions().removeIf(t -> t.getId() == transaction.getId());
				return true;
			});
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
	
	public Boolean changeStatus(Long id) {
		Transaction transaction = this.getOne(id);
		if (transaction == null) {
			return false;
		}
		transaction.setStatus(!transaction.getStatus());
		transactionRepository.save(transaction);
		return true;
	}
	
}
