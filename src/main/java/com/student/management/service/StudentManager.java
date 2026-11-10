package com.student.management.service;

import com.student.management.dao.StudentDao;
import com.student.management.model.Student;
import com.student.management.util.InputValidator;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Service orchestrator handling business workflows, console reporting, and user interactions.
 */
public class StudentManager
{
    private final StudentDao studentDao;

    public StudentManager()
    {
        this(new StudentDao());
    }

    public StudentManager(StudentDao studentDao)
    {
        this.studentDao = studentDao;
    }

    // -------------------------------------------------
    // Console Display Methods
    // -------------------------------------------------

    /**
     * Renders a list of students in an aligned console table.
     *
     * @param students list of students to display
     */
    public void displayStudents(List<Student> students)
    {
        if (students == null || students.isEmpty())
        {
            System.out.println("No student records found.");
            return;
        }

        System.out.println("--------------------------------------------");
        System.out.printf("%-8s | %-18s | %-4s | %-5s%n", "ID", "Name", "Age", "Grade");
        System.out.println("--------------------------------------------");

        for (Student student : students)
        {
            System.out.printf("%-8d | %-18s | %-4d | %-5s%n",
                    student.getId(),
                    student.getName(),
                    student.getAge(),
                    student.getGrade());
        }
        System.out.println("--------------------------------------------");
    }

    // -------------------------------------------------
    // CRUD Workflows
    // -------------------------------------------------

    /**
     * Prompts for new student details, validates entries, and persists the record.
     *
     * @param scanner input scanner
     */
    public void addStudentFromInput(Scanner scanner)
    {
        System.out.println("\n--- Add Student ---");

        String name = InputValidator.promptName(scanner, "Name: ");
        int age = InputValidator.promptAge(scanner, "Age (5-120): ");
        String grade = InputValidator.promptGrade(scanner, "Grade (O, E, A, B, C, D, F): ");

        boolean success = studentDao.addStudent(name, age, grade);
        if (success)
        {
            System.out.println("Student '" + name + "' added.");
        }
        else
        {
            System.out.println("Failed to add student '" + name + "'.");
        }
    }

    /**
     * Fetches and displays all enrolled students.
     */
    public void viewAllStudents()
    {
        List<Student> students = studentDao.getAllStudents();
        System.out.println("\n--- All Students ---");
        displayStudents(students);
    }

    /**
     * Finds a student by identifier.
     *
     * @param id student unique identifier
     * @return Student if located, null otherwise
     */
    public Student searchById(int id)
    {
        return studentDao.getStudentById(id);
    }

    /**
     * Prompts for a search substring and displays matching students.
     *
     * @param scanner input scanner
     */
    public void searchByName(Scanner scanner)
    {
        String name = InputValidator.promptName(scanner, "\nSearch name: ");
        List<Student> students = studentDao.getStudentsByName(name);
        displayStudents(students);
    }

    /**
     * Prompts for a grade and displays matching students.
     *
     * @param scanner input scanner
     */
    public void searchByGrade(Scanner scanner)
    {
        String grade = InputValidator.promptGrade(scanner, "\nSearch grade (O, E, A, B, C, D, F): ");
        List<Student> students = studentDao.getStudentsByGrade(grade);
        displayStudents(students);
    }

    /**
     * Prompts for a student identifier and deletes the corresponding record.
     *
     * @param scanner input scanner
     */
    public void removeStudentById(Scanner scanner)
    {
        int id = validateId(scanner, "\nStudent ID to delete: ");
        boolean deleted = studentDao.deleteStudentById(id);

        if (deleted)
        {
            System.out.println("Student ID " + id + " deleted.");
        }
        else
        {
            System.out.println("No student found with ID " + id + ".");
        }
    }

    /**
     * Prompts for a student identifier and updates their details if found.
     *
     * @param scanner input scanner
     */
    public void updateStudentById(Scanner scanner)
    {
        int id = validateId(scanner, "\nStudent ID to update: ");

        Student student = searchById(id);
        if (student == null)
        {
            System.out.println("No student found with ID " + id + ".");
            return;
        }

        System.out.println("\nEnter updated details:");
        String newName = InputValidator.promptName(scanner, "Name: ");
        int newAge = InputValidator.promptAge(scanner, "Age (5-120): ");
        String newGrade = InputValidator.promptGrade(scanner, "Grade (O, E, A, B, C, D, F): ");

        boolean updated = studentDao.updateStudent(id, newName, newAge, newGrade);
        if (updated)
        {
            System.out.println("Student ID " + id + " updated.");
        }
        else
        {
            System.out.println("Failed to update student.");
        }
    }

    // -------------------------------------------------
    // Validation Delegation
    // -------------------------------------------------

    public static int validateId(Scanner scanner)
    {
        return InputValidator.promptId(scanner, "Enter Student ID: ");
    }

    public static int validateId(Scanner scanner, String prompt)
    {
        return InputValidator.promptId(scanner, prompt);
    }

    // -------------------------------------------------
    // Reporting and Analytics
    // -------------------------------------------------

    /**
     * Displays a breakdown report of grade counts and percentage shares.
     */
    public void generateGradeReport()
    {
        System.out.println("\n--- Grade Distribution ---");
        Map<String, Integer> gradeCount = studentDao.getGradeDistribution();
        renderGradeBreakdownTable(gradeCount);
    }

    /**
     * Prompts for an age interval and displays enrolled students within it.
     *
     * @param scanner input scanner
     */
    public void generateAgeRangeReport(Scanner scanner)
    {
        int minAge = InputValidator.promptAge(scanner, "\nMinimum age (5-120): ");
        int maxAge = InputValidator.promptAge(scanner, "Maximum age (5-120): ");

        while (minAge > maxAge)
        {
            System.out.println("Minimum age cannot exceed maximum age.");
            minAge = InputValidator.promptAge(scanner, "Minimum age (5-120): ");
            maxAge = InputValidator.promptAge(scanner, "Maximum age (5-120): ");
        }

        System.out.println("\n--- Age Range (" + minAge + " - " + maxAge + ") ---");
        List<Student> ageFiltered = studentDao.getStudentsByAgeRange(minAge, maxAge);
        displayStudents(ageFiltered);
    }

    /**
     * Displays aggregate summary statistics including headcount and mean age.
     */
    public void generateSummaryStatisticsReport()
    {
        System.out.println("\n--- Summary Statistics ---");

        int totalStudents = studentDao.getTotalStudentCount();
        if (totalStudents == 0)
        {
            System.out.println("No student records found.");
            return;
        }

        double averageAge = studentDao.getAverageAge();
        System.out.println("Total Students : " + totalStudents);
        System.out.printf("Average Age    : %.2f%n", averageAge);

        System.out.println("\nGrade Breakdown:");
        Map<String, Integer> gradeCount = studentDao.getGradeDistribution();
        renderGradeBreakdownTable(gradeCount);
    }

    /**
     * Displays top-performing students ranked by academic tier.
     */
    public void generateTopPerformersReport()
    {
        List<Student> topPerformers = studentDao.getTopPerformers(10);
        System.out.println("\n--- Top Performers ---");
        displayStudents(topPerformers);
    }

    private void renderGradeBreakdownTable(Map<String, Integer> gradeCount)
    {
        if (gradeCount == null || gradeCount.isEmpty())
        {
            System.out.println("No student records found.");
            return;
        }

        int total = gradeCount.values().stream().mapToInt(Integer::intValue).sum();

        System.out.println("-----------------------");
        System.out.printf(" %-6s| %-6s| %-6s%n", "Grade", "Count", "Share");
        System.out.println("-----------------------");

        for (Map.Entry<String, Integer> entry : gradeCount.entrySet())
        {
            String grade = entry.getKey();
            int count = entry.getValue();
            double share = total > 0 ? (count * 100.0) / total : 0.0;
            System.out.printf("   %-4s|  %4d | %5.1f%%%n", grade, count, share);
        }

        System.out.println("-----------------------");
        System.out.printf(" %-6s|  %4d | %5.1f%%%n", "Total", total, 100.0);
        System.out.println("-----------------------");
    }
}
