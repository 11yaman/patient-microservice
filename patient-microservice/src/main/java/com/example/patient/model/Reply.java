package com.example.patient.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Reply{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;
    private LocalDateTime dateTime;
    @ManyToOne
    private User sender;

    public Reply() {
    }

    public Reply(String content, User sender) {
        this.content = content;
        this.dateTime = LocalDateTime.now();
        this.sender = sender;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public String toString() {
        return "Reply{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", dateTime=" + dateTime +
                ", sender=" + sender +
                '}';
    }
}
