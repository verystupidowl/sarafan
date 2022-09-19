package com.tgc.Sarafan.repositories;

import com.tgc.Sarafan.domain.Message;
import com.tgc.Sarafan.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    Page<Message> findByAuthorIn(List<User> users, Pageable pageable);
}
