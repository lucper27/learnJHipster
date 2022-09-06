package com.kreitek.jhipster.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.kreitek.jhipster.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class StyleDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(StyleDTO.class);
        StyleDTO styleDTO1 = new StyleDTO();
        styleDTO1.setId(1L);
        StyleDTO styleDTO2 = new StyleDTO();
        assertThat(styleDTO1).isNotEqualTo(styleDTO2);
        styleDTO2.setId(styleDTO1.getId());
        assertThat(styleDTO1).isEqualTo(styleDTO2);
        styleDTO2.setId(2L);
        assertThat(styleDTO1).isNotEqualTo(styleDTO2);
        styleDTO1.setId(null);
        assertThat(styleDTO1).isNotEqualTo(styleDTO2);
    }
}
