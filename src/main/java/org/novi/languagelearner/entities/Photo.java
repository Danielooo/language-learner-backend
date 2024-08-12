package org.novi.languagelearner.entities;


import jakarta.persistence.*;

@Entity
public class Photo {

    @Id
    private String filename;

    public Photo(String filename) {
        this.filename = filename;
    }

    public Photo() {

    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}
