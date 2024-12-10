package org.inventory.management.server.service.tag;

import org.inventory.management.server.model.query.ListQueryParam;
import org.inventory.management.server.model.tag.ListTagModelRes;
import org.inventory.management.server.model.tag.TagModelRes;
import org.inventory.management.server.model.tag.UpsertTagModel;

import java.util.List;

public interface TagService {
    TagModelRes getTagById(long id) ;
    TagModelRes upsertTag(Long id, UpsertTagModel tagModel);
    TagModelRes deleteTag(long id);
    List<TagModelRes> getTags();

}
