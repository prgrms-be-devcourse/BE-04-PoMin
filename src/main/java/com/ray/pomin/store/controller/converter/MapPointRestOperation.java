package com.ray.pomin.store.controller.converter;

import com.ray.pomin.store.controller.dto.AddressPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

import static java.net.URLEncoder.encode;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Component
@RequiredArgsConstructor
public class MapPointRestOperation {

    private final OAuth2ClientProperties oAuth2ClientProperties;

    public AddressPoint getResponse(String address) {
        AddressPoint response = operateRestRequest(address);
        validateResponse(response);

        return response;
    }

    private AddressPoint operateRestRequest(String address) {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<Object> entity = getHttpEntity();
        String url = getRequestURL(address);

        return restTemplate.exchange(URI.create(url), HttpMethod.GET, entity, AddressPoint.class)
                .getBody();
    }

    private HttpEntity<Object> getHttpEntity() {
        HttpHeaders headers = new HttpHeaders();
        OAuth2ClientProperties.Registration kakao = oAuth2ClientProperties.getRegistration().get("kakao");
        headers.set(AUTHORIZATION, "KakaoAK " + kakao.getClientId());

        return new HttpEntity<>(headers);
    }

    private String getRequestURL(String address) {
        return "https://dapi.kakao.com/v2/local/search/address.json?query=" + encode(address, UTF_8);
    }

    private void validateResponse(AddressPoint response) {
        if (response == null) {
            throw new IllegalArgumentException("주소를 잘못 입력했습니다");
        }
    }

}
