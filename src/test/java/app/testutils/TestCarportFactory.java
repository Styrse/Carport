package app.testutils;

import app.entities.carport.Carport;
import app.entities.materials.Plank;
import app.entities.materials.RoofCover;

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

    private static final Plank standardPost = new Plank("Post", "97x97mm pressure treated", 100.0, 150.0, "pcs", 600, 97, 600, 97, 340);
    private static final Plank standardBeam = new Plank("Beam", "45x195mm pressure treated", 120.0, 180.0, "pcs", 600, 45, 600, 195, 0);
    private static final Plank standardRafter = new Plank("Rafter", "45x195mm rafters", 110.0, 160.0, "pcs", 600, 45, 600, 195, 55);
    private static final Plank standardFascia = new Plank("Fascia", "22x145mm fascia board", 80.0, 130.0, "pcs", 600, 22, 600, 145, 0);

    /**
     * Creates a Carport with standard posts, beams, rafters, fascias,
     * but allows injecting any RoofCover and custom dimensions.
     *
     * @param roofCover RoofCover to use
     * @param width Carport width in cm
     * @param length Carport length in cm
     * @return Configured Carport
     */
    public static Carport createCarportWithRoofCover(RoofCover roofCover, int width, int length) {
        return new Carport(width, length, 250, "flat", standardPost, standardBeam, standardRafter, standardFascia, roofCover);
    }
}
