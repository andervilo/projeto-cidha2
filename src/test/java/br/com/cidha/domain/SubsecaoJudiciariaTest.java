package br.com.cidha.domain;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.cidha.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SubsecaoJudiciariaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SubsecaoJudiciaria.class);
        SubsecaoJudiciaria subsecaoJudiciaria1 = new SubsecaoJudiciaria();
        subsecaoJudiciaria1.setId(1L);
        SubsecaoJudiciaria subsecaoJudiciaria2 = new SubsecaoJudiciaria();
        subsecaoJudiciaria2.setId(subsecaoJudiciaria1.getId());
        assertThat(subsecaoJudiciaria1).isEqualTo(subsecaoJudiciaria2);
        subsecaoJudiciaria2.setId(2L);
        assertThat(subsecaoJudiciaria1).isNotEqualTo(subsecaoJudiciaria2);
        subsecaoJudiciaria1.setId(null);
        assertThat(subsecaoJudiciaria1).isNotEqualTo(subsecaoJudiciaria2);
    }
}
