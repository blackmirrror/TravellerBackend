package ru.blackmirrror.traveller.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.blackmirrror.traveller.models.Favorite;

import java.util.List;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    List<Favorite> findAllByUserId(Long userId);
    List<Favorite> findAllByMarkId(Long markId);
    Favorite findByUserIdAndMarkId(Long userId, Long markId);
}
