package org.example.homework2;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class RandomDateAnnotationProcessor {
    public static void processAnnotation(Object obj) {

        Class<?> objClass = obj.getClass();
        for (Field field : objClass.getDeclaredFields()) {

            if (field.isAnnotationPresent(RandomDate.class) && field.getType().isAssignableFrom(Date.class)) {
                RandomDate annotation = field.getAnnotation(RandomDate.class);
                long min = annotation.min();
                long max = annotation.max();

                try {
                    field.setAccessible(true); // чтобы можно было изменять финальные поля
                    if (min < max) {
                        field.set(obj, new Date(ThreadLocalRandom.current().nextLong(min, max)));
                    } else throw new NullPointerException("min не должен быть больше max");
                } catch (IllegalAccessException e) {
                    System.err.println("Не удалось вставить значение в поле: " + e.getMessage());
                }
            }
        }

    }

}
