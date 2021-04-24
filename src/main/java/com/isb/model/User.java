package com.isb.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name = "\"user\"")
    private String user;
    private String password;
    private String email;
    @Column(columnDefinition = "boolean default false")
    private boolean activated;

}
