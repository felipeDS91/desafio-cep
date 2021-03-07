package com.desafio.buscacep.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.desafio.buscacep.api.dto.AddressDTO;
import com.desafio.buscacep.api.dto.AddressViaCEPDTO;


@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class AddressSearchAPIServiceTest {
	
	@MockBean
    AddressSearchAPIService service;	
	
	@Test
	@DisplayName("Deve buscar um endereço quando de um cep válido")
	public void shouldFindAddressWithValidZipCode() {
						
		// cenario
		AddressDTO dto = createNewAddress();
		
		Optional<AddressViaCEPDTO> address = Optional.of(AddressViaCEPDTO.builder()					
				.cep(dto.getZipCode())
				.logradouro(dto.getStreet())
				.bairro(dto.getNeighborhood())
				.localidade(dto.getCity())
				.uf(dto.getState())
				.build());	
		
		
		Mockito.when( service.byZipCode( dto.getZipCode()) ).thenReturn(address);
		
		// execucao
		Optional<AddressViaCEPDTO> searchedZipCode = service.byZipCode(dto.getZipCode());
		
		// verificacoes
		assertThat( searchedZipCode.isPresent() ).isTrue();
		assertThat( searchedZipCode.get().getCep() ).isEqualTo(dto.getZipCode());
		assertThat( searchedZipCode.get().getLocalidade() ).isEqualTo(dto.getCity());
		assertThat( searchedZipCode.get().getBairro() ).isEqualTo(dto.getNeighborhood());
		assertThat( searchedZipCode.get().getUf() ).isEqualTo(dto.getState());
		assertThat( searchedZipCode.get().getLogradouro() ).isEqualTo(dto.getStreet());
		assertThat( searchedZipCode.get().getCep() ).isEqualTo(dto.getZipCode());
		
	}
	
	@Test
	@DisplayName("Deve retornar um erro quando cep inválido for pesquisado")
	public void shouldNoFindAddressWithInvalidZipCode() {
						
		// cenario
		String invalidZipCode = "17999999";
						
		Optional<AddressViaCEPDTO> address = Optional.of(AddressViaCEPDTO.builder().erro(true).build());	
				
		
		Mockito.when( service.byZipCode( invalidZipCode )).thenReturn(address);
		
		// execucao
		Optional<AddressViaCEPDTO> searchedZipCode = service.byZipCode( invalidZipCode );
		
		// verificacoes
		assertThat( searchedZipCode.isPresent() ).isTrue();
		assertThat( searchedZipCode.get().getErro() ).isEqualTo( true );
		
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
