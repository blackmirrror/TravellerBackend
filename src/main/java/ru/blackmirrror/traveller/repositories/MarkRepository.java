package ru.blackmirrror.traveller.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.blackmirrror.traveller.models.Mark;

public interface MarkRepository extends JpaRepository<Mark, Long> {

}
