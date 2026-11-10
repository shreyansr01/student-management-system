package com.student.management.util;

import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * Validates and prompts for student entity inputs.
 */
public final class InputValidator
{
    private static final Pattern NAME_PATTERN = Pattern.compile("^[a-zA-Z\\s]+$");
    private static final Pattern GRADE_PATTERN = Pattern.compile("^(O|E|A|B|C|D|F)$");

    private InputValidator()
    {
    }

    public static boolean isValidName(String name)
    {
        return name != null && !name.trim().isEmpty() && NAME_PATTERN.matcher(name.trim()).matches();
    }

    public static boolean isValidAge(int age)
    {
        return age >= 5 && age <= 120;
    }

    public static boolean isValidGrade(String grade)
    {
        return grade != null && GRADE_PATTERN.matcher(grade.trim().toUpperCase()).matches();
    }

    public static boolean isValidId(int id)
    {
        return id > 0;
    }

    public static int promptId(Scanner scanner, String prompt)
    {
        while (true)
        {
            try
            {
                System.out.print(prompt);
                int id = Integer.parseInt(scanner.nextLine().trim());
                if (isValidId(id))
                {
                    return id;
                }
                System.out.println("ID must be a positive integer.");
            }
            catch (NumberFormatException e)
            {
                System.out.println("Invalid input. Enter a valid integer.");
            }
        }
    }

    public static String promptName(Scanner scanner, String prompt)
    {
        while (true)
        {
            System.out.print(prompt);
            String name = scanner.nextLine().trim();
            if (isValidName(name))
            {
                return name;
            }
            System.out.println("Invalid name. Letters and spaces only.");
        }
    }

    public static int promptAge(Scanner scanner, String prompt)
    {
        while (true)
        {
            try
            {
                System.out.print(prompt);
                int age = Integer.parseInt(scanner.nextLine().trim());
                if (isValidAge(age))
                {
                    return age;
                }
                System.out.println("Age must be between 5 and 120.");
            }
            catch (NumberFormatException e)
            {
                System.out.println("Invalid input. Enter a valid integer.");
            }
        }
    }

    public static String promptGrade(Scanner scanner, String prompt)
    {
        while (true)
        {
            System.out.print(prompt);
            String grade = scanner.nextLine().trim().toUpperCase();
            if (isValidGrade(grade))
            {
                return grade;
            }
            System.out.println("Invalid grade. Allowed: O, E, A, B, C, D, F.");
        }
    }
}
