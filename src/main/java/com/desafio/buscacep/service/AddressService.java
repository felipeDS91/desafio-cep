package com.desafio.buscacep.service;

import java.util.Optional;

import com.desafio.buscacep.api.dto.AddressDTO;

public interface AddressService {
	
	Optional<AddressDTO> findByZipCode(String zipCode);
	
}
