package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class ComposantTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Composant.class);
        Composant composant1 = new Composant();
        composant1.setId(1L);
        Composant composant2 = new Composant();
        composant2.setId(composant1.getId());
        assertThat(composant1).isEqualTo(composant2);
        composant2.setId(2L);
        assertThat(composant1).isNotEqualTo(composant2);
        composant1.setId(null);
        assertThat(composant1).isNotEqualTo(composant2);
    }
}
