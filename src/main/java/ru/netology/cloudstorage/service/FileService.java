package ru.netology.cloudstorage.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import ru.netology.cloudstorage.entity.File;
import ru.netology.cloudstorage.entity.User;
import ru.netology.cloudstorage.gzip.GzipUtils;
import ru.netology.cloudstorage.repository.FileRepository;
import ru.netology.cloudstorage.security.MyUserDetailsService;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @author VladSemikin
 */
@Service
public class FileService {

    private final FileRepository fileRepository;

    private final Logger log = LoggerFactory.getLogger(FileService.class);

    public FileService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    public void save(MultipartFile file) {
        try {
            File fileEntity = new File();
            fileEntity.setName(StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename())));
            fileEntity.setContentType(file.getContentType());
            fileEntity.setData(GzipUtils.gzipCompress(file.getBytes()));
            fileEntity.setSize(file.getSize());
            fileEntity.setUser(MyUserDetailsService.getCurrentUser());

            fileRepository.save(fileEntity);
        } catch (IOException e) {
            log.error("Error exception by save {}", e.getMessage());
        }
    }

    @Transactional
    public Optional<File> findByName(String name) {
        Optional<File> getFile = fileRepository.findByNameAndUser(name, MyUserDetailsService.getCurrentUser());
        if (getFile.isPresent()) {
            if (!GzipUtils.isGZIPStream(getFile.get().getData())) {
                return getFile;
            }
            getFile.get().setData(GzipUtils.gzipUncompress(getFile.get().getData()));
        }
        return getFile;
    }

    @Transactional
    public List<File> findAllByUser(User user) {
        return fileRepository.findAllByUser(user);
    }

    @Transactional
    public void deleteFileByName(String name) {
        fileRepository.deleteByName(name);
    }

    @Transactional
    public void updateFile(String name, String newName) {
        Optional<File> getFile = fileRepository.findByNameAndUser(name, MyUserDetailsService.getCurrentUser());
        if (getFile.isPresent()) {
            getFile.get().setName(newName);
            fileRepository.save(getFile.get());
        }
    }
}
