package com.ray.pomin.menu.controller;

import com.ray.pomin.customer.controller.base.ControllerUnit;
import com.ray.pomin.menu.controller.dto.SaveMenuRequest;
import com.ray.pomin.menu.domain.AdditionalDetails;
import com.ray.pomin.menu.domain.Menu;
import com.ray.pomin.menu.domain.MenuInfo;
import com.ray.pomin.menu.domain.MenuOption;
import com.ray.pomin.menu.domain.PrimaryDetails;
import com.ray.pomin.menu.service.MenuService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.JsonFieldType.BOOLEAN;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MenuController.class)
class MenuControllerTest extends ControllerUnit {

    @MockBean
    private MenuService menuService;

    @Test
    void successSaveMenu() throws Exception {
        // given
        SaveMenuRequest request = new SaveMenuRequest("name", "1000", "imageUrl",
                "best_seller", "description",
                false, 1L);
        doNothing().when(menuService).save(any(Menu.class));

        // when
        ResultActions action = mvc.perform(post("/menus")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(request))
        );

        // then
        action.andExpect(status().isOk())
                .andDo(document("menu/save",
                                requestFields(
                                        fieldWithPath("name").type(STRING).description("메뉴 이름"),
                                        fieldWithPath("price").type(STRING).description("메뉴 가격"),
                                        fieldWithPath("imageUrl").type(STRING).description("메뉴 이미지 저장 링크"),
                                        fieldWithPath("menuOptionName").type(STRING).description("메뉴 옵션 라벨 이름 (best_seller, popular_item)"),
                                        fieldWithPath("description").type(STRING).description("메뉴 설명"),
                                        fieldWithPath("verifyAge").type(BOOLEAN).description("19세 이상 구매 가능 여부"),
                                        fieldWithPath("storeId").type(NUMBER).description("저장 될 가게 아이디")
                                )
                        )
                );
    }


}
