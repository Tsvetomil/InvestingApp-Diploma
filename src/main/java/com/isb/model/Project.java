package com.isb.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "project")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String fname;
    private String lname;
    private String companyName;
    private String phone;
    private String description;
    private String toRaise;
//    private String raised;
    private String caption;
//    private String about;
    private String imgName;
//    private String caption;
//    private double raised;
//    private double minTarget;
//    private String location;
//    private String description;
//    private byte[] media;
    private long userID;
}
