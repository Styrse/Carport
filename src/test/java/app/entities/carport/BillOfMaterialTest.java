package app.entities.carport;

import app.entities.materials.planksAndRoofCovers.roof.RoofCover;
import app.testutils.TestCarportFactory;
import app.testutils.TestPlankFactory;
import app.testutils.TestRoofCoverFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * BillOfMaterial RoofCover Calculation Tests
 * <p>
 * Full white-box testing using controlled RoofCover test data.
 * <p>
 * Strategy:
 * - Vary Carport dimensions independently
 * - Vary RoofCover material properties independently
 * - Edge case and stress case testing
 * - Cross-tests combining dimension and material changes
 * <p>
 * Based on clean Arrange - Act - Assert structure for clarity.
 */
class BillOfMaterialTest {

    @Nested
    @DisplayName("Structure Tests")
    class Structure {

        @Nested
        @DisplayName("Posts Tests")
        class Posts {

            @Nested
            @DisplayName("Post Count Width Calculation (calcPostCountWidth)")
            class PostCountWidthTests {

                @Nested
                @DisplayName("Standard Cases")
                class Standard {

                    @Test
                    @DisplayName("Width smaller than standard rafter max length")
                    void widthSmallerThanStandard() {
                        // Arrange
                        Carport carport = TestCarportFactory.createCarportWithRafter(
                                TestPlankFactory.createStandardRafter(),
                                400, 600
                        );
                        BillOfMaterial bom = new BillOfMaterial(carport);

                        // Act
                        int actual = bom.calcPostCountWidth();

                        // Assert
                        int expected = 2;
                        assertEquals(expected, actual);
                    }

                    @Test
                    @DisplayName("Width smaller than shorter rafter max length")
                    void widthSmallerThanShorter() {
                        // Arrange
                        Carport carport = TestCarportFactory.createCarportWithRafter(
                                TestPlankFactory.createShorterRafter(),
                                350, 600
                        );
                        BillOfMaterial bom = new BillOfMaterial(carport);

                        // Act
                        int actual = bom.calcPostCountWidth();

                        // Assert
                        int expected = 2;
                        assertEquals(expected, actual);
                    }

                    @Test
                    @DisplayName("Width smaller than longer rafter max length")
                    void widthSmallerThanLonger() {
                        // Arrange
                        Carport carport = TestCarportFactory.createCarportWithRafter(
                                TestPlankFactory.createLongerRafter(),
                                650, 600
                        );
                        BillOfMaterial bom = new BillOfMaterial(carport);

                        // Act
                        int actual = bom.calcPostCountWidth();

                        // Assert
                        int expected = 2;
                        assertEquals(expected, actual);
                    }
                }

                @Nested
                @DisplayName("Edge Cases with Different Rafters")
                class EdgeCases {

                    @Test
                    @DisplayName("Width just over shorter rafter max length (needs extra post)")
                    void widthOverShorterRafter() {
                        // Arrange
                        Carport carport = TestCarportFactory.createCarportWithRafter(
                                TestPlankFactory.createShorterRafter(),
                                410, 600
                        );
                        BillOfMaterial bom = new BillOfMaterial(carport);

                        // Act
                        int actual = bom.calcPostCountWidth();

                        // Assert
                        int expected = 3;
                        assertEquals(expected, actual);
                    }

                    @Test
                    @DisplayName("Width just over standard rafter max length (needs extra post)")
                    void widthOverStandardRafter() {
                        // Arrange
                        Carport carport = TestCarportFactory.createCarportWithRafter(
                                TestPlankFactory.createStandardRafter(),
                                610, 600
                        );
                        BillOfMaterial bom = new BillOfMaterial(carport);

                        // Act
                        int actual = bom.calcPostCountWidth();

                        // Assert
                        int expected = 3;
                        assertEquals(expected, actual);
                    }

                    @Test
                    @DisplayName("Width just over longer rafter max length (needs extra post)")
                    void widthOverLongerRafter() {
                        // Arrange
                        Carport carport = TestCarportFactory.createCarportWithRafter(
                                TestPlankFactory.createLongerRafter(),
                                710, 600
                        );
                        BillOfMaterial bom = new BillOfMaterial(carport);

                        // Act
                        int actual = bom.calcPostCountWidth();

                        // Assert
                        int expected = 3;
                        assertEquals(expected, actual);
                    }
                }

                @Nested
                @DisplayName("Stress Tests with Different Rafters")
                class Stress {

                    @Test
                    @DisplayName("Huge carport width with standard rafter")
                    void hugeCarportStandardRafter() {
                        // Arrange
                        Carport carport = TestCarportFactory.createCarportWithRafter(
                                TestPlankFactory.createStandardRafter(),
                                5000, 600
                        );
                        BillOfMaterial bom = new BillOfMaterial(carport);

                        // Act
                        int actual = bom.calcPostCountWidth();

                        // Assert
                        int expected = 10;
                        assertEquals(expected, actual);
                    }

                    @Test
                    @DisplayName("Huge carport width with shorter rafter")
                    void hugeCarportShorterRafter() {
                        // Arrange
                        Carport carport = TestCarportFactory.createCarportWithRafter(
                                TestPlankFactory.createShorterRafter(),
                                5000, 600
                        );
                        BillOfMaterial bom = new BillOfMaterial(carport);

                        // Act
                        int actual = bom.calcPostCountWidth();

                        // Assert
                        int expected = 14;
                        assertEquals(expected, actual);
                    }

                    @Test
                    @DisplayName("Huge carport width with longer rafter")
                    void hugeCarportLongerRafter() {
                        // Arrange
                        Carport carport = TestCarportFactory.createCarportWithRafter(
                                TestPlankFactory.createLongerRafter(),
                                5000, 600
                        );
                        BillOfMaterial bom = new BillOfMaterial(carport);

                        // Act
                        int actual = bom.calcPostCountWidth();

                        // Assert
                        int expected = 9;
                        assertEquals(expected, actual);
                    }
                }
            }

            @Nested
            @DisplayName("Post Count Length Calculation (calcPostCountLength)")
            class PostCountLengthTests {

                @Nested
                @DisplayName("Standard Cases")
                class Standard {

                    @Test
                    @DisplayName("Length smaller than standard beam max distance")
                    void lengthSmallerThanStandard() {
                        // Arrange
                        Carport carport = TestCarportFactory.createCarportWithBeam(
                                TestPlankFactory.createStandardBeam(),
                                300, 300
                        );
                        BillOfMaterial bom = new BillOfMaterial(carport);

                        // Act
                        int actual = bom.calcPostCountLength();

                        // Assert
                        int expected = 2;
                        assertEquals(expected, actual);
                    }

                    @Test
                    @DisplayName("Length smaller than shorter beam max distance")
                    void lengthSmallerThanShorter() {
                        // Arrange
                        Carport carport = TestCarportFactory.createCarportWithBeam(
                                TestPlankFactory.createShorterBeam(),
                                300, 200
                        );
                        BillOfMaterial bom = new BillOfMaterial(carport);

                        // Act
                        int actual = bom.calcPostCountLength();

                        // Assert
                        int expected = 2;
                        assertEquals(expected, actual);
                    }

                    @Test
                    @DisplayName("Length smaller than longer beam max distance")
                    void lengthSmallerThanLonger() {
                        // Arrange
                        Carport carport = TestCarportFactory.createCarportWithBeam(
                                TestPlankFactory.createLongerBeam(),
                                300, 350
                        );
                        BillOfMaterial bom = new BillOfMaterial(carport);

                        // Act
                        int actual = bom.calcPostCountLength();

                        // Assert
                        int expected = 2;
                        assertEquals(expected, actual);
                    }
                }

                @Nested
                @DisplayName("Edge Cases with Different Beams")
                class EdgeCases {

                    @Test
                    @DisplayName("Length just over shorter beam max distance (needs extra post)")
                    void lengthOverShorterBeam() {
                        // Arrange
                        Carport carport = TestCarportFactory.createCarportWithBeam(
                                TestPlankFactory.createShorterBeam(),
                                300, 260
                        );
                        BillOfMaterial bom = new BillOfMaterial(carport);

                        // Act
                        int actual = bom.calcPostCountLength();

                        // Assert
                        int expected = 3;
                        assertEquals(expected, actual);
                    }

                    @Test
                    @DisplayName("Length just over standard beam max distance (needs extra post)")
                    void lengthOverStandardBeam() {
                        // Arrange
                        Carport carport = TestCarportFactory.createCarportWithBeam(
                                TestPlankFactory.createStandardBeam(),
                                300, 350
                        );
                        BillOfMaterial bom = new BillOfMaterial(carport);

                        // Act
                        int actual = bom.calcPostCountLength();

                        // Assert
                        int expected = 3;
                        assertEquals(expected, actual);
                    }

                    @Test
                    @DisplayName("Length just over longer beam max distance (needs extra post)")
                    void lengthOverLongerBeam() {
                        // Arrange
                        Carport carport = TestCarportFactory.createCarportWithBeam(
                                TestPlankFactory.createLongerBeam(),
                                300, 410
                        );
                        BillOfMaterial bom = new BillOfMaterial(carport);

                        // Act
                        int actual = bom.calcPostCountLength();

                        // Assert
                        int expected = 3;
                        assertEquals(expected, actual);
                    }
                }

                @Nested
                @DisplayName("Stress Tests with Different Beams")
                class Stress {

                    @Test
                    @DisplayName("Huge carport length with standard beam")
                    void hugeCarportStandardBeam() {
                        // Arrange
                        Carport carport = TestCarportFactory.createCarportWithBeam(
                                TestPlankFactory.createStandardBeam(),
                                300, 5000
                        );
                        BillOfMaterial bom = new BillOfMaterial(carport);

                        // Act
                        int actual = bom.calcPostCountLength();

                        // Assert
                        int expected = 16;
                        assertEquals(expected, actual);
                    }

                    @Test
                    @DisplayName("Huge carport length with shorter beam")
                    void hugeCarportShorterBeam() {
                        // Arrange
                        Carport carport = TestCarportFactory.createCarportWithBeam(
                                TestPlankFactory.createShorterBeam(),
                                300, 5000
                        );
                        BillOfMaterial bom = new BillOfMaterial(carport);

                        // Act
                        int actual = bom.calcPostCountLength();

                        // Assert
                        int expected = 21;
                        assertEquals(expected, actual);
                    }

                    @Test
                    @DisplayName("Huge carport length with longer beam")
                    void hugeCarportLongerBeam() {
                        // Arrange
                        Carport carport = TestCarportFactory.createCarportWithBeam(
                                TestPlankFactory.createLongerBeam(),
                                300, 5000
                        );
                        BillOfMaterial bom = new BillOfMaterial(carport);

                        // Act
                        int actual = bom.calcPostCountLength();

                        // Assert
                        int expected = 14;
                        assertEquals(expected, actual);
                    }
                }
            }
        }

        @Nested
        @DisplayName("Beams Tests")
        class Beams {

            @Nested
            @DisplayName("Beam Count Length Calculation (calcBeamCountLength)")
            class BeamCountLengthTests {

                @Nested
                @DisplayName("Standard Cases")
                class Standard {

                    @Test
                    @DisplayName("Length smaller than standard beam max length")
                    void lengthSmallerThanStandard() {
                        // Arrange
                        Carport carport = TestCarportFactory.createCarportWithBeam(
                                TestPlankFactory.createStandardBeam(),
                                300, 300
                        );
                        BillOfMaterial bom = new BillOfMaterial(carport);

                        // Act
                        int actual = bom.calcBeamCountLength();

                        // Assert
                        int expected = 1;
                        assertEquals(expected, actual);
                    }

                    @Test
                    @DisplayName("Length smaller than shorter beam max length")
                    void lengthSmallerThanShorter() {
                        // Arrange
                        Carport carport = TestCarportFactory.createCarportWithBeam(
                                TestPlankFactory.createShorterBeam(),
                                300, 300
                        );
                        BillOfMaterial bom = new BillOfMaterial(carport);

                        // Act
                        int actual = bom.calcBeamCountLength();

                        // Assert
                        int expected = 1;
                        assertEquals(expected, actual);
                    }

                    @Test
                    @DisplayName("Length smaller than longer beam max length")
                    void lengthSmallerThanLonger() {
                        // Arrange
                        Carport carport = TestCarportFactory.createCarportWithBeam(
                                TestPlankFactory.createLongerBeam(),
                                300, 600
                        );
                        BillOfMaterial bom = new BillOfMaterial(carport);

                        // Act
                        int actual = bom.calcBeamCountLength();

                        // Assert
                        int expected = 1;
                        assertEquals(expected, actual);
                    }
                }

                @Nested
                @DisplayName("Edge Cases with Different Beams")
                class EdgeCases {

                    @Test
                    @DisplayName("Length just over shorter beam max length (needs extra beam)")
                    void lengthOverShorterBeam() {
                        // Arrange
                        Carport carport = TestCarportFactory.createCarportWithBeam(
                                TestPlankFactory.createShorterBeam(),
                                300, 401
                        );
                        BillOfMaterial bom = new BillOfMaterial(carport);

                        // Act
                        int actual = bom.calcBeamCountLength();

                        // Assert
                        int expected = 2;
                        assertEquals(expected, actual);
                    }

                    @Test
                    @DisplayName("Length just over standard beam max length (needs extra beam)")
                    void lengthOverStandardBeam() {
                        // Arrange
                        Carport carport = TestCarportFactory.createCarportWithBeam(
                                TestPlankFactory.createStandardBeam(),
                                300, 601
                        );
                        BillOfMaterial bom = new BillOfMaterial(carport);

                        // Act
                        int actual = bom.calcBeamCountLength();

                        // Assert
                        int expected = 2;
                        assertEquals(expected, actual);
                    }

                    @Test
                    @DisplayName("Length just over longer beam max length (needs extra beam)")
                    void lengthOverLongerBeam() {
                        // Arrange
                        Carport carport = TestCarportFactory.createCarportWithBeam(
                                TestPlankFactory.createLongerBeam(),
                                300, 701
                        );
                        BillOfMaterial bom = new BillOfMaterial(carport);

                        // Act
                        int actual = bom.calcBeamCountLength();

                        // Assert
                        int expected = 2;
                        assertEquals(expected, actual);
                    }
                }

                @Nested
                @DisplayName("Stress Tests with Different Beams")
                class Stress {

                    @Test
                    @DisplayName("Huge carport length with standard beam")
                    void hugeCarportStandardBeam() {
                        // Arrange
                        Carport carport = TestCarportFactory.createCarportWithBeam(
                                TestPlankFactory.createStandardBeam(),
                                300, 5000
                        );
                        BillOfMaterial bom = new BillOfMaterial(carport);

                        // Act
                        int actual = bom.calcBeamCountLength();

                        // Assert
                        int expected = 9;
                        assertEquals(expected, actual);
                    }

                    @Test
                    @DisplayName("Huge carport length with shorter beam")
                    void hugeCarportShorterBeam() {
                        // Arrange
                        Carport carport = TestCarportFactory.createCarportWithBeam(
                                TestPlankFactory.createShorterBeam(),
                                300, 5000
                        );
                        BillOfMaterial bom = new BillOfMaterial(carport);

                        // Act
                        int actual = bom.calcBeamCountLength();

                        // Assert
                        int expected = 13;
                        assertEquals(expected, actual);
                    }

                    @Test
                    @DisplayName("Huge carport length with longer beam")
                    void hugeCarportLongerBeam() {
                        // Arrange
                        Carport carport = TestCarportFactory.createCarportWithBeam(
                                TestPlankFactory.createLongerBeam(),
                                300, 5000
                        );
                        BillOfMaterial bom = new BillOfMaterial(carport);

                        // Act
                        int actual = bom.calcBeamCountLength();

                        // Assert
                        int expected = 9;
                        assertEquals(expected, actual);
                    }
                }
            }
        }

        @Nested
        @DisplayName("Rafters")
        class Rafters {

            @Nested
            @DisplayName("Rafter Count Width Calculation (calcRafterCountWidth)")
            class RafterCountWidthTests {

                @Nested
                @DisplayName("Standard Cases")
                class Standard {

                    @Test
                    @DisplayName("Width smaller than standard rafter max length")
                    void widthSmallerThanStandard() {
                        // Arrange
                        Carport carport = TestCarportFactory.createCarportWithRafter(
                                TestPlankFactory.createStandardRafter(),
                                500, 600
                        );
                        BillOfMaterial bom = new BillOfMaterial(carport);

                        // Act
                        int actual = bom.calcRafterCountWidth();

                        // Assert
                        int expected = 1;
                        assertEquals(expected, actual);
                    }

                    @Test
                    @DisplayName("Width smaller than shorter rafter max length")
                    void widthSmallerThanShorter() {
                        // Arrange
                        Carport carport = TestCarportFactory.createCarportWithRafter(
                                TestPlankFactory.createShorterRafter(),
                                350, 600
                        );
                        BillOfMaterial bom = new BillOfMaterial(carport);

                        // Act
                        int actual = bom.calcRafterCountWidth();

                        // Assert
                        int expected = 1;
                        assertEquals(expected, actual);
                    }

                    @Test
                    @DisplayName("Width smaller than longer rafter max length")
                    void widthSmallerThanLonger() {
                        // Arrange
                        Carport carport = TestCarportFactory.createCarportWithRafter(
                                TestPlankFactory.createLongerRafter(),
                                650, 600
                        );
                        BillOfMaterial bom = new BillOfMaterial(carport);

                        // Act
                        int actual = bom.calcRafterCountWidth();

                        // Assert
                        int expected = 1;
                        assertEquals(expected, actual);
                    }
                }

                @Nested
                @DisplayName("Edge Cases with Different Rafters")
                class EdgeCases {

                    @Test
                    @DisplayName("Width just over shorter rafter max length (needs extra rafter)")
                    void widthOverShorterRafter() {
                        // Arrange
                        Carport carport = TestCarportFactory.createCarportWithRafter(
                                TestPlankFactory.createShorterRafter(),
                                410, 600
                        );
                        BillOfMaterial bom = new BillOfMaterial(carport);

                        // Act
                        int actual = bom.calcRafterCountWidth();

                        // Assert
                        int expected = 2;
                        assertEquals(expected, actual);
                    }

                    @Test
                    @DisplayName("Width just over standard rafter max length (needs extra rafter)")
                    void widthOverStandardRafter() {
                        // Arrange
                        Carport carport = TestCarportFactory.createCarportWithRafter(
                                TestPlankFactory.createStandardRafter(),
                                610, 600
                        );
                        BillOfMaterial bom = new BillOfMaterial(carport);

                        // Act
                        int actual = bom.calcRafterCountWidth();

                        // Assert
                        int expected = 2;
                        assertEquals(expected, actual);
                    }

                    @Test
                    @DisplayName("Width just over longer rafter max length (needs extra rafter)")
                    void widthOverLongerRafter() {
                        // Arrange
                        Carport carport = TestCarportFactory.createCarportWithRafter(
                                TestPlankFactory.createLongerRafter(),
                                710, 600
                        );
                        BillOfMaterial bom = new BillOfMaterial(carport);

                        // Act
                        int actual = bom.calcRafterCountWidth();

                        // Assert
                        int expected = 2;
                        assertEquals(expected, actual);
                    }
                }

                @Nested
                @DisplayName("Stress Tests with Different Rafters")
                class Stress {

                    @Test
                    @DisplayName("Huge carport width with standard rafter")
                    void hugeCarportStandardRafter() {
                        // Arrange
                        Carport carport = TestCarportFactory.createCarportWithRafter(
                                TestPlankFactory.createStandardRafter(),
                                5000, 600
                        );
                        BillOfMaterial bom = new BillOfMaterial(carport);

                        // Act
                        int actual = bom.calcRafterCountWidth();

                        // Assert
                        int expected = 9;
                        assertEquals(expected, actual);
                    }

                    @Test
                    @DisplayName("Huge carport width with shorter rafter")
                    void hugeCarportShorterRafter() {
                        // Arrange
                        Carport carport = TestCarportFactory.createCarportWithRafter(
                                TestPlankFactory.createShorterRafter(),
                                5000, 600
                        );
                        BillOfMaterial bom = new BillOfMaterial(carport);

                        // Act
                        int actual = bom.calcRafterCountWidth();

                        // Assert
                        int expected = 13;
                        assertEquals(expected, actual);
                    }

                    @Test
                    @DisplayName("Huge carport width with longer rafter")
                    void hugeCarportLongerRafter() {
                        // Arrange
                        Carport carport = TestCarportFactory.createCarportWithRafter(
                                TestPlankFactory.createLongerRafter(),
                                5000, 600
                        );
                        BillOfMaterial bom = new BillOfMaterial(carport);

                        // Act
                        int actual = bom.calcRafterCountWidth();

                        // Assert
                        int expected = 8;
                        assertEquals(expected, actual);
                    }
                }
            }

            @Nested
            @DisplayName("Rafter Count Length Calculation (calcRafterCountLength)")
            class RafterCountLengthTests {

                @Nested
                @DisplayName("Standard Cases")
                class Standard {

                    @Test
                    @DisplayName("Length smaller than standard roof cover max distance")
                    void lengthSmallerThanStandardRoofCover() {
                        // Arrange
                        Carport carport = TestCarportFactory.createCarportWithRoofCover(
                                TestRoofCoverFactory.createStandardTestRoofCover(),
                                600, 550
                        );
                        BillOfMaterial bom = new BillOfMaterial(carport);

                        // Act
                        int actual = bom.calcRafterCountLength();

                        // Assert
                        int expected = 11;
                        assertEquals(expected, actual);
                    }
                }

                @Nested
                @DisplayName("Edge Cases")
                class EdgeCases {

                    @Test
                    @DisplayName("Length exactly at standard roof cover max distance")
                    void lengthExactlyAtMax() {
                        // Arrange
                        Carport carport = TestCarportFactory.createCarportWithRoofCover(
                                TestRoofCoverFactory.createStandardTestRoofCover(),
                                600, 551
                        );
                        BillOfMaterial bom = new BillOfMaterial(carport);

                        // Act
                        int actual = bom.calcRafterCountLength();

                        // Assert
                        int expected = 12;
                        assertEquals(expected, actual);
                    }

                    @Test
                    @DisplayName("Length just over standard roof cover max distance (needs extra rafter)")
                    void lengthJustOverStandardRoofCover() {
                        // Arrange
                        Carport carport = TestCarportFactory.createCarportWithRoofCover(
                                TestRoofCoverFactory.createStandardTestRoofCover(),
                                600, 601
                        );
                        BillOfMaterial bom = new BillOfMaterial(carport);

                        // Act
                        int actual = bom.calcRafterCountLength();

                        // Assert
                        int expected = 12;
                        assertEquals(expected, actual);
                    }
                }

                @Nested
                @DisplayName("Stress Tests")
                class Stress {

                    @Test
                    @DisplayName("Huge carport length with standard roof cover")
                    void hugeCarportStandardRoofCover() {
                        // Arrange
                        Carport carport = TestCarportFactory.createCarportWithRoofCover(
                                TestRoofCoverFactory.createStandardTestRoofCover(),
                                600, 5000
                        );
                        BillOfMaterial bom = new BillOfMaterial(carport);

                        // Act
                        int actual = bom.calcRafterCountLength();

                        // Assert
                        int expected = 92;
                        assertEquals(expected, actual);
                    }
                }
            }
        }

    }

    @Nested
    @DisplayName("Roof Tests")
    class RoofTests {

        @Nested
        @DisplayName("Length Calculation (calcRoofCoverCountLength)")
        class LengthTests {

            @Nested
            @DisplayName("Standard cover length (600 cm max length, 20 cm overlap)")
            class StandardLength {

                @Nested
                @DisplayName("Standard Cases")
                class Standard {

                    @Test
                    @DisplayName("Length 500 cm carport")
                    void length500() {
                        // Arrange
                        RoofCover standard = TestRoofCoverFactory.createStandardTestRoofCover();
                        Carport carport = TestCarportFactory.createCarportWithRoofCover(standard, 300, 500);
                        BillOfMaterial bom = new BillOfMaterial(carport);

                        // Act
                        int actual = bom.calcRoofCoverCountLength();

                        // Assert
                        int expected = 1;
                        assertEquals(expected, actual);
                    }

                    @Test
                    @DisplayName("Length 1100 cm carport")
                    void length1100() {
                        // Arrange
                        RoofCover standard = TestRoofCoverFactory.createStandardTestRoofCover();
                        Carport carport = TestCarportFactory.createCarportWithRoofCover(standard, 300, 1100);
                        BillOfMaterial bom = new BillOfMaterial(carport);

                        // Act
                        int actual = bom.calcRoofCoverCountLength();

                        // Assert
                        int expected = 2;
                        assertEquals(expected, actual);
                    }

                    @Test
                    @DisplayName("Length 1300 cm carport")
                    void length1300() {
                        // Arrange
                        RoofCover standard = TestRoofCoverFactory.createStandardTestRoofCover();
                        Carport carport = TestCarportFactory.createCarportWithRoofCover(standard, 300, 1300);
                        BillOfMaterial bom = new BillOfMaterial(carport);

                        // Act
                        int actual = bom.calcRoofCoverCountLength();

                        // Assert
                        int expected = 3;
                        assertEquals(expected, actual);
                    }
                }

                @Nested
                @DisplayName("Edge Cases")
                class EdgeCases {

                    @Test
                    @DisplayName("Length 599 cm carport (just under)")
                    void length599() {
                        // Arrange
                        RoofCover standard = TestRoofCoverFactory.createStandardTestRoofCover();
                        Carport carport = TestCarportFactory.createCarportWithRoofCover(standard, 300, 599);
                        BillOfMaterial bom = new BillOfMaterial(carport);

                        // Act
                        int actual = bom.calcRoofCoverCountLength();

                        // Assert
                        int expected = 1;
                        assertEquals(expected, actual);
                    }

                    @Test
                    @DisplayName("Length 600 cm carport (exact)")
                    void length600() {
                        // Arrange
                        RoofCover standard = TestRoofCoverFactory.createStandardTestRoofCover();
                        Carport carport = TestCarportFactory.createCarportWithRoofCover(standard, 300, 600);
                        BillOfMaterial bom = new BillOfMaterial(carport);

                        // Act
                        int actual = bom.calcRoofCoverCountLength();

                        // Assert
                        int expected = 1;
                        assertEquals(expected, actual);
                    }

                    @Test
                    @DisplayName("Length 601 cm carport (just over)")
                    void length601() {
                        // Arrange
                        RoofCover standard = TestRoofCoverFactory.createStandardTestRoofCover();
                        Carport carport = TestCarportFactory.createCarportWithRoofCover(standard, 300, 601);
                        BillOfMaterial bom = new BillOfMaterial(carport);

                        // Act
                        int actual = bom.calcRoofCoverCountLength();

                        // Assert
                        int expected = 2;
                        assertEquals(expected, actual);
                    }

                    @Test
                    @DisplayName("Length 1179 cm carport (just under 2 sheets)")
                    void length1179() {
                        // Arrange
                        RoofCover standard = TestRoofCoverFactory.createStandardTestRoofCover();
                        Carport carport = TestCarportFactory.createCarportWithRoofCover(standard, 300, 1179);
                        BillOfMaterial bom = new BillOfMaterial(carport);

                        // Act
                        int actual = bom.calcRoofCoverCountLength();

                        // Assert
                        int expected = 2;
                        assertEquals(expected, actual);
                    }

                    @Test
                    @DisplayName("Length 1180 cm carport (exactly 2 sheets)")
                    void length1180() {
                        // Arrange
                        RoofCover standard = TestRoofCoverFactory.createStandardTestRoofCover();
                        Carport carport = TestCarportFactory.createCarportWithRoofCover(standard, 300, 1180);
                        BillOfMaterial bom = new BillOfMaterial(carport);

                        // Act
                        int actual = bom.calcRoofCoverCountLength();

                        // Assert
                        int expected = 2;
                        assertEquals(expected, actual);
                    }

                    @Test
                    @DisplayName("Length 1181 cm carport (just over 2 sheets)")
                    void length1181() {
                        // Arrange
                        RoofCover standard = TestRoofCoverFactory.createStandardTestRoofCover();
                        Carport carport = TestCarportFactory.createCarportWithRoofCover(standard, 300, 1181);
                        BillOfMaterial bom = new BillOfMaterial(carport);

                        // Act
                        int actual = bom.calcRoofCoverCountLength();

                        // Assert
                        int expected = 3;
                        assertEquals(expected, actual);
                    }
                }

                @Nested
                @DisplayName("Stress Tests")
                class Stress {

                    @Test
                    @DisplayName("Tiny carport 50 cm")
                    void tinyCarport50() {
                        // Arrange
                        RoofCover standard = TestRoofCoverFactory.createStandardTestRoofCover();
                        Carport carport = TestCarportFactory.createCarportWithRoofCover(standard, 300, 50);
                        BillOfMaterial bom = new BillOfMaterial(carport);

                        // Act
                        int actual = bom.calcRoofCoverCountLength();

                        // Assert
                        int expected = 1;
                        assertEquals(expected, actual);
                    }

                    @Test
                    @DisplayName("Huge carport 5000 cm")
                    void hugeCarport5000() {
                        // Arrange
                        RoofCover standard = TestRoofCoverFactory.createStandardTestRoofCover();
                        Carport carport = TestCarportFactory.createCarportWithRoofCover(standard, 300, 5000);
                        BillOfMaterial bom = new BillOfMaterial(carport);

                        // Act
                        int actual = bom.calcRoofCoverCountLength();

                        // Assert
                        int expected = 9;
                        assertEquals(expected, actual);
                    }
                }
            }

            @Nested
            @DisplayName("Short Plank cover length (400 cm max length, 20 cm overlap)")
            class ShortPlankLength {

                @Nested
                @DisplayName("Standard Cases")
                class Standard {

                    @Test
                    @DisplayName("Length 300 cm carport")
                    void length300() {
                        // Arrange
                        RoofCover shortPlank = TestRoofCoverFactory.createShortPlankRoofCover();
                        Carport carport = TestCarportFactory.createCarportWithRoofCover(shortPlank, 300, 300);
                        BillOfMaterial bom = new BillOfMaterial(carport);

                        // Act
                        int actual = bom.calcRoofCoverCountLength();

                        // Assert
                        int expected = 1;
                        assertEquals(expected, actual);
                    }

                    @Test
                    @DisplayName("Length 750 cm carport")
                    void length750() {
                        // Arrange
                        RoofCover shortPlank = TestRoofCoverFactory.createShortPlankRoofCover();
                        Carport carport = TestCarportFactory.createCarportWithRoofCover(shortPlank, 300, 750);
                        BillOfMaterial bom = new BillOfMaterial(carport);

                        // Act
                        int actual = bom.calcRoofCoverCountLength();

                        // Assert
                        int expected = 2;
                        assertEquals(expected, actual);
                    }

                    @Test
                    @DisplayName("Length 1150 cm carport")
                    void length1150() {
                        // Arrange
                        RoofCover shortPlank = TestRoofCoverFactory.createShortPlankRoofCover();
                        Carport carport = TestCarportFactory.createCarportWithRoofCover(shortPlank, 300, 1150);
                        BillOfMaterial bom = new BillOfMaterial(carport);

                        // Act
                        int actual = bom.calcRoofCoverCountLength();

                        // Assert
                        int expected = 3;
                        assertEquals(expected, actual);
                    }
                }

                @Nested
                @DisplayName("Edge Cases")
                class EdgeCases {

                    @Test
                    @DisplayName("Length 399 cm carport (just under)")
                    void length399() {
                        // Arrange
                        RoofCover shortPlank = TestRoofCoverFactory.createShortPlankRoofCover();
                        Carport carport = TestCarportFactory.createCarportWithRoofCover(shortPlank, 300, 399);
                        BillOfMaterial bom = new BillOfMaterial(carport);

                        // Act
                        int actual = bom.calcRoofCoverCountLength();

                        // Assert
                        int expected = 1;
                        assertEquals(expected, actual);
                    }

                    @Test
                    @DisplayName("Length 400 cm carport (exact)")
                    void length400() {
                        // Arrange
                        RoofCover shortPlank = TestRoofCoverFactory.createShortPlankRoofCover();
                        Carport carport = TestCarportFactory.createCarportWithRoofCover(shortPlank, 300, 400);
                        BillOfMaterial bom = new BillOfMaterial(carport);

                        // Act
                        int actual = bom.calcRoofCoverCountLength();

                        // Assert
                        int expected = 1;
                        assertEquals(expected, actual);
                    }

                    @Test
                    @DisplayName("Length 401 cm carport (just over)")
                    void length401() {
                        // Arrange
                        RoofCover shortPlank = TestRoofCoverFactory.createShortPlankRoofCover();
                        Carport carport = TestCarportFactory.createCarportWithRoofCover(shortPlank, 300, 401);
                        BillOfMaterial bom = new BillOfMaterial(carport);

                        // Act
                        int actual = bom.calcRoofCoverCountLength();

                        // Assert
                        int expected = 2;
                        assertEquals(expected, actual);
                    }

                    @Test
                    @DisplayName("Length 779 cm carport (just under 2 sheets)")
                    void length759() {
                        // Arrange
                        RoofCover shortPlank = TestRoofCoverFactory.createShortPlankRoofCover();
                        Carport carport = TestCarportFactory.createCarportWithRoofCover(shortPlank, 300, 779);
                        BillOfMaterial bom = new BillOfMaterial(carport);

                        // Act
                        int actual = bom.calcRoofCoverCountLength();

                        // Assert
                        int expected = 2;
                        assertEquals(expected, actual);
                    }

                    @Test
                    @DisplayName("Length 780 cm carport (exactly 2 sheets)")
                    void length760() {
                        // Arrange
                        RoofCover shortPlank = TestRoofCoverFactory.createShortPlankRoofCover();
                        Carport carport = TestCarportFactory.createCarportWithRoofCover(shortPlank, 300, 780);
                        BillOfMaterial bom = new BillOfMaterial(carport);

                        // Act
                        int actual = bom.calcRoofCoverCountLength();

                        // Assert
                        int expected = 2;
                        assertEquals(expected, actual);
                    }

                    @Test
                    @DisplayName("Length 781 cm carport (just over 2 sheets)")
                    void length761() {
                        // Arrange
                        RoofCover shortPlank = TestRoofCoverFactory.createShortPlankRoofCover();
                        Carport carport = TestCarportFactory.createCarportWithRoofCover(shortPlank, 300, 781);
                        BillOfMaterial bom = new BillOfMaterial(carport);

                        // Act
                        int actual = bom.calcRoofCoverCountLength();

                        // Assert
                        int expected = 3;
                        assertEquals(expected, actual);
                    }
                }

                @Nested
                @DisplayName("Stress Tests")
                class Stress {

                    @Test
                    @DisplayName("Tiny carport 50 cm")
                    void tinyCarport50() {
                        // Arrange
                        RoofCover shortPlank = TestRoofCoverFactory.createShortPlankRoofCover();
                        Carport carport = TestCarportFactory.createCarportWithRoofCover(shortPlank, 300, 50);
                        BillOfMaterial bom = new BillOfMaterial(carport);

                        // Act
                        int actual = bom.calcRoofCoverCountLength();

                        // Assert
                        int expected = 1;
                        assertEquals(expected, actual);
                    }

                    @Test
                    @DisplayName("Huge carport 5000 cm")
                    void hugeCarport5000() {
                        // Arrange
                        RoofCover shortPlank = TestRoofCoverFactory.createShortPlankRoofCover();
                        Carport carport = TestCarportFactory.createCarportWithRoofCover(shortPlank, 300, 5000);
                        BillOfMaterial bom = new BillOfMaterial(carport);

                        // Act
                        int actual = bom.calcRoofCoverCountLength();

                        // Assert
                        int expected = 14;
                        assertEquals(expected, actual);
                    }
                }
            }

            @Nested
            @DisplayName("Tiny Overlap cover length (600 cm max length, 5 cm overlap)")
            class TinyOverlapLength {

                @Nested
                @DisplayName("Standard Cases")
                class Standard {

                    @Test
                    @DisplayName("Length 500 cm carport")
                    void length500() {
                        // Arrange
                        RoofCover tinyOverlap = TestRoofCoverFactory.createTinyOverlapRoofCover();
                        Carport carport = TestCarportFactory.createCarportWithRoofCover(tinyOverlap, 300, 500);
                        BillOfMaterial bom = new BillOfMaterial(carport);

                        // Act
                        int actual = bom.calcRoofCoverCountLength();

                        // Assert
                        int expected = 1;
                        assertEquals(expected, actual);
                    }

                    @Test
                    @DisplayName("Length 1100 cm carport")
                    void length1100() {
                        // Arrange
                        RoofCover tinyOverlap = TestRoofCoverFactory.createTinyOverlapRoofCover();
                        Carport carport = TestCarportFactory.createCarportWithRoofCover(tinyOverlap, 300, 1100);
                        BillOfMaterial bom = new BillOfMaterial(carport);

                        // Act
                        int actual = bom.calcRoofCoverCountLength();

                        // Assert
                        int expected = 2;
                        assertEquals(expected, actual);
                    }

                    @Test
                    @DisplayName("Length 1300 cm carport")
                    void length1300() {
                        // Arrange
                        RoofCover tinyOverlap = TestRoofCoverFactory.createTinyOverlapRoofCover();
                        Carport carport = TestCarportFactory.createCarportWithRoofCover(tinyOverlap, 300, 1300);
                        BillOfMaterial bom = new BillOfMaterial(carport);

                        // Act
                        int actual = bom.calcRoofCoverCountLength();

                        // Assert
                        int expected = 3;
                        assertEquals(expected, actual);
                    }
                }

                @Nested
                @DisplayName("Edge Cases")
                class EdgeCases {

                    @Test
                    @DisplayName("Length 599 cm carport (just under)")
                    void length599() {
                        // Arrange
                        RoofCover tinyOverlap = TestRoofCoverFactory.createTinyOverlapRoofCover();
                        Carport carport = TestCarportFactory.createCarportWithRoofCover(tinyOverlap, 300, 599);
                        BillOfMaterial bom = new BillOfMaterial(carport);

                        // Act
                        int actual = bom.calcRoofCoverCountLength();

                        // Assert
                        int expected = 1;
                        assertEquals(expected, actual);
                    }

                    @Test
                    @DisplayName("Length 600 cm carport (exact)")
                    void length600() {
                        // Arrange
                        RoofCover tinyOverlap = TestRoofCoverFactory.createTinyOverlapRoofCover();
                        Carport carport = TestCarportFactory.createCarportWithRoofCover(tinyOverlap, 300, 600);
                        BillOfMaterial bom = new BillOfMaterial(carport);

                        // Act
                        int actual = bom.calcRoofCoverCountLength();

                        // Assert
                        int expected = 1;
                        assertEquals(expected, actual);
                    }

                    @Test
                    @DisplayName("Length 601 cm carport (just over)")
                    void length601() {
                        // Arrange
                        RoofCover tinyOverlap = TestRoofCoverFactory.createTinyOverlapRoofCover();
                        Carport carport = TestCarportFactory.createCarportWithRoofCover(tinyOverlap, 300, 601);
                        BillOfMaterial bom = new BillOfMaterial(carport);

                        // Act
                        int actual = bom.calcRoofCoverCountLength();

                        // Assert
                        int expected = 2;
                        assertEquals(expected, actual);
                    }

                    @Test
                    @DisplayName("Length 1194 cm carport (just under 2 sheets)")
                    void length1194() {
                        // Arrange
                        RoofCover tinyOverlap = TestRoofCoverFactory.createTinyOverlapRoofCover();
                        Carport carport = TestCarportFactory.createCarportWithRoofCover(tinyOverlap, 300, 1194);
                        BillOfMaterial bom = new BillOfMaterial(carport);

                        // Act
                        int actual = bom.calcRoofCoverCountLength();

                        // Assert
                        int expected = 2;
                        assertEquals(expected, actual);
                    }

                    @Test
                    @DisplayName("Length 1195 cm carport (exactly 2 sheets)")
                    void length1195() {
                        // Arrange
                        RoofCover tinyOverlap = TestRoofCoverFactory.createTinyOverlapRoofCover();
                        Carport carport = TestCarportFactory.createCarportWithRoofCover(tinyOverlap, 300, 1195);
                        BillOfMaterial bom = new BillOfMaterial(carport);

                        // Act
                        int actual = bom.calcRoofCoverCountLength();

                        // Assert
                        int expected = 2;
                        assertEquals(expected, actual);
                    }

                    @Test
                    @DisplayName("Length 1196 cm carport (just over 2 sheets)")
                    void length1196() {
                        // Arrange
                        RoofCover tinyOverlap = TestRoofCoverFactory.createTinyOverlapRoofCover();
                        Carport carport = TestCarportFactory.createCarportWithRoofCover(tinyOverlap, 300, 1196);
                        BillOfMaterial bom = new BillOfMaterial(carport);

                        // Act
                        int actual = bom.calcRoofCoverCountLength();

                        // Assert
                        int expected = 3;
                        assertEquals(expected, actual);
                    }
                }

                @Nested
                @DisplayName("Stress Tests")
                class Stress {

                    @Test
                    @DisplayName("Tiny carport 50 cm")
                    void tinyCarport50() {
                        // Arrange
                        RoofCover tinyOverlap = TestRoofCoverFactory.createTinyOverlapRoofCover();
                        Carport carport = TestCarportFactory.createCarportWithRoofCover(tinyOverlap, 300, 50);
                        BillOfMaterial bom = new BillOfMaterial(carport);

                        // Act
                        int actual = bom.calcRoofCoverCountLength();

                        // Assert
                        int expected = 1;
                        assertEquals(expected, actual);
                    }

                    @Test
                    @DisplayName("Huge carport 5000 cm")
                    void hugeCarport5000() {
                        // Arrange
                        RoofCover tinyOverlap = TestRoofCoverFactory.createTinyOverlapRoofCover();
                        Carport carport = TestCarportFactory.createCarportWithRoofCover(tinyOverlap, 300, 5000);
                        BillOfMaterial bom = new BillOfMaterial(carport);

                        // Act
                        int actual = bom.calcRoofCoverCountLength();

                        // Assert
                        int expected = 9;
                        assertEquals(expected, actual);
                    }
                }
            }
        }

        @Nested
        @DisplayName("Width Calculation (calcRoofCoverCountWidth)")
        class WidthTests {

            @Nested
            @DisplayName("Standard cover width (100 cm wide, 20 cm side overlap)")
            class StandardWidth {

                @Nested
                @DisplayName("Standard Cases")
                class Standard {

                    @Test
                    @DisplayName("Width 90 cm carport (single cover)")
                    void width90() {
                        // Arrange
                        RoofCover standard = TestRoofCoverFactory.createStandardTestRoofCover();
                        Carport carport = TestCarportFactory.createCarportWithRoofCover(standard, 90, 600);
                        BillOfMaterial bom = new BillOfMaterial(carport);

                        // Act
                        int actual = bom.calcRoofCoverCountWidth();

                        // Assert
                        int expected = 1;
                        assertEquals(expected, actual);
                    }

                    @Test
                    @DisplayName("Width 250 cm carport (3 covers needed)")
                    void width250() {
                        // Arrange
                        RoofCover standard = TestRoofCoverFactory.createStandardTestRoofCover();
                        Carport carport = TestCarportFactory.createCarportWithRoofCover(standard, 250, 600);
                        BillOfMaterial bom = new BillOfMaterial(carport);

                        // Act
                        int actual = bom.calcRoofCoverCountWidth();

                        // Assert
                        int expected = 3;
                        assertEquals(expected, actual);
                    }

                    @Test
                    @DisplayName("Width 400 cm carport (5 covers needed)")
                    void width400() {
                        // Arrange
                        RoofCover standard = TestRoofCoverFactory.createStandardTestRoofCover();
                        Carport carport = TestCarportFactory.createCarportWithRoofCover(standard, 400, 600);
                        BillOfMaterial bom = new BillOfMaterial(carport);

                        // Act
                        int actual = bom.calcRoofCoverCountWidth();

                        // Assert
                        int expected = 5;
                        assertEquals(expected, actual);
                    }
                }

                @Nested
                @DisplayName("Edge Cases")
                class EdgeCases {

                    @Test
                    @DisplayName("Width 99 cm carport (just under 1 full cover)")
                    void width99() {
                        // Arrange
                        RoofCover standard = TestRoofCoverFactory.createStandardTestRoofCover();
                        Carport carport = TestCarportFactory.createCarportWithRoofCover(standard, 99, 600);
                        BillOfMaterial bom = new BillOfMaterial(carport);

                        // Act
                        int actual = bom.calcRoofCoverCountWidth();

                        // Assert
                        int expected = 1;
                        assertEquals(expected, actual);
                    }

                    @Test
                    @DisplayName("Width 100 cm carport (exactly one cover)")
                    void width100() {
                        // Arrange
                        RoofCover standard = TestRoofCoverFactory.createStandardTestRoofCover();
                        Carport carport = TestCarportFactory.createCarportWithRoofCover(standard, 100, 600);
                        BillOfMaterial bom = new BillOfMaterial(carport);

                        // Act
                        int actual = bom.calcRoofCoverCountWidth();

                        // Assert
                        int expected = 1;
                        assertEquals(expected, actual);
                    }

                    @Test
                    @DisplayName("Width 101 cm carport (just above 1 cover, needs 2)")
                    void width101() {
                        // Arrange
                        RoofCover standard = TestRoofCoverFactory.createStandardTestRoofCover();
                        Carport carport = TestCarportFactory.createCarportWithRoofCover(standard, 101, 600);
                        BillOfMaterial bom = new BillOfMaterial(carport);

                        // Act
                        int actual = bom.calcRoofCoverCountWidth();

                        // Assert
                        int expected = 2;
                        assertEquals(expected, actual);
                    }

                    @Test
                    @DisplayName("Width 179 cm carport (just under 2 covers)")
                    void width169() {
                        // Arrange
                        RoofCover standard = TestRoofCoverFactory.createStandardTestRoofCover();
                        Carport carport = TestCarportFactory.createCarportWithRoofCover(standard, 179, 600);
                        BillOfMaterial bom = new BillOfMaterial(carport);

                        // Act
                        int actual = bom.calcRoofCoverCountWidth();

                        // Assert
                        int expected = 2;
                        assertEquals(expected, actual);
                    }

                    @Test
                    @DisplayName("Width 180 cm carport (exactly 2 covers)")
                    void width170() {
                        // Arrange
                        RoofCover standard = TestRoofCoverFactory.createStandardTestRoofCover();
                        Carport carport = TestCarportFactory.createCarportWithRoofCover(standard, 180, 600);
                        BillOfMaterial bom = new BillOfMaterial(carport);

                        // Act
                        int actual = bom.calcRoofCoverCountWidth();

                        // Assert
                        int expected = 2;
                        assertEquals(expected, actual);
                    }

                    @Test
                    @DisplayName("Width 181 cm carport (just over, needs 3)")
                    void width171() {
                        // Arrange
                        RoofCover standard = TestRoofCoverFactory.createStandardTestRoofCover();
                        Carport carport = TestCarportFactory.createCarportWithRoofCover(standard, 181, 600);
                        BillOfMaterial bom = new BillOfMaterial(carport);

                        // Act
                        int actual = bom.calcRoofCoverCountWidth();

                        // Assert
                        int expected = 3;
                        assertEquals(expected, actual);
                    }
                }

                @Nested
                @DisplayName("Stress Tests")
                class Stress {

                    @Test
                    @DisplayName("Tiny carport 50 cm wide")
                    void tinyCarportWidth50() {
                        // Arrange
                        RoofCover standard = TestRoofCoverFactory.createStandardTestRoofCover();
                        Carport carport = TestCarportFactory.createCarportWithRoofCover(standard, 50, 600);
                        BillOfMaterial bom = new BillOfMaterial(carport);

                        // Act
                        int actual = bom.calcRoofCoverCountWidth();

                        // Assert
                        int expected = 1;
                        assertEquals(expected, actual);
                    }

                    @Test
                    @DisplayName("Huge carport 5000 cm wide")
                    void hugeCarportWidth5000() {
                        // Arrange
                        RoofCover standard = TestRoofCoverFactory.createStandardTestRoofCover();
                        Carport carport = TestCarportFactory.createCarportWithRoofCover(standard, 5000, 600);
                        BillOfMaterial bom = new BillOfMaterial(carport);

                        // Act
                        int actual = bom.calcRoofCoverCountWidth();

                        // Assert
                        int expected = 63;
                        assertEquals(expected, actual);
                    }
                }
            }

            @Nested
            @DisplayName("Tiny overlap cover width (100 cm wide, 5 cm side overlap)")
            class TinyOverlapWidth {

                @Nested
                @DisplayName("Standard Cases")
                class Standard {

                    @Test
                    @DisplayName("Width 90 cm carport (single cover)")
                    void width90() {
                        // Arrange
                        RoofCover tiny = TestRoofCoverFactory.createTinyOverlapRoofCover();
                        Carport carport = TestCarportFactory.createCarportWithRoofCover(tiny, 90, 600);
                        BillOfMaterial bom = new BillOfMaterial(carport);

                        // Act
                        int actual = bom.calcRoofCoverCountWidth();

                        // Assert
                        int expected = 1;
                        assertEquals(expected, actual);
                    }

                    @Test
                    @DisplayName("Width 250 cm carport (3 covers needed)")
                    void width250() {
                        // Arrange
                        RoofCover tiny = TestRoofCoverFactory.createTinyOverlapRoofCover();
                        Carport carport = TestCarportFactory.createCarportWithRoofCover(tiny, 250, 600);
                        BillOfMaterial bom = new BillOfMaterial(carport);

                        // Act
                        int actual = bom.calcRoofCoverCountWidth();

                        // Assert
                        int expected = 3;
                        assertEquals(expected, actual);
                    }

                    @Test
                    @DisplayName("Width 400 cm carport (5 covers needed)")
                    void width400() {
                        // Arrange
                        RoofCover tiny = TestRoofCoverFactory.createTinyOverlapRoofCover();
                        Carport carport = TestCarportFactory.createCarportWithRoofCover(tiny, 400, 600);
                        BillOfMaterial bom = new BillOfMaterial(carport);

                        // Act
                        int actual = bom.calcRoofCoverCountWidth();

                        // Assert
                        int expected = 5;
                        assertEquals(expected, actual);
                    }
                }

                @Nested
                @DisplayName("Edge Cases")
                class EdgeCases {

                    @Test
                    @DisplayName("Width 99 cm carport (just under 1 cover)")
                    void width99() {
                        // Arrange
                        RoofCover tiny = TestRoofCoverFactory.createTinyOverlapRoofCover();
                        Carport carport = TestCarportFactory.createCarportWithRoofCover(tiny, 99, 600);
                        BillOfMaterial bom = new BillOfMaterial(carport);

                        // Act
                        int actual = bom.calcRoofCoverCountWidth();

                        // Assert
                        int expected = 1;
                        assertEquals(expected, actual);
                    }

                    @Test
                    @DisplayName("Width 100 cm carport (exact 1 cover)")
                    void width100() {
                        // Arrange
                        RoofCover tiny = TestRoofCoverFactory.createTinyOverlapRoofCover();
                        Carport carport = TestCarportFactory.createCarportWithRoofCover(tiny, 100, 600);
                        BillOfMaterial bom = new BillOfMaterial(carport);

                        // Act
                        int actual = bom.calcRoofCoverCountWidth();

                        // Assert
                        int expected = 1;
                        assertEquals(expected, actual);
                    }

                    @Test
                    @DisplayName("Width 101 cm carport (just over needs 2 covers)")
                    void width101() {
                        // Arrange
                        RoofCover tiny = TestRoofCoverFactory.createTinyOverlapRoofCover();
                        Carport carport = TestCarportFactory.createCarportWithRoofCover(tiny, 101, 600);
                        BillOfMaterial bom = new BillOfMaterial(carport);

                        // Act
                        int actual = bom.calcRoofCoverCountWidth();

                        // Assert
                        int expected = 2;
                        assertEquals(expected, actual);
                    }

                    @Test
                    @DisplayName("Width 194 cm carport (just under 2 covers)")
                    void width194() {
                        // Arrange
                        RoofCover tiny = TestRoofCoverFactory.createTinyOverlapRoofCover();
                        Carport carport = TestCarportFactory.createCarportWithRoofCover(tiny, 194, 600);
                        BillOfMaterial bom = new BillOfMaterial(carport);

                        // Act
                        int actual = bom.calcRoofCoverCountWidth();

                        // Assert
                        int expected = 2;
                        assertEquals(expected, actual);
                    }

                    @Test
                    @DisplayName("Width 195 cm carport (exactly 2 covers)")
                    void width195() {
                        // Arrange
                        RoofCover tiny = TestRoofCoverFactory.createTinyOverlapRoofCover();
                        Carport carport = TestCarportFactory.createCarportWithRoofCover(tiny, 195, 600);
                        BillOfMaterial bom = new BillOfMaterial(carport);

                        // Act
                        int actual = bom.calcRoofCoverCountWidth();

                        // Assert
                        int expected = 2;
                        assertEquals(expected, actual);
                    }

                    @Test
                    @DisplayName("Width 196 cm carport (needs 3 covers)")
                    void width196() {
                        // Arrange
                        RoofCover tiny = TestRoofCoverFactory.createTinyOverlapRoofCover();
                        Carport carport = TestCarportFactory.createCarportWithRoofCover(tiny, 196, 600);
                        BillOfMaterial bom = new BillOfMaterial(carport);

                        // Act
                        int actual = bom.calcRoofCoverCountWidth();

                        // Assert
                        int expected = 3;
                        assertEquals(expected, actual);
                    }
                }

                @Nested
                @DisplayName("Stress Tests")
                class Stress {

                    @Test
                    @DisplayName("Tiny carport 50 cm wide")
                    void tinyCarportWidth50() {
                        // Arrange
                        RoofCover tiny = TestRoofCoverFactory.createTinyOverlapRoofCover();
                        Carport carport = TestCarportFactory.createCarportWithRoofCover(tiny, 50, 600);
                        BillOfMaterial bom = new BillOfMaterial(carport);

                        // Act
                        int actual = bom.calcRoofCoverCountWidth();

                        // Assert
                        int expected = 1;
                        assertEquals(expected, actual);
                    }

                    @Test
                    @DisplayName("Huge carport 5000 cm wide")
                    void hugeCarportWidth5000() {
                        // Arrange
                        RoofCover tiny = TestRoofCoverFactory.createTinyOverlapRoofCover();
                        Carport carport = TestCarportFactory.createCarportWithRoofCover(tiny, 5000, 600);
                        BillOfMaterial bom = new BillOfMaterial(carport);

                        // Act
                        int actual = bom.calcRoofCoverCountWidth();

                        // Assert
                        int expected = 53;
                        assertEquals(expected, actual);
                    }
                }
            }

            @Nested
            @DisplayName("Wide cover width (400 cm wide, 20 cm side overlap)")
            class WideWidth {

                @Nested
                @DisplayName("Standard Cases")
                class Standard {

                    @Test
                    @DisplayName("Width 390 cm carport (single cover)")
                    void width390() {
                        // Arrange
                        RoofCover wide = TestRoofCoverFactory.createWideRoofCover();
                        Carport carport = TestCarportFactory.createCarportWithRoofCover(wide, 390, 600);
                        BillOfMaterial bom = new BillOfMaterial(carport);

                        // Act
                        int actual = bom.calcRoofCoverCountWidth();

                        // Assert
                        int expected = 1;
                        assertEquals(expected, actual);
                    }

                    @Test
                    @DisplayName("Width 600 cm carport (needs 2 covers)")
                    void width600() {
                        // Arrange
                        RoofCover wide = TestRoofCoverFactory.createWideRoofCover();
                        Carport carport = TestCarportFactory.createCarportWithRoofCover(wide, 600, 600);
                        BillOfMaterial bom = new BillOfMaterial(carport);

                        // Act
                        int actual = bom.calcRoofCoverCountWidth();

                        // Assert
                        int expected = 2;
                        assertEquals(expected, actual);
                    }

                    @Test
                    @DisplayName("Width 1100 cm carport (needs 3 covers)")
                    void width1100() {
                        // Arrange
                        RoofCover wide = TestRoofCoverFactory.createWideRoofCover();
                        Carport carport = TestCarportFactory.createCarportWithRoofCover(wide, 1100, 600);
                        BillOfMaterial bom = new BillOfMaterial(carport);

                        // Act
                        int actual = bom.calcRoofCoverCountWidth();

                        // Assert
                        int expected = 3;
                        assertEquals(expected, actual);
                    }
                }

                @Nested
                @DisplayName("Edge Cases")
                class EdgeCases {

                    @Test
                    @DisplayName("Width 399 cm carport (just under 1 full cover)")
                    void width399() {
                        // Arrange
                        RoofCover wide = TestRoofCoverFactory.createWideRoofCover();
                        Carport carport = TestCarportFactory.createCarportWithRoofCover(wide, 399, 600);
                        BillOfMaterial bom = new BillOfMaterial(carport);

                        // Act
                        int actual = bom.calcRoofCoverCountWidth();

                        // Assert
                        int expected = 1;
                        assertEquals(expected, actual);
                    }

                    @Test
                    @DisplayName("Width 400 cm carport (exactly fits 1 cover)")
                    void width400() {
                        // Arrange
                        RoofCover wide = TestRoofCoverFactory.createWideRoofCover();
                        Carport carport = TestCarportFactory.createCarportWithRoofCover(wide, 400, 600);
                        BillOfMaterial bom = new BillOfMaterial(carport);

                        // Act
                        int actual = bom.calcRoofCoverCountWidth();

                        // Assert
                        int expected = 1;
                        assertEquals(expected, actual);
                    }

                    @Test
                    @DisplayName("Width 401 cm carport (needs 2 covers)")
                    void width401() {
                        // Arrange
                        RoofCover wide = TestRoofCoverFactory.createWideRoofCover();
                        Carport carport = TestCarportFactory.createCarportWithRoofCover(wide, 401, 600);
                        BillOfMaterial bom = new BillOfMaterial(carport);

                        // Act
                        int actual = bom.calcRoofCoverCountWidth();

                        // Assert
                        int expected = 2;
                        assertEquals(expected, actual);
                    }

                    @Test
                    @DisplayName("Width 779 cm carport (just under 2 covers)")
                    void width779() {
                        // Arrange
                        RoofCover wide = TestRoofCoverFactory.createWideRoofCover();
                        Carport carport = TestCarportFactory.createCarportWithRoofCover(wide, 779, 600);
                        BillOfMaterial bom = new BillOfMaterial(carport);

                        // Act
                        int actual = bom.calcRoofCoverCountWidth();

                        // Assert
                        int expected = 2;
                        assertEquals(expected, actual);
                    }

                    @Test
                    @DisplayName("Width 780 cm carport (exactly fits 2 covers)")
                    void width780() {
                        // Arrange
                        RoofCover wide = TestRoofCoverFactory.createWideRoofCover();
                        Carport carport = TestCarportFactory.createCarportWithRoofCover(wide, 780, 600);
                        BillOfMaterial bom = new BillOfMaterial(carport);

                        // Act
                        int actual = bom.calcRoofCoverCountWidth();

                        // Assert
                        int expected = 2;
                        assertEquals(expected, actual);
                    }

                    @Test
                    @DisplayName("Width 781 cm carport (needs 3 covers)")
                    void width781() {
                        // Arrange
                        RoofCover wide = TestRoofCoverFactory.createWideRoofCover();
                        Carport carport = TestCarportFactory.createCarportWithRoofCover(wide, 781, 600);
                        BillOfMaterial bom = new BillOfMaterial(carport);

                        // Act
                        int actual = bom.calcRoofCoverCountWidth();

                        // Assert
                        int expected = 3;
                        assertEquals(expected, actual);
                    }
                }

                @Nested
                @DisplayName("Stress Tests")
                class Stress {

                    @Test
                    @DisplayName("Tiny carport 50 cm wide")
                    void tinyCarportWidth50() {
                        // Arrange
                        RoofCover wide = TestRoofCoverFactory.createWideRoofCover();
                        Carport carport = TestCarportFactory.createCarportWithRoofCover(wide, 50, 600);
                        BillOfMaterial bom = new BillOfMaterial(carport);

                        // Act
                        int actual = bom.calcRoofCoverCountWidth();

                        // Assert
                        int expected = 1;
                        assertEquals(expected, actual);
                    }

                    @Test
                    @DisplayName("Huge carport 5000 cm wide")
                    void hugeCarportWidth5000() {
                        // Arrange
                        RoofCover wide = TestRoofCoverFactory.createWideRoofCover();
                        Carport carport = TestCarportFactory.createCarportWithRoofCover(wide, 5000, 600);
                        BillOfMaterial bom = new BillOfMaterial(carport);

                        // Act
                        int actual = bom.calcRoofCoverCountWidth();

                        // Assert
                        int expected = 14;
                        assertEquals(expected, actual);
                    }
                }
            }
        }

    }
}
