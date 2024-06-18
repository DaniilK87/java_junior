package org.example.homework3;

import java.sql.*;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class JDBC {

    public static void dbConnection() {
        try (Connection connection = DriverManager.getConnection("jdbc:h2:mem:test")) {
            createTableDepartment(connection);
            createTablePerson(connection);
            insertDataToDepartment(connection);
            insertDataToPerson(connection);
            selectDataFromPerson(connection);
            System.out.println("-----------------------");
            String personDepartmentName = getPersonDepartmentName(connection,10);
            System.out.println("Название департамента: department_id = " + personDepartmentName + "]");
            System.out.println("-----------------------");
            Map<String,String> result = getPersonDepartments(connection);
            System.out.println(result);
        } catch (SQLException e) {
            System.err.println("Во время подключения произошла ошибка: " + e.getMessage());
        }
    }

    private static void createTableDepartment(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            String SQLDepartment = "create table department " +
                    "(id bigint primary key not NULL, " +
                    " name varchar(128) not NULL)";
            statement.execute(SQLDepartment);
        } catch (SQLException e) {
            System.err.println("Во время создания таблицы Department произошла ошибка: " + e.getMessage());
            throw e;
        }
    }

    private static void createTablePerson(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            String SQLPerson = "create table person " +
                    "(id bigint primary key not NULL, " +
                    " name varchar(256), " +
                    " age integer, " +
                    " active boolean, " +
                    " department_id bigint, " +
                    " FOREIGN KEY (department_id) REFERENCES department(id))";
            statement.execute(SQLPerson);

        } catch (SQLException e) {
            System.err.println("Во время создания таблицы Person произошла ошибка: " + e.getMessage());
            throw e;
        }
    }
    private static List<Long> getDepartmentId(Connection connection) throws SQLException {
        List<Long> listId = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement
                    .executeQuery(" SELECT id, name FROM department");

            while (resultSet.next()) {
                listId.add(resultSet.getLong("id"));
            }
        }
        return listId;
    }
    private static void insertDataToDepartment(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            StringBuilder insertQueryDep = new StringBuilder("insert into department(id, name) values\n");
            for (int i = 1; i <= 3; i++) {
                insertQueryDep.append(String.format("(%s, '%s')", i, "Department #" + i));

                if (i != 3) {
                    insertQueryDep.append(",\n");
                }
            }
            statement.executeUpdate(insertQueryDep.toString());
        }
    }
    private static void insertDataToPerson(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            List<Long> listId = getDepartmentId(connection);
            StringBuilder insertQueryPerson = new StringBuilder("insert into person(id, name, age, active, department_id) values\n");
            for (int i = 1; i <= 10; i++) {
                int age = ThreadLocalRandom.current().nextInt(20, 60);
                boolean active = ThreadLocalRandom.current().nextBoolean();
                long dep_id = ThreadLocalRandom.current().nextLong(listId.get(0), listId.size());
                insertQueryPerson.append(String.format("(%s, '%s', %s, %s, %d)", i, "Person #" + i, age, active, dep_id));

                if (i != 10) {
                    insertQueryPerson.append(",\n");
                }
            }
            statement.executeUpdate(insertQueryPerson.toString());
        }
    }
    private static void selectDataFromPerson(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement
                    .executeQuery(" SELECT id, name, age, department_id FROM person");

            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                int department = resultSet.getInt("department_id");
                System.out.println("Найдена строка: [id = " + id + ", name = " + name + "" +
                        ", age = " + age + ", department = " + department + "]");
            }
        }
    }
    /**
     * Пункт 4. Метод, который загружает Имя department по Идентификатору person
     */
    private static String getPersonDepartmentName(Connection connection, long personId) throws SQLException {
        String name = null;
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement
                    .executeQuery(" SELECT department_id FROM person WHERE id =" + personId);

            while (resultSet.next()) {
                name = resultSet.getString("department_id");
            }
        }
        return name;
    }

    /**
     * Пункт 5.Написать метод, который загружает Map<String, String>, в которой маппинг person.name -> department.name
     *  *   Пример: [{"person #1", "department #1"}, {"person #2", "department #3}]
     */
    static Map<String, String> getPersonDepartments(Connection connection) throws SQLException {
        Map<String, String> resultMap = new LinkedHashMap<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement
                    .executeQuery(" SELECT person.name, department.name FROM person INNER JOIN department ON person.id = department.id");

            while (resultSet.next()) {
               String personName = resultSet.getString("person.name");
               String departmentName = resultSet.getString("department.name");
                resultMap.put(personName,departmentName);
            }
            return resultMap;
        }
    }
}

