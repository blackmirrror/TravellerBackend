package ru.blackmirrror.traveller.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.blackmirrror.traveller.controller.dto.MarkDto;
import ru.blackmirrror.traveller.controller.dto.MarkLatLngDto;
import ru.blackmirrror.traveller.models.Mark;
import ru.blackmirrror.traveller.models.MarkCategory;
import ru.blackmirrror.traveller.models.MarkReview;
import ru.blackmirrror.traveller.models.User;
import ru.blackmirrror.traveller.services.MarkLikeService;
import ru.blackmirrror.traveller.services.MarkReviewService;
import ru.blackmirrror.traveller.services.MarkService;
import ru.blackmirrror.traveller.services.UserService;

import java.util.List;

@RestController
@RequestMapping("/marks")
public class MarkController {

    @Autowired
    private MarkService markService;
    @Autowired
    private UserService userService;
    @Autowired
    private MarkLikeService markLikeService;
    @Autowired
    private MarkReviewService markReviewService;

//    @GetMapping
//    public ResponseEntity<List<Mark>> getAllMarks(
//        @RequestParam(required = false) Double minRating,
//        @RequestParam(required = false) Double distance,
//        @RequestParam(required = false) Double lat,
//        @RequestParam(required = false) Double lon,
//        @RequestParam(required = false) List<MarkCategory> categories
//    ) {
//        List<Mark> marks = markService.getFilteredMarks(minRating, categories, lat, lon, distance);
//        if (!marks.isEmpty()) {
//            return ResponseEntity.ok(marks);
//        } else {
//            return ResponseEntity.noContent().build();
//        }
//    }

    @GetMapping
    public ResponseEntity<List<MarkLatLngDto>> getAllMarks(
            @RequestParam(required = false) Double minRating,
            @RequestParam(required = false) Double distance,
            @RequestParam(required = false) Double lat,
            @RequestParam(required = false) Double lon,
            @RequestParam(required = false) List<MarkCategory> categories
    ) {
        List<Mark> marks = markService.getFilteredMarks(minRating, categories, lat, lon, distance);
        if (!marks.isEmpty()) {
            return ResponseEntity.ok(marks.stream().map(mark -> {
                MarkLatLngDto dto = new MarkLatLngDto();
                dto.setId(mark.getId());
                dto.setLat(mark.getLatitude());
                dto.setLon(mark.getLongitude());
                return dto;
            }
            ).toList());
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("/{markId}")
    public ResponseEntity<MarkDto> getMark(@PathVariable Long markId, @RequestParam Long userId) {
        MarkDto mark = markService.getMarkDtoById(markId, userId);
        if (mark != null)
            return ResponseEntity.ok(mark);
        return ResponseEntity.notFound().build();
    }

    @PostMapping()
    public ResponseEntity<Mark> createMark(@RequestBody Mark mark) {
        mark.setAuthor(userService.getUserById(mark.getAuthor().getId()));
        User user = mark.getAuthor();
        user.setMarkCount(user.getMarkCount() + 1);
        userService.updateUser(user.getId(), user);
        Mark createdMark = markService.createMark(mark);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdMark);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MarkDto> updateMark(@PathVariable Long id, @RequestBody Mark mark) {
        Mark updatedMark = markService.updateMark(id, mark);
        MarkDto result = markService.getMarkDtoById(id, mark.getAuthor().getId());
        if (updatedMark != null && result != null) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMark(@PathVariable Long id) {
        Mark mark = markService.getMarkById(id);
        User user = mark.getAuthor();
        user.setMarkCount(user.getMarkCount() - 1);
        userService.updateUser(user.getId(), user);
        markService.deleteMark(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/{markId}/like")
    public ResponseEntity<Void> likeMark(@PathVariable Long markId, @RequestParam Long userId) {
        if (markLikeService.likeMark(userId, markId)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
        }
    }

    @DeleteMapping("/{markId}/unlike")
    public ResponseEntity<Void> unlikeMark(@PathVariable Long markId, @RequestParam Long userId) {
        if (markLikeService.unlikeMark(userId, markId)) {
            return ResponseEntity.ok().build();
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
        }
    }

//    @GetMapping("/{markId}/like")
//    public ResponseEntity<Boolean> isMarkLiked(
//            @PathVariable Long markId,
//            @RequestParam Long userId
//    ) {
//        boolean liked = markLikeService.isMarkLikedByUser(userId, markId);
//        return ResponseEntity.ok(liked);
//    }

    @PostMapping("/{markId}/review")
    public ResponseEntity<MarkReview> reviewMark(@PathVariable Long markId, @RequestParam Long userId, @RequestBody MarkReview markReview) {
        MarkReview review = markReviewService.reviewMark(userId, markId, markReview);
        if (review != null) {
            return ResponseEntity.ok(review);
        }
        return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
    }

    @GetMapping("/{markId}/reviews")
    public ResponseEntity<List<MarkReview>> getMarkReviews(
            @PathVariable Long markId
    ) {
        List<MarkReview> reviews = markReviewService.reviewsByMarkId(markId);
        if (reviews != null)
            return ResponseEntity.ok(reviews);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}

