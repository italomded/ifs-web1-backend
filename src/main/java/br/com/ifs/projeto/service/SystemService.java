package br.com.ifs.projeto.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ifs.projeto.dto.form.SystemForm;
import br.com.ifs.projeto.model.ModelSystem;
import br.com.ifs.projeto.repository.SystemRepository;

@Service
public class SystemService {

	@Autowired
	private SystemRepository systemRepository;
	
	public List<ModelSystem> getAll() {
		return systemRepository.findAll();
	}

	public ModelSystem getOne(Long id) {
		Optional<ModelSystem> optSystem = systemRepository.findById(id);
		if (optSystem.isPresent()) {
			return optSystem.get();
		} else {
			return null;
		}
	}

	public Boolean delete(Long id) {
		ModelSystem system = this.getOne(id);
		if (system != null) {
			systemRepository.delete(system);
			return true;
		} else {
			return false;
		}	
	}

	public Long create(SystemForm form) {
		ModelSystem system = form.toSystem();
		systemRepository.save(system);
		return system.getId();
	}
	
	public Boolean changeStatus(Long id) {
		ModelSystem system = this.getOne(id);
		if (system == null) {
			return false;
		}
		system.setStatus(!system.getStatus());
		systemRepository.save(system);
		return true;
	}
	
}
