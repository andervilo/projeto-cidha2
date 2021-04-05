package br.com.cidha.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.cidha.web.rest.TestUtil;

public class JurisprudenciaTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Jurisprudencia.class);
        Jurisprudencia jurisprudencia1 = new Jurisprudencia();
        jurisprudencia1.setId(1L);
        Jurisprudencia jurisprudencia2 = new Jurisprudencia();
        jurisprudencia2.setId(jurisprudencia1.getId());
        assertThat(jurisprudencia1).isEqualTo(jurisprudencia2);
        jurisprudencia2.setId(2L);
        assertThat(jurisprudencia1).isNotEqualTo(jurisprudencia2);
        jurisprudencia1.setId(null);
        assertThat(jurisprudencia1).isNotEqualTo(jurisprudencia2);
    }
}
