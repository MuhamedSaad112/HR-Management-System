package com.hrapp.global.controller;

import com.hrapp.global.controller.errors.BadRequestAlertException;
import com.hrapp.global.dto.RegionDto;
import com.hrapp.global.entity.Region;
import com.hrapp.global.mapper.RegionMapper;
import com.hrapp.global.repository.RegionRepo;
import com.hrapp.global.service.RegionService;
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
 * REST controller for managing {@link com.hrapp.global.entity.Region}.
 */

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Log4j2
public class RegionController {

	private final RegionService regionService;
	private final RegionMapper regionMapper;
	private static final String ENTITY_NAME = "region";
	private final RegionRepo regionRepo;
	@Value("${properties.clientApp.name}")
	private String applicationName;

	/**
	 * {@code POST  /regions} : Create a new region.
	 *
	 * @param dto the region to create.
	 * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new region, or with status {@code 400 (Bad Request)} if the region has already an ID.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PostMapping("/regions")
	public ResponseEntity<RegionDto> createRegion(@Valid @RequestBody RegionDto dto) throws URISyntaxException {
		log.debug("REST request to save Region : {}", dto);

		if (dto.getId() != null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A new Region cannot already have an ID");
		}

		if (dto.getRegionName() == null || dto.getRegionName().isEmpty()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Region Name is required");
		}

		Region Region = regionMapper.unMap(dto);
		Region entity = regionService.save(Region);
		RegionDto returnDto = regionMapper.map(entity);

		return ResponseEntity
				.created(new URI("/api/regions/" + returnDto.getId()))
				.headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, returnDto.getId().toString()))
				.body(returnDto);
	}

	/**
	 * {@code PUT  /regions/:id} : Updates an existing region.
	 *
	 * @param id  the id of the region to save.
	 * @param dto the region to update.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated region,
	 * or with status {@code 400 (Bad Request)} if the region is not valid,
	 * or with status {@code 500 (Internal Server Error)} if the region couldn't be updated.
	 */
	@PutMapping("/regions/{id}")
	public ResponseEntity<RegionDto> updateRegion(@PathVariable(value = "id", required = false) final Long id, @Valid @RequestBody RegionDto dto) {
		log.debug("REST request to partial update Region partially : {}", dto);

		if (dto.getId() == null) {
			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
		}
		if (!Objects.equals(id, dto.getId())) {
			throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
		}

		if (!regionRepo.existsById(id)) {
			throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
		}

		Region currentRegion = regionService.getById(dto.getId())
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity not found"));

		Region Region = regionMapper.unMap(dto, currentRegion);
		Optional<Region> entity = regionService.update(Region);
		RegionDto returnDto = regionMapper.map(entity.get());

		return ResponseEntity
				.ok()
				.headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, returnDto.getId().toString()))
				.body(returnDto);
	}

	/**
	 * {@code GET  /regions} : get all the regions.
	 *
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of regions in body.
	 */
	@GetMapping("/regions")
	public List<Region> getAllRegions() {
		log.debug("REST request to get all Regions");
		return regionService.findAll();
	}

	/**
	 * {@code GET  /regions/:id} : get the "id" region.
	 *
	 * @param id the id of the region to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the region, or with status {@code 404 (Not Found)}.
	 */
	@GetMapping("/regions/{id}")
	public ResponseEntity<RegionDto> getRegion(@PathVariable Long id) {
		log.debug("REST request to get Region : {}", id);

		Region entity = regionService.findById(id);
		if (entity != null) {
			RegionDto dto = regionMapper.map(entity);
			return ResponseEntity.ok(dto);
		}

		return ResponseEntity.noContent().build();
	}

	/**
	 * {@code DELETE  /regions/:id} : delete the "id" region.
	 *
	 * @param id the id of the region to delete.
	 * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
	 */
	@DeleteMapping("/regions/{id}")
	public ResponseEntity<Void> deleteRegion(@PathVariable Long id) {
		log.debug("REST request to delete Region : {}", id);
		if (!regionService.getById(id).isPresent()) {
			return ResponseEntity.noContent().build();
		} else {

			regionService.delete(id);
			return ResponseEntity
					.noContent()
					.headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
					.build();
		}

	}

}
