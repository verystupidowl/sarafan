package com.tgc.Sarafan.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "message")
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @JsonView(Views.Id.class)
    private Long id;

    @Column(name = "text")
    @JsonView(Views.IdName.class)
    @NotEmpty(message = "Message text should not be empty!")
    @Size(min = 1, max = 50, message = "Message text size should be between 1 and 50 characters!")
    private String text;

    @Column(name = "edited")
    @JsonView(Views.FullMessage.class)
    private Boolean edited;

    @JsonView(Views.FullMessage.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @ToString.Exclude
    @Column(name = "edited_date")
    private LocalDateTime editedDate;

    @Column(name = "creation_date", updatable = false)
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
    @Column(name = "link")
    private String link;

    @JsonView(Views.FullMessage.class)
    @ToString.Exclude
    @Column(name = "link_title")
    private String linkTitle;

    @JsonView(Views.FullMessage.class)
    @ToString.Exclude
    @Column(name = "link_description")
    private String linkDescription;

    @JsonView(Views.FullMessage.class)
    @ToString.Exclude
    @Column(name = "link_cover")
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