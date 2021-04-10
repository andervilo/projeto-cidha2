package br.com.cidha.domain;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.cidha.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EmbargoRespReTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmbargoRespRe.class);
        EmbargoRespRe embargoRespRe1 = new EmbargoRespRe();
        embargoRespRe1.setId(1L);
        EmbargoRespRe embargoRespRe2 = new EmbargoRespRe();
        embargoRespRe2.setId(embargoRespRe1.getId());
        assertThat(embargoRespRe1).isEqualTo(embargoRespRe2);
        embargoRespRe2.setId(2L);
        assertThat(embargoRespRe1).isNotEqualTo(embargoRespRe2);
        embargoRespRe1.setId(null);
        assertThat(embargoRespRe1).isNotEqualTo(embargoRespRe2);
    }
}
