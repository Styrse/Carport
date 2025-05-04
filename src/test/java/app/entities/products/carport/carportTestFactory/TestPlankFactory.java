package app.entities.products.carport.carportTestFactory;

import app.entities.products.materials.planks.Beam;
import app.entities.products.materials.planks.Fascia;
import app.entities.products.materials.planks.Post;
import app.entities.products.materials.planks.Rafter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestPlankFactory {

    public static List<Integer> PRE_CUT_LENGTHS = new ArrayList<>(Arrays.asList(300, 330, 360, 390, 420, 450, 480, 510, 540, 570, 600));
    public static final List<Integer> SHORT_PRE_CUTS_LENGTHS = List.of(300, 330, 360, 390);
    public static final List<Integer> LONG_PRE_CUTS_LENGTHS = List.of(300, 330, 360, 390, 400, 700);


    public static Post createStandardPost() {
        return new Post(
                1,
                "Standard Test Post",
                "Basic post for structural testing",
                100.0,
                150,
                PRE_CUT_LENGTHS,
                "pcs",
                25,
                25,
                5
        );
    }

    public static Beam createStandardBeam() {
        return new Beam(
                2,
                "Standard Beam",
                "Basic beam for testing",
                120.0,
                180.0,
                PRE_CUT_LENGTHS,
                "pcs",
                25,
                25,
                340
        );
    }

    public static Beam createShorterGapBeam() {
        return new Beam(
                2,
                "Shorter Beam",
                "Shorter max gap beam",
                120.0,
                180.0,
                PRE_CUT_LENGTHS,
                "pcs",
                25,
                25,
                240
        );
    }

    public static Beam createLongerGapBeam() {
        return new Beam(
                2,
                "Longer Beam",
                "Longer max distance beam",
                120.0,
                180.0,
                LONG_PRE_CUTS_LENGTHS,
                "pcs",
                25,
                25,
                400
        );
    }

    public static Rafter createStandardRafter() {
        return new Rafter(
                3,
                "Standard Rafter",
                "Basic rafter for testing",
                110.0,
                160.0,
                PRE_CUT_LENGTHS,
                "pcs",
                25,
                25
        );
    }

    public static Rafter createShorterRafter() {
        return new Rafter(
                3,
                "Shorter Rafter",
                "Shorter max length rafter",
                110.0,
                160.0,
                SHORT_PRE_CUTS_LENGTHS,
                "pcs",
                25,
                25
        );
    }

    public static Rafter createLongerRafter() {
        return new Rafter(
                3,
                "Longer Rafter",
                "Longer max length rafter",
                110.0,
                160.0,
                LONG_PRE_CUTS_LENGTHS,
                "pcs",
                25,
                25
        );
    }

    public static Fascia createStandardFascia() {
        return new Fascia(
                4,
                "Standard Fascia",
                "Basic fascia board for testing",
                80.0,
                130.0,
                PRE_CUT_LENGTHS,
                "pcs",
                25,
                25
        );
    }
}
