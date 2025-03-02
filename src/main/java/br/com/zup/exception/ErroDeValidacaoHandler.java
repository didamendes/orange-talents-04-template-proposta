package br.com.zup.exception;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErroDeValidacaoHandler {
	
	@Autowired
	private MessageSource messageSource;

	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public List<ErroDeFormularioDTO> handle(MethodArgumentNotValidException ex) {
		List<ErroDeFormularioDTO> dto = new ArrayList<>();
		List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
		
		fieldErrors.forEach(e -> {
			String mensagem = messageSource.getMessage(e, LocaleContextHolder.getLocale());
			ErroDeFormularioDTO erro = new ErroDeFormularioDTO(e.getField(), mensagem);
			dto.add(erro);
		});
		
		return dto;
	}

	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(Exception.class)
	public ErroDeFormularioDTO handle(Exception ex) {
		ErroDeFormularioDTO erroDeFormularioDTO = new ErroDeFormularioDTO(ex.getMessage());
		return erroDeFormularioDTO;
	}

	@ResponseStatus(code = HttpStatus.NOT_FOUND)
	@ExceptionHandler(NenhumRegistroEncontado.class)
	public ErroDeFormularioDTO nenhumRegistroEncontrado(Exception ex) {
		ErroDeFormularioDTO erroDeFormularioDTO = new ErroDeFormularioDTO(ex.getMessage());
		return erroDeFormularioDTO;
	}
	
}
