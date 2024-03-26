package ru.blackmirrror.traveller.models;

import javax.persistence.*;

@Entity
@Table(name = "favorite", indexes = {
        @Index(name = "idx_user_id", columnList = "user_id")
})
public class Favorite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "mark_id", nullable = false)
    private Mark mark;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Mark getMark() {
        return mark;
    }

    public void setMark(Mark mark) {
        this.mark = mark;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
