package br.com.cidha.domain;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.cidha.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ConflitoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Conflito.class);
        Conflito conflito1 = new Conflito();
        conflito1.setId(1L);
        Conflito conflito2 = new Conflito();
        conflito2.setId(conflito1.getId());
        assertThat(conflito1).isEqualTo(conflito2);
        conflito2.setId(2L);
        assertThat(conflito1).isNotEqualTo(conflito2);
        conflito1.setId(null);
        assertThat(conflito1).isNotEqualTo(conflito2);
    }
}
