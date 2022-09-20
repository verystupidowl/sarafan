package com.tgc.Sarafan.domain;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.io.Serial;
import java.io.Serializable;

@Embeddable
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserSubscriptionId implements Serializable {

    @Serial
    private static final long serialVersionUID = 1504274905590332033L;


    @JsonView(Views.IdName.class)
    private String channelId;
    @JsonView(Views.IdName.class)
    private String subscriberId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserSubscriptionId that = (UserSubscriptionId) o;

        if (!channelId.equals(that.channelId)) return false;
        return subscriberId.equals(that.subscriberId);
    }

    @Override
    public int hashCode() {
        int result = channelId == null ? 0 : channelId.hashCode();
        result = 31 * result + (subscriberId == null ? 0 : subscriberId.hashCode());
        return result;
    }
}
