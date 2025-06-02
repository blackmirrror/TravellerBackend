package ru.blackmirrror.traveller.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.blackmirrror.traveller.models.Mark;
import ru.blackmirrror.traveller.models.MarkCategory;

import java.util.List;
import java.util.Optional;

public interface MarkRepository extends JpaRepository<Mark, Long> {
    @Query("""
                SELECT DISTINCT m FROM Mark m
                WHERE (:minRating IS NULL OR m.averageRating >= :minRating)
            """)
    Optional<List<Mark>> findByMinRatingAndCategories(
            @Param("minRating") Double minRating
    );


}
