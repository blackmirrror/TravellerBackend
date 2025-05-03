package ru.blackmirrror.traveller.repositories;

import org.springframework.data.jpa.repository.Query;
import ru.blackmirrror.traveller.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    User findByEmail(String email);
    User findByPhone(String phone);

    List<User> findByUsernameContaining(String username);
    List<User> findByEmailContaining(String email);
    List<User> findByPhoneContaining(String phone);

    @Query("SELECT u FROM User u WHERE " +
            "u.email LIKE %:searchTerm% OR " +
            "u.phone LIKE %:searchTerm% OR " +
            "u.username LIKE %:searchTerm%")
    List<User> searchUsers(String searchTerm);
}

