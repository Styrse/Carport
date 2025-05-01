package app.entities.products.carport.carportTestFactory;

import app.entities.products.materials.planks.Beam;
import app.entities.products.materials.planks.Fascia;
import app.entities.products.materials.planks.Post;
import app.entities.products.materials.planks.Rafter;
import app.entities.products.materials.roof.RoofCover;

import java.util.Arrays;
import java.util.List;

public class TestPlankFactory {

    public static List<Integer> preCutsLengths = Arrays.asList(300, 330, 360, 390, 420, 450, 480, 510, 540, 570, 600);

    public static Post createStandardPost() {
        return new Post(
                "Standard Test Post",
                "Basic post for structural testing",
                100.0,
                150,
                preCutsLengths,
                "pcs",
                25,
                1,
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
                preCutsLengths,
                "pcs",
                25,
                340,
                25,
                340
        );
    }

    public static Beam createShorterBeam() {
        preCutsLengths.removeIf(n -> n > 400);
        return new Beam(
                "Shorter Beam",
                "Shorter max distance beam",
                120.0,
                180.0,
                preCutsLengths,
                "pcs",
                25,
                250,
                25,
                240
        );
    }

    public static Beam createLongerBeam() {
        preCutsLengths.add(700);
        return new Beam(
                "Longer Beam",
                "Longer max distance beam",
                120.0,
                180.0,
                preCutsLengths,
                "pcs",
                25,
                1,
                25,
                400
        );
    }

    public static Rafter createStandardRafter() {
        return new Rafter(
                "Standard Rafter",
                "Basic rafter for testing",
                110.0,
                160.0,
                preCutsLengths,
                "pcs",
                25,
                1,
                25
        );
    }

    public static Rafter createShorterRafter() {
        preCutsLengths.removeIf(n -> n > 400);
        return new Rafter(
                "Shorter Rafter",
                "Shorter max length rafter",
                110.0,
                160.0,
                preCutsLengths,
                "pcs",
                25,
                1,
                25
        );
    }

    public static Rafter createLongerRafter() {
        preCutsLengths.add(700);
        return new Rafter(
                "Longer Rafter",
                "Longer max length rafter",
                110.0,
                160.0,
                preCutsLengths,
                "pcs",
                25,
                1,
                25
        );
    }

    public static Fascia createStandardFascia() {
        return new Fascia(
                "Standard Fascia",
                "Basic fascia board for testing",
                80.0,
                130.0,
                preCutsLengths,
                "pcs",
                25,
                1,
                25
        );
    }

    public static RoofCover createStandardRoofCover() {
        return new RoofCover(
                "Standard roof",
                "Clear strong roof",
                25,
                55,
                preCutsLengths,
                "m",
                100,
                1,
                20,
                10,
                55
        );
    }
}
