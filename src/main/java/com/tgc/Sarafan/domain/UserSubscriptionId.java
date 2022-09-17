package com.tgc.Sarafan.domain;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serial;
import java.io.Serializable;

@Embeddable
@Data
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
        int result = channelId.hashCode();
        result = 31 * result + subscriberId.hashCode();
        return result;
    }
}
