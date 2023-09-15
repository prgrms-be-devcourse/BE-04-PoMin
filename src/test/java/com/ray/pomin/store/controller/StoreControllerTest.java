package com.ray.pomin.store.controller;

import com.ray.pomin.common.domain.Address;
import com.ray.pomin.common.domain.Point;
import com.ray.pomin.customer.controller.base.ControllerUnit;
import com.ray.pomin.menu.domain.AdditionalDetails;
import com.ray.pomin.menu.domain.Menu;
import com.ray.pomin.menu.domain.MenuInfo;
import com.ray.pomin.menu.domain.PrimaryDetails;
import com.ray.pomin.menu.service.MenuService;
import com.ray.pomin.store.controller.converter.MapPointConverter;
import com.ray.pomin.store.controller.dto.StoreSaveRequest;
import com.ray.pomin.store.domain.Store;
import com.ray.pomin.store.domain.StoreTime;
import com.ray.pomin.store.service.StoreService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalTime;
import java.util.List;

import static com.ray.pomin.menu.domain.MenuOption.BEST_SELLER;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.JsonFieldType.ARRAY;
import static org.springframework.restdocs.payload.JsonFieldType.BOOLEAN;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StoreController.class)
class StoreControllerTest extends ControllerUnit {

    @MockBean
    private StoreService storeService;

    @MockBean
    private MenuService menuService;

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

    @Test
    void successGetOneStoreWithMenu() throws Exception {
        // given
        Long storeId = 1L;
        String name = "store1";
        String phoneNumber = "010-1234-5678";
        Address address = new Address("서울시 강남구 역삼동", "12-2");
        Point point = new Point(34.123, 123.45);
        StoreTime storeTime = new StoreTime(false, LocalTime.of(12, 0), LocalTime.of(22, 0));
        List<String> images = List.of("imageUrl1", "imageUrl2");

        PrimaryDetails primary = new PrimaryDetails("menu1", "10000");
        AdditionalDetails additional = new AdditionalDetails("image", BEST_SELLER, "description");
        MenuInfo menuInfo = new MenuInfo(primary, additional);
        Menu menu = new Menu(menuInfo, true, 1L);
        List<Menu> menus = List.of(menu);

        Store store = new Store(name, phoneNumber, address, point, storeTime, images);

        given(storeService.getOne(storeId)).willReturn(store);
        given(menuService.getAllInStore(storeId)).willReturn(menus);

        // when
        ResultActions action = mvc.perform(get("/stores/{storeId}", storeId));

        // then
        action.andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.storeName").value(name),
                        jsonPath("$.phoneNumber").value(phoneNumber),
                        jsonPath("$.address").value(address.getPrimaryAddress() + address.getAdditionalAddress()),
                        jsonPath("$.images[0]").value("imageUrl1"),
                        jsonPath("$.menuInfos[0].menuName").value("menu1"),
                        jsonPath("$.menuInfos[0].price").value("10000"),
                        jsonPath("$.menuInfos[0].description").value("description"),
                        jsonPath("$.menuInfos[0].imageUrl").value("image"),
                        jsonPath("$.menuInfos[0].label").value(BEST_SELLER.name()),
                        jsonPath("$.menuInfos[0].verifyAge").value(true)
                )
                .andDo(
                        document("store/getOne",
                                pathParameters(
                                        parameterWithName("storeId").description("store 아이디")
                                ),
                                responseFields(
                                        fieldWithPath("storeName").type(STRING).description("가게 이름"),
                                        fieldWithPath("phoneNumber").type(STRING).description("가게 전화번호"),
                                        fieldWithPath("address").type(STRING).description("가게 전체 주소"),
                                        fieldWithPath("images").type(ARRAY).description("가게 대표 이미지 목록"),
                                        fieldWithPath("menuInfos").type(ARRAY).description("등록된 메뉴 목록"),
                                        fieldWithPath("menuInfos[].menuName").type(STRING).description("메뉴 이름"),
                                        fieldWithPath("menuInfos[].price").type(STRING).description("메뉴 가격"),
                                        fieldWithPath("menuInfos[].description").type(STRING).description("메뉴 설명"),
                                        fieldWithPath("menuInfos[].imageUrl").type(STRING).description("메뉴 이미지 링크 주소"),
                                        fieldWithPath("menuInfos[].label").type(STRING).description("메뉴 라벨(인기 메뉴, 대표 메뉴)"),
                                        fieldWithPath("menuInfos[].verifyAge").type(BOOLEAN).description("19세 이상 구매 가능 여부")
                                )
                        )
                );
    }

}
