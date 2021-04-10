package br.com.cidha.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.cidha.web.rest.TestUtil;

public class ConcessaoLiminarTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ConcessaoLiminar.class);
        ConcessaoLiminar concessaoLiminar1 = new ConcessaoLiminar();
        concessaoLiminar1.setId(1L);
        ConcessaoLiminar concessaoLiminar2 = new ConcessaoLiminar();
        concessaoLiminar2.setId(concessaoLiminar1.getId());
        assertThat(concessaoLiminar1).isEqualTo(concessaoLiminar2);
        concessaoLiminar2.setId(2L);
        assertThat(concessaoLiminar1).isNotEqualTo(concessaoLiminar2);
        concessaoLiminar1.setId(null);
        assertThat(concessaoLiminar1).isNotEqualTo(concessaoLiminar2);
    }
}
