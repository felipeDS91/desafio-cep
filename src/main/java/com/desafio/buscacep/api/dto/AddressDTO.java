package com.desafio.buscacep.api.dto;

import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressDTO {
		
//	private Long id;
	
	@NotEmpty
	private String zipCode;
	
	@NotEmpty
	private String street;
	
	@NotEmpty
	private String neighborhood;
	
	@NotEmpty
	private String city;
	
	@NotEmpty
	private String state;
		
}
