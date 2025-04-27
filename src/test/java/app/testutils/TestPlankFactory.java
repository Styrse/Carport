package app.testutils;

import app.entities.materials.planksAndRoofCovers.planks.*;

public class TestPlankFactory {

    public static Post createStandardPost() {
        return new Post(
                "Standard Test Post",
                "Basic post for structural testing",
                100.0, 150.0, "pcs",
                600, 97, 97,
                true
        );
    }

    public static Beam createStandardBeam() {
        return new Beam(
                "Standard Beam",
                "Basic beam for testing",
                120.0, 180.0, "pcs",
                600, 45, 195,
                340
        );
    }

    public static Beam createShorterBeam() {
        return new Beam(
                "Shorter Beam",
                "Shorter max distance beam",
                120.0, 180.0, "pcs",
                600, 45, 195,
                250
        );
    }

    public static Beam createLongerBeam() {
        return new Beam(
                "Longer Beam",
                "Longer max distance beam",
                120.0, 180.0, "pcs",
                600, 45, 195,
                400
        );
    }

    public static Rafter createStandardRafter() {
        return new Rafter(
                "Standard Rafter",
                "Basic rafter for testing",
                110.0, 160.0, "pcs",
                600, 45, 55
        );
    }

    public static Rafter createShorterRafter() {
        Rafter rafter = new Rafter(
                "Shorter Rafter",
                "Shorter max length rafter",
                110.0, 160.0, "pcs",
                400, 45, 55
        );
        rafter.setMaxLength(400);
        return rafter;
    }

    public static Rafter createLongerRafter() {
        Rafter rafter = new Rafter(
                "Longer Rafter",
                "Longer max length rafter",
                110.0, 160.0, "pcs",
                700, 45, 55
        );
        rafter.setMaxLength(700);
        return rafter;
    }

    public static Fascia createStandardFascia() {
        return new Fascia(
                "Standard Fascia",
                "Basic fascia board for testing",
                80.0, 130.0, "pcs",
                600, 22, 145
        );
    }
}
