package com.tgc.Sarafan.repositories;

import com.tgc.Sarafan.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    @Override
    Optional<User> findById(String id);
}
