package org.example.homework4.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "post")
public class Post {

    @Id
    @Column(name = "id")
    private long id;
    @Column(name = "title")
    private String title;

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    private List<PostComment> commentList;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Override
    public String toString() {
        return "Публикация{" +
                "id=" + id +
                ", title='" + title + '\'' +
                '}';
    }
}
