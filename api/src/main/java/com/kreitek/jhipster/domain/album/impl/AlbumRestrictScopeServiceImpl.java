package com.kreitek.jhipster.domain.album.impl;

import com.kreitek.jhipster.domain.album.AlbumRestrictScopeService;
import com.kreitek.jhipster.security.AuthoritiesConstants;
import com.kreitek.jhipster.security.SecurityUtils;
import com.kreitek.jhipster.service.criteria.AlbumCriteria;
import org.springframework.stereotype.Service;
import tech.jhipster.service.filter.LongFilter;
@Service
public class AlbumRestrictScopeServiceImpl implements AlbumRestrictScopeService {

    public static final long POP_STYLE_ID = 1202L;
    @Override
    public AlbumCriteria restrict(AlbumCriteria criteria) {
        boolean currentUserisEditor = SecurityUtils.hasCurrentUserThisAuthority(AuthoritiesConstants.EDITOR);
        if (currentUserisEditor) {
            LongFilter filterPopStyle = new LongFilter();
            filterPopStyle.setEquals(POP_STYLE_ID);
            criteria.setStyleId(filterPopStyle);
        }
        return criteria;
    }
}
