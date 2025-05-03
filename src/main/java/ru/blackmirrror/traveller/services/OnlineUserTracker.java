package ru.blackmirrror.traveller.services;

import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class OnlineUserTracker {

    private final Set<Long> onlineUsers = ConcurrentHashMap.newKeySet();

    public void setOnline(Long userId) {
        onlineUsers.add(userId);
    }

    public void setOffline(Long userId) {
        onlineUsers.remove(userId);
    }

    public boolean isOnline(Long userId) {
        return onlineUsers.contains(userId);
    }

    public Set<Long> getAllOnlineUsers() {
        return Collections.unmodifiableSet(onlineUsers);
    }
}

