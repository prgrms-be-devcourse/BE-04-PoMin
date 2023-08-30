package com.ray.pomin.user.service;

import com.ray.pomin.user.domain.Owner;
import com.ray.pomin.user.repository.OwnerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class OwnerServiceTest {

    @Mock
    private OwnerRepository ownerRepository;

    @InjectMocks
    private OwnerService ownerService;

    @Test
    void successSaveOwner() {
        // given
        Owner owner = new Owner("owner", "owner1234!", "owner@gmail.com", NoOpPasswordEncoder.getInstance());
        given(ownerRepository.findByLoginId("owner")).willReturn(Optional.empty());
        given(ownerRepository.save(owner)).willReturn(owner);

        // when
        Owner savedOwner = ownerService.save(owner);

        // then
        verify(ownerRepository, times(1)).save(owner);
        assertThat(owner).isEqualTo(savedOwner);
    }

    @Test
    void failSaveOwnerWithUsedLoginId() {
        // given
        Owner owner = new Owner("owner", "owner1234!", "owner@gmail.com", NoOpPasswordEncoder.getInstance());
        given(ownerRepository.findByLoginId("owner")).willReturn(Optional.of(owner));

        // when & then
        assertThatThrownBy(() -> ownerService.save(owner))
                .isInstanceOf(IllegalArgumentException.class);
    }

}
