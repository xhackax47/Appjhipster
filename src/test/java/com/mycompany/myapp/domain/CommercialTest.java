package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class CommercialTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Commercial.class);
        Commercial commercial1 = new Commercial();
        commercial1.setId(1L);
        Commercial commercial2 = new Commercial();
        commercial2.setId(commercial1.getId());
        assertThat(commercial1).isEqualTo(commercial2);
        commercial2.setId(2L);
        assertThat(commercial1).isNotEqualTo(commercial2);
        commercial1.setId(null);
        assertThat(commercial1).isNotEqualTo(commercial2);
    }
}
