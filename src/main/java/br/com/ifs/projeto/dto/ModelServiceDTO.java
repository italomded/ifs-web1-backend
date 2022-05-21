package br.com.ifs.projeto.dto;

import java.util.List;

import br.com.ifs.projeto.model.ModelService;
import lombok.Getter;

@Getter
public class ModelServiceDTO {

	private Long id;
	private String name;
	private String url;
	private Boolean status;
	private Long system_id;
	private List<ModelDTO> transactions;
	
	public ModelServiceDTO(ModelService service) {
		this.id = service.getId();
		this.name = service.getName();
		this.url = service.getUrl();
		this.status = service.getStatus();
		this.system_id = service.getSystem().getId();
		this.transactions = service.getTransactions().stream().map(ModelDTO::new).toList();
	}
	
}
