package ru.blackmirrror.traveller.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.blackmirrror.traveller.models.Mark;
import ru.blackmirrror.traveller.models.MarkLike;
import ru.blackmirrror.traveller.models.User;

import java.util.List;

public interface MarkLikeRepository extends JpaRepository<MarkLike, Long> {
    boolean existsByUserAndMark(User user, Mark mark);
    void deleteByUserAndMark(User user, Mark mark);
    List<MarkLike> findByMark(Mark mark);
    List<MarkLike> findByUser(User user);
}

