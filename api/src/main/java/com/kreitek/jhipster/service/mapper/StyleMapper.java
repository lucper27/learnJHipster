package com.kreitek.jhipster.service.mapper;

import com.kreitek.jhipster.domain.Style;
import com.kreitek.jhipster.service.dto.StyleDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Style} and its DTO {@link StyleDTO}.
 */
@Mapper(componentModel = "spring")
public interface StyleMapper extends EntityMapper<StyleDTO, Style> {}
