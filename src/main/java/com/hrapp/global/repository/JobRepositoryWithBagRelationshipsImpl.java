package com.hrapp.global.repository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import com.hrapp.global.entity.Job;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;


import jakarta.persistence.EntityManager;


/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
@RequiredArgsConstructor
public class JobRepositoryWithBagRelationshipsImpl implements JobRepositoryWithBagRelationships {


	private final EntityManager entityManager;

	@Override
	public Optional<Job> fetchBagRelationships(Optional<Job> job) {
		return job.map(this::fetchTasks);
	}

	@Override
	public Page<Job> fetchBagRelationships(Page<Job> jobs) {
		return new PageImpl<>(fetchBagRelationships(jobs.getContent()), jobs.getPageable(), jobs.getTotalElements());
	}

	@Override
	public List<Job> fetchBagRelationships(List<Job> jobs) {
		return Optional.of(jobs).map(this::fetchTasks).get();
	}

	Job fetchTasks(Job result) {
		return entityManager
				.createQuery("select distinct job from Job job left join fetch job.tasks where job = :job", Job.class)
				.setParameter("job", result).getSingleResult();
	}

	List<Job> fetchTasks(List<Job> jobs) {
		List<Job> results = entityManager
				.createQuery("select job from Job job left join fetch job.tasks where job in :jobs", Job.class)
				.setParameter("jobs", jobs).getResultList();

		return new ArrayList<>(new HashSet<>(results));
	}

}
