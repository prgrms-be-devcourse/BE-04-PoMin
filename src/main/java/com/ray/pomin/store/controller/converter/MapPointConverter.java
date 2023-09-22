package com.ray.pomin.store.controller.converter;

import com.ray.pomin.common.domain.Point;
import com.ray.pomin.store.controller.dto.AddressPoint;
import com.ray.pomin.store.controller.dto.AddressPoint.Documents;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MapPointConverter {

    private final MapPointRestOperation operation;

    public Point getPoint(String address) {
        AddressPoint response = operation.getResponse(address);

        return convertPoint(response);
    }


    private Point convertPoint(AddressPoint response) {
        Documents documents = response.documents().get(0);

        String latitude = documents.y();
        String longitude = documents.x();

        return new Point(Double.parseDouble(latitude), Double.parseDouble(longitude));
    }

}
