package com.desafio.buscacep.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.desafio.buscacep.api.dto.AddressDTO;
import com.desafio.buscacep.service.impl.AddressServiceImpl;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class AddressServiceTest {

	AddressService service;
	
	@MockBean
	AddressSearchService searchService;
	
	@BeforeEach
	public void setUp() {
		this.service = new AddressServiceImpl( searchService );
	}

	
	@Test
	@DisplayName("Deve tentar localizar um cep através de um cep que não existe substituindo os digitos finais por 0")
	public void shouldFindAValidCep () {
		// cenario
		Optional<AddressDTO> address = Optional.of(AddressDTO.builder().zipCode("17207700").street("Rua Humberto Matiello").neighborhood("Jardim Carolina").city("Jaú").state("SP").build());					
		Optional<AddressDTO> emptyAdddress = Optional.ofNullable(null);
									
		Mockito.when( searchService.byZipCode("17207799") ).thenReturn(emptyAdddress);
		Mockito.when( searchService.byZipCode("17207790") ).thenReturn(emptyAdddress);
		Mockito.when( searchService.byZipCode("17207700") ).thenReturn(address);
			
		this.service = new AddressServiceImpl( searchService );
		
		// execução
		Optional<AddressDTO> foundAddress = service.findByZipCode("17207799");
				
		// verificação		
		assertThat( foundAddress.isPresent() ).isTrue();	
		assertThat( foundAddress.get().getZipCode() ).isEqualTo( address.get().getZipCode() );
		assertThat( foundAddress.get().getStreet() ).isEqualTo( address.get().getStreet() );
		assertThat( foundAddress.get().getNeighborhood() ).isEqualTo( address.get().getNeighborhood() );
		assertThat( foundAddress.get().getCity() ).isEqualTo( address.get().getCity() );
		assertThat( foundAddress.get().getState() ).isEqualTo( address.get().getState() );
		
		Mockito.verify(searchService, Mockito.times(3)).byZipCode(Mockito.anyString());
	}

	@Test
	@DisplayName("Não deve conseguir localizar um cep através de um cep que não existe substituindo os digitos finais por 0")
	public void shouldNotFindAValidCep () {
		// cenario						
		Optional<AddressDTO> emptyAdddress = Optional.ofNullable(null);
									
		Mockito.when( searchService.byZipCode("17207799") ).thenReturn(emptyAdddress);
		Mockito.when( searchService.byZipCode("17207790") ).thenReturn(emptyAdddress);
		Mockito.when( searchService.byZipCode("17207700") ).thenReturn(emptyAdddress);
		Mockito.when( searchService.byZipCode("17207000") ).thenReturn(emptyAdddress);
		Mockito.when( searchService.byZipCode("17200000") ).thenReturn(emptyAdddress);
		Mockito.when( searchService.byZipCode("17200000") ).thenReturn(emptyAdddress);
		Mockito.when( searchService.byZipCode("17000000") ).thenReturn(emptyAdddress);
		Mockito.when( searchService.byZipCode("10000000") ).thenReturn(emptyAdddress);
		Mockito.when( searchService.byZipCode("00000000") ).thenReturn(emptyAdddress);
		
			
		this.service = new AddressServiceImpl( searchService );
		
		// execução
		Optional<AddressDTO> foundAddress = service.findByZipCode("17207799");
				
		// verificação		
		assertThat( foundAddress.isPresent() ).isFalse();	
		Mockito.verify(searchService, Mockito.times(9)).byZipCode(Mockito.anyString());
	}
	
}
