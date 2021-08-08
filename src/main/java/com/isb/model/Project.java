package com.isb.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.log4j.Logger;

import javax.persistence.*;
import java.lang.reflect.Field;
import java.time.LocalDateTime;

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
    private Integer evaluationPrice;
    private Integer equity;
    private String caption;
    private String imgName;
    private String city;
    private LocalDateTime uploadedDate;
    private long userID;

    public void merge(Object newObject) {

        assert this.getClass().getName().equals(newObject.getClass().getName());

        for (Field field : this.getClass().getDeclaredFields()) {

            for (Field newField : newObject.getClass().getDeclaredFields()) {

                if (field.getName().equals(newField.getName())) {

                    try {

                        field.set(
                                this,
                                newField.get(newObject) == null
                                        ? field.get(this)
                                        : newField.get(newObject));

                    } catch (IllegalAccessException ignore) {
                        // Field update exception on final modifier and other cases.
                        Logger.getLogger(this.getClass()).warn(ignore);
                    }
                    break;
                }
            }
        }
    }
}
