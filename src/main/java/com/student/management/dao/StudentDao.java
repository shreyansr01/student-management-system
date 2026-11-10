package com.student.management.dao;

import com.student.management.model.Student;
import com.student.management.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Data Access Object for persisting and querying student records in the database.
 */
public class StudentDao
{

    /**
     * Inserts a new student record into the database.
     *
     * @param name  student full name
     * @param age   student age
     * @param grade letter grade
     * @return true if inserted successfully, false otherwise
     */
    public boolean addStudent(String name, int age, String grade)
    {
        String sql = "INSERT INTO students (name, age, grade) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql))
        {
            ps.setString(1, name);
            ps.setInt(2, age);
            ps.setString(3, grade);
            return ps.executeUpdate() > 0;
        }
        catch (SQLException e)
        {
            System.err.println("Database error adding student: " + e.getMessage());
            return false;
        }
    }

    /**
     * Retrieves all student records.
     *
     * @return list of student records
     */
    public List<Student> getAllStudents()
    {
        String sql = "SELECT id, name, age, grade FROM students";
        List<Student> students = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery())
        {
            while (rs.next())
            {
                students.add(mapRow(rs));
            }
        }
        catch (SQLException e)
        {
            System.err.println("Database error fetching all students: " + e.getMessage());
        }
        return students;
    }

    /**
     * Finds a single student by identifier.
     *
     * @param id student unique identifier
     * @return matching Student entity, or null if not found
     */
    public Student getStudentById(int id)
    {
        String sql = "SELECT id, name, age, grade FROM students WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql))
        {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery())
            {
                if (rs.next())
                {
                    return mapRow(rs);
                }
            }
        }
        catch (SQLException e)
        {
            System.err.println("Database error searching student by ID: " + e.getMessage());
        }
        return null;
    }

    /**
     * Searches for students matching a name substring.
     *
     * @param nameQuery partial or full name
     * @return matching student list
     */
    public List<Student> getStudentsByName(String nameQuery)
    {
        String sql = "SELECT id, name, age, grade FROM students WHERE name LIKE ?";
        List<Student> students = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql))
        {
            ps.setString(1, "%" + nameQuery + "%");
            try (ResultSet rs = ps.executeQuery())
            {
                while (rs.next())
                {
                    students.add(mapRow(rs));
                }
            }
        }
        catch (SQLException e)
        {
            System.err.println("Database error searching students by name: " + e.getMessage());
        }
        return students;
    }

    /**
     * Searches for students with a specific letter grade.
     *
     * @param grade letter grade
     * @return matching student list
     */
    public List<Student> getStudentsByGrade(String grade)
    {
        String sql = "SELECT id, name, age, grade FROM students WHERE grade = ?";
        List<Student> students = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql))
        {
            ps.setString(1, grade);
            try (ResultSet rs = ps.executeQuery())
            {
                while (rs.next())
                {
                    students.add(mapRow(rs));
                }
            }
        }
        catch (SQLException e)
        {
            System.err.println("Database error searching students by grade: " + e.getMessage());
        }
        return students;
    }

    /**
     * Deletes a student by identifier.
     *
     * @param id student unique identifier
     * @return true if deleted, false otherwise
     */
    public boolean deleteStudentById(int id)
    {
        String sql = "DELETE FROM students WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql))
        {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
        catch (SQLException e)
        {
            System.err.println("Database error deleting student: " + e.getMessage());
            return false;
        }
    }

    /**
     * Updates an existing student's record.
     *
     * @param id       student identifier
     * @param newName  updated name
     * @param newAge   updated age
     * @param newGrade updated letter grade
     * @return true if updated, false otherwise
     */
    public boolean updateStudent(int id, String newName, int newAge, String newGrade)
    {
        String sql = "UPDATE students SET name = ?, age = ?, grade = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql))
        {
            ps.setString(1, newName);
            ps.setInt(2, newAge);
            ps.setString(3, newGrade);
            ps.setInt(4, id);
            return ps.executeUpdate() > 0;
        }
        catch (SQLException e)
        {
            System.err.println("Database error updating student: " + e.getMessage());
            return false;
        }
    }

    /**
     * Retrieves grade distribution counts ordered by academic tier.
     *
     * @return ordered map of letter grades to student counts
     */
    public Map<String, Integer> getGradeDistribution()
    {
        Map<String, Integer> gradeCount = new LinkedHashMap<>();
        String sql = """
                SELECT grade, COUNT(*) AS count
                FROM students
                GROUP BY grade
                ORDER BY
                    CASE grade
                        WHEN 'O' THEN 7
                        WHEN 'E' THEN 6
                        WHEN 'A' THEN 5
                        WHEN 'B' THEN 4
                        WHEN 'C' THEN 3
                        WHEN 'D' THEN 2
                        WHEN 'F' THEN 1
                        ELSE 0
                    END DESC;
                """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery())
        {
            while (rs.next())
            {
                gradeCount.put(rs.getString("grade"), rs.getInt("count"));
            }
        }
        catch (SQLException e)
        {
            System.err.println("Database error fetching grade distribution: " + e.getMessage());
        }
        return gradeCount;
    }

    /**
     * Retrieves students within a specified age range.
     *
     * @param minAge minimum age inclusive
     * @param maxAge maximum age inclusive
     * @return list of matching students
     */
    public List<Student> getStudentsByAgeRange(int minAge, int maxAge)
    {
        String sql = "SELECT id, name, age, grade FROM students WHERE age BETWEEN ? AND ?";
        List<Student> students = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql))
        {
            ps.setInt(1, minAge);
            ps.setInt(2, maxAge);
            try (ResultSet rs = ps.executeQuery())
            {
                while (rs.next())
                {
                    students.add(mapRow(rs));
                }
            }
        }
        catch (SQLException e)
        {
            System.err.println("Database error filtering by age range: " + e.getMessage());
        }
        return students;
    }

    /**
     * Returns the total count of students currently enrolled.
     *
     * @return total student count
     */
    public int getTotalStudentCount()
    {
        String sql = "SELECT COUNT(*) AS total FROM students";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery())
        {
            if (rs.next())
            {
                return rs.getInt("total");
            }
        }
        catch (SQLException e)
        {
            System.err.println("Database error counting students: " + e.getMessage());
        }
        return 0;
    }

    /**
     * Computes the average age of enrolled students.
     *
     * @return average age
     */
    public double getAverageAge()
    {
        String sql = "SELECT AVG(age) AS avg_age FROM students";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery())
        {
            if (rs.next())
            {
                return rs.getDouble("avg_age");
            }
        }
        catch (SQLException e)
        {
            System.err.println("Database error computing average age: " + e.getMessage());
        }
        return 0.0;
    }

    /**
     * Retrieves the top performing students ordered by grade tier.
     *
     * @param limit maximum records to retrieve
     * @return top student list
     */
    public List<Student> getTopPerformers(int limit)
    {
        String sql = """
                SELECT id, name, age, grade
                FROM students
                ORDER BY
                    CASE grade
                        WHEN 'O' THEN 7
                        WHEN 'E' THEN 6
                        WHEN 'A' THEN 5
                        WHEN 'B' THEN 4
                        WHEN 'C' THEN 3
                        WHEN 'D' THEN 2
                        WHEN 'F' THEN 1
                        ELSE 0
                    END DESC
                LIMIT ?;
                """;

        List<Student> topPerformers = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql))
        {
            ps.setInt(1, limit);
            try (ResultSet rs = ps.executeQuery())
            {
                while (rs.next())
                {
                    topPerformers.add(mapRow(rs));
                }
            }
        }
        catch (SQLException e)
        {
            System.err.println("Database error fetching top performers: " + e.getMessage());
        }
        return topPerformers;
    }

    private Student mapRow(ResultSet rs) throws SQLException
    {
        return new Student(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getInt("age"),
                rs.getString("grade")
        );
    }
}
