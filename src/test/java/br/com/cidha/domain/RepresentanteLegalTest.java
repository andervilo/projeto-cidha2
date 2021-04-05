package br.com.cidha.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.cidha.web.rest.TestUtil;

public class RepresentanteLegalTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RepresentanteLegal.class);
        RepresentanteLegal representanteLegal1 = new RepresentanteLegal();
        representanteLegal1.setId(1L);
        RepresentanteLegal representanteLegal2 = new RepresentanteLegal();
        representanteLegal2.setId(representanteLegal1.getId());
        assertThat(representanteLegal1).isEqualTo(representanteLegal2);
        representanteLegal2.setId(2L);
        assertThat(representanteLegal1).isNotEqualTo(representanteLegal2);
        representanteLegal1.setId(null);
        assertThat(representanteLegal1).isNotEqualTo(representanteLegal2);
    }
}
