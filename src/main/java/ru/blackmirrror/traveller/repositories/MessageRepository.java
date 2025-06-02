package ru.blackmirrror.traveller.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.blackmirrror.traveller.models.Message;
import ru.blackmirrror.traveller.models.User;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

    Message findMessageById(Long id);

    List<Message> findByChatIdOrderByDateCreateAsc(Long chatId);

    @Query("""
                SELECT COUNT(m)
                FROM Message m
                WHERE m.chat.id = :chatId
                  AND m.read = false
                  AND m.sender <> :user
            """)
    Integer countUnreadMessages(@Param("chatId") Long chatId, @Param("user") User user);
}

