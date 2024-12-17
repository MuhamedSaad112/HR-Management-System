package com.hrapp.global.service;

import java.util.Optional;

import com.hrapp.global.entity.Region;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;



public interface RegionService {

	// Save a Region.

	Region save(Region region);

	// updates a Region.

	Region update(Region region);

	// Get all the Region.

	Page<Region> findAll(Pageable pageable);

	// Get the "id" Region

	Region findById(Long id);

	// Get the "id" Location from cash
	public Optional<Region> getById(Long id);

	// Delete the "id" Region.

	void delete(Long id);
}