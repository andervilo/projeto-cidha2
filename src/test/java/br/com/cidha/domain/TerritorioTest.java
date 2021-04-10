package br.com.cidha.domain;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.cidha.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TerritorioTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Territorio.class);
        Territorio territorio1 = new Territorio();
        territorio1.setId(1L);
        Territorio territorio2 = new Territorio();
        territorio2.setId(territorio1.getId());
        assertThat(territorio1).isEqualTo(territorio2);
        territorio2.setId(2L);
        assertThat(territorio1).isNotEqualTo(territorio2);
        territorio1.setId(null);
        assertThat(territorio1).isNotEqualTo(territorio2);
    }
}
