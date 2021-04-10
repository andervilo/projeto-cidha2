package br.com.cidha.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.cidha.web.rest.TestUtil;

public class TipoRecursoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TipoRecurso.class);
        TipoRecurso tipoRecurso1 = new TipoRecurso();
        tipoRecurso1.setId(1L);
        TipoRecurso tipoRecurso2 = new TipoRecurso();
        tipoRecurso2.setId(tipoRecurso1.getId());
        assertThat(tipoRecurso1).isEqualTo(tipoRecurso2);
        tipoRecurso2.setId(2L);
        assertThat(tipoRecurso1).isNotEqualTo(tipoRecurso2);
        tipoRecurso1.setId(null);
        assertThat(tipoRecurso1).isNotEqualTo(tipoRecurso2);
    }
}
