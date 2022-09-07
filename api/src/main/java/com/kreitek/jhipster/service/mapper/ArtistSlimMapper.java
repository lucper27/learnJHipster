package com.kreitek.jhipster.service.mapper;

import com.kreitek.jhipster.domain.Artist;
import com.kreitek.jhipster.service.dto.ArtistSlimDTO;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ArtistSlimMapper extends EntityMapper<ArtistSlimDTO, Artist>{

    @Override
    @Mapping(target = "image", ignore = true)
    @Mapping(target = "imageContentType", ignore = true)
    Artist toEntity(ArtistSlimDTO dto);

    @Override
    @Mapping(target = "image", ignore = true)
    @Mapping(target = "imageContentType", ignore = true)
    @Named("partialUpdate")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void partialUpdate(@MappingTarget Artist entity, ArtistSlimDTO dto);
}
