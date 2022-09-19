package com.tgc.Sarafan.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(Views.Id.class)
    private Long id;
    @JsonView(Views.IdName.class)
    private String text;

    @Column(updatable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonView(Views.FullMessage.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @ToString.Exclude
    private LocalDateTime creationDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonView(Views.FullMessage.class)
    @ToString.Exclude
    private User author;

    @OneToMany(mappedBy = "message", orphanRemoval = true)
    @JsonView(Views.FullMessage.class)
    @ToString.Exclude
    private List<Comment> comments;

    @JsonView(Views.FullMessage.class)
    @ToString.Exclude
    private String link;
    @JsonView(Views.FullMessage.class)
    @ToString.Exclude
    private String linkTitle;
    @JsonView(Views.FullMessage.class)
    @ToString.Exclude
    private String linkDescription;
    @JsonView(Views.FullMessage.class)
    @ToString.Exclude
    private String linkCover;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Message message = (Message) o;

        return id.equals(message.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}