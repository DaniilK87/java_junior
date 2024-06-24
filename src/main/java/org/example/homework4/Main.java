package org.example.homework4;


import org.example.homework4.entity.Post;
import org.example.homework4.entity.PostComment;
import org.example.homework4.entity.User;
import org.example.homework4.service.*;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;

/**
 * Используя hibernate, создать таблицы:
 * 1. Post (публикация) (id, title)
 * 2. PostComment (комментарий к публикации) (id, text, post_id)
 *
 * Написать стандартные CRUD-методы: создание, загрузка, удаление.
 *
 * Доп. задания:
 * 1. * В сущностях post и postComment добавить поля timestamp с датами.
 * 2. * Создать таблицу users(id, name) и в сущностях post и postComment добавить ссылку на юзера.
 * 3. * Реализовать методы:
 * 3.1 Загрузить все комментарии публикации
 * 3.2 Загрузить все публикации по идентификатору юзера
 * 3.3 ** Загрузить все комментарии по идентификатору юзера
 * 3.4 **** По идентификатору юзера загрузить юзеров, под чьими публикациями он оставлял комменты.
 * // userId -> List<User>
 *
 *
 * Замечание:
 * 1. Можно использовать ЛЮБУЮ базу данных (например, h2)
 * 2. Если запутаетесь, приходите в группу в телеграме или пишите мне @inchestnov в личку.
 */
public class Main {

    public static void main(String[] args) {
        UserService user = new UserServiceImpl();
        PostService post = new PostServiceImpl();
        PostCommentService comment = new PostCommentServiceImpl();
        org.hibernate.cfg.Configuration configuration = new Configuration();
        configuration.configure();
        try (SessionFactory sessionFactory = configuration.buildSessionFactory()) {
            user.createUser(sessionFactory, 1L, "User1");
            user.createUser(sessionFactory, 2L, "User2");
            User user1 = user.getUserById(sessionFactory,1L);
            User user2 = user.getUserById(sessionFactory,2L);

            post.createPost(sessionFactory, 1L, "Post1",user1);
            post.createPost(sessionFactory, 2L, "Post2",user1);
            post.createPost(sessionFactory, 3L, "Post3",user2);
            Post post1 = post.getPostById(sessionFactory,1L);
            Post post2 = post.getPostById(sessionFactory,2L);
            Post post3 = post.getPostById(sessionFactory,3L);

            comment.createComment(sessionFactory, 1L,"Text1", post1, user1);
            comment.createComment(sessionFactory, 2L,"Text2", post1, user1);
            comment.createComment(sessionFactory, 3L,"Text3", post2, user2);
            comment.createComment(sessionFactory, 4L,"Text4", post3, user2);

//            String deletePost1 = post.deletePost(sessionFactory,1L);
//            System.out.println(deletePost1);

            List<PostComment> commentList = post.getAllCommentByPost(sessionFactory, post1.getId());
            System.out.println(commentList);

            List<Post> postList = user.getAllPostByUser(sessionFactory, user2.getId());
            System.out.println(postList);

            List<PostComment> userCommentList = user.getAllCommentByUser(sessionFactory, user2.getId());
            System.out.println(userCommentList);
        }
    }
}
