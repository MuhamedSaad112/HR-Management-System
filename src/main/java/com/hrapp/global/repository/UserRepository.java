package com.hrapp.global.repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import com.hrapp.global.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the {@link User} entity.
 */

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	String USERS_BY_LOGIN_CACHE = "usersByLogin";

	String USERS_BY_EMAIL_CACHE = "usersByEmail";

	Optional<User> findOneByActivationKey(String activationKey);

	List<User> findAllByActivatedIsFalseAndActivationKeyIsNotNullAndCreatedDateBefore(Instant dateTime);

	Optional<User> findOneByResetKey(String resetKey);

	@Query("SELECT u FROM User u WHERE upper(u.email) = upper(:email)")
	Optional<User> findOneByEmailIgnoreCase(String email);

	Optional<User> findOneByLogin(String login);

	@EntityGraph(attributePaths = "authorities")
	Optional<User> findOneWithAuthoritiesByLogin(String login);

	@EntityGraph(attributePaths = "authorities")
	Optional<User> findOneWithAuthoritiesByEmailIgnoreCase(String email);

	Page<User> findAllByIdNotNullAndActivatedIsTrue(Pageable pageable);
}
