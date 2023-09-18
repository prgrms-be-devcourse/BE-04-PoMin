package com.ray.pomin.store.controller.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.List;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record AddressPoint(List<Documents> documents, Meta meta) {

    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public record Documents(RoadAddress roadAddress, Address address, String addressType,
                            String x, String y, String addressName) {
    }

    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public record RoadAddress(String x, String y, String zoneNo,
                              String buildingName, String subBuildingNo, String mainBuildingNo,
                              String undergroundYn, String roadName, String region_3depth_name,
                              String region_2depth_name, String region_1depth_name, String addressName) {
    }

    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public record Address(String y, String x, String subAddressNo,
                          String mainAddressNo, String mountainYn, String bCode,
                          String hCode, String region_3depth_h_name, String region_3depth_name,
                          String region_2depth_name, String region_1depth_name, String addressName) {
    }

    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public record Meta(boolean isEnd, int pageableCount, int totalCount) {
    }

}
