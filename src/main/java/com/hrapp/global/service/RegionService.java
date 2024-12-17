package com.hrapp.global.service;

import java.util.Optional;

import com.hrapp.global.entity.Region;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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
	Region update(Region region);

	/**
	 * Retrieve all region entities with pagination support.
	 *
	 * @param pageable pagination information.
	 * @return a paginated list of {@link Region} entities.
	 */
	Page<Region> findAll(Pageable pageable);

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
