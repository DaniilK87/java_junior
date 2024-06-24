package org.example.homework4.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "post_comment")
public class PostComment {

    @Id
    @Column(name = "id")
    private long id;

    @Column(name = "text")
    private String text;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Override
    public String toString() {
        return "Комментарий{" +
                "id=" + id +
                ", text='" + text + '\'' +
                '}';
    }
}
