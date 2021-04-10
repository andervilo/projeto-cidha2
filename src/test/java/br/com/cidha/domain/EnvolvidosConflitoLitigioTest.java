package br.com.cidha.domain;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.cidha.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EnvolvidosConflitoLitigioTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EnvolvidosConflitoLitigio.class);
        EnvolvidosConflitoLitigio envolvidosConflitoLitigio1 = new EnvolvidosConflitoLitigio();
        envolvidosConflitoLitigio1.setId(1L);
        EnvolvidosConflitoLitigio envolvidosConflitoLitigio2 = new EnvolvidosConflitoLitigio();
        envolvidosConflitoLitigio2.setId(envolvidosConflitoLitigio1.getId());
        assertThat(envolvidosConflitoLitigio1).isEqualTo(envolvidosConflitoLitigio2);
        envolvidosConflitoLitigio2.setId(2L);
        assertThat(envolvidosConflitoLitigio1).isNotEqualTo(envolvidosConflitoLitigio2);
        envolvidosConflitoLitigio1.setId(null);
        assertThat(envolvidosConflitoLitigio1).isNotEqualTo(envolvidosConflitoLitigio2);
    }
}
