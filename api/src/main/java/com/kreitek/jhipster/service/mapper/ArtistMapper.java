package com.kreitek.jhipster.service.mapper;

import com.kreitek.jhipster.domain.Artist;
import com.kreitek.jhipster.service.dto.ArtistDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Artist} and its DTO {@link ArtistDTO}.
 */
@Mapper(componentModel = "spring")
public interface ArtistMapper extends EntityMapper<ArtistDTO, Artist> {

    ArtistDTO toDto(Artist artist);

    Artist toEntity(ArtistDTO artistDTO);
}
