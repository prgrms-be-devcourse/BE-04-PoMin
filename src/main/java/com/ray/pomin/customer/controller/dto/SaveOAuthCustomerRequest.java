package com.ray.pomin.customer.controller.dto;

import com.ray.pomin.global.auth.model.OAuthCustomerInfo;

public record SaveOAuthCustomerRequest(String nickname, String birthDate, String phoneNumber, Long oAuthId) {

    public SaveCustomerRequest convertToSaveCustomerRequest(OAuthCustomerInfo oAuthUserInfo) {
        return new SaveCustomerRequest(
                oAuthUserInfo.getEmail(), "Password1!", oAuthUserInfo.getName(), nickname,
                birthDate, phoneNumber, oAuthUserInfo.getProviderName()
        );
    }

}
