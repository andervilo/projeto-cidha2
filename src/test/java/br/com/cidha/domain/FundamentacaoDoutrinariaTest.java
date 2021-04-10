package br.com.cidha.domain;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.cidha.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FundamentacaoDoutrinariaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FundamentacaoDoutrinaria.class);
        FundamentacaoDoutrinaria fundamentacaoDoutrinaria1 = new FundamentacaoDoutrinaria();
        fundamentacaoDoutrinaria1.setId(1L);
        FundamentacaoDoutrinaria fundamentacaoDoutrinaria2 = new FundamentacaoDoutrinaria();
        fundamentacaoDoutrinaria2.setId(fundamentacaoDoutrinaria1.getId());
        assertThat(fundamentacaoDoutrinaria1).isEqualTo(fundamentacaoDoutrinaria2);
        fundamentacaoDoutrinaria2.setId(2L);
        assertThat(fundamentacaoDoutrinaria1).isNotEqualTo(fundamentacaoDoutrinaria2);
        fundamentacaoDoutrinaria1.setId(null);
        assertThat(fundamentacaoDoutrinaria1).isNotEqualTo(fundamentacaoDoutrinaria2);
    }
}
