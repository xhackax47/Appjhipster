package com.mycompany.myapp.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CommercialMapperTest {

    private CommercialMapper commercialMapper;

    @BeforeEach
    public void setUp() {
        commercialMapper = new CommercialMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(commercialMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(commercialMapper.fromId(null)).isNull();
    }
}
