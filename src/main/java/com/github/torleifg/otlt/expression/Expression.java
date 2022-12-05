package com.github.torleifg.otlt.expression;

import com.github.torleifg.otlt.codelist.onix.ProductContentType;

import javax.persistence.*;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Expression {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany
    @JoinTable(name = "expression_product_content_type_xref")
    private final Set<ProductContentType> productContentType = new HashSet<>();

    public Set<ProductContentType> getProductContentType() {
        return Collections.unmodifiableSet(this.productContentType);
    }

    public void addProductContentType(ProductContentType productContentType) {
        this.productContentType.add(productContentType);
    }

    public void removeProductContentType(ProductContentType productContentType) {
        this.productContentType.removeIf(type -> type.getId().equals(productContentType.getId()));
    }
}