package com.ray.pomin.store.domain;

import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

import static com.ray.pomin.global.util.Validator.validate;
import static java.util.Objects.isNull;
import static lombok.AccessLevel.PROTECTED;

@Getter
@Embeddable
@EqualsAndHashCode
@NoArgsConstructor(access = PROTECTED)
public class StoreTime {

    private boolean isOpen;

    private LocalTime open;

    private LocalTime close;

    public StoreTime(boolean isOpen, LocalTime open, LocalTime close) {
        validate(!isNull(open), "오픈 시간을 정해주세요");
        validate(!isNull(close), "폐점 시간을 정해주세요");

        this.isOpen = isOpen;
        this.open = open;
        this.close = close;
    }

}
