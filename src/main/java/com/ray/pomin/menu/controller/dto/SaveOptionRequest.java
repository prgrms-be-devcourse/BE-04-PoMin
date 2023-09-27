package com.ray.pomin.menu.controller.dto;

import com.ray.pomin.menu.domain.Menu;
import com.ray.pomin.menu.domain.Option;
import com.ray.pomin.menu.domain.OptionGroup;

import java.util.List;

public record SaveOptionRequest(
        Long groupId, String groupName, boolean defaultSelection, boolean exclusiveSelection,
        int maxSize, Long menuId, List<OptionRequest> option
) {

    public OptionGroup toEntity(Menu menu) {
        OptionGroup optionGroup = new OptionGroup(groupId, groupName, defaultSelection, exclusiveSelection,
                maxSize, menu);

        optionGroup.addOption(option.stream().map(optionRequest -> optionRequest.toEntity(optionGroup)).toList());

        return optionGroup;
    }

    private record OptionRequest(Long optionId, String name, int price) {

        public Option toEntity(OptionGroup optionGroup) {
            return new Option(optionId, name, price, optionGroup);
        }

    }
}
