package com.isb.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String firstName;
    private String lastName;
    private String password;
    private String email;
    @Column(columnDefinition = "DATE")
    private LocalDate dateOfBirth;
    @Column(columnDefinition = "boolean default false")
    private boolean activated;
    @Column(columnDefinition = "boolean default false")
    private boolean isAdmin;
    private LocalDateTime lastLogin;

}
