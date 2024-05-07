package ru.blackmirrror.traveller.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.blackmirrror.traveller.models.Subscribe;
import ru.blackmirrror.traveller.models.User;
import ru.blackmirrror.traveller.services.SubscribeService;
import ru.blackmirrror.traveller.services.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/subscribes")
public class SubscribeController {

    @Autowired
    private SubscribeService subscribeService;
    @Autowired UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<List<Subscribe>> getAllSubscribesByUserId(@PathVariable Long id) {
        List<Subscribe> subscribes = subscribeService.getAllSubscribesByUserId(id);
        if (!subscribes.isEmpty()) {
            return ResponseEntity.ok(subscribes);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @PostMapping()
    public ResponseEntity<Subscribe> createSubscribe(@RequestBody Subscribe subscribe) {
        if (Objects.equals(subscribe.getUser().getId(), subscribe.getSubscribe().getId()))
            return ResponseEntity.status(HttpStatus.CONFLICT).build();

        Subscribe createdSubscribe = subscribeService.createSubscribe(subscribe);
        createdSubscribe.setUser(userService.getUserById(createdSubscribe.getUser().getId()));
        createdSubscribe.setSubscribe(userService.getUserById(createdSubscribe.getSubscribe().getId()));
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSubscribe);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubscribe(@PathVariable Long id) {
        subscribeService.deleteSubscribe(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}

