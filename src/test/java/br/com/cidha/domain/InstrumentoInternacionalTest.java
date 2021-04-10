package br.com.cidha.domain;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.cidha.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class InstrumentoInternacionalTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(InstrumentoInternacional.class);
        InstrumentoInternacional instrumentoInternacional1 = new InstrumentoInternacional();
        instrumentoInternacional1.setId(1L);
        InstrumentoInternacional instrumentoInternacional2 = new InstrumentoInternacional();
        instrumentoInternacional2.setId(instrumentoInternacional1.getId());
        assertThat(instrumentoInternacional1).isEqualTo(instrumentoInternacional2);
        instrumentoInternacional2.setId(2L);
        assertThat(instrumentoInternacional1).isNotEqualTo(instrumentoInternacional2);
        instrumentoInternacional1.setId(null);
        assertThat(instrumentoInternacional1).isNotEqualTo(instrumentoInternacional2);
    }
}
