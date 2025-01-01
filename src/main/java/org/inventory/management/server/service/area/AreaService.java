package org.inventory.management.server.service.area;


import org.inventory.management.server.entity.Area;
import org.inventory.management.server.model.area.AreaRequest;
import org.inventory.management.server.model.area.AreaRes;

import java.util.List;

public interface AreaService {
    AreaRes createArea(AreaRequest category);
    List<AreaRes> getAll();
    AreaRes getAreaById(long id) ;
    AreaRes updateArea(long categoryId, AreaRequest categoryRequest);

    AreaRes deleteArea(long id) ;
}
