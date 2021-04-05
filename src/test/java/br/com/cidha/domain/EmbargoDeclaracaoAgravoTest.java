package br.com.cidha.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.cidha.web.rest.TestUtil;

public class EmbargoDeclaracaoAgravoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmbargoDeclaracaoAgravo.class);
        EmbargoDeclaracaoAgravo embargoDeclaracaoAgravo1 = new EmbargoDeclaracaoAgravo();
        embargoDeclaracaoAgravo1.setId(1L);
        EmbargoDeclaracaoAgravo embargoDeclaracaoAgravo2 = new EmbargoDeclaracaoAgravo();
        embargoDeclaracaoAgravo2.setId(embargoDeclaracaoAgravo1.getId());
        assertThat(embargoDeclaracaoAgravo1).isEqualTo(embargoDeclaracaoAgravo2);
        embargoDeclaracaoAgravo2.setId(2L);
        assertThat(embargoDeclaracaoAgravo1).isNotEqualTo(embargoDeclaracaoAgravo2);
        embargoDeclaracaoAgravo1.setId(null);
        assertThat(embargoDeclaracaoAgravo1).isNotEqualTo(embargoDeclaracaoAgravo2);
    }
}
