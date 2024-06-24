package org.example.homework4.service;

import org.example.homework4.entity.Post;
import org.example.homework4.entity.PostComment;
import org.example.homework4.entity.User;
import org.hibernate.SessionFactory;

import java.util.List;

public interface UserService {

    void createUser(SessionFactory sessionFactory, long id, String name);
    String deleteUser(SessionFactory sessionFactory, long id);
    User getUserById(SessionFactory sessionFactory, long id);
    List<Post> getAllPostByUser(SessionFactory sessionFactory, long user_id);
    List<PostComment> getAllCommentByUser(SessionFactory sessionFactory, long user_id);

}
