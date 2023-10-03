package com.healinghaven.bigmomma.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "images")
public class Image {
    @Id
    @GeneratedValue
    private int Id;
    private String base64String;
    private String url;
    private double size;
    private String imageType;
}
