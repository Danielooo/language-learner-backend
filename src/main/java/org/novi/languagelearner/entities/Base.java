package org.novi.languagelearner.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
@MappedSuperclass
public abstract class Base {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @Column(name = "created_date", updatable = false)

    private LocalDateTime createDate;

    @Column(name = "edited_date")

    private LocalDateTime editDate;

    @PrePersist
    public void onCreate() {
        createDate = LocalDateTime.now();
    }

    @PreUpdate
    public void onEdit() {
        editDate = LocalDateTime.now();
    }

}
