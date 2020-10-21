package com.mycompany.myapp.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class CommercialDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CommercialDTO.class);
        CommercialDTO commercialDTO1 = new CommercialDTO();
        commercialDTO1.setId(1L);
        CommercialDTO commercialDTO2 = new CommercialDTO();
        assertThat(commercialDTO1).isNotEqualTo(commercialDTO2);
        commercialDTO2.setId(commercialDTO1.getId());
        assertThat(commercialDTO1).isEqualTo(commercialDTO2);
        commercialDTO2.setId(2L);
        assertThat(commercialDTO1).isNotEqualTo(commercialDTO2);
        commercialDTO1.setId(null);
        assertThat(commercialDTO1).isNotEqualTo(commercialDTO2);
    }
}
