package com.ray.pomin.user.service;

import com.ray.pomin.user.domain.Owner;
import com.ray.pomin.user.repository.OwnerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OwnerService {

    private final OwnerRepository ownerRepository;

    public Owner save(Owner owner) {
        ownerRepository.findByLoginId(owner.getLoginId())
                .ifPresent(findOwner -> {
                    throw new IllegalArgumentException("이미 사용중인 이메일입니다");
                });

        return ownerRepository.save(owner);
    }

}
