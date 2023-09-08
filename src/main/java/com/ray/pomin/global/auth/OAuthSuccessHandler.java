package com.ray.pomin.global.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ray.pomin.customer.domain.Customer;
import com.ray.pomin.customer.repository.CustomerRepository;
import com.ray.pomin.global.auth.model.OAuthCustomer;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class OAuthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final ObjectMapper objectMapper;
    private final CustomerRepository customerRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuthCustomer oAuthCustomer = (OAuthCustomer) authentication.getPrincipal();

        response.setCharacterEncoding("UTF-8");
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        Optional<Customer> findCustomer = customerRepository.findByLoginEmail(oAuthCustomer.getRegistration().email());
        if (findCustomer.isPresent()) {
            Customer customer = findCustomer.get().addLoginProvider(oAuthCustomer.getRegistration().providerName());
            customerRepository.save(customer);

            response.getWriter().println(objectMapper.writeValueAsString(createJWT(customer)));

            return;
        }

        response.setHeader("Location", "/api/v1/customers");

        response.getWriter().println(objectMapper.writeValueAsString(oAuthCustomer.getRegistration()));
    }

    private String createJWT(Customer customer) {
        return "accessToken";
    }

}
