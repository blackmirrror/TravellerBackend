package ru.blackmirrror.traveller.models;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "marks")
public class Mark {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "latitude", nullable = false)
    private Double latitude;

    @Column(name = "longitude", nullable = false)
    private Double longitude;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "average_rating", nullable = false)
    private Double averageRating;

    @Column(name = "likeCount", nullable = false)
    private Integer likeCount;

    @Column(name = "date_change", nullable = false)
    private Long dateChange;

    @Column(name = "date_create", nullable = false)
    private Long dateCreate;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    private Set<MarkCategory> categories;

    @ManyToOne
    private User author;

//    @OneToMany(mappedBy = "mark", cascade = CascadeType.ALL)
//    private List<MarkReview> reviews;
//
//    @OneToMany(mappedBy = "mark", cascade = CascadeType.ALL)
//    private List<MarkLike> likes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Long getDateChanges() {
        return dateChange;
    }

    public void setDateChanges(Long dateChanges) {
        this.dateChange = dateChanges;
    }

    public Long getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(Long dateCreate) {
        this.dateCreate = dateCreate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getDateChange() {
        return dateChange;
    }

    public void setDateChange(Long dateChange) {
        this.dateChange = dateChange;
    }

    public Set<MarkCategory> getCategories() {
        return categories;
    }

    public void setCategories(Set<MarkCategory> categories) {
        this.categories = categories;
    }

    public Double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(Double averageRating) {
        this.averageRating = averageRating;
    }

//    public List<MarkLike> getLikes() {
//        return likes;
//    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

//    public List<MarkReview> getReviews() {
//        return reviews;
//    }
//
//    public void setReviews(List<MarkReview> reviews) {
//        this.reviews = reviews;
//    }

    public Integer getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(Integer likeCount) {
        this.likeCount = likeCount;
    }

//    public void setLikes(List<MarkLike> likes) {
//        this.likes = likes;
//    }
}
