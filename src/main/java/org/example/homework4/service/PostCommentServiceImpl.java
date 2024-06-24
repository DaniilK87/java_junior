package org.example.homework4.service;

import org.example.homework4.entity.Post;
import org.example.homework4.entity.PostComment;
import org.example.homework4.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class PostCommentServiceImpl implements PostCommentService{
    @Override
    public void createComment(SessionFactory sessionFactory, long id, String text, Post post, User user) {
        try(Session session = sessionFactory.openSession()) {
            PostComment comment = new PostComment();
            comment.setId(id);
            comment.setText(text);
            comment.setPost(post);
            comment.setUser(user);
            Transaction tx = session.beginTransaction();
            session.persist(comment);
            tx.commit();
        }
    }

    @Override
    public String deleteComment(SessionFactory sessionFactory, long id) {
        try(Session session = sessionFactory.openSession()) {
            PostComment comment = session.find(PostComment.class, id);
            if (comment == null) throw new RuntimeException("Нет такого комментария");
            Transaction tx = session.beginTransaction();
            session.remove(comment);
            tx.commit();
        }
        return "Комментарий " + id + "удален";
    }
}
