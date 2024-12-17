package com.hrapp.global.service;

import java.util.Optional;

import com.hrapp.global.entity.Location;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface LocationService {

	// Save a Location.

	Location save(Location location);

	// updates a Location.

	Location update(Location location);

	// Get all the Location.

	Page<Location> findAll(Pageable pageable);

	// Get the "id" Location

	Location findById(Long id);

	// Get the "id" Location from cash
	public Optional<Location> getById(Long id);

	// Delete the "id" Location.

	void delete(Long id);
}