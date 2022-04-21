package ru.netology.cloudstorage.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.netology.cloudstorage.entity.User;


import java.util.Optional;

/**
 * @author VladSemikin
 */
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByLogin(String login);
}
