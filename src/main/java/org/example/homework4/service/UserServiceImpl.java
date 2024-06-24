package org.example.homework4.service;

import org.example.homework4.entity.Post;
import org.example.homework4.entity.PostComment;
import org.example.homework4.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class UserServiceImpl implements UserService{

    @Override
    public void createUser(SessionFactory sessionFactory, long id, String name) {
        try(Session session = sessionFactory.openSession()) {
            User user = new User();
            user.setId(id);
            user.setName(name);
            Transaction tx = session.beginTransaction();
            session.persist(user);
            tx.commit();
        }
    }

    @Override
    public String deleteUser(SessionFactory sessionFactory, long id) {
        try(Session session = sessionFactory.openSession()) {
            User user = session.find(User.class, id);
            if (user == null) throw new RuntimeException("Нет такого пользователя");
            Transaction tx = session.beginTransaction();
            session.remove(user);
            tx.commit();
        }
        return "Пользователь " + id + "удален";
    }

    @Override
    public User getUserById(SessionFactory sessionFactory, long id) {
        User user;
        try(Session session = sessionFactory.openSession()) {
            user = session.find(User.class, id);
            if (user == null) throw new RuntimeException("Нет такого пользователя");
        }
        return user;
    }

    @Override
    public List<Post> getAllPostByUser(SessionFactory sessionFactory, long user_id) {
        List<Post> list;
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            list = session.createQuery("select u.postList FROM Post p " +
                            "LEFT join User u on p.id = u.id where u.id = " + user_id, Post.class)
                    .getResultList();
            tx.commit();
        }
        return list;
    }

    @Override
    public List<PostComment> getAllCommentByUser(SessionFactory sessionFactory, long user_id) {
        List<PostComment> list;
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            list = session.createQuery("select u.postCommentList FROM PostComment ps " +
                            "LEFT join User u on ps.id = u.id where u.id = " + user_id, PostComment.class)
                    .getResultList();
            tx.commit();
        }
        return list;
    }

}
