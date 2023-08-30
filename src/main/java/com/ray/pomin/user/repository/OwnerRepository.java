package com.ray.pomin.user.repository;

import com.ray.pomin.user.domain.Owner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OwnerRepository extends JpaRepository<Owner, Long> {

    Optional<Owner> findByLoginId(String loginId);

}
