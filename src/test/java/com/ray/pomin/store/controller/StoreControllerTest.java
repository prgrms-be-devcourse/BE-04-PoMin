package com.ray.pomin.store.controller;

import com.ray.pomin.common.domain.Point;
import com.ray.pomin.customer.controller.base.ControllerUnit;
import com.ray.pomin.store.controller.converter.MapPointConverter;
import com.ray.pomin.store.controller.dto.StoreSaveRequest;
import com.ray.pomin.store.domain.Store;
import com.ray.pomin.store.service.StoreService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static javax.management.openmbean.SimpleType.STRING;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.asm.Type.ARRAY;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StoreController.class)
class StoreControllerTest extends ControllerUnit {

    @MockBean
    private StoreService storeService;

    @MockBean
    private MapPointConverter mapPointConverter;

    @Test
    void successSaveStore() throws Exception {
        // given
        StoreSaveRequest request = new StoreSaveRequest(
                "storeName",
                "010-1234-5678",
                "서울시 강남구 역삼동",
                "12-234",
                "12:00",
                "22:00",
                List.of()
        );
        Point point = new Point(34.123, 123.245);
        Store store = request.toEntity(point);
        given(mapPointConverter.getPoint("서울시 강남구 역삼동")).willReturn(point);
        doNothing().when(storeService).save(store);

        //when
        ResultActions action = mvc.perform(post("/stores")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(request)));

        // then
        action.andExpect(status().isNoContent())
                .andDo(
                        document("store/save",
                                requestFields(
                                        fieldWithPath("name").type(STRING).description(""),
                                        fieldWithPath("phoneNumber").type(STRING).description(""),
                                        fieldWithPath("mainAddress").type(STRING).description(""),
                                        fieldWithPath("detailAddress").type(STRING).description(""),
                                        fieldWithPath("openTime").type(STRING).description(""),
                                        fieldWithPath("closeTime").type(STRING).description(""),
                                        fieldWithPath("images").type(ARRAY).description("")
                                )
                        )
                );
    }

}
