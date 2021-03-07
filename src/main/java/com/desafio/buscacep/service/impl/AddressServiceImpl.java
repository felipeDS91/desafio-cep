package com.desafio.buscacep.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.desafio.buscacep.api.dto.AddressDTO;
import com.desafio.buscacep.service.AddressService;
import com.desafio.buscacep.service.AddressSearchService;

@Service
public class AddressServiceImpl implements AddressService {
				
	private AddressSearchService addressSearchService;	

	public AddressServiceImpl(AddressSearchService addressSearchService) {		
		this.addressSearchService = addressSearchService;
	}
		
	@Override
	public Optional<AddressDTO> findByZipCode(String zipCode) {
				
		Optional<AddressDTO> address = null;		
				
		String findZipCode = zipCode;
		
		for (int i = zipCode.length(); i >= 0; i--) {
									
			findZipCode = replaceCharWithZero(findZipCode, i);					
			
			address = addressSearchService.byZipCode(findZipCode.toString());
			
			if (address.isPresent()) break;
		 }
		
						
		return address;
	}


	private String replaceCharWithZero(String string, int index) {
		StringBuilder result = new StringBuilder(string);
		
		if (index >= 0 & index < string.length()) result.setCharAt(index, '0');		
		
		return result.toString();
	}

			
}
