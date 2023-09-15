package com.ray.pomin.menu.domain;

import com.ray.pomin.common.domain.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Entity
@NoArgsConstructor(access = PROTECTED)
@EqualsAndHashCode(of = "id", callSuper = false)
public class Menu extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "MENU_ID")
    private Long id;

    @Embedded
    private MenuInfo menuInfo;

    private boolean verifyAge;

    private Long storeId;

    public Menu(MenuInfo menuInfo, boolean verifyAge, Long storeId) {
        this.menuInfo = menuInfo;
        this.verifyAge = verifyAge;
        this.storeId = storeId;
    }

    public MenuInfo getMenuInfo() {
        return menuInfo;
    }

    public boolean isVerifyAge() {
        return verifyAge;
    }

}
