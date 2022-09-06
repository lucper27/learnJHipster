package com.kreitek.jhipster.domain.album;

import com.kreitek.jhipster.service.criteria.AlbumCriteria;
import org.springframework.stereotype.Service;

@Service
public interface AlbumRestrictScopeService {
    AlbumCriteria restrict(AlbumCriteria criteria);
}
