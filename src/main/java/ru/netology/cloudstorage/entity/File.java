package ru.netology.cloudstorage.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * @author VladSemikin
 */
@Entity
@Table(name = "files")
public class File {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "content_type")
    private String contentType;

    @Column(name = "size")
    private Long size;

    @Lob
    @Column(name = "data", columnDefinition = "clob")
    private byte[] data;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "login")
    private User user;

    public File() {
    }

    public File(String id, String name, String contentType, Long size, byte[] data, User user) {
        this.id = id;
        this.name = name;
        this.contentType = contentType;
        this.size = size;
        this.data = data;
        this.user = user;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

