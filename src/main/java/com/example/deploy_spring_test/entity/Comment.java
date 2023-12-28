package com.example.deploy_spring_test.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Table(name = "ma_comment")
@Entity
@Getter
@Setter
public class Comment {
    @Id
    private Long id;
    private String comment;

    /*
    many to one bidirectional
     */
    @ManyToOne
    private User user;

}
