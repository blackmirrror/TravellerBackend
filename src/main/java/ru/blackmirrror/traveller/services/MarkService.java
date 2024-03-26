package ru.blackmirrror.traveller.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.blackmirrror.traveller.models.Favorite;
import ru.blackmirrror.traveller.models.Mark;
import ru.blackmirrror.traveller.repositories.FavoriteRepository;
import ru.blackmirrror.traveller.repositories.MarkRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class MarkService {

    @Autowired
    private MarkRepository markRepository;

    public List<Mark> getAllMarks() {
        return markRepository.findAll();
    }

    public Mark getMarkById(Long id) {
        return markRepository.findById(id).orElse(null);
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
}