package com.student.management.service;

import com.student.management.model.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("StudentManager Service Tests")
class StudentManagerTest
{
    private StudentManager studentManager;

    @BeforeEach
    void setUp()
    {
        studentManager = new StudentManager();
    }

    @Test
    @DisplayName("Instantiates default StudentManager successfully")
    void testInitialization()
    {
        assertNotNull(studentManager);
    }

    @Test
    @DisplayName("displayStudents handles empty student list gracefully without exception")
    void testDisplayStudentsEmpty()
    {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        try
        {
            System.setOut(new PrintStream(outContent));
            assertDoesNotThrow(() -> studentManager.displayStudents(Collections.emptyList()));
            assertTrue(outContent.toString().contains("No student records found."));
        }
        finally
        {
            System.setOut(originalOut);
        }
    }

    @Test
    @DisplayName("displayStudents handles null list gracefully")
    void testDisplayStudentsNull()
    {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        try
        {
            System.setOut(new PrintStream(outContent));
            assertDoesNotThrow(() -> studentManager.displayStudents(null));
            assertTrue(outContent.toString().contains("No student records found."));
        }
        finally
        {
            System.setOut(originalOut);
        }
    }

    @Test
    @DisplayName("displayStudents prints table header and student row correctly")
    void testDisplayStudentsWithData()
    {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        try
        {
            System.setOut(new PrintStream(outContent));
            List<Student> students = List.of(new Student(1, "John Doe", 20, "A"));
            studentManager.displayStudents(students);

            String output = outContent.toString();
            assertTrue(output.contains("John Doe"));
            assertTrue(output.contains("ID"));
            assertTrue(output.contains("Grade"));
        }
        finally
        {
            System.setOut(originalOut);
        }
    }
}
