package app.entities.products.carport.carportTestFactory;

import app.entities.products.materials.planks.Beam;
import app.entities.products.materials.planks.Fascia;
import app.entities.products.materials.planks.Post;
import app.entities.products.materials.planks.Rafter;
import app.entities.products.materials.roof.RoofCover;

public class TestPlankFactory {

    public static Post createStandardPost() {
        return new Post(
                "Standard Test Post",
                "Basic post for structural testing",
                100.0,
                150,
                0,
                150,
                20,
                600,
                "pcs",
                25,
                5
        );
    }

    public static Beam createStandardBeam() {
        return new Beam(
                "Standard Beam",
                "Basic beam for testing",
                120.0,
                180.0,
                0,
                600,
                20,
                600,
                "pcs",
                0,
                340
        );
    }

    public static Beam createShorterBeam() {
        Beam beam = new Beam(
                "Shorter Beam",
                "Shorter max distance beam",
                120.0,
                180.0,
                0,
                600,
                20,
                400,
                "pcs",
                0,
                250
        );
        beam.setMaxLength(400);
        return beam;
    }

    public static Beam createLongerBeam() {
        return new Beam(
                "Longer Beam",
                "Longer max distance beam",
                120.0,
                180.0,
                0,
                600,
                20,
                600,
                "pcs",
                0,
                400
        );
    }

    public static Rafter createStandardRafter() {
        return new Rafter(
                "Standard Rafter",
                "Basic rafter for testing",
                110.0,
                160.0,
                0,
                600,
                30,
                600,
                "pcs",
                0
        );
    }

    public static Rafter createShorterRafter() {
        Rafter rafter = new Rafter(
                "Shorter Rafter",
                "Shorter max length rafter",
                110.0,
                160.0,
                0,
                400,
                30,
                600,
                "pcs",
                0
        );
        rafter.setMaxLength(400);
        return rafter;
    }

    public static Rafter createLongerRafter() {
        Rafter rafter = new Rafter(
                "Longer Rafter",
                "Longer max length rafter",
                110.0,
                160.0,
                0,
                700,
                30,
                600,
                "pcs",
                0
        );
        rafter.setMaxLength(700);
        return rafter;
    }

    public static Fascia createStandardFascia() {
        return new Fascia(
                "Standard Fascia",
                "Basic fascia board for testing",
                80.0,
                130.0,
                0,
                600,
                22,
                50,
                "pcs",
                0
        );
    }

    public static RoofCover createStandardRoofCover() {
        return new RoofCover(
                "Standard roof",
                "Clear strong roof",
                25,
                55,
                0,
                250,
                110,
                300,
                "m",
                20,
                10,
                55
        );
    }
}
