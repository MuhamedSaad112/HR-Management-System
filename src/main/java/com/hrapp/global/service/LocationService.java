package com.hrapp.global.service;

import java.util.Optional;

import com.hrapp.global.entity.Location;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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
	Location update(Location location);

	/**
	 * Retrieve all location entities with pagination support.
	 *
	 * @param pageable pagination information.
	 * @return a paginated list of {@link Location} entities.
	 */
	Page<Location> findAll(Pageable pageable);

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
