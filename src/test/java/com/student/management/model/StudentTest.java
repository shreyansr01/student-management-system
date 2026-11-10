package com.student.management.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Student Model Tests")
class StudentTest
{

    @Test
    @DisplayName("Constructor and getters assign and return properties accurately")
    void testConstructorAndGetters()
    {
        Student student = new Student(101, "Jane Doe", 22, "A");

        assertEquals(101, student.getId());
        assertEquals("Jane Doe", student.getName());
        assertEquals(22, student.getAge());
        assertEquals("A", student.getGrade());
    }

    @Test
    @DisplayName("toString includes all student properties in formatted block")
    void testToStringFormatting()
    {
        Student student = new Student(1, "Alice", 19, "O");
        String output = student.toString();

        assertTrue(output.contains("1"));
        assertTrue(output.contains("Alice"));
        assertTrue(output.contains("19"));
        assertTrue(output.contains("O"));
    }

    @Test
    @DisplayName("equals and hashCode contract works across equivalent and different instances")
    void testEqualsAndHashCode()
    {
        Student student1 = new Student(1, "Bob", 20, "B");
        Student student2 = new Student(1, "Bob", 20, "B");
        Student student3 = new Student(2, "Charlie", 21, "C");

        assertEquals(student1, student2);
        assertEquals(student1.hashCode(), student2.hashCode());
        assertNotEquals(student1, student3);
    }
}
