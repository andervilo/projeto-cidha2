package br.com.cidha.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.cidha.web.rest.TestUtil;

public class TerraIndigenaTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TerraIndigena.class);
        TerraIndigena terraIndigena1 = new TerraIndigena();
        terraIndigena1.setId(1L);
        TerraIndigena terraIndigena2 = new TerraIndigena();
        terraIndigena2.setId(terraIndigena1.getId());
        assertThat(terraIndigena1).isEqualTo(terraIndigena2);
        terraIndigena2.setId(2L);
        assertThat(terraIndigena1).isNotEqualTo(terraIndigena2);
        terraIndigena1.setId(null);
        assertThat(terraIndigena1).isNotEqualTo(terraIndigena2);
    }
}
