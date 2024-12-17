package com.hrapp.global.service.impl;

import java.util.Optional;

import com.hrapp.global.entity.Location;
import com.hrapp.global.repository.LocationRepo;
import com.hrapp.global.service.LocationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
public class LocationServiceImpl implements LocationService {

	private final LocationRepo locationRepo; // Injecting the LocationRepo for database operations

	/**
	 * Saves the given location to the database.
	 *
	 * @param location The location entity to save
	 * @return The saved location entity
	 */
	@Override
	public Location save(Location location) {
		log.debug("Request to save location : {}", location); // Logging the save request
		return locationRepo.save(location); // Saving the location entity
	}

	/**
	 * Updates the given location in the database.
	 *
	 * @param location The location entity to update
	 * @return The updated location entity
	 */
	@Override
	public Location update(Location location) {
		log.debug("Request to update location : {}", location); // Logging the update request
		return locationRepo.save(location); // Saving the updated location entity
	}

	/**
	 * Retrieves all locations with pagination and sorting.
	 *
	 * @param pageable Pagination and sorting information (page, size, sort)
	 * @return A paginated list of locations
	 */
	@Override
	public Page<Location> findAll(Pageable pageable) {
		log.debug("Request to get list of locations"); // Logging the request to fetch all locations
		return locationRepo.findAll(pageable); // Returning the paginated list of locations
	}

	/**
	 * Retrieves a location by its ID.
	 *
	 * @param id The ID of the location
	 * @return The location with the given ID, or null if not found
	 */
	@Override
	public Location findById(Long id) {
		log.debug("Request to get location : {}", id); // Logging the request for a location with the given ID
		return locationRepo.findById(id).orElse(null); // Returning the location with the given ID, or null if not found
	}

	/**
	 * Retrieves a reference to a location by its ID using a cache for better performance.
	 *
	 * @param id The ID of the location
	 * @return An Optional containing the location reference, or empty if not found
	 */
	@Override
	public Optional<Location> getById(Long id) {
		log.debug("Request to Get Location from cache : {}", id); // Logging the cache retrieval request
		return Optional.of(locationRepo.getReferenceById(id)); // Returning the location reference
	}

	/**
	 * Deletes a location by its ID.
	 *
	 * @param id The ID of the location to delete
	 */
	@Override
	public void delete(Long id) {
		log.debug("Request to delete location : {}", id); // Logging the delete request
		locationRepo.deleteById(id); // Deleting the location by its ID
	}
}
