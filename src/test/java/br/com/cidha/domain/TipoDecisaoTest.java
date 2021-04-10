package br.com.cidha.domain;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.cidha.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TipoDecisaoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TipoDecisao.class);
        TipoDecisao tipoDecisao1 = new TipoDecisao();
        tipoDecisao1.setId(1L);
        TipoDecisao tipoDecisao2 = new TipoDecisao();
        tipoDecisao2.setId(tipoDecisao1.getId());
        assertThat(tipoDecisao1).isEqualTo(tipoDecisao2);
        tipoDecisao2.setId(2L);
        assertThat(tipoDecisao1).isNotEqualTo(tipoDecisao2);
        tipoDecisao1.setId(null);
        assertThat(tipoDecisao1).isNotEqualTo(tipoDecisao2);
    }
}
