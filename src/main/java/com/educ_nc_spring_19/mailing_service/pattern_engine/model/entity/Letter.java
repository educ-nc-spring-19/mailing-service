package com.educ_nc_spring_19.mailing_service.pattern_engine.model.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Data
@Table
public class Letter {
    @Id
    @GeneratedValue
    @Column
    private UUID id;

    @Basic(optional = false)
    @Column(name = "Date_Creating", nullable = false, columnDefinition = "timestamp with time zone")
    private OffsetDateTime dateCreating = OffsetDateTime.now();

    @Column
    private UUID receiverId;

    @Column
    private String text;

    @Column
    private String header;

    @Column
    private String type;

    public Letter() {}

    public Letter(UUID id, String text, String header, String type) {
        this.receiverId = id;
        this.dateCreating = OffsetDateTime.now();
        this.text = text;
        this.header = header;
        this.type = type;
    }
}
