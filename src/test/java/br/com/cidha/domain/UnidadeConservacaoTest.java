package br.com.cidha.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.cidha.web.rest.TestUtil;

public class UnidadeConservacaoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UnidadeConservacao.class);
        UnidadeConservacao unidadeConservacao1 = new UnidadeConservacao();
        unidadeConservacao1.setId(1L);
        UnidadeConservacao unidadeConservacao2 = new UnidadeConservacao();
        unidadeConservacao2.setId(unidadeConservacao1.getId());
        assertThat(unidadeConservacao1).isEqualTo(unidadeConservacao2);
        unidadeConservacao2.setId(2L);
        assertThat(unidadeConservacao1).isNotEqualTo(unidadeConservacao2);
        unidadeConservacao1.setId(null);
        assertThat(unidadeConservacao1).isNotEqualTo(unidadeConservacao2);
    }
}
