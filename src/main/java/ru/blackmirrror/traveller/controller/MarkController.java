package ru.blackmirrror.traveller.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.blackmirrror.traveller.models.Favorite;
import ru.blackmirrror.traveller.models.Mark;
import ru.blackmirrror.traveller.models.User;
import ru.blackmirrror.traveller.services.FavoriteService;
import ru.blackmirrror.traveller.services.MarkService;
import ru.blackmirrror.traveller.services.UserService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/marks")
public class MarkController {

    @Autowired
    private FavoriteService favoriteService;
    @Autowired
    private MarkService markService;
    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<Mark>> getAllMarks() {
        List<Mark> marks = markService.getAllMarks();
        if (!marks.isEmpty()) {
            return ResponseEntity.ok(marks);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @PostMapping()
    public ResponseEntity<Mark> createMark(@RequestBody Mark mark) {
        mark.setUser(userService.getUserById(mark.getUser().getId()));
        Mark createdMark = markService.createMark(mark);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdMark);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Mark> updateMark(@PathVariable Long id, @RequestBody Mark mark) {
        Mark updatedMark = markService.updateMark(id, mark);
        if (updatedMark != null) {
            List<Favorite> favorites = favoriteService.getAllFavoriteByMarkId(updatedMark.getId());
            for (Favorite f: favorites) {
                f.setMark(updatedMark);
            }
            return ResponseEntity.ok(updatedMark);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMark(@PathVariable Long id) {
        List<Favorite> favorites = favoriteService.getAllFavoriteByMarkId(id);
        favoriteService.deleteAll(favorites);
        markService.deleteMark(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/favorite/{id}")
    public ResponseEntity<List<Mark>> getAllFavoriteMarksByUserId(@PathVariable Long id) {
        List<Favorite> favorites = favoriteService.getAllFavoriteByUserId(id);
        List<Mark> marks = new ArrayList<>();
        for (Favorite f: favorites) {
            marks.add(f.getMark());
        }
        if (!marks.isEmpty()) {
            return ResponseEntity.ok(marks);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @PostMapping("/favorite")
    public ResponseEntity<Favorite> createFavorite(@RequestBody Favorite favorite) {
        Mark mark = markService.getMarkById(favorite.getMark().getId());
        mark.setLikes(mark.getLikes() + 1);
        markService.updateMark(mark.getId(), mark);
        favorite.setMark(mark);

        favorite.setUser(userService.getUserById(favorite.getUser().getId()));

        Favorite createdFavorite = favoriteService.createFavorite(favorite);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdFavorite);
    }

    @DeleteMapping("/favorite/{id}")
    public ResponseEntity<Void> deleteFavorite(@PathVariable Favorite _favorite) {
        Favorite favorite = favoriteService.getFavoriteByUserIdAndMarkId(_favorite.getUser().getId(), _favorite.getMark().getId());
        favoriteService.deleteFavorite(favorite.getId());
        Mark mark = favorite.getMark();
        mark.setLikes(mark.getLikes() - 1);
        markService.updateMark(mark.getId(), mark);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}

