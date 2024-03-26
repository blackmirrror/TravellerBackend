package ru.blackmirrror.traveller.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.blackmirrror.traveller.models.Subscribe;

import java.util.List;

public interface SubscribeRepository extends JpaRepository<Subscribe, Long> {
    List<Subscribe> findAllByUserId(Long userId);
}
