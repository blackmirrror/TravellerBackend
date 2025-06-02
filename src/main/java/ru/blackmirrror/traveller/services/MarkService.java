package ru.blackmirrror.traveller.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.blackmirrror.traveller.controller.dto.MarkDto;
import ru.blackmirrror.traveller.models.Mark;
import ru.blackmirrror.traveller.models.MarkCategory;
import ru.blackmirrror.traveller.models.MarkReview;
import ru.blackmirrror.traveller.repositories.MarkLikeRepository;
import ru.blackmirrror.traveller.repositories.MarkRepository;
import ru.blackmirrror.traveller.repositories.MarkReviewRepository;
import ru.blackmirrror.traveller.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class MarkService {

    @Autowired
    private MarkRepository markRepository;
    @Autowired
    private MarkReviewRepository markReviewRepository;
    @Autowired
    private MarkLikeRepository markLikeRepository;
    @Autowired
    private UserRepository userRepository;

    public List<Mark> getAllMarks() {
        return markRepository.findAll();
    }

    public Mark getMarkById(Long id) {
        return markRepository.findById(id).orElse(null);
    }

    public MarkDto getMarkDtoById(Long markId, Long userId) {
        Mark mark = markRepository.findById(markId).orElse(null);
        List<MarkReview> mrs = markReviewRepository.findTop2ByMarkIdOrderByDataCreateDesc(markId);
        boolean reviewed = markReviewRepository.existsByUserIdAndMarkIdAndRatingIsNotNull(userId, markId);
        boolean liked = markLikeRepository.existsByUserAndMark(
                userRepository.getById(userId),
                mark
        );
        MarkDto dto = new MarkDto();
        dto.setMark(mark);
        dto.setMrs(mrs);
        dto.setWasLiked(liked);
        dto.setWasReviewed(reviewed);
        return dto;
    }

    public Mark createMark(Mark mark) {
        return markRepository.save(mark);
    }

    public Mark updateMark(Long id, Mark mark) {
        if (markRepository.existsById(id)) {
            mark.setId(id);
            return markRepository.save(mark);
        }
        return null;
    }

    public void deleteMark(Long id) {
        markRepository.deleteById(id);
    }

    public List<Mark> getFilteredMarks(Double minRating, List<MarkCategory> categories, Double userLat, Double userLon, Double maxDistanceKm) {
        List<Mark> filtered = markRepository.findByMinRatingAndCategories(minRating).orElse(null);

        if (filtered == null) {
            return new ArrayList<Mark>().stream().toList();
        }

        if (categories != null && !categories.isEmpty()) {
            filtered = filtered.stream()
                    .filter(mark -> {
                        Set<MarkCategory> markCategories = mark.getCategories();
                        return markCategories != null &&
                                markCategories.size() == categories.size() &&
                                markCategories.containsAll(categories);
                    })
                    .toList();
        }

        if (userLat != null && userLon != null && maxDistanceKm != null) {
            filtered = filtered.stream()
                    .filter(mark -> {
                        Double markLat = mark.getLatitude();
                        Double markLon = mark.getLongitude();
                        return markLat != null && markLon != null &&
                                haversine(markLat, markLon, userLat, userLon) <= maxDistanceKm;
                    })
                    .toList();
        }

        return filtered;
    }

    private double haversine(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }

}