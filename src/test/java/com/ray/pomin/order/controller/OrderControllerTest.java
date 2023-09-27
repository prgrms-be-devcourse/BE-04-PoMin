package com.ray.pomin.order.controller;

import com.ray.pomin.common.domain.Point;
import com.ray.pomin.customer.controller.base.ControllerUnit;
import com.ray.pomin.order.Cart;
import com.ray.pomin.order.Order;
import com.ray.pomin.order.service.OrderService;
import com.ray.pomin.payment.service.PaymentService;
import com.ray.pomin.store.controller.dto.StoreSaveRequest;
import com.ray.pomin.store.domain.Store;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderController.class)
class OrderControllerTest extends ControllerUnit {

    @MockBean
    private OrderService orderService;

    @MockBean
    private PaymentService paymentService;

    private Store store;

    @BeforeEach
    void setUp() {
        StoreSaveRequest request = new StoreSaveRequest(
                1L,
                "storeName",
                "010-1234-5678",
                "서울시 강남구 역삼동",
                "12-234",
                "12:00",
                "22:00",
                List.of()
        );
        Point point = new Point(34.123, 123.245);
        store = request.toEntity(point);
    }

    @Test
    void saveOrder() throws Exception {
        Cart cart = new Cart(1L, List.of(), store);

        ResultActions resultActions = mvc.perform(RestDocumentationRequestBuilders.post("/api/v1/orders")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(cart)));

        resultActions
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(
                        document("order/save",
                                responseFields(
                                        fieldWithPath("id").type(JsonFieldType.NULL).description("주문 ID"),
                                        fieldWithPath("store").type(JsonFieldType.OBJECT).description("가게 정보"),
                                        fieldWithPath("store.id").type(JsonFieldType.NUMBER).description("가게 아이디"),
                                        fieldWithPath("store.name").type(JsonFieldType.STRING).description("가게 이름"),
                                        fieldWithPath("store.createdDate").type(JsonFieldType.NULL).description("가게 생성시간"),
                                        fieldWithPath("store.lastModifiedDate").type(JsonFieldType.NULL).description("가게 수정시간"),
                                        fieldWithPath("store.phoneNumber").type(JsonFieldType.STRING).description("가게 전화번호"),
                                        fieldWithPath("store.time").type(JsonFieldType.OBJECT).description("가게 시간"),
                                        fieldWithPath("store.time.open").type(JsonFieldType.STRING).description("가게 오픈시간"),
                                        fieldWithPath("store.time.close").type(JsonFieldType.STRING).description("가게 마감시간"),
                                        fieldWithPath("store.storeImages").type(JsonFieldType.ARRAY).description("가게 이미지"),
                                        fieldWithPath("orderInfo").type(JsonFieldType.NULL).description("주문 정보"),
                                        fieldWithPath("totalPrice").type(JsonFieldType.NUMBER).description("주문 총 가격"),
                                        fieldWithPath("payment").type(JsonFieldType.NULL).description("결제 정보")
                                        )
                        )
                );

    }

}
