package app.testutils;

import app.entities.products.materials.roof.RoofCover;

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
                0,
                0,
                "Standard Test Roof",
                "Baseline for comparisons",
                150,
                250,
                600,
                100,
                600,
                "m2",
                20,
                20f,
                55
        );
    }

    public static RoofCover createShortPlankRoofCover() {
        RoofCover roofCover = new RoofCover(
                0,
                0,
                "Short Plank Roof",
                "Shorter planks for stress length tests",
                160,
                260,
                400,
                100,
                400,
                "m2",
                20,
                20f,
                55
        );
        roofCover.setMaxLength(400);
        return roofCover;
    }

    public static RoofCover createTinyOverlapRoofCover() {
        return new RoofCover(
                0,
                0,
                "Tiny Overlap Roof",
                "Minimal overlaps to maximize coverage",
                140,
                240,
                600,
                100,
                600,
                "m2",
                5,
                5f,
                55
        );
    }

    public static RoofCover createWideRoofCover() {
        return new RoofCover(
                0,
                0,
                "Wide Roof Cover",
                "Extra wide sheet for width tests",
                170,
                270,
                600,
                400,
                600,
                "m2",
                20,
                20f,
                55
        );
    }
}
