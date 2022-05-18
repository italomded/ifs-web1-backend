package br.com.ifs.projeto.dto.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import br.com.ifs.projeto.model.ModelService;
import lombok.Getter;

@Getter
public class ServiceForm {

	@NotBlank @NotNull
	private String name;
	
	@NotBlank @NotNull
	private String url;
	
	@NotBlank @NotNull
	private Long systemId;
	
	public ModelService toService() {
		ModelService service = new ModelService();
		service.setName(name);
		service.setUrl(url);
		service.setStatus(true);
		return service;		
	}
	
}
