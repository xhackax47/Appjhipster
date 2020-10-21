package com.mycompany.myapp.service.mapper;


import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.ComposantDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Composant} and its DTO {@link ComposantDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ComposantMapper extends EntityMapper<ComposantDTO, Composant> {



    default Composant fromId(Long id) {
        if (id == null) {
            return null;
        }
        Composant composant = new Composant();
        composant.setId(id);
        return composant;
    }
}
