package br.com.cidha.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.cidha.web.rest.TestUtil;

public class ProblemaJuridicoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProblemaJuridico.class);
        ProblemaJuridico problemaJuridico1 = new ProblemaJuridico();
        problemaJuridico1.setId(1L);
        ProblemaJuridico problemaJuridico2 = new ProblemaJuridico();
        problemaJuridico2.setId(problemaJuridico1.getId());
        assertThat(problemaJuridico1).isEqualTo(problemaJuridico2);
        problemaJuridico2.setId(2L);
        assertThat(problemaJuridico1).isNotEqualTo(problemaJuridico2);
        problemaJuridico1.setId(null);
        assertThat(problemaJuridico1).isNotEqualTo(problemaJuridico2);
    }
}
