package org.inventory.management.server.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.inventory.management.server.model.tag.ListTagModelRes;
import org.inventory.management.server.model.tag.TagModelRes;
import org.inventory.management.server.model.tag.UpsertTagModel;
import org.inventory.management.server.service.tag.TagService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/api/tags")
@AllArgsConstructor
public class TagController {
    private final TagService tagService;
    @GetMapping("/{id}")
    ResponseEntity<TagModelRes> getTagById(@PathVariable Long id){
        return ResponseEntity.ok(tagService.getTagById(id));
    }
    @PostMapping()
    ResponseEntity<TagModelRes> addTag( @Valid @RequestBody UpsertTagModel tagModel){
        return ResponseEntity.ok(tagService.upsertTag(null, tagModel));
    }
    @PutMapping("/{id}")
    ResponseEntity<TagModelRes> updateTag(@PathVariable Long id, @Valid @RequestBody UpsertTagModel tagModel){
        return ResponseEntity.ok(tagService.upsertTag(id, tagModel));
    }
    @GetMapping("/list")
    ResponseEntity<List<TagModelRes>> getTags(){
        return ResponseEntity.ok(tagService.getTags());
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<TagModelRes> deleteTag(@PathVariable Long id) {
        return ResponseEntity.ok(tagService.deleteTag(id));
    }
}