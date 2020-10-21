package com.mycompany.myapp.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class ComposantDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ComposantDTO.class);
        ComposantDTO composantDTO1 = new ComposantDTO();
        composantDTO1.setId(1L);
        ComposantDTO composantDTO2 = new ComposantDTO();
        assertThat(composantDTO1).isNotEqualTo(composantDTO2);
        composantDTO2.setId(composantDTO1.getId());
        assertThat(composantDTO1).isEqualTo(composantDTO2);
        composantDTO2.setId(2L);
        assertThat(composantDTO1).isNotEqualTo(composantDTO2);
        composantDTO1.setId(null);
        assertThat(composantDTO1).isNotEqualTo(composantDTO2);
    }
}
