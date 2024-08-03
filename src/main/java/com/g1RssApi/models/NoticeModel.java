package com.g1RssApi.models;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "Notice")
@Table(name = "TB_NOTICES")
public class NoticeModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;  // Id da noticia

    @Column(name = "title", nullable = false)
    private String title; // Titulo da noticia

    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    private String description;  // Descricao da noticia

    @Column(name = "image_url")
    private String imageUrl;  // Url da imagem

    @Column(name = "pub_date", nullable = false)
    private Date pubDate;  // Data da publicacao

    @ManyToOne
    @JoinColumn(name = "category_fk")
    private CategoryModel category;

}
