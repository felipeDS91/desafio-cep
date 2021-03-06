package com.desafio.buscacep.api.resource;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.desafio.buscacep.api.dto.AddressDTO;
import com.desafio.buscacep.api.exception.ApiErrors;
import com.desafio.buscacep.service.AddressService;

@RestController
@RequestMapping("/api/cep")
public class CepController {
	

	private AddressService addressService; 
				
	public CepController(AddressService addressService) {
		this.addressService = addressService;
	}

	
	@GetMapping("{zipCode}")
	public AddressDTO get(@PathVariable String zipCode) {
		
		Optional<AddressDTO> address = addressService.findByZipCode(zipCode);
						
		return address.orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND) );
		
	}

	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ApiErrors handleValidationException(MethodArgumentNotValidException ex) {
		BindingResult bindingResult = ex.getBindingResult();
				
		return new ApiErrors(bindingResult);
	}
	
}
