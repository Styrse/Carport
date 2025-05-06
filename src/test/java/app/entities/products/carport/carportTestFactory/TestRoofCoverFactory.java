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
        RoofCover roofCover = new RoofCover(
                5,
                "Standard Roof",
                "Basic roof cover for testing",
                1.079,
                1.5,
                "cm",
                100,
                20,
                10,
                55
        );
        roofCover.getPreCutsLengths().addAll(PRE_CUT_LENGTHS);
        return roofCover;
    }

    public static RoofCover createShortSheetRoofCover() {
        RoofCover roofCover = new RoofCover(
                5,
                "Short Plank Roof",
                "Shorter planks for stress length tests",
                .971,
                1.35,
                "cm",
                100,
                20,
                10,
                55
        );
        roofCover.getPreCutsLengths().addAll(PRE_CUT_LENGTHS);
        return roofCover;
    }

    public static RoofCover createTinyOverlapRoofCover() {
        RoofCover roofCover = new RoofCover(
                5,
                "Tiny Overlap Roof",
                "Minimal overlaps to maximize coverage",
                1.115,
                1.55,
                "cm",
                100,
                20,
                5,
                55
        );
        roofCover.getPreCutsLengths().addAll(PRE_CUT_LENGTHS);
        return roofCover;
    }

    public static RoofCover createTinyOverlapLengthRoofCover() {
        RoofCover roofCover = new RoofCover(
                5,
                "Tiny Overlap Roof",
                "Minimal overlaps to maximize coverage",
                1.151,
                1.6,
                "cm",
                100,
                5,
                10,
                55
        );
        roofCover.getPreCutsLengths().addAll(PRE_CUT_LENGTHS);
        return roofCover;
    }

    public static RoofCover createWideRoofCover() {
        RoofCover roofCover = new RoofCover(
                5,
                "Wide Roof Cover",
                "Extra wide sheet for width tests",
                4.316,
                6,
                "cm",
                400,
                20,
                10,
                55
        );
        roofCover.getPreCutsLengths().addAll(PRE_CUT_LENGTHS);
        return roofCover;
    }
}
