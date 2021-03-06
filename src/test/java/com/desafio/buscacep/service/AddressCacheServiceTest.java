package com.desafio.buscacep.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.desafio.buscacep.api.model.entity.Address;
import com.desafio.buscacep.exception.BusinessException;
import com.desafio.buscacep.model.repository.AddressRepository;
import com.desafio.buscacep.service.impl.AddresCacheServiceImpl;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class AddressCacheServiceTest {

	AddressCacheService service;
	
	@MockBean
	AddressRepository repository;
	
	@BeforeEach
	public void setUp() {
		this.service = new AddresCacheServiceImpl( repository );
	}
	
	@Test
	@DisplayName("Deve salvar um endereço")
	public void saveCepTest() {
		// cenario
		Address cep = createValidAddress();
		Mockito.when( repository.existsByZipCode(Mockito.anyString()) ).thenReturn(false);		
		Mockito.when( repository.save(cep) )
				.thenReturn(
					Address.builder()
					.id((long) 1)
					.zipCode("17240000")
					.street("Rua teste")
					.neighborhood("Bairro teste")
					.city("Cidade teste")
					.state("SP")
					.build()
				);
		
		// execução
		Address savedAddress = service.save(cep);
		
		// verificação
		assertThat(savedAddress.getId()).isNotNull();
		assertThat(savedAddress.getZipCode()).isEqualTo("17240000");
		assertThat(savedAddress.getStreet()).isEqualTo("Rua teste");
		assertThat(savedAddress.getNeighborhood()).isEqualTo("Bairro teste");
		assertThat(savedAddress.getCity()).isEqualTo("Cidade teste");
		assertThat(savedAddress.getState()).isEqualTo("SP");
	}
	
	@Test
	@DisplayName("Deve lançar erro de negocio ao tentar salvar um cep duplicado")
	public void shouldNotSaveADuplicatedCep() {
		// cenario
		Address cep = createValidAddress();
		Mockito.when( repository.existsByZipCode(Mockito.anyString()) ).thenReturn(true);
		
		// execucao
	  	Throwable exception = Assertions.catchThrowable( () -> service.save(cep) );
		assertThat(exception)
				.isInstanceOf(BusinessException.class)
				.hasMessage("Cep já cadastrado");
		
		Mockito.verify(repository, Mockito.never()).save(cep);
	}
	
	@Test
	@DisplayName("Deve obter um endereco pelo cep")
	public void getByZipCodeTest( ) {
		String zipCode = "14214165";
		
		Address cep = createValidAddress();
		cep.setZipCode(zipCode);
		
		Mockito.when(repository.findByZipCode(zipCode)).thenReturn(Optional.of(cep));
		
		// execucao
		Optional<Address> foundCep = service.getByZipCode(zipCode);
		
		// verificacoes
		assertThat( foundCep.isPresent() ).isTrue();
		assertThat( foundCep.get().getZipCode() ).isEqualTo(zipCode);
		assertThat( foundCep.get().getCity() ).isEqualTo(cep.getCity());
		assertThat( foundCep.get().getNeighborhood() ).isEqualTo(cep.getNeighborhood());
		assertThat( foundCep.get().getState() ).isEqualTo(cep.getState());
		assertThat( foundCep.get().getStreet() ).isEqualTo(cep.getStreet());
		assertThat( foundCep.get().getZipCode() ).isEqualTo(cep.getZipCode());
	}
	
	@Test
	@DisplayName("Deve retornar vazio ao buscar um endereco por um cep que não existe na base")
	public void zipCodeNotFoundByZipCodeTest( ) {
		String zipCode = "14214165";
		
		Mockito.when( repository.findByZipCode(zipCode) ).thenReturn(Optional.empty());
		
		// execucao
		Optional<Address> cep = service.getByZipCode(zipCode);
		
		// verificacoes
		assertThat( cep.isPresent() ).isFalse();
	}	
	
	private Address createValidAddress() {
		return Address.builder()
				.zipCode("17240000")
				.street("Rua teste")
				.neighborhood("Bairro teste")
				.city("Cidade teste")
				.state("SP")
				.build();
	}
	
}
