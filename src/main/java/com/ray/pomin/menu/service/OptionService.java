package com.ray.pomin.menu.service;

import com.ray.pomin.menu.domain.OptionGroup;
import com.ray.pomin.menu.repository.OptionGroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OptionService {

    private final OptionGroupRepository optionGroupRepository;

    public void save(OptionGroup optionGroup) {
        optionGroupRepository.save(optionGroup);
    }

}
