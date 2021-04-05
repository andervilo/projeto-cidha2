package br.com.cidha.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.cidha.web.rest.TestUtil;

public class ConcessaoLiminarCassadaTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ConcessaoLiminarCassada.class);
        ConcessaoLiminarCassada concessaoLiminarCassada1 = new ConcessaoLiminarCassada();
        concessaoLiminarCassada1.setId(1L);
        ConcessaoLiminarCassada concessaoLiminarCassada2 = new ConcessaoLiminarCassada();
        concessaoLiminarCassada2.setId(concessaoLiminarCassada1.getId());
        assertThat(concessaoLiminarCassada1).isEqualTo(concessaoLiminarCassada2);
        concessaoLiminarCassada2.setId(2L);
        assertThat(concessaoLiminarCassada1).isNotEqualTo(concessaoLiminarCassada2);
        concessaoLiminarCassada1.setId(null);
        assertThat(concessaoLiminarCassada1).isNotEqualTo(concessaoLiminarCassada2);
    }
}
