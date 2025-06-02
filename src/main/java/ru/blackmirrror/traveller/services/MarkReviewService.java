package ru.blackmirrror.traveller.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.blackmirrror.traveller.models.Mark;
import ru.blackmirrror.traveller.models.MarkLike;
import ru.blackmirrror.traveller.models.MarkReview;
import ru.blackmirrror.traveller.models.User;
import ru.blackmirrror.traveller.repositories.MarkRepository;
import ru.blackmirrror.traveller.repositories.MarkReviewRepository;
import ru.blackmirrror.traveller.repositories.UserRepository;

import java.util.List;

@Service
public class MarkReviewService {

    @Autowired
    private MarkReviewRepository markReviewRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MarkRepository markRepository;

    public MarkReview reviewMark(Long userId, Long markId, MarkReview markReview) {
        User user = userRepository.findById(userId).orElse(null);
        Mark mark = markRepository.findById(markId).orElse(null);
        if (mark != null && user != null) {
            if (markReview.getRating() != null) {
                mark.setAverageRating((mark.getAverageRating() + markReview.getRating()) / 2);
            }
            markReview.setUser(user);
            markReview.setMark(mark);
            markRepository.save(mark);
            markReviewRepository.save(markReview);
            return markReview;
        }
        return null;
    }

    public List<MarkReview> reviewsByMarkId(Long markId) {
        Mark mark = markRepository.findById(markId).orElse(null);

        if (mark != null) {
            return markReviewRepository.findAllByMarkIdOrderByDataCreateDesc(markId);
        }
        return null;
    }
}
