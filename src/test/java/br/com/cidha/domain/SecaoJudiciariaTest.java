package br.com.cidha.domain;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.cidha.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SecaoJudiciariaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SecaoJudiciaria.class);
        SecaoJudiciaria secaoJudiciaria1 = new SecaoJudiciaria();
        secaoJudiciaria1.setId(1L);
        SecaoJudiciaria secaoJudiciaria2 = new SecaoJudiciaria();
        secaoJudiciaria2.setId(secaoJudiciaria1.getId());
        assertThat(secaoJudiciaria1).isEqualTo(secaoJudiciaria2);
        secaoJudiciaria2.setId(2L);
        assertThat(secaoJudiciaria1).isNotEqualTo(secaoJudiciaria2);
        secaoJudiciaria1.setId(null);
        assertThat(secaoJudiciaria1).isNotEqualTo(secaoJudiciaria2);
    }
}
