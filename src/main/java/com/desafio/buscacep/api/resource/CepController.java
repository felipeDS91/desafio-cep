package com.desafio.buscacep.api.resource;

import java.util.Optional;

import javax.validation.ConstraintViolationException;
import javax.validation.constraints.Pattern;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.desafio.buscacep.api.dto.AddressDTO;
import com.desafio.buscacep.core.RegexPattern;
import com.desafio.buscacep.service.AddressService;

@RestController
@Validated
@RequestMapping("/api/cep")
public class CepController {
	

	private AddressService addressService; 
				
	public CepController(AddressService addressService) {
		this.addressService = addressService;
	}
		
	@GetMapping("{zipCode}")
	public AddressDTO get(
			@PathVariable("zipCode") 
			@Pattern(regexp = RegexPattern.ZIP_CODE, message = "Formato inválido para o cep") 
			String zipCode) {
		
		Optional<AddressDTO> address = addressService.findByZipCode(zipCode);
						
		return address.orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND) );
		
	}	
	
	// Trata as excessões de validação
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<String> handleValidationException(ConstraintViolationException exception) {
		
		return new ResponseEntity<String>(exception.getMessage(), HttpStatus.BAD_REQUEST);
	}	
	
	// Trata as excessões quando o recurso não for localizado
	@ExceptionHandler(ResponseStatusException.class)
	public ResponseEntity<String> handleValidationException(ResponseStatusException exception) {
				
		return new ResponseEntity<String>(exception.getMessage() , HttpStatus.NOT_FOUND);
	}	
	
	// Trata as excessões gerais
	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handleValidationException(Exception exception) {
		
		return new ResponseEntity<String>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}	
	
}
