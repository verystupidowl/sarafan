package com.tgc.Sarafan.domain;

import com.fasterxml.jackson.annotation.*;
import lombok.*;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = "user_subscription")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserSubscription implements Serializable {

    @Serial
    private static final long serialVersionUID = -4356278199989312047L;

    @EmbeddedId
    @JsonIgnore
    private UserSubscriptionId id = new UserSubscriptionId();

    @MapsId("channelId")
    @ManyToOne
    @JoinColumn(name = "channel_id", referencedColumnName = "id")
    @JsonView(Views.IdName.class)
    @JsonIdentityReference
    @JsonIdentityInfo(
            property = "id",
            generator = ObjectIdGenerators.PropertyGenerator.class
    )
    private User channel;

    @MapsId("subscriberId")
    @JoinColumn(name = "subscriber_id", referencedColumnName = "id")
    @ManyToOne
    @JsonView(Views.IdName.class)
    @JsonIdentityReference
    @JsonIdentityInfo(
            property = "id",
            generator = ObjectIdGenerators.PropertyGenerator.class
    )
    private User subscriber;

    @JsonView(Views.IdName.class)
    private boolean active;

    @Override
    public int hashCode() {
        return id == null ? 0 : id.hashCode();
    }
}
