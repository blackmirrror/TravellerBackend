package ru.blackmirrror.traveller.models;

import javax.persistence.*;

@Entity
@Table(name = "chats")
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user1;

    @ManyToOne
    private User user2;

    @Column(name = "last_message_text")
    private String lastMessageText;

    @Column(name = "last_message_time")
    private Long lastMessageTime;

    @Column(name = "unread_count_user_1")
    private Integer unreadCountUser1;

    @Column(name = "unread_count_user_2")
    private Integer unreadCountUser2;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser1() {
        return user1;
    }

    public void setUser1(User user1) {
        this.user1 = user1;
    }

    public User getUser2() {
        return user2;
    }

    public void setUser2(User user2) {
        this.user2 = user2;
    }

    public String getLastMessageText() {
        return lastMessageText;
    }

    public void setLastMessageText(String lastMessageText) {
        this.lastMessageText = lastMessageText;
    }

    public Long getLastMessageTime() {
        return lastMessageTime;
    }

    public void setLastMessageTime(Long lastMessageTime) {
        this.lastMessageTime = lastMessageTime;
    }

    public Integer getUnreadCountUser1() {
        return unreadCountUser1;
    }

    public void setUnreadCountUser1(Integer unreadCountUser1) {
        this.unreadCountUser1 = unreadCountUser1;
    }

    public Integer getUnreadCountUser2() {
        return unreadCountUser2;
    }

    public void setUnreadCountUser2(Integer unreadCountUser2) {
        this.unreadCountUser2 = unreadCountUser2;
    }
}

//@Entity
//@Table(name = "chats")
//public class Chat {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Column(name = "last_message")
//    private String lastMessage;
//
//    @Column(name = "last_message_time")
//    private Long lastMessageTime;
//
//    public Chat() {
//    }
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public List<User> getUsers() {
//        return users;
//    }
//
//    public void setUsers(List<User> users) {
//        this.users = users;
//    }
//
//    public String getLastMessage() {
//        return lastMessage;
//    }
//
//    public void setLastMessage(String lastMessage) {
//        this.lastMessage = lastMessage;
//    }
//
//    public Long getLastMessageTime() {
//        return lastMessageTime;
//    }
//
//    public void setLastMessageTime(Long lastMessageTime) {
//        this.lastMessageTime = lastMessageTime;
//    }
//
//    public String getImageUrl() {
//        return imageUrl;
//    }
//
//    public void setImageUrl(String chatImageUrl) {
//        this.imageUrl = chatImageUrl;
//    }
//}

