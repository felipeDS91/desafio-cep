package com.desafio.buscacep.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.desafio.buscacep.api.model.entity.Address;
import com.desafio.buscacep.exception.BusinessException;
import com.desafio.buscacep.model.repository.AddressRepository;
import com.desafio.buscacep.service.AddressSearchCacheService;

@Service
public class AddresCacheServiceImpl implements AddressSearchCacheService {

	private AddressRepository repository;

	public AddresCacheServiceImpl(AddressRepository repository) {		
		this.repository = repository;
	}

	@Override
	public Address save(Address cep) {
		if( repository.existsByZipCode(cep.getZipCode()) ) {
			throw new BusinessException("Cep já cadastrado");
		}
				
		String sanitizedZipCode = cep.getZipCode().replace("-", "");		
		cep.setZipCode(sanitizedZipCode);
		
		return repository.save(cep);
	}

	@Override
	public Optional<Address> getByZipCode(String zipCode) {		
		return this.repository.findByZipCode(zipCode);
	}

}
