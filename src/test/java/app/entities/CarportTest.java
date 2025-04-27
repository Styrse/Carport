package app.entities;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CarportTest {

    @Nested
    @DisplayName("Posts Test")
    class Posts {

        @Nested
        @DisplayName("Posts Width Calculation Tests")
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
        @DisplayName("Posts Length Calculation Tests")
        class PostAmountLengthTests {

            @Test
            @DisplayName("Posts test 1: just below maxLengthBetweenPost")
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
            @DisplayName("Posts test 2: exactly at limit")
            void calcPostAmountLength_test2() {
                // Arrange
                Carport carport = new Carport(600, 370, "flat");
                // Expected
                int expected = 2;
                // Act
                int actual = carport.calcPostAmountLength();
                // Assert
                assertEquals(expected, actual);
            }

            @Test
            @DisplayName("Posts test 3: just over limit")
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
            @DisplayName("Posts test 4: bigger length but still 3 posts")
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
            @DisplayName("Posts test 5: even bigger length needs 4 posts")
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
            @DisplayName("Post Amount Length Stress Tests")
            class PostAmountLengthStressTests {

                @Test
                @DisplayName("Posts stress test 1: very small carport")
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
                @DisplayName("Posts stress test 2: very large carport")
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

        @Nested
        @DisplayName("Total Posts Calculation Tests")
        class TotalPostsTests {

            @Test
            @DisplayName("Total Posts Calculation Tests")
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
    }

    @Nested
    @DisplayName("Beams Test")
    class Beams {
        @Nested
        @DisplayName("Posts Length Calculation Tests")
        class BeamAmountLengthTests {

            @Test
            @DisplayName("Beams test 1: small carport, no extra beams")
            void calcBeamAmountLength_test1() {
                // Arrange
                Carport carport = new Carport(300, 500, "flat");
                // Expected
                int expected = 1;
                // Act
                int actual = carport.calcBeamAmountLength();
                // Assert
                assertEquals(expected, actual);
            }

            @Test
            @DisplayName("Beams test 2: exactly at beam limit (600 cm)")
            void calcBeamAmountLength_test2() {
                // Arrange
                Carport carport = new Carport(300, 600, "flat");
                // Expected
                int expected = 1;
                // Act
                int actual = carport.calcBeamAmountLength();
                // Assert
                assertEquals(expected, actual);
            }

            @Test
            @DisplayName("Beams test 3: just over beam limit (601 cm)")
            void calcBeamAmountLength_test3() {
                // Arrange
                Carport carport = new Carport(300, 601, "flat");
                // Expected
                int expected = 2;
                // Act
                int actual = carport.calcBeamAmountLength();
                // Assert
                assertEquals(expected, actual);
            }

            @Test
            @DisplayName("Beams test 4: bigger length, multiple extra beams")
            void calcBeamAmountLength_test4() {
                // Arrange
                Carport carport = new Carport(300, 1250, "flat");
                // Expected
                int expected = 3;
                // Act
                int actual = carport.calcBeamAmountLength();
                // Assert
                assertEquals(expected, actual);
            }

            @Nested
            @DisplayName("Beam Amount Length Stress Tests")
            class BeamAmountLengthStressTests {

                @Test
                @DisplayName("Beams stress test 1: very small carport")
                void calcBeamAmountLength_stress_test1() {
                    // Arrange
                    Carport carport = new Carport(300, 50, "flat");
                    // Expected
                    int expected = 1;
                    // Act
                    int actual = carport.calcBeamAmountLength();
                    // Assert
                    assertEquals(expected, actual);
                }

                @Test
                @DisplayName("Beams stress test 2: very large carport")
                void calcBeamAmountLength_stress_test2() {
                    // Arrange
                    Carport carport = new Carport(300, 5000, "flat");
                    // Expected
                    int expected = 9;
                    // Act
                    int actual = carport.calcBeamAmountLength();
                    // Assert
                    assertEquals(expected, actual);
                }
            }
        }

        @Nested
        @DisplayName("Total Beams Calculation Tests")
        class TotalBeamsTests {

            @Test
            @DisplayName("Total beams test 1: small carport")
            void calcTotalBeams_smallCarport() {
                // Arrange
                Carport carport = new Carport(300, 500, "flat");
                // Expected
                int expected = 2;
                // Act
                int actual = carport.calcTotalBeams();
                // Assert
                assertEquals(expected, actual);
            }

            @Test
            @DisplayName("Total beams test 2: wider carport")
            void calcTotalBeams_wideCarport() {
                // Arrange
                Carport carport = new Carport(1300, 500, "flat");
                // Expected
                int expected = 4;
                // Act
                int actual = carport.calcTotalBeams();
                // Assert
                assertEquals(expected, actual);
            }

            @Test
            @DisplayName("Total beams test 3: longer carport")
            void calcTotalBeams_longCarport() {
                // Arrange
                Carport carport = new Carport(300, 1250, "flat");
                // Expected
                int expected = 6;
                // Act
                int actual = carport.calcTotalBeams();
                // Assert
                assertEquals(expected, actual);
            }

            @Test
            @DisplayName("Total beams test 4: wide and long carport")
            void calcTotalBeams_wideLongCarport() {
                // Arrange
                Carport carport = new Carport(1300, 1250, "flat");
                // Expected
                int expected = 12;
                // Act
                int actual = carport.calcTotalBeams();
                // Assert
                assertEquals(expected, actual);
            }
        }
    }

    @Nested
    @DisplayName("Rafters Test")
    class Rafters {

        @Nested
        @DisplayName("Rafters Length Calculation Tests")
        class RaftersLengthTests {

            @Test
            @DisplayName("Rafters length test 1: very small carport")
            void calcRaftersLength_smallCarport() {
                // Arrange
                Carport carport = new Carport(300, 50, "flat");
                // Expected
                int expected = 2;
                // Act
                int actual = carport.calcRaftersLength();
                // Assert
                assertEquals(expected, actual);
            }

            @Test
            @DisplayName("Rafters length test 2: exactly at limit (55 cm)")
            void calcRaftersLength_exactLimit() {
                // Arrange
                Carport carport = new Carport(300, 55, "flat");
                // Expected
                int expected = 2;
                // Act
                int actual = carport.calcRaftersLength();
                // Assert
                assertEquals(expected, actual);
            }

            @Test
            @DisplayName("Rafters length test 3: just over limit (56 cm)")
            void calcRaftersLength_justOverLimit() {
                // Arrange
                Carport carport = new Carport(300, 56, "flat");
                // Expected
                int expected = 3;
                // Act
                int actual = carport.calcRaftersLength();
                // Assert
                assertEquals(expected, actual);
            }

            @Test
            @DisplayName("Rafters length test: 500 cm carport")
            void calcRaftersLength_500() {
                // Arrange
                Carport carport = new Carport(300, 500, "flat");
                // Expected
                int expected = 11;
                // Act
                int actual = carport.calcRaftersLength();
                // Assert
                assertEquals(expected, actual);
            }

            @Test
            @DisplayName("Rafters length test: 700 cm carport")
            void calcRaftersLength_700() {
                // Arrange
                Carport carport = new Carport(300, 700, "flat");
                // Expected
                int expected = 14;
                // Act
                int actual = carport.calcRaftersLength();
                // Assert
                assertEquals(expected, actual);
            }

            @Test
            @DisplayName("Rafters length test: 1200 cm carport")
            void calcRaftersLength_1200() {
                // Arrange
                Carport carport = new Carport(300, 1200, "flat");
                // Expected
                int expected = 23;
                // Act
                int actual = carport.calcRaftersLength();
                // Assert
                assertEquals(expected, actual);
            }

            @Test
            @DisplayName("Rafters length stress test: very large carport")
            void calcRaftersLength_stressTest() {
                // Arrange
                Carport carport = new Carport(300, 5000, "flat");
                // Expected
                int expected = 92;
                // Act
                int actual = carport.calcRaftersLength();
                // Assert
                assertEquals(expected, actual);
            }
        }

        @Nested
        @DisplayName("Rafters Width Calculation Tests")
        class RaftersWidthTests {

            @Test
            @DisplayName("Rafters width test 1: small width, no extra rafters")
            void calcRaftersWidth_smallWidth() {
                // Arrange
                Carport carport = new Carport(500, 300, "flat");
                // Expected
                int expected = 1;
                // Act
                int actual = carport.calcRaftersWidth();
                // Assert
                assertEquals(expected, actual);
            }

            @Test
            @DisplayName("Rafters width test 2: exactly at limit (600 cm)")
            void calcRaftersWidth_exactLimit() {
                // Arrange
                Carport carport = new Carport(600, 300, "flat");
                // Expected
                int expected = 1;
                // Act
                int actual = carport.calcRaftersWidth();
                // Assert
                assertEquals(expected, actual);
            }

            @Test
            @DisplayName("Rafters width test 3: just over limit (601 cm)")
            void calcRaftersWidth_justOverLimit() {
                // Arrange
                Carport carport = new Carport(601, 300, "flat");
                // Expected
                int expected = 2;
                // Act
                int actual = carport.calcRaftersWidth();
                // Assert
                assertEquals(expected, actual);
            }

            @Test
            @DisplayName("Rafters width stress test: very wide carport")
            void calcRaftersWidth_stressTest() {
                // Arrange
                Carport carport = new Carport(5000, 300, "flat");
                // Expected
                int expected = 9;
                // Act
                int actual = carport.calcRaftersWidth();
                // Assert
                assertEquals(expected, actual);
            }
        }

        @Nested
        @DisplayName("Total Rafters Calculation Tests")
        class TotalRaftersTests {

            @Test
            @DisplayName("Total rafters test: normal carport")
            void calcRafters_normalCarport() {
                // Arrange
                Carport carport = new Carport(500, 500, "flat");
                // Expected
                int expected = 11;
                // Act
                int actual = carport.calcRafters();
                // Assert
                assertEquals(expected, actual);
            }

            @Test
            @DisplayName("Total rafters test: large carport")
            void calcRafters_largeCarport() {
                // Arrange
                Carport carport = new Carport(5000, 5000, "flat");
                // Expected
                int expected = 828;
                // Act
                int actual = carport.calcRafters();
                // Assert
                assertEquals(expected, actual);
            }
        }
    }
}
