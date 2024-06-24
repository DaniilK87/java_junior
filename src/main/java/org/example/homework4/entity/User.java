package org.example.homework4.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @Column(name = "id")
    private long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "user")
    private List<Post> postList;

    @OneToMany(mappedBy = "user")
    private List<PostComment> postCommentList;
}
