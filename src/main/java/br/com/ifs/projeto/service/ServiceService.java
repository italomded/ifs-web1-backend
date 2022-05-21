package br.com.ifs.projeto.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ifs.projeto.dto.form.ServiceForm;
import br.com.ifs.projeto.model.ModelService;
import br.com.ifs.projeto.model.ModelSystem;
import br.com.ifs.projeto.repository.ServiceRepository;
import br.com.ifs.projeto.repository.SystemRepository;

@Service
public class ServiceService {

	@Autowired
	private ServiceRepository serviceRepository;
	
	@Autowired
	private SystemRepository systemRepository;
	
	public List<ModelService> getAll() {
		return serviceRepository.findAll();
	}

	public ModelService getOne(Long id) {
		Optional<ModelService> optService = serviceRepository.findById(id);
		if (optService.isPresent()) {
			return optService.get();
		} else {
			return null;
		}
	}
	
	public Boolean delete(Long id) {
		ModelService service = this.getOne(id);
		if (service != null) {
			serviceRepository.delete(service);
			return true;
		} else {
			return false;
		}
	}

	public Long create(ServiceForm form) {
		ModelService service = form.toService();
		Optional<ModelSystem> optSystem = systemRepository.findById(form.getSystemId());
		if (optSystem.isPresent()) {
			ModelSystem system = optSystem.get();
			service.setSystem(system);
			system.getServices().add(service);
			serviceRepository.save(service);
			return service.getId();
		} else {
			return null;
		}
	}
	
	public Boolean changeStatus(Long id) {
		ModelService service = this.getOne(id);
		if (service == null) {
			return false;
		}
		service.setStatus(!service.getStatus());
		serviceRepository.save(service);
		return true;
	}
	
}
