package com.mycompany.myapp.service.mapper;


import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.DevisDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Devis} and its DTO {@link DevisDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DevisMapper extends EntityMapper<DevisDTO, Devis> {



    default Devis fromId(Long id) {
        if (id == null) {
            return null;
        }
        Devis devis = new Devis();
        devis.setId(id);
        return devis;
    }
}
