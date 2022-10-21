package com.tgc.Sarafan.domain;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Table(name = "comment")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Comment {

    @Id
    @JsonView(Views.IdName.class)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "text")
    @JsonView(Views.IdName.class)
    @NotEmpty(message = "Message text should not be empty!")
    @Size(min = 1, max = 20, message = "Message text size should be between 1 and 20 characters!")
    private String text;

    @ManyToOne
    @JoinColumn(name = "message_id")
    @JsonView(Views.FullComment.class)
    private Message message;

    @ManyToOne
    @JoinColumn(name = "usr_id", nullable = false, updatable = false)
    @JsonView(Views.IdName.class)
    private User author;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Comment comment = (Comment) o;

        return id.equals(comment.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
