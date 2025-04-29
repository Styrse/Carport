package app.testutils;

import app.entities.products.carport.Carport;
import app.entities.products.materials.planks.Beam;
import app.entities.products.materials.planks.Rafter;
import app.entities.products.materials.roof.RoofCover;

/**
 * TestCarportFactory
 *
 * This helper class provides pre-configured Carport instances for testing purposes.
 *
 * Motivation:
 * - Avoid repeating long Carport and Material setup inside every test.
 * - Focus each test on what matters (e.g., roof covers, post counts) instead of setup details.
 * - Enable easier future changes (if material properties change, update here only).
 *
 * Pattern:
 * We reuse standard Plank materials and allow flexibility by passing in different RoofCover materials and carport dimensions.
 * This allows clean testing of roof cover calculations without unnecessary duplication.
 */
public class TestCarportFactory {

    /*
      Creates a Carport with standard posts, beams, rafters, fascias,
      but allows injecting any RoofCover and custom dimensions.
     */
    public static Carport createCarportWithRoofCover(RoofCover roofCover, int width, int length) {
        return new Carport(
                0,
                0,
                "Test Carport",
                "Generated carport for measurement tests",
                0.0,
                0.0,
                width,
                length,
                250,
                "flat",
                TestPlankFactory.createStandardPost(),
                TestPlankFactory.createStandardBeam(),
                TestPlankFactory.createStandardRafter(),
                TestPlankFactory.createStandardFascia(),
                roofCover
        );
    }

    public static Carport createCarportWithRafter(Rafter rafter, int width, int length) {
        return new Carport(
                0,
                0,
                "Test Carport",
                "Generated carport for measurement tests",
                0.0,
                0.0,
                width,
                length,
                250,
                "flat",
                TestPlankFactory.createStandardPost(),
                TestPlankFactory.createStandardBeam(),
                rafter,
                TestPlankFactory.createStandardFascia(),
                TestRoofCoverFactory.createStandardTestRoofCover()
        );
    }

    public static Carport createCarportWithBeam(Beam beam, int width, int length) {
        return new Carport(
                0,
                0,
                "Test Carport",
                "Generated carport for measurement tests",
                0.0,
                0.0,
                width,
                length,
                250,
                "flat",
                TestPlankFactory.createStandardPost(),
                beam,
                TestPlankFactory.createStandardRafter(),
                TestPlankFactory.createStandardFascia(),
                TestRoofCoverFactory.createStandardTestRoofCover()
        );
    }
}
