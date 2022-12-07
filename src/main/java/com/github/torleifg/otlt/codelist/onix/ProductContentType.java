package com.github.torleifg.otlt.codelist.onix;

import com.github.torleifg.otlt.expression.Expression;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.HashSet;
import java.util.Set;

@Entity
@DiscriminatorValue("81")
public class ProductContentType extends OnixCodelist {

    @ManyToMany(mappedBy = "productContentType")
    private final Set<Expression> expression = new HashSet<>();

    protected ProductContentType() {
    }

    private ProductContentType(int code) {
        super(81, code);
    }

    public static ProductContentType of(int code) {
        return new ProductContentType(code);
    }

    public Set<Expression> getExpression() {
        return expression;
    }
}