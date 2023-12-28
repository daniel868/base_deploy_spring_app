package com.example.deploy_spring_test.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Entity(name = "ma_user")
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_id_generator")
    @SequenceGenerator(name = "user_id_generator", allocationSize = 1)
    Long id;

    @Column(name = "username")
    private String username;
    @Column(name = "created_date")
    private Date createdDate;
    @Column(name = "updated_date")
    private Date updatedDate;

    /*
        mapping from the other side, one directional mapping,
        it will create a new table for car and user

        the ownership of the relation -> where the foreign key is

        @JoinColumn(name = "post_id") - used for one directional mapping
        for eliminating the joining table on this one-to-many relationship

        for directional mapping, it's not need to use JoinColumn - just use mappedBy on the opposite
        side of the relationship
     */
    @OneToMany
    @JoinColumn(name = "post_id")
    private Set<Car> usersCars;

    /*
    one to many bidirectional size
     */
    @OneToMany(mappedBy = "user")
    private Set<Comment> usersComment;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(username, user.username) && Objects.equals(createdDate, user.createdDate) && Objects.equals(updatedDate, user.updatedDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, createdDate, updatedDate);
    }

    public void addNewCar(Car car) {
        if (usersCars == null) {
            usersCars = new HashSet<>();
        }
        usersCars.add(car);
    }

    public void addNewComment(Comment comment) {
        if (usersComment == null) {
            usersComment = new HashSet<>();
        }
        usersComment.add(comment);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", createdDate=" + createdDate +
                ", updatedDate=" + updatedDate +
                '}';
    }
}
