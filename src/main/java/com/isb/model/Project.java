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
    private String reasonsToInvest;
    private String description;
    private String toRaise;
    private String website;
    private String address;
    private int evaluationPrice;
    private int equity;
    private String caption;
    private String imgName;
    private String city;
    private long userID;
}
