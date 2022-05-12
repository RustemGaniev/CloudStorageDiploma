package ru.netology.cloudstorage.service;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import ru.netology.cloudstorage.entity.File;
import ru.netology.cloudstorage.entity.User;
import ru.netology.cloudstorage.repository.FileRepository;
import ru.netology.cloudstorage.security.MyUserDetailsService;

/**
 * @author VladSemikin
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FileService.class)
@WithMockUser(username = "user")
class FileServiceTest {

    @MockBean
    private FileRepository fileRepository;

    @Autowired
    private FileService fileService;

    @Test
    void save() {
        File file = new File();
        file.setData(new byte[]{});
        file.setName("file");

        fileService.save(new MockMultipartFile(file.getName(), file.getData()));

        Mockito.verify(fileRepository, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    void findByName() {
        fileService.findByName("file");

        Mockito.verify(fileRepository, Mockito.times(1))
                .findByNameAndUser(Mockito.anyString(), ArgumentMatchers.any(User.class));
    }

    @Test
    void findAllByUser() {
        fileService.findAllByUser(MyUserDetailsService.getCurrentUser());

        Mockito.verify(fileRepository, Mockito.times(1))
                .findAllByUser(ArgumentMatchers.any(User.class));
    }

    @Test
    void deleteFileByName() {
        fileService.deleteFileByName(Mockito.anyString());

        Mockito.verify(fileRepository, Mockito.times(1))
                .deleteByName(Mockito.anyString());
    }

    @Test
    void updateFile() {
        fileService.updateFile("name", "newName");

        Mockito.verify(fileRepository, Mockito.times(1))
                .findByNameAndUser(Mockito.anyString(), ArgumentMatchers.any(User.class));
    }
}