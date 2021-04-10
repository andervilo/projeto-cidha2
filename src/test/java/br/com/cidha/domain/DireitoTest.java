package br.com.cidha.domain;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.cidha.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DireitoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Direito.class);
        Direito direito1 = new Direito();
        direito1.setId(1L);
        Direito direito2 = new Direito();
        direito2.setId(direito1.getId());
        assertThat(direito1).isEqualTo(direito2);
        direito2.setId(2L);
        assertThat(direito1).isNotEqualTo(direito2);
        direito1.setId(null);
        assertThat(direito1).isNotEqualTo(direito2);
    }
}
