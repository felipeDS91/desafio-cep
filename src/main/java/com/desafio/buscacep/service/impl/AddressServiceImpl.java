package com.desafio.buscacep.service.impl;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.desafio.buscacep.api.dto.AddressDTO;
import com.desafio.buscacep.api.dto.AddressViaCEPDTO;
import com.desafio.buscacep.api.model.entity.Address;
import com.desafio.buscacep.service.AddressSearchService;
import com.desafio.buscacep.service.AddressService;
import com.desafio.buscacep.service.AddressCacheService;

@Service
public class AddressServiceImpl implements AddressService {
	
	private AddressCacheService addressCacheService;	
	private ModelMapper modelMapper;
	private AddressSearchService addressSearchService;

	public AddressServiceImpl(AddressCacheService addressCacheService, ModelMapper modelMapper, AddressSearchService addressSearchService) {		
		this.addressCacheService = addressCacheService;
		this.modelMapper = modelMapper;
		this.addressSearchService = addressSearchService;
	}
	
	
	@Override
	public Optional<AddressDTO> findByZipCode(String zipCode) {
		
		Optional<AddressDTO> address = addressCacheService
				.getByZipCode(zipCode)
				.map( cep -> modelMapper.map(cep, AddressDTO.class)); 
							
		if (address.isEmpty()) {			
			Optional<AddressViaCEPDTO> cep = addressSearchService.byZipCode(zipCode);					
			
			if (cep.isPresent()) {
				if (cep.get().getErro() == null) {					
					address = Optional.of(addressDTOToCepDTO(cep));
					Address entity = modelMapper.map(addressDTOToCepDTO(cep), Address.class);				
					addressCacheService.save(entity);										
				}
			}
		}
		
		return address;
	}
	
	private AddressDTO addressDTOToCepDTO(Optional<AddressViaCEPDTO> optional) {
		
		return AddressDTO.builder()
						.city(optional.get().getLocalidade())
						.neighborhood(optional.get().getBairro())
						.state(optional.get().getUf())
						.street(optional.get().getLogradouro())
						.zipCode(optional.get().getCep())
						.build(); 						
	}	

}
