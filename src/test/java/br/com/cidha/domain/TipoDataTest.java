package br.com.cidha.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.cidha.web.rest.TestUtil;

public class TipoDataTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TipoData.class);
        TipoData tipoData1 = new TipoData();
        tipoData1.setId(1L);
        TipoData tipoData2 = new TipoData();
        tipoData2.setId(tipoData1.getId());
        assertThat(tipoData1).isEqualTo(tipoData2);
        tipoData2.setId(2L);
        assertThat(tipoData1).isNotEqualTo(tipoData2);
        tipoData1.setId(null);
        assertThat(tipoData1).isNotEqualTo(tipoData2);
    }
}
