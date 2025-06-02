package ru.blackmirrror.traveller.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.blackmirrror.traveller.models.MarkReview;

import java.util.List;

public interface MarkReviewRepository extends JpaRepository<MarkReview, Long> {
    List<MarkReview> findTop2ByMarkIdOrderByDataCreateDesc(Long markId);

    // Все отзывы по конкретной метке по убыванию
    List<MarkReview> findAllByMarkIdOrderByDataCreateDesc(Long markId);

    boolean existsByUserIdAndMarkIdAndRatingIsNotNull(Long userId, Long markId);

}
