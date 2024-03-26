package ru.blackmirrror.traveller.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.blackmirrror.traveller.models.Favorite;
import ru.blackmirrror.traveller.repositories.FavoriteRepository;

import java.util.List;

@Service
public class FavoriteService {

    @Autowired
    private FavoriteRepository favoriteRepository;

    public List<Favorite> getAllFavoriteByUserId(Long userId) {
        return favoriteRepository.findAllByUserId(userId);
    }

    public List<Favorite> getAllFavoriteByMarkId(Long markId) {
        return favoriteRepository.findAllByMarkId(markId);
    }

    public Favorite getFavoriteById(Long id) {
        return favoriteRepository.findById(id).orElse(null);
    }

    public Favorite getFavoriteByUserIdAndMarkId(Long userId, Long markId) {
        return favoriteRepository.findByUserIdAndMarkId(userId, markId);
    }

    public Favorite createFavorite(Favorite favorite) {
        return favoriteRepository.save(favorite);
    }

    public void deleteFavorite(Long id) {
        favoriteRepository.deleteById(id);
    }

    public void deleteAll(List<Favorite> favorites) {
        for (Favorite f: favorites)
            favoriteRepository.delete(f);
    }
}
