package br.com.cidha.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.cidha.web.rest.TestUtil;

public class FundamentacaoLegalTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FundamentacaoLegal.class);
        FundamentacaoLegal fundamentacaoLegal1 = new FundamentacaoLegal();
        fundamentacaoLegal1.setId(1L);
        FundamentacaoLegal fundamentacaoLegal2 = new FundamentacaoLegal();
        fundamentacaoLegal2.setId(fundamentacaoLegal1.getId());
        assertThat(fundamentacaoLegal1).isEqualTo(fundamentacaoLegal2);
        fundamentacaoLegal2.setId(2L);
        assertThat(fundamentacaoLegal1).isNotEqualTo(fundamentacaoLegal2);
        fundamentacaoLegal1.setId(null);
        assertThat(fundamentacaoLegal1).isNotEqualTo(fundamentacaoLegal2);
    }
}
