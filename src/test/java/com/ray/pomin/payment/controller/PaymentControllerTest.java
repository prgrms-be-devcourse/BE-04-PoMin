package com.ray.pomin.payment.controller;

import com.ray.pomin.common.util.OrderNumberGenerator;
import com.ray.pomin.common.util.WithPominUser;
import com.ray.pomin.customer.controller.base.ControllerUnit;
import com.ray.pomin.order.service.OrderService;
import com.ray.pomin.payment.domain.PGInfo;
import com.ray.pomin.payment.domain.PGType;
import com.ray.pomin.payment.domain.PayInfo;
import com.ray.pomin.payment.domain.PayMethod;
import com.ray.pomin.payment.domain.PayType;
import com.ray.pomin.payment.domain.Payment;
import com.ray.pomin.payment.service.PaymentService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;

import static com.ray.pomin.payment.domain.PaymentStatus.COMPLETE;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PaymentController.class)
class PaymentControllerTest extends ControllerUnit {

    @MockBean
    private PaymentService paymentService;

    @MockBean
    private OrderService orderService;

    @Test
    @WithPominUser
    @DisplayName("결제 정보를 id 값으로 조회할 수 있다")
    public void successFindPayment() throws Exception {
        //given
        Long paymentId = 1L;
        LocalDateTime approvedAt = LocalDateTime.now().minusSeconds(2);

        Payment payment = Payment.builder()
                .id(paymentId)
                .amount(1000)
                .status(COMPLETE)
                .pgInfo(new PGInfo(PGType.TOSS, "paymentKey23223"))
                .payInfo(new PayInfo(PayMethod.CARD, PayType.KB))
                .customerId(1L)
                .approvedAt(approvedAt).build();

        given(paymentService.findOneCheckingAuth(paymentId, 1L)).willReturn(payment);

        //when
        ResultActions resultActions = mvc.perform(RestDocumentationRequestBuilders.get("/payments/{paymentId}", paymentId)
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON));

        //then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(payment.getId()))
                .andExpect(jsonPath("amount").value(payment.getAmount()))
                .andExpect(jsonPath("status").value(payment.getStatus().toString()))
                .andExpect(jsonPath("payMethod").value(payment.getPayInfo().getMethod().toString()))
                .andExpect(jsonPath("payType").value(payment.getPayInfo().getType().toString()))
                .andDo(print())
                .andDo(document("payment-get-one",
                        pathParameters(
                                parameterWithName("paymentId").description("결제 id")
                        ),
                        responseFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("결제 id"),
                                fieldWithPath("amount").type(JsonFieldType.NUMBER).description("결제금액"),
                                fieldWithPath("status").type(JsonFieldType.STRING).description("결제상태"),
                                fieldWithPath("payMethod").type(JsonFieldType.STRING).description("결제수단"),
                                fieldWithPath("payType").type(JsonFieldType.STRING).description("결제수단상세")
                        )
                ));
    }

    @Test
    @DisplayName("결제 요청에 실패한다")
    public void failPaymentRequest() throws Exception {
        //given
        String code = "PAY_PROCESS_CANCELED";
        String message = "사용자에 의해 결제가 취소되었습니다.";
        String orderId = OrderNumberGenerator.generateOrderNumber();

        //when
        ResultActions resultActions = mvc.perform(RestDocumentationRequestBuilders.get("/payment/fail")
                .param("code", code)
                .param("message", message)
                .param("orderId", orderId)
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON));

        //then
        resultActions.andExpect(status().isBadRequest())
                .andExpect(jsonPath("message").value(message))
                .andExpect(jsonPath("orderId").value(orderId))
                .andDo(print())
                .andDo(document("payment-request-fail",
                        responseFields(
                                fieldWithPath("message").type(JsonFieldType.STRING).description("결제상태"),
                                fieldWithPath("orderId").type(JsonFieldType.STRING).description("주문번호")
                        )
                ));
    }

}
