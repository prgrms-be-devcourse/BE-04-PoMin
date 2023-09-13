package com.ray.pomin.store.domain;

import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

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
        this.isOpen = isOpen;
        this.open = open;
        this.close = close;
    }

}
