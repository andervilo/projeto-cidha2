package br.com.cidha.domain;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.cidha.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ComarcaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Comarca.class);
        Comarca comarca1 = new Comarca();
        comarca1.setId(1L);
        Comarca comarca2 = new Comarca();
        comarca2.setId(comarca1.getId());
        assertThat(comarca1).isEqualTo(comarca2);
        comarca2.setId(2L);
        assertThat(comarca1).isNotEqualTo(comarca2);
        comarca1.setId(null);
        assertThat(comarca1).isNotEqualTo(comarca2);
    }
}
