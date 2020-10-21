package com.mycompany.myapp.service.mapper;


import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.CommercialDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Commercial} and its DTO {@link CommercialDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CommercialMapper extends EntityMapper<CommercialDTO, Commercial> {



    default Commercial fromId(Long id) {
        if (id == null) {
            return null;
        }
        Commercial commercial = new Commercial();
        commercial.setId(id);
        return commercial;
    }
}
