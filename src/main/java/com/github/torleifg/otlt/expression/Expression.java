package com.github.torleifg.otlt.expression;

import com.github.torleifg.otlt.codelist.onix.ProductContentType;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Expression {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany
    @JoinTable(name = "expression_product_content_type_xref")
    private Set<ProductContentType> productContentType = new HashSet<>();

    public Long getId() {
        return id;
    }

    public Set<ProductContentType> getProductContentType() {
        return productContentType;
    }

    public void addProductContentType(ProductContentType productContentType) {
        this.productContentType.add(productContentType);
        productContentType.getExpression().add(this);
    }

    public void removeProductContentType(ProductContentType productContentType) {
        this.productContentType.remove(productContentType);
        productContentType.getExpression().remove(this);
    }
}