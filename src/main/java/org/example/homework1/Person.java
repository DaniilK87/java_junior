package org.example.homework1;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Person {
    private String name;
    private int age;
    private double salary;
    private Department department;
}
