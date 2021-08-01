package com.isb.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "subComment")
public class SubComment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String owner;
    private String message;
    private LocalDateTime timestamp;
    private long userID;
}
