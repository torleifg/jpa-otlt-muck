package com.github.torleifg.discriminator.expression;

import com.github.torleifg.discriminator.codelist.LiteratureType;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class ExpressionService {
    private final ExpressionRepository repository;

    public ExpressionService(ExpressionRepository repository) {
        this.repository = repository;
    }

    public Expression createExpression(Set<Integer> literatureTypeCodes) {
        final var expression = new Expression();

        literatureTypeCodes.stream()
                .map(LiteratureType::of)
                .forEach(expression::addLiteratureType);

        return repository.save(expression);
    }

    public Optional<Expression> findExpression(UUID id) {
        return repository.findById(id);
    }
}