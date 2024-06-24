package org.example.homework4.service;

import org.example.homework4.entity.Post;
import org.example.homework4.entity.PostComment;
import org.example.homework4.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class PostServiceImpl implements PostService {

    @Override
    public void createPost (SessionFactory sessionFactory, long id, String title, User user){
        try(Session session = sessionFactory.openSession()) {
            Post post = new Post();
            post.setId(id);
            post.setTitle(title);
            post.setUser(user);
            Transaction tx = session.beginTransaction();
            session.persist(post);
            tx.commit();
        }
    }

    @Override
    public String deletePost (SessionFactory sessionFactory,long id){
        try(Session session = sessionFactory.openSession()) {
            Post post = session.find(Post.class, id);
            if (post == null) throw new RuntimeException("Нет такой публикации");
            Transaction tx = session.beginTransaction();
            session.remove(post);
            tx.commit();
        }
        return "Публикация " + id + "удалена";
    }

    @Override
    public Post getPostById(SessionFactory sessionFactory, long id) {
        Post post;
        try(Session session = sessionFactory.openSession()) {
            post = session.find(Post.class, id);
            if (post == null) throw new RuntimeException("Нет такой публикации");
        }
        return post;
    }

    @Override
    public List<PostComment> getAllCommentByPost(SessionFactory sessionFactory, long post_id) {
        List<PostComment> list;
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            list = session.createQuery("select p.commentList FROM PostComment ps " +
                            "LEFT join Post p on ps.id = p.id where p.id = " + post_id, PostComment.class)
                    .getResultList();
            tx.commit();
        }
        return list;
    }

}
