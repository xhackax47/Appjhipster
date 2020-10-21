package com.mycompany.myapp.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class DevisMapperTest {

    private DevisMapper devisMapper;

    @BeforeEach
    public void setUp() {
        devisMapper = new DevisMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(devisMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(devisMapper.fromId(null)).isNull();
    }
}
