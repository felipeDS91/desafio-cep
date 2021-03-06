package com.desafio.buscacep.service;

import java.util.Optional;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.desafio.buscacep.api.dto.AddressViaCEPDTO;


@FeignClient(url= "https://viacep.com.br/ws/" , name = "viacep")
public interface AddressSearchService  {	
	
	@GetMapping("{cep}/json")
	Optional<AddressViaCEPDTO> byZipCode(@PathVariable("cep") String cep);			
	
}
