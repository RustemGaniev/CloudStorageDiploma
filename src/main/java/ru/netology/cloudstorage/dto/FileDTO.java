package ru.netology.cloudstorage.dto;

/**
 * @author VladSemikin
 */
public class FileDTO {

    private String filename;
    private Long size;

    public FileDTO() {
    }

    public FileDTO(String filename, Long size) {
        this.filename = filename;
        this.size = size;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }
}
