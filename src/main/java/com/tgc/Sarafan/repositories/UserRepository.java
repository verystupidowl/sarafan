package com.tgc.Sarafan.repositories;

import com.tgc.Sarafan.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    @Override
    @NonNull
    Optional<User> findById(@NonNull String id);
}
