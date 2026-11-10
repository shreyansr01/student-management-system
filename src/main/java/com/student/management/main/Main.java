package com.student.management.main;

import com.student.management.model.Student;
import com.student.management.service.StudentManager;

import java.util.Scanner;

/**
 * Application entry point providing the interactive console menu interface.
 */
public class Main
{

    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);
        StudentManager studentManager = new StudentManager();

        showHeader();

        while (true)
        {
            displayMenu();
            String input = scanner.nextLine().trim();

            try
            {
                int choice = Integer.parseInt(input);

                if (choice == 0)
                {
                    exitApplication(scanner);
                    return;
                }

                handleChoice(choice, scanner, studentManager);
            }
            catch (NumberFormatException e)
            {
                System.out.println("\nInvalid option. Enter a number (0-11).");
            }
        }
    }

    // ======================== Console Display Methods ========================

    /**
     * Prints a sleek header banner at the start of the program.
     */
    private static void showHeader()
    {
        System.out.println("+--------------------------------------------------------+");
        System.out.println("|               STUDENT MANAGEMENT SYSTEM                |");
        System.out.println("+--------------------------------------------------------+");
    }

    /**
     * Prints the two-column options menu for student operations and reporting features.
     */
    private static void displayMenu()
    {
        System.out.println();
        System.out.println("  [ Records ]                       [ Reports ]");
        System.out.println("   1. Add Student                    8. Grade Distribution");
        System.out.println("   2. View All Students              9. Age Analysis");
        System.out.println("   3. Search by ID                  10. Summary Statistics");
        System.out.println("   4. Search by Name                11. Top Performers");
        System.out.println("   5. Search by Grade");
        System.out.println("   6. Delete Student                 0. Exit");
        System.out.println("   7. Update Student");
        System.out.println();
        System.out.println("----------------------------------------------------------");
        System.out.print("Select option [0-11]: ");
    }

    // ======================== Menu Dispatcher ========================

    /**
     * Routes the selected menu option to the corresponding operation.
     *
     * @param choice  menu option number
     * @param scanner input scanner
     * @param manager service manager
     */
    private static void handleChoice(int choice, Scanner scanner, StudentManager manager)
    {
        switch (choice)
        {
            case 1:
                addStudent(scanner, manager);
                break;
            case 2:
                manager.viewAllStudents();
                break;
            case 3:
                searchStudentById(scanner, manager);
                break;
            case 4:
                manager.searchByName(scanner);
                break;
            case 5:
                manager.searchByGrade(scanner);
                break;
            case 6:
                manager.removeStudentById(scanner);
                break;
            case 7:
                manager.updateStudentById(scanner);
                break;
            case 8:
                manager.generateGradeReport();
                break;
            case 9:
                manager.generateAgeRangeReport(scanner);
                break;
            case 10:
                manager.generateSummaryStatisticsReport();
                break;
            case 11:
                manager.generateTopPerformersReport();
                break;
            default:
                System.out.println("Invalid option. Enter a number (0-11).");
                break;
        }
    }

    // ======================== Student Actions ========================

    /**
     * Tells the student manager to start the steps for adding a new student.
     *
     * @param scanner Scanner to read input
     * @param manager Service instance
     */
    private static void addStudent(Scanner scanner, StudentManager manager)
    {
        manager.addStudentFromInput(scanner);
    }

    /**
     * Asks the user for an ID, searches for that student, and prints their details if found.
     *
     * @param scanner Scanner to read the ID
     * @param manager Service instance to search the database
     */
    private static void searchStudentById(Scanner scanner, StudentManager manager)
    {
        int id = StudentManager.validateId(scanner, "\nEnter student ID: ");

        Student student = manager.searchById(id);
        if (student != null)
        {
            System.out.println(student);
        }
        else
        {
            System.out.println("No student found with ID: " + id);
        }
    }

    // ======================== Lifecycle and Cleanup ========================

    /**
     * Safely closes the Scanner resource and prints an exit message.
     *
     * @param scanner The Scanner tool to close
     */
    private static void exitApplication(Scanner scanner)
    {
        scanner.close();
        System.out.println("\nApplication exited.");
    }
}
