package br.com.cidha.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.cidha.web.rest.TestUtil;

public class TipoEmpreendimentoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TipoEmpreendimento.class);
        TipoEmpreendimento tipoEmpreendimento1 = new TipoEmpreendimento();
        tipoEmpreendimento1.setId(1L);
        TipoEmpreendimento tipoEmpreendimento2 = new TipoEmpreendimento();
        tipoEmpreendimento2.setId(tipoEmpreendimento1.getId());
        assertThat(tipoEmpreendimento1).isEqualTo(tipoEmpreendimento2);
        tipoEmpreendimento2.setId(2L);
        assertThat(tipoEmpreendimento1).isNotEqualTo(tipoEmpreendimento2);
        tipoEmpreendimento1.setId(null);
        assertThat(tipoEmpreendimento1).isNotEqualTo(tipoEmpreendimento2);
    }
}
