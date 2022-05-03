package ru.netology.cloudstorage.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.netology.cloudstorage.dto.FileDTO;
import ru.netology.cloudstorage.entity.File;
import ru.netology.cloudstorage.security.MyUserDetailsService;
import ru.netology.cloudstorage.service.FileService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author VladSemikin
 */

@RestController
public class FileController {

    private final FileService fileService;

    private final Logger log = LoggerFactory.getLogger(FileController.class);

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping("/list")
    public List<FileDTO> listFile(@RequestParam String limit) {
        return fileService.findAllByUser(MyUserDetailsService.getCurrentUser())
                .stream()
                .map(this::mapToFileResponse)
                .limit(Long.parseLong(limit))
                .collect(Collectors.toList());
    }

    private FileDTO mapToFileResponse(File fileEntity) {
        FileDTO fileResponse = new FileDTO();
        fileResponse.setFilename(fileEntity.getName());
        fileResponse.setSize(fileEntity.getSize());

        return fileResponse;
    }

    @PostMapping("/file")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            fileService.save(file);

            log.info("File uploaded successfully: {}", file.getOriginalFilename());
            return ResponseEntity.status(HttpStatus.OK)
                    .body(String.format("File uploaded successfully: %s", file.getOriginalFilename()));
        } catch (Exception e) {
            log.error("Could not upload the file: {}", file.getOriginalFilename());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(String.format("Could not upload the file: %s!", file.getOriginalFilename()));
        }
    }

    @DeleteMapping("/file")
    public ResponseEntity<String> deleteFile(@RequestParam String filename) {
        try {
            fileService.deleteFileByName(filename);

            log.info("File deleted successfully: {}", filename);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(String.format("File deleted successfully: %s", filename));
        } catch (Exception e) {
            log.info("Could not deleted the file: {}", filename);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(String.format("Could not deleted the file: %s!", filename));
        }
    }

    @GetMapping("/file")
    public ResponseEntity<byte[]> getFile(@RequestParam String filename) {
        Optional<File> fileOptional = fileService.findByName(filename);

        if (fileOptional.isEmpty()) {
            return ResponseEntity.notFound()
                    .build();
        }
        File fileEntity = fileOptional.get();
        log.info("File download successfully: {}", filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""
                        + fileEntity.getName() + "\"")
                .contentType(MediaType.valueOf(fileEntity.getContentType()))
                .body(fileEntity.getData());
    }

    @PutMapping("/file")
    public ResponseEntity<String> updateFile(@RequestParam String filename, @RequestBody FileDTO name) {
        try {
            fileService.updateFile(filename, name.getFilename());

            log.info("File update successfully: {}", name.getFilename());
            return ResponseEntity.status(HttpStatus.OK)
                    .body(String.format("File update successfully: %s", name.getFilename()));
        } catch (Exception e) {
            log.error("Could not update the file: {}", filename);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(String.format("Could not update the file: %s!", filename));
        }
    }
}
