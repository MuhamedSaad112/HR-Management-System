package com.hrapp.global.repository;

import com.hrapp.global.entity.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data SQL repository for the Region entity.
 */

@SuppressWarnings("unused")
@Repository
public interface RegionRepo extends JpaRepository<Region, Long> {

}
