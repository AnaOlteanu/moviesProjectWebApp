package com.example.projectmovie.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
@ToString
@Entity
@Table(name = "contact_info")
public class ContactInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "Birthday should not be empty!")
    @Column(name = "date_of_birth")
    public LocalDate dateOfBirth;
    @Enumerated(value = EnumType.STRING)
    @Column(columnDefinition = "enum('F', 'M')")
    @NotNull(message = "Gender should not be empty!")
    public Gender gender;
//    @Lob
//    private Byte[] image;

    @OneToOne
    private Actor actor;

    public void removeActor(Actor actor) {
        actor.setContactInfo(null);
        this.actor = null;
    }
}
