package com.kreitek.jhipster.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.kreitek.jhipster.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class StyleTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Style.class);
        Style style1 = new Style();
        style1.setId(1L);
        Style style2 = new Style();
        style2.setId(style1.getId());
        assertThat(style1).isEqualTo(style2);
        style2.setId(2L);
        assertThat(style1).isNotEqualTo(style2);
        style1.setId(null);
        assertThat(style1).isNotEqualTo(style2);
    }
}
