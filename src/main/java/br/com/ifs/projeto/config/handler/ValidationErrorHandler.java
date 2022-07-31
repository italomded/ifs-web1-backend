package br.com.ifs.projeto.config.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.com.ifs.projeto.dto.ErrorDTO;

@RestControllerAdvice
public class ValidationErrorHandler {
	
	@Autowired
	private MessageSource messageSource;

	@ExceptionHandler(value = BindException.class)
	public ResponseEntity<List<ErrorDTO>> formError(BindException exception) {
		List<ErrorDTO> list = new ArrayList<>();
		exception.getBindingResult().getFieldErrors().forEach(error -> {
			String label = error.getField();
			String message = messageSource.getMessage(error, Locale.US);
			ErrorDTO errorDTO = new ErrorDTO(label, message);
			list.add(errorDTO);
		});
		return ResponseEntity.badRequest().body(list);
	}
	
}
