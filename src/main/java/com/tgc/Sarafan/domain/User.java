package com.tgc.Sarafan.domain;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Getter;
import lombok.Setter;

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

    @Id
    @JsonView(Views.IdName.class)
    private String id;
    @JsonView(Views.IdName.class)
    private String name;
    @JsonView(Views.IdName.class)
    private String userpic;
    private String email;
    @JsonView(Views.FullProfile.class)
    private String gender;
    @JsonView(Views.FullProfile.class)
    private String locale;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonView(Views.FullProfile.class)
    private LocalDateTime lastVisit;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_subscriptions",
            joinColumns = @JoinColumn(name = "subscriber_id"),
            inverseJoinColumns = @JoinColumn(name = "channel_id")
    )
    @JsonView(Views.FullProfile.class)
    @JsonIdentityReference
    @JsonIdentityInfo(
            property = "id",
            generator = ObjectIdGenerators.PropertyGenerator.class
    )
    private Set<User> subscriptions = new HashSet<>();

    @ManyToMany(mappedBy = "subscriptions", fetch = FetchType.EAGER)
    @JsonView(Views.FullProfile.class)
    @JsonIdentityReference
    @JsonIdentityInfo(
            property = "id",
            generator = ObjectIdGenerators.PropertyGenerator.class
    )
    private Set<User> subscribers = new HashSet<>();

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
