package com.tgc.Sarafan.domain;

import com.fasterxml.jackson.annotation.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = "user_subscription")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class UserSubscription implements Serializable {

    @Serial
    private static final long serialVersionUID = -4356278199989312047L;

    @EmbeddedId
    @JsonIgnore
    private UserSubscriptionId id;

    @MapsId("channelId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "channel_id", referencedColumnName = "id")
    @JsonView(Views.IdName.class)
    @JsonIdentityReference
    @NotFound(action = NotFoundAction.IGNORE)
    @JsonIdentityInfo(
            property = "id",
            generator = ObjectIdGenerators.PropertyGenerator.class
    )
    @ToString.Exclude
    private User channel;

    @MapsId("subscriberId")
    @ManyToOne(fetch = FetchType.LAZY)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "subscriber_id", referencedColumnName = "id")
    @JsonView(Views.IdName.class)
    @JsonIdentityReference
    @JsonIdentityInfo(
            property = "id",
            generator = ObjectIdGenerators.PropertyGenerator.class
    )
    @ToString.Exclude
    private User subscriber;

    private boolean active;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserSubscription that = (UserSubscription) o;

        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id == null ? 0 : id.hashCode();
    }
}
