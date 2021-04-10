package br.com.cidha.domain;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.cidha.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RelatorTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Relator.class);
        Relator relator1 = new Relator();
        relator1.setId(1L);
        Relator relator2 = new Relator();
        relator2.setId(relator1.getId());
        assertThat(relator1).isEqualTo(relator2);
        relator2.setId(2L);
        assertThat(relator1).isNotEqualTo(relator2);
        relator1.setId(null);
        assertThat(relator1).isNotEqualTo(relator2);
    }
}
