package br.com.ifs.projeto.config.handler;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintViolationException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.com.ifs.projeto.dto.ErrorDTO;

@RestControllerAdvice
public class ConstraintViolationHandler {

	@ExceptionHandler(value = ConstraintViolationException.class)
	public ResponseEntity<List<ErrorDTO>> integrityError(ConstraintViolationException exception) {
		List<ErrorDTO> listError = new ArrayList<>();
		exception.getConstraintViolations().forEach(cve -> {
			// nice POG
			List<String> listName = new ArrayList<>();
			cve.getPropertyPath().forEach(pp -> {
				listName.add(pp.getName());
			});
			String label = listName.get(listName.size() - 1);
			String message = cve.getMessage();
			ErrorDTO errorDTO = new ErrorDTO(label, message);
			listError.add(errorDTO);
		});
		return ResponseEntity.badRequest().body(listError);
	}
	
}
