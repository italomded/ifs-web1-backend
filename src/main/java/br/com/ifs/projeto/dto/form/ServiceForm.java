package br.com.ifs.projeto.dto.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import br.com.ifs.projeto.model.ModelService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ServiceForm {

	@NotBlank @NotNull
	private String name;
	
	@NotBlank @NotNull
	private String url;
	
	@NotNull
	private Long systemId;
	
	public ModelService toService() {
		ModelService service = new ModelService();
		service.setName(name);
		service.setUrl(url);
		service.setStatus(true);
		return service;		
	}
	
}
