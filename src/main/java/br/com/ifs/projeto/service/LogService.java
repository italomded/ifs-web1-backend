package br.com.ifs.projeto.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.ifs.projeto.model.Log;
import br.com.ifs.projeto.repository.LogRepository;

@Service
public class LogService {

	@Autowired
	private LogRepository logRepository;
	
	public Page<Log> getAll(Pageable pageable) {
		return logRepository.findAll(pageable);
	}

	public Log getOne(Long id) {
		Optional<Log> optLog = logRepository.findById(id);
		if (optLog.isPresent()) {
			return optLog.get();
		} else {
			return null;
		}
	}

	public Boolean clear() {
		logRepository.deleteAll();
		return true;
	}

}
