package com.ray.pomin.customer.repository;

import com.ray.pomin.customer.domain.Customer;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @EntityGraph(attributePaths = "loginProviders")
    Optional<Customer> findByLoginEmail(String email);

}
