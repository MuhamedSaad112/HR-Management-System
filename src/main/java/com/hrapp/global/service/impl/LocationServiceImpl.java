package com.hrapp.global.service.impl;

import com.hrapp.global.entity.Location;
import com.hrapp.global.repository.LocationRepo;
import com.hrapp.global.service.LocationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Location}.
 */

@Service
@RequiredArgsConstructor
@Log4j2
@Transactional
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
    public Optional<Location> update(Location location) {
        log.debug("Request to update location : {}", location); // Logging the update request
        return locationRepo.findById(location.getId()).map(existingLocation -> {

            if (location.getStreetAddress() != null) {
                existingLocation.setStreetAddress(location.getStreetAddress());
            }
            if (location.getPostalCode() != null) {
                existingLocation.setPostalCode(location.getPostalCode());
            }
            if (location.getCity() != null) {
                existingLocation.setCity(location.getCity());
            }
            if (location.getCountry() != null) {
                existingLocation.setCountry(location.getCountry());
            }
            if (location.getStateProvince() != null) {
                existingLocation.setStateProvince(location.getStateProvince());
            }
            return existingLocation;


        }).map(locationRepo::save);// Saving the updated location entity
    }

    /**
     * Retrieves all locations with pagination and sorting.
     *
     * @return A paginated list of locations
     */
    @Override
    @Transactional(readOnly = true)
    public List<Location> findAll() {
        log.debug("Request to get list of locations"); // Logging the request to fetch all locations
        return locationRepo.findAll(); // Returning the paginated list of locations
    }

    /**
     * Retrieves a location by its ID.
     *
     * @param id The ID of the location
     * @return The location with the given ID, or null if not found
     */
    @Override
    @Transactional(readOnly = true)
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
    @Transactional(readOnly = true)
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
