package ru.blackmirrror.traveller.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.blackmirrror.traveller.models.Chat;
import ru.blackmirrror.traveller.models.User;

import java.util.List;

public interface ChatRepository extends JpaRepository<Chat, Long> {

    @Query("""
                SELECT DISTINCT c FROM Chat c
                WHERE LOWER(c.user1.username) LIKE LOWER(CONCAT('%', :query, '%'))
                   OR LOWER(c.user1.email) LIKE LOWER(CONCAT('%', :query, '%'))
                   OR c.user1.phone LIKE CONCAT('%', :query, '%')
                   OR LOWER(c.user2.username) LIKE LOWER(CONCAT('%', :query, '%'))
                   OR LOWER(c.user2.email) LIKE LOWER(CONCAT('%', :query, '%'))
                   OR c.user2.phone LIKE CONCAT('%', :query, '%')
            """)
    List<Chat> searchChats(@Param("query") String query);


    @Query("SELECT c FROM Chat c WHERE c.user1 = :user OR c.user2 = :user")
    List<Chat> findChatsByUser(@Param("user") User user);

    @Query("SELECT c FROM Chat c LEFT JOIN FETCH c.user1 LEFT JOIN FETCH c.user2 WHERE c.id = :chatId")
    Chat findByIdWithUsers(@Param("chatId") Long chatId);

}

