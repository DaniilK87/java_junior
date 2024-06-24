package org.example.homework4.service;


import org.example.homework4.entity.Post;
import org.example.homework4.entity.User;
import org.hibernate.SessionFactory;

public interface PostCommentService {

    void createComment(SessionFactory sessionFactory, long id, String text, Post post, User user);
    String deleteComment(SessionFactory sessionFactory,long id);

}
