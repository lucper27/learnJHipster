package com.kreitek.jhipster.repository;

import com.kreitek.jhipster.domain.Artist;
import com.kreitek.jhipster.service.dto.ArtistSlimDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Artist entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ArtistRepository extends JpaRepository<Artist, Long>, JpaSpecificationExecutor<Artist> {
    @Query("SELECT a.id as id, a.name as name FROM Artist a")
    Page<ArtistSlimDTO> getAllArtistSlim(Specification<Artist> criteria, Pageable page);
}
