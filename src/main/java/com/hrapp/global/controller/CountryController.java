package com.hrapp.global.controller;

import com.hrapp.global.controller.errors.BadRequestAlertException;
import com.hrapp.global.dto.CountryDto;
import com.hrapp.global.entity.Country;
import com.hrapp.global.mapper.CountryMapper;
import com.hrapp.global.repository.CountryRepo;
import com.hrapp.global.service.CountryService;
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
 * REST controller for managing {@link Country}.
 */

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Log4j2
public class CountryController {

    private static final String ENTITY_NAME = "country";
    private final CountryService countryService;
    private final CountryMapper countryMapper;
    private final CountryRepo countryRepo;
    @Value("${properties.clientApp.name}")
    private String applicationName;


    /**
     * {@code POST  /countries} : Create a new country.
     *
     * @param dto the country to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new country, or with status {@code 400 (Bad Request)} if the country has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */

    @PostMapping("/countries")
    public ResponseEntity<CountryDto> createCountry(@Valid @RequestBody CountryDto dto) throws URISyntaxException {
        log.debug("REST request to save Country : {}", dto);

        if (dto.getId() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A new Country cannot already have an ID");
        }

        if (dto.getCountryName() == null || dto.getCountryName().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Country Name is required");
        }
        if (dto.getRegion() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Region is required");
        }

        Country country = countryMapper.unMap(dto);
        Country entity = countryService.save(country);
        CountryDto returnDto = countryMapper.map(entity);

        return ResponseEntity.created(new URI("/api/countries/" + returnDto.getId())).headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, returnDto.getId().toString())).body(returnDto);
    }


    /**
     * {@code PUT  /countries/:id} : Updates an existing country.
     *
     * @param id  the id of the country to save.
     * @param dto the country to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated country,
     * or with status {@code 400 (Bad Request)} if the country is not valid,
     * or with status {@code 500 (Internal Server Error)} if the country couldn't be updated.
     */

    @PutMapping("/countries/{id}")
    public ResponseEntity<CountryDto> updateCountry(@PathVariable(value = "id", required = false) final Long id, @Valid @RequestBody CountryDto dto) {
        log.debug("REST request to update Country : {}, {}", id, dto);


        if (dto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dto.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!countryRepo.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }


        Country currentCountry = countryService.getById(dto.getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity not found"));


        Country country = countryMapper.unMap(dto, currentCountry);

        Optional<Country> entity = countryService.update(country);
        CountryDto returnDto = countryMapper.map(entity.get());

        return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, country.getId().toString())).body(returnDto);
    }


    /**
     * {@code GET  /countries} : get all the countries.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of countries in body.
     */

    @GetMapping("/countries")
    public List<Country> getAllCountries() {
        log.debug("REST request to get all Countries");
        return countryService.findAll();
    }

    /**
     * {@code GET  /countries/:id} : get the "id" country.
     *
     * @param id the id of the country to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the country, or with status {@code 404 (Not Found)}.
     */

    @GetMapping("/countries/{id}")
    public ResponseEntity<CountryDto> getEmployee(@PathVariable Long id) {
        log.debug("REST request to get Country : {}", id);

        Country entity = countryService.findById(id);
        if (entity != null) {
            CountryDto dto = countryMapper.map(entity);
            return ResponseEntity.ok(dto);
        }

        return ResponseEntity.noContent().build();
    }

    /**
     * {@code DELETE  /countries/:id} : delete the "id" country.
     *
     * @param id the id of the country to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */

    @DeleteMapping("/countries/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        log.debug("REST request to delete Country : {}", id);
        if (!countryService.getById(id).isPresent()) {
            return ResponseEntity.noContent().build();
        } else {

            countryService.delete(id);
            return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
        }

    }

}
