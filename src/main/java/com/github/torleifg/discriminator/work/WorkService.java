package com.github.torleifg.discriminator.work;

import com.github.torleifg.discriminator.codelist.IntellectualLevel;
import com.github.torleifg.discriminator.codelist.LiteratureType;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class WorkService {
    private final WorkRepostitory repostitory;

    public WorkService(WorkRepostitory repostitory) {
        this.repostitory = repostitory;
    }

    public Work createWork(Set<Integer> literatureTypeCodes, Set<Integer> intellectualLevelCodes) {
        final var work = new Work();

        literatureTypeCodes.stream()
                .map(LiteratureType::of)
                .forEach(work::addLiteratureType);

        intellectualLevelCodes.stream()
                .map(IntellectualLevel::of)
                .forEach(work::addIntellectualLevel);

        return repostitory.save(work);
    }

    public Optional<Work> findWork(UUID id) {
        return repostitory.findById(id);
    }
}