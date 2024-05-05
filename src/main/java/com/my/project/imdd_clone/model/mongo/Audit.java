package com.my.project.imdd_clone.model.mongo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDateTime;
import java.util.Date;

@Document(collection = "audit")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Audit {

    @MongoId
    private ObjectId id;

    private String message;

    private String action;
    private String user;

    private Integer date;

    private LocalDateTime createdOn;

}
