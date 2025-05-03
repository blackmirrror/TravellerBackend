package ru.blackmirrror.traveller.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.blackmirrror.traveller.models.Chat;
import ru.blackmirrror.traveller.models.User;

import java.util.List;
import java.util.Optional;

public interface ChatRepository extends JpaRepository<Chat, Long> {
    @Query("SELECT DISTINCT c FROM Chat c JOIN c.users u " +
            "WHERE LOWER(u.username) LIKE LOWER(CONCAT('%', :query, '%')) " +
            "OR LOWER(u.email) LIKE LOWER(CONCAT('%', :query, '%')) " +
            "OR u.phone LIKE CONCAT('%', :query, '%')")
    List<Chat> searchChats(@Param("query") String query);

    List<Chat> findByUsers(User currentUser);

    @Query("SELECT c FROM Chat c JOIN FETCH c.users WHERE c.id = :chatId")
    Optional<Chat> findByIdWithUsers(@Param("chatId") Long chatId);
}

