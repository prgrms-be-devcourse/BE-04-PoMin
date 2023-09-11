package com.ray.pomin.customer.service;

import com.ray.pomin.customer.domain.Customer;
import com.ray.pomin.customer.domain.CustomerInfo;
import com.ray.pomin.customer.domain.Login;
import com.ray.pomin.customer.domain.PhoneNumber;
import com.ray.pomin.customer.domain.Provider;
import com.ray.pomin.customer.repository.CustomerRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    CustomerRepository customerRepository;

    @InjectMocks
    CustomerService customerService;

    @Test
    @DisplayName("사용자 저장에 성공한다")
    void successSaveCustomer() {
        // given
        Customer customer = new Customer(
                new Login("email@gmail.com", "Password1!", NoOpPasswordEncoder.getInstance()),
                "nickname",
                new CustomerInfo("name", LocalDate.now(),
                new PhoneNumber("010-9100-2583")), Provider.KAKAO
        );
        given(customerRepository.save(customer)).willReturn(customer);

        // when
        Customer savedCustomer = customerService.save(customer);

        // then
        assertThat(savedCustomer).isEqualTo(customer);
    }

    @Test
    @DisplayName("이미 사용중인 이메일 사용 시 저장에 실패한다")
    void failSaveCustomerWithUsedEmail() {
        // given
        Customer customer = new Customer(
                new Login("email@gmail.com", "Password1!", NoOpPasswordEncoder.getInstance()),
                "nickname",
                new CustomerInfo("name", LocalDate.now(),
                        new PhoneNumber("010-9100-2583")), Provider.KAKAO
        );
        given(customerRepository.save(customer)).willThrow(IllegalArgumentException.class);

        // when & then
        assertThatThrownBy(() -> customerService.save(customer))
                .isInstanceOf(IllegalArgumentException.class);
    }

}
