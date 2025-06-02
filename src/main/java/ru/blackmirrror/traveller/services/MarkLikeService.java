package ru.blackmirrror.traveller.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.blackmirrror.traveller.models.Mark;
import ru.blackmirrror.traveller.models.MarkLike;
import ru.blackmirrror.traveller.models.User;
import ru.blackmirrror.traveller.repositories.MarkLikeRepository;
import ru.blackmirrror.traveller.repositories.MarkRepository;
import ru.blackmirrror.traveller.repositories.UserRepository;

@Service
public class MarkLikeService {

    @Autowired
    private MarkLikeRepository markLikeRepository;
    @Autowired
    private  UserRepository userRepository;
    @Autowired
    private MarkRepository markRepository;

    public Boolean likeMark(Long userId, Long markId) {
        User user = userRepository.findById(userId).orElse(null);
        Mark mark = markRepository.findById(markId).orElse(null);
        if (mark != null && user != null && !markLikeRepository.existsByUserAndMark(user, mark)) {
            int markLikes = mark.getLikeCount() + 1;
            mark.setLikeCount(markLikes);
            markRepository.save(mark);
            MarkLike markLike = new MarkLike();
            markLike.setUser(user);
            markLike.setMark(mark);
            markLikeRepository.save(markLike);
            return true;
        }
        return false;
    }

    public Boolean unlikeMark(Long userId, Long markId) {
        User user = userRepository.findById(userId).orElse(null);
        Mark mark = markRepository.findById(markId).orElse(null);
        if (mark != null && user != null) {
            int markLikes = mark.getLikeCount() - 1;
            mark.setLikeCount(markLikes);
            markRepository.save(mark);
            markLikeRepository.deleteByUserAndMark(user, mark);
            return true;
        }
        return false;
    }

    public boolean isMarkLikedByUser(Long userId, Long markId) {
        User user = userRepository.findById(userId).orElse(null);
        Mark mark = markRepository.findById(markId).orElse(null);

        return markLikeRepository.existsByUserAndMark(user, mark);
    }
}

