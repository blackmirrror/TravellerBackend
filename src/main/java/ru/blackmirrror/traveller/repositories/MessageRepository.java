package ru.blackmirrror.traveller.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.blackmirrror.traveller.models.Message;
import ru.blackmirrror.traveller.models.User;

import java.util.List;
import java.util.Optional;

public interface MessageRepository extends JpaRepository<Message, Long> {

    @Query("SELECT m FROM Message m LEFT JOIN FETCH m.readBy WHERE m.id = :messageId")
    Optional<Message> findMessageWithReadBy(@Param("messageId") Long messageId);

    List<Message> findByChatIdOrderByTimestampAsc(Long chatId);

    @Query("SELECT COUNT(m) FROM Message m " +
            "WHERE m.chat.id = :chatId AND :user NOT MEMBER OF m.readBy")
    int countUnreadMessages(@Param("chatId") Long chatId, @Param("user") User user);
}

