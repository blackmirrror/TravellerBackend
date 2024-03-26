package ru.blackmirrror.traveller.models;

import javax.persistence.*;

@Entity
@Table(name = "subscribes", indexes = {
        @Index(name = "idx_user_id", columnList = "user_id")
})
public class Subscribe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "subscribe_id", nullable = false)
    private User subscribe;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getSubscribe() {
        return subscribe;
    }

    public void setSubscribe(User subscribe) {
        this.subscribe = subscribe;
    }
}
