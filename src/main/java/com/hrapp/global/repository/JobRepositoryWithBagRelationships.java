package com.hrapp.global.repository;

import com.hrapp.global.entity.Job;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;



public interface JobRepositoryWithBagRelationships {

	Optional<Job> fetchBagRelationships(Optional<Job> job);

	List<Job> fetchBagRelationships(List<Job> jobs);

	Page<Job> fetchBagRelationships(Page<Job> jobs);

}
