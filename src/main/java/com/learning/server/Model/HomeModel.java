package com.learning.server.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;


@Document(collection = "home")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HomeModel {

    @Id
    @MongoId(FieldType.OBJECT_ID)
    private String id;
    private String image;
    private String title;
    private String body;
    private String createdBy;

}
