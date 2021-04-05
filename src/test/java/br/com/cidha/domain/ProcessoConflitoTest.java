package br.com.cidha.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.cidha.web.rest.TestUtil;

public class ProcessoConflitoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProcessoConflito.class);
        ProcessoConflito processoConflito1 = new ProcessoConflito();
        processoConflito1.setId(1L);
        ProcessoConflito processoConflito2 = new ProcessoConflito();
        processoConflito2.setId(processoConflito1.getId());
        assertThat(processoConflito1).isEqualTo(processoConflito2);
        processoConflito2.setId(2L);
        assertThat(processoConflito1).isNotEqualTo(processoConflito2);
        processoConflito1.setId(null);
        assertThat(processoConflito1).isNotEqualTo(processoConflito2);
    }
}
