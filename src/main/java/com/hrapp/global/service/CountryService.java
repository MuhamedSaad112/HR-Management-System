package com.hrapp.global.service;

import com.hrapp.global.entity.Country;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Country} entities.
 */
public interface CountryService {

	/**
	 * Save a country entity to the database.
	 *
	 * @param country the entity to save.
	 * @return the persisted {@link Country} entity.
	 */
	Country save(Country country);

	/**
	 * Partially update a country entity. This may include only specific fields.
	 *
	 * @param country the entity containing updated fields.
	 * @return the updated {@link Country} entity.
	 */
	Optional<Country> update(Country country);

	/**
	 * Retrieve all country entities with pagination support.
	 *
	 *
	 * @return a paginated list of {@link Country} entities.
	 */
	List<Country> findAll();

	/**
	 * Retrieve a country entity by its unique identifier.
	 *
	 * @param id the ID of the country to retrieve.
	 * @return the {@link Country} entity, or {@code null} if not found.
	 */
	Country findById(Long id);

	/**
	 * Retrieve a country entity by its unique identifier, possibly using caching.
	 *
	 * @param id the ID of the country to retrieve.
	 * @return an {@link Optional} containing the country entity if found, or empty otherwise.
	 */
	Optional<Country> getById(Long id);

	/**
	 * Delete a country entity by its unique identifier.
	 *
	 * @param id the ID of the country to delete.
	 */
	void delete(Long id);
}
