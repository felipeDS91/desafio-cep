package com.desafio.buscacep.service;

import java.util.Optional;

import com.desafio.buscacep.api.model.entity.Address;

public interface AddressSearchCacheService {

	Address save(Address any);

	Optional<Address> getByZipCode(String zipCode);

}
