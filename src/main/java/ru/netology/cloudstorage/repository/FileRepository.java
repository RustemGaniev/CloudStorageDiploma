package ru.netology.cloudstorage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.netology.cloudstorage.entity.File;
import ru.netology.cloudstorage.entity.User;

import java.util.List;
import java.util.Optional;

/**
 * @author VladSemikin
 */

public interface FileRepository extends JpaRepository<File, String> {
    void deleteByName(String name);

    Optional<File> findByNameAndUser(String name, User user);

    List<File> findAllByUser (User user);
}
