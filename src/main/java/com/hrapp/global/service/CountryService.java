package com.hrapp.global.service;

import java.util.Optional;

import com.hrapp.global.entity.Country;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface CountryService {

	// Save a country.

	Country save(Country country);

	// Partially updates a country.

	Country update(Country country);

	// Get all the countries.

	Page<Country> findAll(Pageable pageable);

	// Get the "id" country

	Country findById(Long id);

	// Get the "id" Country from cash
	public Optional<Country> getById(Long id);

	// Delete the "id" country.

	void delete(Long id);
}
