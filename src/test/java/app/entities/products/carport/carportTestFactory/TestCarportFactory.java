package app.entities.products.carport.carportTestFactory;

import app.entities.products.carport.Carport;
import app.entities.products.materials.Material;
import app.entities.products.materials.MaterialRole;
import app.entities.products.materials.planks.Beam;
import app.entities.products.materials.planks.Rafter;
import app.entities.products.materials.roof.RoofCover;

import java.util.HashMap;
import java.util.Map;

/**
 * TestCarportFactory
 * <p>
 * This helper class provides pre-configured Carport instances for testing purposes.
 * <p>
 * Motivation:
 * - Avoid repeating long Carport and Material setup inside every test.
 * - Focus each test on what matters (e.g., roof covers, post counts) instead of setup details.
 * - Enable easier future changes (if material properties change, update here only).
 * <p>
 * Pattern:
 * We reuse standard Plank materials and allow flexibility by passing in different RoofCover materials and carport dimensions.
 * This allows clean testing of roof cover calculations without unnecessary duplication.
 */
public class TestCarportFactory {

    /*
      Creates a Carport with standard posts, beams, rafters, fascias,
      but allows injecting any RoofCover and custom dimensions.
     */
    public static Map<MaterialRole, Material> materials = new HashMap<>();

    public static Carport createCarport() {
        materials.put(MaterialRole.POST, TestPlankFactory.createStandardPost());
        materials.put(MaterialRole.BEAM, TestPlankFactory.createStandardBeam());
        materials.put(MaterialRole.RAFTER, TestPlankFactory.createStandardRafter());
        materials.put(MaterialRole.FASCIA, TestPlankFactory.createStandardFascia());
        materials.put(MaterialRole.ROOF_COVER, TestRoofCoverFactory.createStandardTestRoofCover());


        return new Carport(
                1,
                "Test Carport",
                "Generated carport for measurement tests",
                600,
                500,
                250,
                "flat",
                materials
        );
    }

    public static Carport createCarportWidthLength(int width, int length) {
        materials.put(MaterialRole.POST, TestPlankFactory.createStandardPost());
        materials.put(MaterialRole.BEAM, TestPlankFactory.createStandardBeam());
        materials.put(MaterialRole.RAFTER, TestPlankFactory.createStandardRafter());
        materials.put(MaterialRole.FASCIA, TestPlankFactory.createStandardFascia());
        materials.put(MaterialRole.ROOF_COVER, TestRoofCoverFactory.createStandardTestRoofCover());

        return new Carport(
                1,
                "Test Carport",
                "Generated carport for measurement tests",
                width,
                length,
                250,
                "flat",
                materials
        );
    }

    public static Carport createCarportWithRoofCover(RoofCover roofCover, int width, int length) {
        materials.put(MaterialRole.POST, TestPlankFactory.createStandardPost());
        materials.put(MaterialRole.BEAM, TestPlankFactory.createStandardBeam());
        materials.put(MaterialRole.RAFTER, TestPlankFactory.createStandardRafter());
        materials.put(MaterialRole.FASCIA, TestPlankFactory.createStandardFascia());
        materials.put(MaterialRole.ROOF_COVER, roofCover);

        return new Carport(
                1,
                "Test Carport",
                "Generated carport for measurement tests",
                width,
                length,
                250,
                "flat",
                materials
        );
    }

    public static Carport createCarportWithRafter(Rafter rafter, int width, int length) {
        materials.put(MaterialRole.POST, TestPlankFactory.createStandardPost());
        materials.put(MaterialRole.BEAM, TestPlankFactory.createStandardBeam());
        materials.put(MaterialRole.RAFTER, rafter);
        materials.put(MaterialRole.FASCIA, TestPlankFactory.createStandardFascia());
        materials.put(MaterialRole.ROOF_COVER, TestRoofCoverFactory.createStandardTestRoofCover());

        return new Carport(
                1,
                "Test Carport",
                "Generated carport for measurement tests",
                width,
                length,
                250,
                "flat",
                materials
        );
    }

    public static Carport createCarportWithBeam(Beam beam, int width, int length) {
        materials.put(MaterialRole.POST, TestPlankFactory.createStandardPost());
        materials.put(MaterialRole.BEAM, beam);
        materials.put(MaterialRole.RAFTER, TestPlankFactory.createStandardRafter());
        materials.put(MaterialRole.FASCIA, TestPlankFactory.createStandardFascia());
        materials.put(MaterialRole.ROOF_COVER, TestRoofCoverFactory.createStandardTestRoofCover());

        return new Carport(
                1,
                "Test Carport",
                "Generated carport for measurement tests",
                width,
                length,
                250,
                "flat",
                materials
        );
    }
}
