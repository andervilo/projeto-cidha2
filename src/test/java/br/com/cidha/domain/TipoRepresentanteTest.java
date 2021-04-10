package br.com.cidha.domain;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.cidha.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TipoRepresentanteTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TipoRepresentante.class);
        TipoRepresentante tipoRepresentante1 = new TipoRepresentante();
        tipoRepresentante1.setId(1L);
        TipoRepresentante tipoRepresentante2 = new TipoRepresentante();
        tipoRepresentante2.setId(tipoRepresentante1.getId());
        assertThat(tipoRepresentante1).isEqualTo(tipoRepresentante2);
        tipoRepresentante2.setId(2L);
        assertThat(tipoRepresentante1).isNotEqualTo(tipoRepresentante2);
        tipoRepresentante1.setId(null);
        assertThat(tipoRepresentante1).isNotEqualTo(tipoRepresentante2);
    }
}
