package com.github.torleifg.otlt.codelist.onix;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("81")
public class ProductContentType extends OnixCodelist {

    protected ProductContentType() {
    }

    private ProductContentType(int code) {
        super(81, code);
    }

    public static ProductContentType of(int code) {
        return new ProductContentType(code);
    }
}