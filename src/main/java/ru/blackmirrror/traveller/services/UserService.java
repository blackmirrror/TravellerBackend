package ru.blackmirrror.traveller.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import ru.blackmirrror.traveller.models.User;
import ru.blackmirrror.traveller.repositories.SubscribeRepository;
import ru.blackmirrror.traveller.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public User registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (userRepository.findByUsername(user.getUsername()) != null)
            return null;
        return userRepository.save(user);
    }

    public User loginUser(User loginUser) {
        User user = userRepository.findByUsername(loginUser.getUsername());
        if (user != null && passwordEncoder.matches(loginUser.getPassword(), user.getPassword()))
            return user;
        return null;
    }

    public User updateUser(Long id, User user) {
        if (userRepository.existsById(id)) {
            user.setId(id);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            return userRepository.save(user);
        }
        return null;
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}

