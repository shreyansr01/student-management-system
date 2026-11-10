package com.student.management.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("InputValidator Tests")
class InputValidatorTest
{

    @Nested
    @DisplayName("Name Validation")
    class NameValidationTests
    {
        @ParameterizedTest
        @ValueSource(strings = {"John Doe", "Alice", "Mary Jane Watson", "O Connor"})
        @DisplayName("Valid names with letters and spaces pass")
        void testValidNames(String name)
        {
            assertTrue(InputValidator.isValidName(name));
        }

        @ParameterizedTest
        @ValueSource(strings = {"", "   ", "John123", "Jane@Doe", "Alex_Smith", "#!?"})
        @DisplayName("Invalid names with numbers or symbols fail")
        void testInvalidNames(String name)
        {
            assertFalse(InputValidator.isValidName(name));
        }

        @Test
        @DisplayName("Null name fails")
        void testNullName()
        {
            assertFalse(InputValidator.isValidName(null));
        }
    }

    @Nested
    @DisplayName("Age Validation")
    class AgeValidationTests
    {
        @ParameterizedTest
        @ValueSource(ints = {5, 6, 20, 50, 100, 120})
        @DisplayName("Valid ages between 5 and 120 inclusive pass")
        void testValidAges(int age)
        {
            assertTrue(InputValidator.isValidAge(age));
        }

        @ParameterizedTest
        @ValueSource(ints = {-10, 0, 1, 4, 121, 200})
        @DisplayName("Ages below 5 or above 120 fail")
        void testInvalidAges(int age)
        {
            assertFalse(InputValidator.isValidAge(age));
        }
    }

    @Nested
    @DisplayName("Grade Validation")
    class GradeValidationTests
    {
        @ParameterizedTest
        @ValueSource(strings = {"O", "E", "A", "B", "C", "D", "F", "o", "e", "a", "b", "c", "d", "f"})
        @DisplayName("Valid standard letter grades pass (case-insensitive)")
        void testValidGrades(String grade)
        {
            assertTrue(InputValidator.isValidGrade(grade));
        }

        @ParameterizedTest
        @ValueSource(strings = {"", "  ", "G", "Z", "A+", "A-", "100", "PASS"})
        @DisplayName("Non-standard grade inputs fail")
        void testInvalidGrades(String grade)
        {
            assertFalse(InputValidator.isValidGrade(grade));
        }

        @Test
        @DisplayName("Null grade fails")
        void testNullGrade()
        {
            assertFalse(InputValidator.isValidGrade(null));
        }
    }

    @Nested
    @DisplayName("ID Validation")
    class IdValidationTests
    {
        @ParameterizedTest
        @ValueSource(ints = {1, 2, 42, 9999})
        @DisplayName("Positive integer IDs pass")
        void testValidIds(int id)
        {
            assertTrue(InputValidator.isValidId(id));
        }

        @ParameterizedTest
        @ValueSource(ints = {0, -1, -50})
        @DisplayName("Zero or negative IDs fail")
        void testInvalidIds(int id)
        {
            assertFalse(InputValidator.isValidId(id));
        }
    }
}
