package ru.blackmirrror.traveller.services;

import ru.blackmirrror.traveller.models.User;
import ru.blackmirrror.traveller.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User getUserByPhone(String phone) {
        return userRepository.findByPhone(phone).orElse(null);
    }

    public User registerUser(User user) {
        User result = userRepository.findByPhone(user.getPhone()).orElse(null);
        if (result != null)
            return result;
        return userRepository.save(user);
    }

    public User updateUser(Long id, User user) {
        if (userRepository.existsById(id)) {
            user.setId(id);
            return userRepository.save(user);
        }
        return null;
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public List<User> getUsersBySearch(String query) {
        List<User> users = userRepository.searchUsers(query);
        Set<User> set = new HashSet<>(users);
        return set.stream().toList();
    }

    public void onlineUser(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            user.setOnline(true);
            updateUser(id, user);
        }
    }

    public void offlineUser(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            user.setOnline(false);
            updateUser(id, user);
        }
    }
}

