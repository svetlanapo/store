package ru.spo.store.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.spo.store.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findFirstByName(String name);
}
