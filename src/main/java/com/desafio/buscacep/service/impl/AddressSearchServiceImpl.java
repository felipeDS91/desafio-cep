package com.desafio.buscacep.service.impl;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.desafio.buscacep.api.dto.AddressDTO;
import com.desafio.buscacep.api.dto.AddressViaCEPDTO;
import com.desafio.buscacep.api.model.entity.Address;
import com.desafio.buscacep.service.AddressSearchAPIService;
import com.desafio.buscacep.service.AddressSearchCacheService;
import com.desafio.buscacep.service.AddressSearchService;

@Service
public class AddressSearchServiceImpl implements AddressSearchService {
	
	private AddressSearchCacheService addressCacheService;	
	private ModelMapper modelMapper;
	private AddressSearchAPIService addressSearchService;	

	public AddressSearchServiceImpl(AddressSearchCacheService addressCacheService, ModelMapper modelMapper, AddressSearchAPIService addressSearchService) {		
		this.addressCacheService = addressCacheService;
		this.modelMapper = modelMapper;
		this.addressSearchService = addressSearchService;
	}

	@Override
	public Optional<AddressDTO> byZipCode(String zipCode) {
		Optional<AddressDTO> address = addressCacheService
				.getByZipCode(zipCode)
				.map( cep -> modelMapper.map(cep, AddressDTO.class)); 
							
		if (address.isEmpty()) {			
			Optional<AddressViaCEPDTO> cep = addressSearchService.byZipCode(zipCode);					
			
			if (cep.isPresent()) {
				if (cep.get().getErro() == null) {					
					address = Optional.of(addressViaCEPDTOToCepDTO(cep));
					Address entity = modelMapper.map(addressViaCEPDTOToCepDTO(cep), Address.class);				
					addressCacheService.save(entity);						
				}
			}
		}

		return address;

	}
	
	private AddressDTO addressViaCEPDTOToCepDTO(Optional<AddressViaCEPDTO> optional) {
		
		return AddressDTO.builder()
						.city(optional.get().getLocalidade())
						.neighborhood(optional.get().getBairro())
						.state(optional.get().getUf())
						.street(optional.get().getLogradouro())
						.zipCode(optional.get().getCep())
						.build(); 						
	}	
	

}
