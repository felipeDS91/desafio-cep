package com.desafio.buscacep.model.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.desafio.buscacep.api.model.entity.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {

	boolean existsByZipCode(String zipCode);

	Optional<Address> findByZipCode(String zipCode);

}
