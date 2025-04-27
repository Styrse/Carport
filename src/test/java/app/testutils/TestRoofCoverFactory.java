package app.testutils;

import app.entities.materials.RoofCover;

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

    public static RoofCover createStandardTestRoofCover() {
        return new RoofCover(
                "Standard Test Roof",
                "Baseline for comparisons",
                150, 250, "m2",
                600, 100, 600,
                20, 20,
                55
        );
    }

    public static RoofCover createShortPlankRoofCover() {
        return new RoofCover(
                "Short Plank Roof",
                "Shorter planks for stress length tests",
                160, 260, "m2",
                400, 100, 400,
                20, 20,
                55
        );
    }

    public static RoofCover createTinyOverlapRoofCover() {
        return new RoofCover(
                "Tiny Overlap Roof",
                "Minimal overlaps to maximize coverage",
                140, 240, "m2",
                600, 100, 600,
                5, 5,
                55
        );
    }

    public static RoofCover createWideRoofCover() {
        return new RoofCover(
                "Wide Roof Cover",
                "Extra wide sheet for width tests",
                170, 270, "m2",
                600, 400, 600,
                20, 20,
                55
        );
    }
}
