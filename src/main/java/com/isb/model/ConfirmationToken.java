package com.isb.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "confirmationToken")
public class ConfirmationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long tokenId;
    private String code;
    private LocalDate createdDate;
    private long userID;


    public ConfirmationToken(long userID) {
        this.userID = userID;
        createdDate = LocalDate.now();
        code = UUID.randomUUID().toString();
    }
}
