package org.inventory.management.server.service.tag;

import lombok.AllArgsConstructor;
import org.inventory.management.server.entity.Area;
import org.inventory.management.server.entity.Tag;
import org.inventory.management.server.model.tag.TagModelRes;
import org.inventory.management.server.model.tag.UpsertTagModel;
import org.inventory.management.server.repository.AreaRepository;
import org.inventory.management.server.repository.TagRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class TagServiceImpl implements  TagService{
    private final TagRepository tagRepository;
    private final AreaRepository areaRepository;
    private final ModelMapper modelMapper;
    @Override
    public TagModelRes getTagById(long id) {

        Tag tag = tagRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Not found tags"));
        return modelMapper.map(tag, TagModelRes.class);
    }

    @Override
    public TagModelRes upsertTag(Long id, UpsertTagModel tagModel) {
        Tag tag;
        Area area = areaRepository.findById(tagModel.getAreaId())
                .orElseThrow(() -> new IllegalArgumentException("Not found area"));

        if(id != null){
            tag = tagRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Not found tags"));
            tag.setName(tagModel.getName());
            tag.setDescription(tagModel.getDescription());
            tag.setArea(area);
        }
        else{
            tag = Tag.builder()
                    .name(tagModel.getName())
                    .description(tagModel.getDescription())
                    .createdDate(new Date())
                    .area(area)
                    .build();
        }
        return modelMapper.map(tagRepository.save(tag),TagModelRes.class );
    }

    @Override
    public TagModelRes deleteTag(long id) {
       Tag tag = tagRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Not found tags"));
        tag.setIsDeleted(true);
        return modelMapper.map(tagRepository.save(tag),TagModelRes.class );
    }

    @Override
    public List<TagModelRes> getTags() {
        List<Tag> tags = tagRepository.findByIsDeletedFalse();
        return tags.stream().map(item -> modelMapper.map(item, TagModelRes.class)).toList();
    }
}
