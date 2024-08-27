package org.novi.languagelearner.entities;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
public class Photo extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String filename;
    private String fileType;

    @Lob
    private byte[] data;

    @OneToOne(mappedBy = "photo", fetch = FetchType.LAZY)
    @JsonBackReference
    private User user;

}
