package br.com.cidha.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.cidha.web.rest.TestUtil;

public class EtniaIndigenaTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EtniaIndigena.class);
        EtniaIndigena etniaIndigena1 = new EtniaIndigena();
        etniaIndigena1.setId(1L);
        EtniaIndigena etniaIndigena2 = new EtniaIndigena();
        etniaIndigena2.setId(etniaIndigena1.getId());
        assertThat(etniaIndigena1).isEqualTo(etniaIndigena2);
        etniaIndigena2.setId(2L);
        assertThat(etniaIndigena1).isNotEqualTo(etniaIndigena2);
        etniaIndigena1.setId(null);
        assertThat(etniaIndigena1).isNotEqualTo(etniaIndigena2);
    }
}
