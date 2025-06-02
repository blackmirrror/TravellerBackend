package ru.blackmirrror.traveller.repositories;

import org.springframework.data.jpa.repository.Query;
import ru.blackmirrror.traveller.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByPhone(String phone);

    @Query("SELECT u FROM User u WHERE " +
            "u.phone LIKE %:searchTerm% OR " +
            "u.username LIKE %:searchTerm%")
    List<User> searchUsers(String searchTerm);
}

