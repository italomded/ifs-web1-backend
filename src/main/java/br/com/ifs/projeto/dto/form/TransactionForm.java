package br.com.ifs.projeto.dto.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import br.com.ifs.projeto.model.Transaction;
import lombok.Getter;

@Getter
public class TransactionForm {

	@NotBlank @NotNull
	private String name;
	
	@NotBlank @NotNull
	private String url;
	
	@NotBlank @NotNull
	private Long serviceId;
	
	public Transaction toTransaction() {
		Transaction transaction = new Transaction();
		transaction.setName(name);
		transaction.setUrl(url);
		transaction.setStatus(true);
		return transaction;
	}
	
}
