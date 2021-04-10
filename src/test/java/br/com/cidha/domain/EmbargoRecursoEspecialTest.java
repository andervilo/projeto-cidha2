package br.com.cidha.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.cidha.web.rest.TestUtil;

public class EmbargoRecursoEspecialTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmbargoRecursoEspecial.class);
        EmbargoRecursoEspecial embargoRecursoEspecial1 = new EmbargoRecursoEspecial();
        embargoRecursoEspecial1.setId(1L);
        EmbargoRecursoEspecial embargoRecursoEspecial2 = new EmbargoRecursoEspecial();
        embargoRecursoEspecial2.setId(embargoRecursoEspecial1.getId());
        assertThat(embargoRecursoEspecial1).isEqualTo(embargoRecursoEspecial2);
        embargoRecursoEspecial2.setId(2L);
        assertThat(embargoRecursoEspecial1).isNotEqualTo(embargoRecursoEspecial2);
        embargoRecursoEspecial1.setId(null);
        assertThat(embargoRecursoEspecial1).isNotEqualTo(embargoRecursoEspecial2);
    }
}
