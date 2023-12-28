package com.example.deploy_spring_test.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "ma_car")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "car_id_generator")
    @SequenceGenerator(name = "car_id_generator", allocationSize = 1)
    private Long id;
    private String carDescription;

    /*
    many cars -> one user
     default -> no cascade
    @JoinColumn(name = "user") - use this annotation if you want to
    rename the foreign_key column
     */
//    @ManyToOne
//    @JoinColumn(name = "user")
//    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCarDescription() {
        return carDescription;
    }

    public void setCarDescription(String carDescription) {
        this.carDescription = carDescription;
    }

}
