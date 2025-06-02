package ru.blackmirrror.traveller.models;

import javax.persistence.*;

@Entity
@Table(name = "mark_reviews")
public class MarkReview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "rating")
    private Integer rating;

    @Column(name = "comment")
    private String comment;

    @Column(name = "date_create")
    private Long dataCreate;

    @ManyToOne
    private User user;

    @ManyToOne
    private Mark mark;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Mark getMark() {
        return mark;
    }

    public void setMark(Mark mark) {
        this.mark = mark;
    }

    public Long getDataCreate() {
        return dataCreate;
    }

    public void setDataCreate(Long dataCreate) {
        this.dataCreate = dataCreate;
    }
}

