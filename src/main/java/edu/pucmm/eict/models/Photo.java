package edu.pucmm.eict.models;
import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Photo implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String mimeType;
    @Lob
    private String photoBase64;

    public Photo() {
    }

    public Photo(String name, String mimeType, String photoBase64) {
        this.name = name;
        this.mimeType = mimeType;
        this.photoBase64 = photoBase64;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getPhotoBase64() {
        return photoBase64;
    }

    public void setPhotoBase64(String photoBase64) {
        this.photoBase64 = photoBase64;
    }
}