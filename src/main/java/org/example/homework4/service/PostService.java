package org.example.homework4.service;

import org.example.homework4.entity.Post;
import org.example.homework4.entity.PostComment;
import org.example.homework4.entity.User;
import org.hibernate.SessionFactory;

import java.util.List;

public interface PostService {

    void createPost(SessionFactory sessionFactory, long id, String title, User user);
    String deletePost(SessionFactory sessionFactory, long id);
    Post getPostById(SessionFactory sessionFactory, long id);
    List<PostComment> getAllCommentByPost(SessionFactory sessionFactory, long post_id);
}
