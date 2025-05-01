package app.entities.products.carport.carportTestFactory;

import app.entities.products.materials.roof.RoofCover;

import java.util.Arrays;
import java.util.List;

/**
 * TestRoofCoverFactory
 *
 * Provides controlled RoofCover test objects for unit testing.
 *
 * Purpose:
 * - Test different roof behaviors precisely by isolating individual variables
 * - Achieve full white-box coverage without relying on real-world brand variations
 */

public class TestRoofCoverFactory {

    public static List<Integer> preCutsLengths = Arrays.asList(300, 330, 360, 390, 420, 450, 480, 510, 540, 570, 600);

    public static RoofCover createStandardTestRoofCover() {
        return new RoofCover(
                1,
                "Standard Test Roof",
                "Baseline for comparisons",
                150,
                250,
                preCutsLengths,
                "m2",
                100,
                20,
                10,
                55
        );
    }

    public static RoofCover createShortPlankRoofCover() {
        preCutsLengths.removeIf(n -> n > 400);
        return new RoofCover(
                1,
                "Short Plank Roof",
                "Shorter planks for stress length tests",
                160,
                260,
                preCutsLengths,
                "m2",
                100,
                20,
                10,
                55

        );
    }

    public static RoofCover createTinyOverlapRoofCover() {
        return new RoofCover(
                1,
                "Tiny Overlap Roof",
                "Minimal overlaps to maximize coverage",
                140,
                240,
                preCutsLengths,
                "m2",
                100,
                20,
                5,
                55
        );
    }

    public static RoofCover createWideRoofCover() {
        return new RoofCover(
                1,
                "Wide Roof Cover",
                "Extra wide sheet for width tests",
                170,
                270,
                preCutsLengths,
                "m2",
                400,
                20,
                10,
                55
        );
    }
}
