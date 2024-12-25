package com.hrapp.global.service;

import com.hrapp.global.entity.Region;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Region} entities.
 */
public interface RegionService {

	/**
	 * Save a region entity to the database.
	 *
	 * @param region the entity to save.
	 * @return the persisted {@link Region} entity.
	 */
	Region save(Region region);

	/**
	 * Update an existing region entity.
	 *
	 * @param region the entity containing updated fields.
	 * @return the updated {@link Region} entity.
	 */
	Optional<Region> update(Region region);

	/**
	 * Retrieve all region entities with pagination support.
	 *
	 * @return a paginated list of {@link Region} entities.
	 */
	List<Region> findAll();

	/**
	 * Retrieve a region entity by its unique identifier.
	 *
	 * @param id the ID of the region to retrieve.
	 * @return the {@link Region} entity, or {@code null} if not found.
	 */
	Region findById(Long id);

	/**
	 * Retrieve a region entity by its unique identifier, possibly using caching.
	 *
	 * @param id the ID of the region to retrieve.
	 * @return an {@link Optional} containing the region entity if found, or empty otherwise.
	 */
	Optional<Region> getById(Long id);

	/**
	 * Delete a region entity by its unique identifier.
	 *
	 * @param id the ID of the region to delete.
	 */
	void delete(Long id);
}
