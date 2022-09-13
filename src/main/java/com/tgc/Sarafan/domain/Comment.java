package com.tgc.Sarafan.domain;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table
@Data
@EqualsAndHashCode(of = {"id"})
public class Comment implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(Views.IdName.class)
    private long id;

    @JsonView(Views.IdName.class)
    private String text;

    @ManyToOne
    @JoinColumn(name = "message_id")
    @JsonView(Views.FullComment.class)
    private Message message;

    @ManyToOne
    @JoinColumn(name = "usr_id", nullable = false, updatable = false)
    @JsonView(Views.IdName.class)
    private User user;
}
