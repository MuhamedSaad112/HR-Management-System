package com.hrapp.global.service.impl;

import java.util.Optional;

import com.hrapp.global.entity.Country;
import com.hrapp.global.repository.CountryRepo;
import com.hrapp.global.service.CountryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
public class CountryServiceImpl implements CountryService {

	private final CountryRepo countryRepo; // Injecting the CountryRepo for database operations

	/**
	 * Saves the given country to the database.
	 *
	 * @param country The country entity to save
	 * @return The saved country entity
	 */
	@Override
	public Country save(Country country) {
		log.debug("Request to save Country : {}", country); // Logging the save request
		return countryRepo.save(country); // Saving the country entity
	}

	/**
	 * Updates the given country in the database.
	 *
	 * @param country The country entity to update
	 * @return The updated country entity
	 */
	@Override
	public Country update(Country country) {
		log.debug("Request to  update Country : {}", country); // Logging the update request
		return countryRepo.save(country); // Saving the updated country entity
	}

	/**
	 * Retrieves all countries with pagination support.
	 *
	 * @param pageable Pagination information (page, size, sort)
	 * @return A paginated list of countries
	 */
	@Override
	public Page<Country> findAll(Pageable pageable) {
		log.debug("Request to get all Countries"); // Logging the find all request
		return countryRepo.findAll(pageable); // Returning the paginated list of countries
	}

	/**
	 * Retrieves a country by its ID.
	 *
	 * @param id The ID of the country
	 * @return The country with the given ID, or null if not found
	 */
	@Override
	public Country findById(Long id) {
		log.debug("Request to get Country : {}", id); // Logging the find by ID request
		return countryRepo.findById(id).orElse(null); // Returning the country or null if not found
	}

	/**
	 * Retrieves a country by its ID using a reference for better performance.
	 *
	 * @param id The ID of the country
	 * @return An Optional containing the country, or empty if not found
	 */
	@Override
	public Optional<Country> getById(Long id) {
		log.debug("Request to Get Country from cache : {}", id); // Logging the cache retrieval request
		return Optional.of(countryRepo.getReferenceById(id)); // Returning a reference of the country
	}

	/**
	 * Deletes a country by its ID.
	 *
	 * @param id The ID of the country to delete
	 */
	@Override
	public void delete(Long id) {
		log.debug("Request to delete Country : {}", id); // Logging the delete request
		countryRepo.deleteById(id); // Deleting the country by its ID
	}
}
