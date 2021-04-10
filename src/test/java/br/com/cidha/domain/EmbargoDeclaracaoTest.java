package br.com.cidha.domain;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.cidha.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EmbargoDeclaracaoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmbargoDeclaracao.class);
        EmbargoDeclaracao embargoDeclaracao1 = new EmbargoDeclaracao();
        embargoDeclaracao1.setId(1L);
        EmbargoDeclaracao embargoDeclaracao2 = new EmbargoDeclaracao();
        embargoDeclaracao2.setId(embargoDeclaracao1.getId());
        assertThat(embargoDeclaracao1).isEqualTo(embargoDeclaracao2);
        embargoDeclaracao2.setId(2L);
        assertThat(embargoDeclaracao1).isNotEqualTo(embargoDeclaracao2);
        embargoDeclaracao1.setId(null);
        assertThat(embargoDeclaracao1).isNotEqualTo(embargoDeclaracao2);
    }
}
