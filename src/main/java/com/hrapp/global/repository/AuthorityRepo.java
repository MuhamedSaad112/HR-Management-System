package com.hrapp.global.repository;

import com.hrapp.global.entity.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the {@link Authority} entity.
 */
@Repository
public interface AuthorityRepo extends JpaRepository<Authority, String> {
}
