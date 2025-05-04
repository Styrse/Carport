package app.entities.products.carport.carportTestFactory;

import app.entities.products.materials.roof.RoofCover;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static app.entities.products.carport.carportTestFactory.TestPlankFactory.PRE_CUT_LENGTHS;
import static app.entities.products.carport.carportTestFactory.TestPlankFactory.SHORT_PRE_CUTS_LENGTHS;

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
                5,
                "Standard Test Roof",
                "Baseline for comparisons",
                150,
                250,
                PRE_CUT_LENGTHS,
                "m2",
                100,
                20,
                10,
                55
        );
    }

    public static RoofCover createShortSheetRoofCover() {
        return new RoofCover(
                5,
                "Short Plank Roof",
                "Shorter planks for stress length tests",
                160,
                260,
                SHORT_PRE_CUTS_LENGTHS,
                "m2",
                100,
                20,
                10,
                55
        );
    }

    public static RoofCover createTinyOverlapRoofCover() {
        return new RoofCover(
                5,
                "Tiny Overlap Roof",
                "Minimal overlaps to maximize coverage",
                140,
                240,
                PRE_CUT_LENGTHS,
                "m2",
                100,
                20,
                5,
                55
        );
    }

    public static RoofCover createTinyOverlapLengthRoofCover() {
        return new RoofCover(
                5,
                "Tiny Overlap Roof",
                "Minimal overlaps to maximize coverage",
                140,
                240,
                PRE_CUT_LENGTHS,
                "m2",
                100,
                5,
                10,
                55
        );
    }

    public static RoofCover createWideRoofCover() {
        return new RoofCover(
                5,
                "Wide Roof Cover",
                "Extra wide sheet for width tests",
                170,
                270,
                PRE_CUT_LENGTHS,
                "m2",
                400,
                20,
                10,
                55
        );
    }
}
