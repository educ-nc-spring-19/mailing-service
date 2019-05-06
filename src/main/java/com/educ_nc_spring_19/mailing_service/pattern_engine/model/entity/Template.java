package com.educ_nc_spring_19.mailing_service.pattern_engine.model.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Data
@Table
public class Template {
    @Id
    @GeneratedValue
    @Column
    private UUID id;

    @Basic(optional = false)
    @Column(name = "Date_Creating", nullable = false, columnDefinition = "timestamp with time zone")
    private OffsetDateTime dateCreating = OffsetDateTime.now();

    @Column
    private UUID creatorId;

    @Column(unique = true)
    private String type;

    @Column
    private String header;

    @Column
    private String text;

    public Template() {}

    public Template(UUID id, String type, String header, String text) {
        this.creatorId = id;
        this.type = type;
        this.dateCreating = OffsetDateTime.now();
        this.header = header;
        this.text = text;
    }
}
