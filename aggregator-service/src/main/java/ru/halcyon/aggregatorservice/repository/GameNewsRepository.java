package ru.halcyon.aggregatorservice.repository;

import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.halcyon.aggregatorservice.model.GameNews;

import java.util.List;

@Repository
public interface GameNewsRepository extends JpaRepository<GameNews, Long> {
    boolean existsByTitle(String title);
    @NonNull Page<GameNews> findAll(@NonNull Pageable pageable);
    List<GameNews> findGameNewsByTitleContainingIgnoreCase(String title);
}
