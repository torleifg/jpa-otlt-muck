package com.github.torleifg.otlt.work;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkRepostitory extends JpaRepository<Work, Long> {
}