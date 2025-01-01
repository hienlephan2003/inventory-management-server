package org.inventory.management.server.service.area;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.inventory.management.server.entity.Area;
import org.inventory.management.server.model.area.AreaRequest;
import org.inventory.management.server.model.area.AreaRes;
import org.inventory.management.server.repository.AreaRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AreaServiceImpl implements AreaService {
    private final AreaRepository areaRepository;
    private final ModelMapper modelMapper;
    @Override 
    @Transactional
    public AreaRes createArea(AreaRequest areaRequest) {
        Area newArea = Area
                .builder()
                .name(areaRequest.getName())
                .build();
        return modelMapper.map(areaRepository.save(newArea), AreaRes.class);
    }

    @Override
    public List<AreaRes> getAll() {
        return areaRepository.findAll().stream().map(item -> modelMapper.map(item, AreaRes.class)).toList();
    }

    @Override
    public AreaRes getAreaById(long id) {
        return modelMapper.map(areaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Area not found")), AreaRes.class);
    }
    
    @Override
    @Transactional
    public AreaRes updateArea(long areaId, AreaRequest areaRequest) {
        Area existingArea = areaRepository.findById(areaId)
                .orElseThrow(() -> new RuntimeException("Area not found"));
        existingArea.setName(areaRequest.getName());
        return modelMapper.map(areaRepository.save(existingArea), AreaRes.class);
    }

    @Override
    @Transactional
    public AreaRes deleteArea(long id)  {
        Area area = areaRepository.findById(id)
                .orElseThrow(() ->
                new EntityNotFoundException("Not found area with id"+ id));
         areaRepository.delete(area);
        return modelMapper.map(area, AreaRes.class);
    }
}
