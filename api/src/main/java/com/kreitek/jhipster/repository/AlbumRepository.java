package com.kreitek.jhipster.repository;

import com.kreitek.jhipster.domain.Album;
import java.util.List;
import java.util.Optional;

import com.kreitek.jhipster.service.dto.AlbumSlimDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Album entity.
 */
@Repository
public interface AlbumRepository extends JpaRepository<Album, Long>, JpaSpecificationExecutor<Album> {
    default Optional<Album> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Album> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Album> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }
    @Query("SELECT " +
        "alb.id as id, " +
        "alb.title as title, " +
        "art.id as artistId, " +
        "art.name as artistName, " +
        "sty.id as styleId, " +
        "sty.name as styleName " +
        "FROM Album alb JOIN alb.artist art " +
        "JOIN alb.style sty")
    Page<AlbumSlimDTO> getAllAlbumsSlim(Specification<Album> criteria, Pageable page);
    @Query(
        value = "select distinct album from Album album left join fetch album.artist left join fetch album.style",
        countQuery = "select count(distinct album) from Album album"
    )
    Page<Album> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct album from Album album left join fetch album.artist left join fetch album.style")
    List<Album> findAllWithToOneRelationships();

    @Query("select album from Album album left join fetch album.artist left join fetch album.style where album.id =:id")
    Optional<Album> findOneWithToOneRelationships(@Param("id") Long id);
}
