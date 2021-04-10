package br.com.cidha.domain;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.cidha.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OpcaoRecursoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OpcaoRecurso.class);
        OpcaoRecurso opcaoRecurso1 = new OpcaoRecurso();
        opcaoRecurso1.setId(1L);
        OpcaoRecurso opcaoRecurso2 = new OpcaoRecurso();
        opcaoRecurso2.setId(opcaoRecurso1.getId());
        assertThat(opcaoRecurso1).isEqualTo(opcaoRecurso2);
        opcaoRecurso2.setId(2L);
        assertThat(opcaoRecurso1).isNotEqualTo(opcaoRecurso2);
        opcaoRecurso1.setId(null);
        assertThat(opcaoRecurso1).isNotEqualTo(opcaoRecurso2);
    }
}
