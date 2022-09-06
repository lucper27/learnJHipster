package com.kreitek.jhipster.repository;

import com.kreitek.jhipster.domain.Style;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Style entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StyleRepository extends JpaRepository<Style, Long> {}
