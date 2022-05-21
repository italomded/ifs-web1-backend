package br.com.ifs.projeto.dto;

import java.util.List;

import br.com.ifs.projeto.model.Transaction;
import lombok.Getter;

@Getter
public class TransactionDTO {

	private Long id;
	private String name;
	private Boolean status;
	private String url;
	private Long service_id;
	private List<ModelDTO> profiles;
	
	public TransactionDTO(Transaction transaction) {
		this.id = transaction.getId();
		this.name = transaction.getName();
		this.status = transaction.getStatus();
		this.url = transaction.getUrl();
		this.service_id = transaction.getService().getId();
		this.profiles = transaction.getProfiles().stream().map(ModelDTO::new).toList();
	}
	
}
