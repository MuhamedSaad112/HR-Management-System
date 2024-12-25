package com.hrapp.global.service.impl;

import com.hrapp.global.entity.Region;
import com.hrapp.global.repository.RegionRepo;
import com.hrapp.global.service.RegionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Region}.
 */

@Service
@RequiredArgsConstructor
@Log4j2
@Transactional
public class RegionServiceImpl implements RegionService {

    private final RegionRepo regionRepo; // Injecting the RegionRepo for database operations

    /**
     * Saves the given region to the database.
     *
     * @param region The region entity to save
     * @return The saved region entity
     */
    @Override
    public Region save(Region region) {
        log.debug("Request to save Region : {}", region); // Logging the save request
        return regionRepo.save(region); // Saving the region entity
    }

    /**
     * Updates the given region in the database.
     *
     * @param region The region entity to update
     * @return The updated region entity
     */
    @Override
    public Optional<Region> update(Region region) {
        log.debug("Request to update Region : {}", region); // Logging the update request
        return regionRepo.findById(region.getId()).map(existingRegion -> {
            if (existingRegion.getRegionName() != null) {
                existingRegion.setRegionName(region.getRegionName());
            }
            return existingRegion;
        }).map(regionRepo::save);// Saving the updated region entity
    }

    /**
     * Retrieves all regions with pagination and sorting.
     *
     * @return A paginated list of regions
     */
    @Override
    @Transactional(readOnly = true)
    public List<Region> findAll() {
        log.debug("Request to get all Regions"); // Logging the request to fetch all regions
        return regionRepo.findAll(); // Returning the paginated list of regions
    }

    /**
     * Retrieves a region by its ID.
     *
     * @param id The ID of the region
     * @return The region with the given ID, or null if not found
     */
    @Override
    @Transactional(readOnly = true)
    public Region findById(Long id) {
        log.debug("Request to get Region : {}", id); // Logging the request for a region with the given ID
        return regionRepo.findById(id).orElse(null); // Returning the region with the given ID, or null if not found
    }

    /**
     * Retrieves a reference to a region by its ID using a cache for better performance.
     *
     * @param id The ID of the region
     * @return An Optional containing the region reference, or empty if not found
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Region> getById(Long id) {
        log.debug("Request to Get Region from cache : {}", id); // Logging the cache retrieval request
        return Optional.of(regionRepo.getReferenceById(id)); // Returning the region reference
    }

    /**
     * Deletes a region by its ID.
     *
     * @param id The ID of the region to delete
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Region : {}", id); // Logging the delete request
        regionRepo.deleteById(id); // Deleting the region by its ID
    }
}
