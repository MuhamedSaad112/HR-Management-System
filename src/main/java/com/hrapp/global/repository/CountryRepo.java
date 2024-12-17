package com.hrapp.global.repository;

import com.hrapp.global.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Country entity.
 */

@SuppressWarnings("unused")
@Repository
public interface CountryRepo extends JpaRepository<Country, Long> {

}
