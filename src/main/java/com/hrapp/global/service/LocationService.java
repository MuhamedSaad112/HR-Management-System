package com.hrapp.global.service;

import com.hrapp.global.entity.Location;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Location} entities.
 */
public interface LocationService {

	/**
	 * Save a location entity to the database.
	 *
	 * @param location the entity to save.
	 * @return the persisted {@link Location} entity.
	 */
	Location save(Location location);

	/**
	 * Update an existing location entity.
	 *
	 * @param location the entity containing updated fields.
	 * @return the updated {@link Location} entity.
	 */
	Optional<Location> update(Location location);

	/**
	 * Retrieve all location entities with pagination support.
	 *

	 * @return a paginated list of {@link Location} entities.
	 */
	List<Location> findAll();

	/**
	 * Retrieve a location entity by its unique identifier.
	 *
	 * @param id the ID of the location to retrieve.
	 * @return the {@link Location} entity, or {@code null} if not found.
	 */
	Location findById(Long id);

	/**
	 * Retrieve a location entity by its unique identifier, possibly using caching.
	 *
	 * @param id the ID of the location to retrieve.
	 * @return an {@link Optional} containing the location entity if found, or empty otherwise.
	 */
	Optional<Location> getById(Long id);

	/**
	 * Delete a location entity by its unique identifier.
	 *
	 * @param id the ID of the location to delete.
	 */
	void delete(Long id);
}
