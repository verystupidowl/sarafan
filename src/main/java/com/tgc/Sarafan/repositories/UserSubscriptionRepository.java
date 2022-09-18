package com.tgc.Sarafan.repositories;

import com.tgc.Sarafan.model.User;
import com.tgc.Sarafan.model.UserSubscription;
import com.tgc.Sarafan.model.UserSubscriptionId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserSubscriptionRepository extends JpaRepository<UserSubscription, UserSubscriptionId> {

    List<UserSubscription> findBySubscriber(User user);
}
