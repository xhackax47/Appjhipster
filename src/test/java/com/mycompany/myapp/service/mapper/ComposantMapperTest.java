package com.mycompany.myapp.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ComposantMapperTest {

    private ComposantMapper composantMapper;

    @BeforeEach
    public void setUp() {
        composantMapper = new ComposantMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(composantMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(composantMapper.fromId(null)).isNull();
    }
}
