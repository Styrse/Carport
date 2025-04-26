package app.entities;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CarportTest {
    @Nested
    @DisplayName("Total Posts Calculation Tests")
    class TotalPostsTests {

        @Test
        @DisplayName("Simple small carport")
        void calcTotalPosts_simpleCase() {
            // Arrange
            Carport carport = new Carport(300, 300, "flat");

            // Expected
            int expected = 4;

            // Act
            int actual = carport.calcTotalPosts();

            // Assert
            assertEquals(expected, actual);
        }
    }

    @Nested
    @DisplayName("Width Calculation Tests")
    class WidthTests {

        @Test
        @DisplayName("Width test 1: just below maxPlankLength")
        void calcPostAmountWidth_test1() {
            // Arrange
            Carport carport = new Carport(599, 600, "flat");
            // Expected
            int expected = 2;
            // Act
            int actual = carport.calcPostAmountWidth();
            // Assert
            assertEquals(expected, actual);
        }

        @Test
        @DisplayName("Width test 2: exactly maxPlankLength")
        void calcPostAmountWidth_test2() {
            // Arrange
            Carport carport = new Carport(600, 600, "flat");
            // Expected
            int expected = 2;
            // Act
            int actual = carport.calcPostAmountWidth();
            // Assert
            assertEquals(expected, actual);
        }

        @Test
        @DisplayName("Width test 3: just above maxPlankLength")
        void calcPostAmountWidth_test3() {
            // Arrange
            Carport carport = new Carport(601, 600, "flat");
            // Expected
            int expected = 3;
            // Act
            int actual = carport.calcPostAmountWidth();
            // Assert
            assertEquals(expected, actual);
        }

        @Test
        @DisplayName("Width test 4: much bigger width")
        void calcPostAmountWidth_test4() {
            // Arrange
            Carport carport = new Carport(1300, 600, "flat");
            // Expected
            int expected = 4;
            // Act
            int actual = carport.calcPostAmountWidth();
            // Assert
            assertEquals(expected, actual);
        }

        @Nested
        @DisplayName("Width Calculation Stress Tests")
        class WidthStressTests {

            @Test
            @DisplayName("Width stress test 1: test very small number")
            void calcPostAmountWidth_stress_test1() {
                // Arrange
                Carport carport = new Carport(50, 600, "flat");
                // Expected
                int expected = 2;
                // Act
                int actual = carport.calcPostAmountWidth();
                // Assert
                assertEquals(expected, actual);
            }

            @Test
            @DisplayName("Width stress test 2: test very big number")
            void calcPostAmountWidth_stress_test2() {
                // Arrange
                Carport carport = new Carport(5000, 600, "flat");
                // Expected
                int expected = 10;
                // Act
                int actual = carport.calcPostAmountWidth();
                // Assert
                assertEquals(expected, actual);
            }
        }

    }

    @Nested
    @DisplayName("Length Calculation Tests")
    class LengthTests {

        @Test
        @DisplayName("Length test 1: just below maxLengthBetweenPost")
        void calcPostAmountLength_test1() {
            // Arrange
            Carport carport = new Carport(600, 300, "flat");
            // Expected
            int expected = 2;
            // Act
            int actual = carport.calcPostAmountLength();
            // Assert
            assertEquals(expected, actual);
        }

        @Test
        @DisplayName("Length test 2: exactly at limit")
        void calcPostAmountLength_test2() {
            // Arrange
            Carport carport = new Carport(600, 370, "flat"); // 340 + 30 = 370
            // Expected
            int expected = 2;
            // Act
            int actual = carport.calcPostAmountLength();
            // Assert
            assertEquals(expected, actual);
        }

        @Test
        @DisplayName("Length test 3: just over limit")
        void calcPostAmountLength_test3() {
            // Arrange
            Carport carport = new Carport(600, 371, "flat");
            // Expected
            int expected = 3;
            // Act
            int actual = carport.calcPostAmountLength();
            // Assert
            assertEquals(expected, actual);
        }

        @Test
        @DisplayName("Length test 4: bigger length but still 3 posts")
        void calcPostAmountLength_test4() {
            // Arrange
            Carport carport = new Carport(600, 710, "flat");
            // Expected
            int expected = 3;
            // Act
            int actual = carport.calcPostAmountLength();
            // Assert
            assertEquals(expected, actual);
        }

        @Test
        @DisplayName("Length test 5: even bigger length needs 4 posts")
        void calcPostAmountLength_test5() {
            // Arrange
            Carport carport = new Carport(600, 711, "flat");
            // Expected
            int expected = 4;
            // Act
            int actual = carport.calcPostAmountLength();
            // Assert
            assertEquals(expected, actual);
        }


        @Nested
        @DisplayName("Length Calculation Stress Tests")
        class LengthStressTests {

            @Test
            @DisplayName("Length stress test 1: test very small number")
            void calcPostAmountLength_stress_test1() {
                // Arrange
                Carport carport = new Carport(600, 50, "flat");
                // Expected
                int expected = 2;
                // Act
                int actual = carport.calcPostAmountLength();
                // Assert
                assertEquals(expected, actual);
            }

            @Test
            @DisplayName("Length stress test 2: test very big number")
            void calcPostAmountLength_stress_test2() {
                // Arrange
                Carport carport = new Carport(600, 5000, "flat");
                // Expected
                int expected = 16;
                // Act
                int actual = carport.calcPostAmountLength();
                // Assert
                assertEquals(expected, actual);
            }
        }
    }
}
