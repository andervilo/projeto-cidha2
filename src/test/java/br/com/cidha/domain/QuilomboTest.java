package br.com.cidha.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.cidha.web.rest.TestUtil;

public class QuilomboTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Quilombo.class);
        Quilombo quilombo1 = new Quilombo();
        quilombo1.setId(1L);
        Quilombo quilombo2 = new Quilombo();
        quilombo2.setId(quilombo1.getId());
        assertThat(quilombo1).isEqualTo(quilombo2);
        quilombo2.setId(2L);
        assertThat(quilombo1).isNotEqualTo(quilombo2);
        quilombo1.setId(null);
        assertThat(quilombo1).isNotEqualTo(quilombo2);
    }
}
