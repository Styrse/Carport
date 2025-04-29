package app.entities.carport.carportTestFactory;

import app.entities.products.materials.planks.Beam;
import app.entities.products.materials.planks.Fascia;
import app.entities.products.materials.planks.Post;
import app.entities.products.materials.planks.Rafter;

public class TestPlankFactory {

    public static Post createStandardPost() {
        return new Post(
                0,
                0,
                "Standard Test Post",
                "Basic post for structural testing",
                100.0,
                150.0,
                600,
                10,
                10,
                "pcs",
                0,
                true
        );
    }

    public static Beam createStandardBeam() {
        return new Beam(
                0,
                0,
                "Standard Beam",
                "Basic beam for testing",
                120.0,
                180.0,
                600,
                20,
                45,
                "pcs",
                0,
                340
        );
    }

    public static Beam createShorterBeam() {
        Beam beam = new Beam(
                0,
                0,
                "Shorter Beam",
                "Shorter max distance beam",
                120.0,
                180.0,
                400,
                20,
                45,
                "pcs",
                0,
                250
        );
        beam.setMaxLength(400);
        return beam;
    }

    public static Beam createLongerBeam() {
        return new Beam(
                0,
                0,
                "Longer Beam",
                "Longer max distance beam",
                120.0,
                180.0,
                600,
                20,
                45,
                "pcs",
                0,
                400
        );
    }

    public static Rafter createStandardRafter() {
        return new Rafter(
                0,
                0,
                "Standard Rafter",
                "Basic rafter for testing",
                110.0,
                160.0,
                600,
                30,
                55,
                "pcs",
                0
        );
    }

    public static Rafter createShorterRafter() {
        Rafter rafter = new Rafter(
                0,
                0,
                "Shorter Rafter",
                "Shorter max length rafter",
                110.0,
                160.0,
                400,
                30,
                55,
                "pcs",
                0
        );
        rafter.setMaxLength(400);
        return rafter;
    }

    public static Rafter createLongerRafter() {
        Rafter rafter = new Rafter(
                0,
                0,
                "Longer Rafter",
                "Longer max length rafter",
                110.0,
                160.0,
                700,
                30,
                55,
                "pcs",
                0
        );
        rafter.setMaxLength(700);
        return rafter;
    }

    public static Fascia createStandardFascia() {
        return new Fascia(
                0,
                0,
                "Standard Fascia",
                "Basic fascia board for testing",
                80.0,
                130.0,
                600,
                22,
                50,
                "pcs",
                0,
                false
        );
    }
}
