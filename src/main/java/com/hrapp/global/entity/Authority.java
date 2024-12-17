package com.hrapp.global.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.io.Serializable;

/**
 * An authority (a security role) used by Spring Security.
 */

@Entity
@Table(name = "sec_authority")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Authority implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotNull
	@Size(max = 50)
	@Id
	@Column(length = 50)
	private String name;

}
