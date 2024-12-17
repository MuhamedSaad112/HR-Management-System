package com.hrapp.global.repository;

import com.hrapp.global.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data SQL repository for the Location entity.
 */

@SuppressWarnings("unused")
@Repository
public interface LocationRepo extends JpaRepository<Location, Long> {

}
