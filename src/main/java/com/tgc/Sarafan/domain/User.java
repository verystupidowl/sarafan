package com.tgc.Sarafan.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "usr")
@Getter
@Setter
public class User implements Serializable {

    @ToString.Include
    @Id
    @Column(nullable = false)
    @JsonView(Views.IdName.class)
    private String id;

    @ToString.Include
    @JsonView(Views.IdName.class)
    private String name;
    @ToString.Include
    @JsonView(Views.IdName.class)
    private String userpic;
    @ToString.Include
    private String email;
    @ToString.Include
    @JsonView(Views.FullProfile.class)
    private String gender;
    @ToString.Include
    @JsonView(Views.FullProfile.class)
    private String locale;
    @ToString.Include
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonView(Views.FullProfile.class)
    private LocalDateTime lastVisit;


    @JsonView(Views.FullProfile.class)
    @OneToMany(
            mappedBy = "subscriber",
            orphanRemoval = true,
            fetch = FetchType.EAGER
    )
    private Set<UserSubscription> subscriptions = new HashSet<>();

    @JsonView(Views.FullProfile.class)
    @OneToMany(
            mappedBy = "channel",
            orphanRemoval = true,
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER
    )
    private Set<UserSubscription> subscribers = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return id.equals(user.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
