package com.ray.pomin.customer.controller;

import com.ray.pomin.customer.controller.base.ControllerUnit;
import com.ray.pomin.customer.controller.dto.SaveCustomerRequest;
import com.ray.pomin.customer.controller.dto.SaveOAuthCustomerRequest;
import com.ray.pomin.customer.domain.Customer;
import com.ray.pomin.customer.service.CustomerService;
import com.ray.pomin.global.auth.model.Claims;
import com.ray.pomin.global.auth.model.OAuthCustomerInfo;
import com.ray.pomin.global.auth.model.Token;
import com.ray.pomin.global.auth.token.JwtService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.ResultActions;

import static javax.management.openmbean.SimpleType.STRING;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CustomerController.class)
class CustomerControllerTest extends ControllerUnit {

    @MockBean
    private CustomerService customerService;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private JwtService jwtService;

    @Test
    @DisplayName("사용자 저장에 성공한다")
    void successSaveCustomer() throws Exception {
        // given
        SaveCustomerRequest request = new SaveCustomerRequest(
                "email@gamil.com",
                "Password1!",
                "user",
                "nickname",
                "1999-01-29",
                "010-1234-5678",
                "KAKAO"
        );
        Customer customer = request.toEntity(passwordEncoder);
        given(customerService.save(customer)).willReturn(customer);
        given(jwtService.createToken(any(Claims.class))).willReturn(new Token("accessToken"));

        // when
        ResultActions action = mvc.perform(post("/api/v1/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(request)));

        // then
        action
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(
                        document("customer/save",
                                requestFields(
                                        fieldWithPath("email").type(STRING).description("사용자 로그인 이메일"),
                                        fieldWithPath("password").type(STRING).description("사용자 로그인 비밀번호"),
                                        fieldWithPath("name").type(STRING).description("사용자 개인 정보 이름"),
                                        fieldWithPath("nickname").type(STRING).description("앱 사용시 이용 될 닉네임"),
                                        fieldWithPath("birthDate").type(STRING).description("사용자 생년월일"),
                                        fieldWithPath("phoneNumber").type(STRING).description("사용자 비밀번호"),
                                        fieldWithPath("provider").type(STRING).description("사용자가 로그인 시 사용한 곳 (NORMAL, KAKAO)")
                                ),
                                responseFields(
                                        fieldWithPath("accessToken").type(STRING).description("로그인 성공 토큰 권한 필요한 곳에서 사용")
                                )
                        )
                );
    }

    @Test
    @DisplayName("소셜 사용자 저장에 성공한다")
    void successSaveOAuthCustomer() throws Exception {
        // given
        SaveOAuthCustomerRequest request = new SaveOAuthCustomerRequest(
                "nickname",
                "1999-01-29",
                "010-1234-5678",
                1L
        );
        OAuthCustomerInfo info = new OAuthCustomerInfo("email@gmail.com", "user", "KAKAO");
        SaveCustomerRequest saveRequest = request.convertToSaveCustomerRequest(info);
        Customer customer = saveRequest.toEntity(passwordEncoder);

        given(customerService.additionalCustomerInfo(1L)).willReturn(info);
        given(customerService.save(customer)).willReturn(customer);
        given(jwtService.createToken(any(Claims.class))).willReturn(new Token("accessToken"));

        // when
        ResultActions action = mvc.perform(post("/api/v1/customers/oauth")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(request)));

        // then
        action
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(
                        document("customer/saveOAuth",
                                requestFields(
                                        fieldWithPath("nickname").type(STRING).description("앱 사용시 이용 될 닉네임"),
                                        fieldWithPath("birthDate").type(STRING).description("사용자 생년월일"),
                                        fieldWithPath("phoneNumber").type(STRING).description("사용자 비밀번호"),
                                        fieldWithPath("oAuthId").type(NUMBER).description("OAuth 로그인 성공 후 발급 받은 키 값")
                                ),
                                responseFields(
                                        fieldWithPath("accessToken").type(STRING).description("로그인 성공 토큰 권한 필요한 곳에서 사용")
                                )
                        )
                );
    }

}
