package com.kreitek.jhipster.domain.song.impl;

import com.kreitek.jhipster.domain.song.SongCheckerFilterByAlbumAppliedGuard;
import com.kreitek.jhipster.service.criteria.SongCriteria;
import com.kreitek.jhipster.web.rest.errors.BadRequestAlertException;
import org.springframework.stereotype.Service;

@Service
public class SongCheckerFilterByAlbumAppliedGuardImpl implements SongCheckerFilterByAlbumAppliedGuard {
    @Override
    public void check(SongCriteria criteria) {
        if (criteria.getAlbumId() == null) {
            throw new BadRequestAlertException("Not Album defined for this request", null, "Not album defined for this request");
        }
    }
}
