package com.hrapp.global.controller;

import com.hrapp.global.controller.errors.BadRequestAlertException;
import com.hrapp.global.dto.LocationDto;
import com.hrapp.global.entity.Location;
import com.hrapp.global.mapper.LocationMapper;
import com.hrapp.global.repository.LocationRepo;
import com.hrapp.global.service.LocationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import tech.jhipster.web.util.HeaderUtil;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * REST controller for managing {@link com.hrapp.global.entity.Location}.
 */

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Log4j2
public class LocationController {

	private final LocationService locationService;
	private final LocationMapper locationMapper;
	private static final String ENTITY_NAME = "location";
	private final LocationRepo locationRepo;
	@Value("${properties.clientApp.name}")
	private String applicationName;

	/**
	 * {@code POST  /locations} : Create a new location.
	 *
	 * @param dto the location to create.
	 * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new location, or with status {@code 400 (Bad Request)} if the location has already an ID.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PostMapping("/locations")
	public ResponseEntity<LocationDto> createLocation(@Valid @RequestBody LocationDto dto) throws URISyntaxException {
		log.debug("REST request to save Location : {}", dto);

		if (dto.getId() != null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A new Location cannot already have an ID");
		}

		if (dto.getStreetAddress() == null || dto.getStreetAddress().isEmpty()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Street Address is required");
		}
		if (dto.getPostalCode() == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Postal Code is required");
		}
		if (dto.getCity() == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "City is required");
		}
		if (dto.getStateProvince() == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "StateProvince is required");
		}
		if (dto.getCountry() == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Country is required");
		}

		Location Location = locationMapper.unMap(dto);
		Location entity = locationService.save(Location);
		LocationDto returnDto = locationMapper.map(entity);

		return ResponseEntity
				.created(new URI("/api/locations/" + returnDto.getId()))
				.headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, returnDto.getId().toString()))
				.body(returnDto);
	}

	/**
	 * {@code PUT  /locations/:id} : Updates an existing location.
	 *
	 * @param id  the id of the location to save.
	 * @param dto the location to update.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated location,
	 * or with status {@code 400 (Bad Request)} if the location is not valid,
	 * or with status {@code 500 (Internal Server Error)} if the location couldn't be updated.
	 */
	@PutMapping("/locations/{id}")
	public ResponseEntity<LocationDto> updateLocation(@PathVariable(value = "id", required = false) final Long id, @Valid @RequestBody LocationDto dto) {
		log.debug("REST request to partial update Location partially : {}", dto);


		if (dto.getId() == null) {
			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
		}
		if (!Objects.equals(id, dto.getId())) {
			throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
		}

		if (!locationRepo.existsById(id)) {
			throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
		}

		Location currentLocation = locationService.getById(dto.getId())
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity not found"));

		Location Location = locationMapper.unMap(dto, currentLocation);
		Optional<Location> entity = locationService.update(Location);
		LocationDto returnDto = locationMapper.map(entity.get());

		return ResponseEntity
				.ok()
				.headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, returnDto.getId().toString()))
				.body(returnDto);
	}

	/**
	 * {@code GET  /locations} : get all the locations.
	 *
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of locations in body.
	 */
	@GetMapping("/locations")
	public List<Location> getAllLocations() {
		log.debug("REST request to get all Locations");
		return locationService.findAll();
	}

	/**
	 * {@code GET  /locations/:id} : get the "id" location.
	 *
	 * @param id the id of the location to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the location, or with status {@code 404 (Not Found)}.
	 */
	@GetMapping("/locations/{id}")
	public ResponseEntity<LocationDto> getLocation(@PathVariable Long id) {
		log.debug("REST request to get Location : {}", id);

		Location entity = locationService.findById(id);
		if (entity != null) {
			LocationDto dto = locationMapper.map(entity);
			return ResponseEntity.ok(dto);
		}

		return ResponseEntity.noContent().build();
	}

	/**
	 * {@code DELETE  /locations/:id} : delete the "id" location.
	 *
	 * @param id the id of the location to delete.
	 * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
	 */
	@DeleteMapping("/locations/{id}")
	public ResponseEntity<Void> deleteLocation(@PathVariable Long id) {
		log.debug("REST request to delete Location : {}", id);
		if (!locationService.getById(id).isPresent()) {
			return ResponseEntity.noContent().build();
		} else {

			locationService.delete(id);
			return ResponseEntity
					.noContent()
					.headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
					.build();
		}

	}

}
