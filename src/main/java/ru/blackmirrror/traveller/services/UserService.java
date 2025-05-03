package ru.blackmirrror.traveller.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import ru.blackmirrror.traveller.models.User;
import ru.blackmirrror.traveller.repositories.SubscribeRepository;
import ru.blackmirrror.traveller.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SubscribeRepository subscribeRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User getUserByPhone(String phone) {
        return userRepository.findByPhone(phone);
    }

    public User registerUser(User user) {
        if (userRepository.findByPhone(user.getPhone()) != null)
            return loginUser(user);
        return userRepository.save(user);
    }

    public User loginUser(User loginUser) {
        return userRepository.findByPhone(loginUser.getPhone());
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
}

