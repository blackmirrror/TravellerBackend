package ru.blackmirrror.traveller.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.blackmirrror.traveller.models.Subscribe;
import ru.blackmirrror.traveller.repositories.SubscribeRepository;

import java.util.List;

@Service
public class SubscribeService {
    @Autowired
    private SubscribeRepository subscribeRepository;

    public List<Subscribe> getAllSubscribesByUserId(Long userId) {
        return subscribeRepository.findAllByUserId(userId);
    }

    public Subscribe createSubscribe(Subscribe subscribe) {
        return subscribeRepository.save(subscribe);
    }

    public void deleteSubscribe(Long id) {
        subscribeRepository.deleteById(id);
    }
}
