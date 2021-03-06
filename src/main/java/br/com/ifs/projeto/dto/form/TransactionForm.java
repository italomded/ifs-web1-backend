package br.com.ifs.projeto.dto.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import br.com.ifs.projeto.model.Transaction;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionForm {

	@NotBlank @NotNull
	private String name;
	
	@NotBlank @NotNull
	private String url;
	
	@NotNull
	private Long serviceId;
	
	public Transaction toTransaction() {
		Transaction transaction = new Transaction();
		transaction.setName(name);
		transaction.setUrl(url);
		transaction.setStatus(true);
		return transaction;
	}
	
}
