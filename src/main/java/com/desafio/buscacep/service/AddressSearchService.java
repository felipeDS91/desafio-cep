package com.desafio.buscacep.service;

import java.util.Optional;

import com.desafio.buscacep.api.dto.AddressDTO;

public interface AddressSearchService {
	Optional<AddressDTO> byZipCode(String zipCode);
}
