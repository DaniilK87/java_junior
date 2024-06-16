package org.example.homework2;

/**
 * В существующий класс ObjectCreator добавить поддержку аннотации RandomDate (по аналогии с Random):
 * 1. Аннотация должна обрабатываться только над полями типа java.util.Date
 * 2. Проверить, что min < max
 * 3. В поле, помеченной аннотацией, нужно вставлять рандомную дату,
 * UNIX-время которой находится в диапазоне [min, max)
 *
 * 4. *** Добавить поддержку для типов Instant, ...
 * 5. *** Добавить атрибут Zone и поддержку для типов LocalDate, LocalDateTime
 */

import java.util.Date;

/**
 * Примечание:
 * Unix-время - количество милисекунд, прошедших с 1 января 1970 года по UTC-0.
 */

public class Main {

    public static void main(String[] args) {
        Test rndTest = ObjectCreator.createObj(Test.class);
        assert rndTest != null;
        System.out.println(rndTest.dateTest1);
        System.out.println(rndTest.dateTest2);
    }

    public static class Test {
        @RandomDate(min = 169406520000L, max = 1695688600000L)
        Date dateTest1;

        @RandomDate(min = 1704067200000L, max = 1735689600000L)
        Date dateTest2;


    }
}


