package com.student.management.model;

import java.util.Objects;

/**
 * Represents a student domain entity.
 */
public class Student
{
    private final int id;
    private final String name;
    private final int age;
    private final String grade;

    /**
     * Constructs a new Student instance.
     *
     * @param id    unique student identifier
     * @param name  full name of the student
     * @param age   age in years
     * @param grade letter grade achieved
     */
    public Student(int id, String name, int age, String grade)
    {
        this.id = id;
        this.name = name;
        this.age = age;
        this.grade = grade;
    }

    public int getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public int getAge()
    {
        return age;
    }

    public String getGrade()
    {
        return grade;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return id == student.id && age == student.age && Objects.equals(name, student.name) && Objects.equals(grade, student.grade);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(id, name, age, grade);
    }

    @Override
    public String toString()
    {
        return String.format(
                """
                ------------------------------
                ID    : %d
                Name  : %s
                Age   : %d
                Grade : %s
                ------------------------------""",
                id, name, age, grade);
    }
}
