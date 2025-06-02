package ru.blackmirrror.traveller.controller.dto;

import ru.blackmirrror.traveller.models.Mark;
import ru.blackmirrror.traveller.models.MarkReview;

import java.util.List;

public class MarkDto {

    private Mark mark;
    private Boolean wasReviewed;
    private Boolean wasLiked;
    private List<MarkReview> mrs;

    public Mark getMark() {
        return mark;
    }

    public void setMark(Mark mark) {
        this.mark = mark;
    }

    public Boolean getWasReviewed() {
        return wasReviewed;
    }

    public void setWasReviewed(Boolean wasReviewed) {
        this.wasReviewed = wasReviewed;
    }

    public Boolean getWasLiked() {
        return wasLiked;
    }

    public void setWasLiked(Boolean wasLiked) {
        this.wasLiked = wasLiked;
    }

    public List<MarkReview> getMrs() {
        return mrs;
    }

    public void setMrs(List<MarkReview> mrs) {
        this.mrs = mrs;
    }
}
