package br.com.cidha.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.cidha.web.rest.TestUtil;

public class ParteInteresssadaTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ParteInteresssada.class);
        ParteInteresssada parteInteresssada1 = new ParteInteresssada();
        parteInteresssada1.setId(1L);
        ParteInteresssada parteInteresssada2 = new ParteInteresssada();
        parteInteresssada2.setId(parteInteresssada1.getId());
        assertThat(parteInteresssada1).isEqualTo(parteInteresssada2);
        parteInteresssada2.setId(2L);
        assertThat(parteInteresssada1).isNotEqualTo(parteInteresssada2);
        parteInteresssada1.setId(null);
        assertThat(parteInteresssada1).isNotEqualTo(parteInteresssada2);
    }
}
