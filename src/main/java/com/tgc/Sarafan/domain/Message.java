package com.tgc.Sarafan.domain;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table
@ToString
@EqualsAndHashCode(of = {"id"})
@Getter
@Setter
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id"
)
public class Message implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @JsonView(Views.IdName.class)
    private Long id;

    @Column(name = "text")
    @JsonView(Views.IdName.class)
    private String text;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonView(Views.FullMessage.class)
    private User author;

    @OneToMany(mappedBy = "message", orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonView(Views.FullMessage.class)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private List<Comment> comments;

    @Column(updatable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonView(Views.FullMessage.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime creationDate;

    @JsonView(Views.FullMessage.class)
    private String link;
    @JsonView(Views.FullMessage.class)
    private String linkTitle;
    @JsonView(Views.FullMessage.class)
    private String linkDescription;
    @JsonView(Views.FullMessage.class)
    private String linkCover;
}
