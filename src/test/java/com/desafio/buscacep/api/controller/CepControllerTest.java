package com.desafio.buscacep.api.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.desafio.buscacep.api.dto.AddressDTO;
import com.desafio.buscacep.service.AddressService;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebMvcTest
@AutoConfigureMockMvc
public class CepControllerTest {
	
	static String CEP_API = "/api/cep"; 
	
	@Autowired
	MockMvc mvc;

	@MockBean
	AddressService addressService; 
		
	@Test
	@DisplayName("Deve obter informações de endereço de um dado cep")
	public void getAddressDetailsTest() throws Exception {
		
		String zipCode = "17214165";
		
		AddressDTO dto = createNewAddress();
		
		Optional<AddressDTO> address = Optional.of(AddressDTO.builder()					
					.zipCode(zipCode)
					.street(dto.getStreet())
					.neighborhood(dto.getNeighborhood())
					.city(dto.getCity())
					.state(dto.getState())
					.build());	
		
		BDDMockito.given( addressService.findByZipCode(zipCode) ).willReturn(address);
		
		// execucao
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders
			.get(CEP_API.concat("/" + zipCode))
			.accept(MediaType.APPLICATION_JSON);
		
		mvc
			.perform(request)
			.andExpect(status().isOk())					
			.andExpect( jsonPath("zipCode").value(zipCode) )
			.andExpect( jsonPath("street").value(dto.getStreet()) )
			.andExpect( jsonPath("neighborhood").value(dto.getNeighborhood()) )
			.andExpect( jsonPath("city").value(dto.getCity()) )
			.andExpect( jsonPath("state").value(dto.getState()) );
					
	}
	
	@Test
	@DisplayName("Deve retornar CEP inválido quando o cep não existir")
	public void cepNotFoundTest() throws Exception {

		String zipCode = "00000000";
		
		BDDMockito.given( addressService.findByZipCode(Mockito.anyString()) ).willReturn( Optional.empty() );
		
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders
				.get(CEP_API.concat("/" + zipCode))
				.accept(MediaType.APPLICATION_JSON);
		
		mvc
		.perform(request)
		.andExpect(status().isNotFound());					
		
	}
		
	private AddressDTO createNewAddress() {
		return AddressDTO.builder()
							.zipCode("17240000")
							.street("Rua teste")
							.neighborhood("Bairro teste")
							.city("Cidade teste")
							.state("SP")
							.build();
	}

}
