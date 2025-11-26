package com.nocountry.crm.mapper.helper;

import com.nocountry.crm.entity.Tag;
import com.nocountry.crm.service.ITagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TagMapperHelper {

    private final ITagService tagService;

    public List<Tag> mapTagCodesToTags(List<String> codes) {
        if (codes == null) return null;

        List<Tag> tags = new ArrayList<>();
        for (String code : codes) {
            Tag tag = tagService.findByCode(code);
            if (tag != null) {
                tags.add(tag);
            }
        }
        return tags;
    }

    public List<String> mapTagsToCodes(List<Tag> tags) {
        if (tags == null) return null;

        List<String> codes = new ArrayList<>();
        for (Tag tag : tags) {
            if (tag != null && tag.getCode() != null) {
                codes.add(tag.getCode());
            }
        }
        return codes;
    }
}
