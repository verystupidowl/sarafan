package com.tgc.Sarafan.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.tgc.Sarafan.dto.NotificationType;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "usr")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {

    @Id
    @Column(nullable = false)
    @JsonView(Views.IdName.class)
    private String id;

    @JsonView(Views.IdName.class)
    private String name;

    @JsonView(Views.IdName.class)
    private String userpic;

    @Email
    private String email;


    @JsonView(Views.FullProfile.class)
    private String gender;

    @JsonView(Views.FullProfile.class)
    private String locale;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonView(Views.FullProfile.class)
    @ToString.Exclude
    private LocalDateTime lastVisit;


    @JsonView(Views.FullProfile.class)
    @OneToMany(
            mappedBy = "subscriber",
            orphanRemoval = true,
            fetch = FetchType.EAGER
    )
    @ToString.Exclude
    private Set<UserSubscription> subscriptions = new HashSet<>();

    @JsonView(Views.FullProfile.class)
    @OneToMany(
            mappedBy = "channel",
            orphanRemoval = true,
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER
    )
    @ToString.Exclude
    private Set<UserSubscription> subscribers = new HashSet<>();

    @ElementCollection(targetClass = NotificationType.class, fetch = FetchType.LAZY)
    @CollectionTable(name = "user_notification_types", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    @JoinTable(
            name = "user_notification_types",
            joinColumns = @JoinColumn(name = "user_id")
    )
    @ToString.Exclude
    private Set<NotificationType> notificationTypes;

    public User(String id) {
        this.id = id;
    }

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
