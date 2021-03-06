package com.desafio.buscacep.model.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.desafio.buscacep.api.model.entity.Address;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
public class AddressRepositoryTest {
	
	@Autowired
	TestEntityManager entityManager;
	
	@Autowired
	AddressRepository repository;
	
	@Test
	@DisplayName("Deve retornar verdadeiro quando existir um endereço na base com o cep informado")
	public void returnTrueWhenZipCodeExists() {
		// cenario
		String zipCode = "17214165";
		Address cep = createNewAddress(zipCode);
		entityManager.persist(cep );
		
		// execucao
		boolean exists = repository.existsByZipCode(zipCode);
		
		// verificacao
		assertThat(exists).isTrue();
	}

	private Address createNewAddress(String zipCode) {
		return Address.builder()
				.zipCode(zipCode)
				.street("Rua teste")
				.neighborhood("Bairro teste")
				.city("Cidade teste")
				.state("SP")
				.build();
	}
	
	@Test
	@DisplayName("Deve retornar falso quando existir um endereço na base com o cep informado")
	public void returnFalseWhenZipCodeDoesntExists() {
		// cenario
		String zipCode = "17214165";
		
		// execucao
		boolean exists = repository.existsByZipCode(zipCode);
		
		// verificacao
		assertThat(exists).isFalse();
	}	
	
	@Test
	@DisplayName("Deve obter um endereço por cep")
	public void findByCepTest() {
		// cenario
		Address cep = createNewAddress("17214165");
		entityManager.persist(cep);
		
		// execucao
		Optional<Address> foundCep = repository.findByZipCode(cep.getZipCode());
		
		// verificacao
		assertThat(foundCep.isPresent()).isTrue();
	}
}
