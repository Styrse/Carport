package app.entities.products.carport.carportTestFactory;

import app.entities.products.materials.planks.Beam;
import app.entities.products.materials.planks.Fascia;
import app.entities.products.materials.planks.Post;
import app.entities.products.materials.planks.Rafter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestPlankFactory {

    public static final List<Integer> PRE_CUT_LENGTHS = List.of(300, 330, 360, 390, 420, 450, 480, 510, 540, 570, 600);
    public static final List<Integer> SHORT_PRE_CUTS_LENGTHS = List.of(300, 330, 360, 390);
    public static final List<Integer> LONG_PRE_CUTS_LENGTHS = List.of(300, 330, 360, 390, 400, 700);

    public static Post createStandardPost() {
        Post post = new Post(
                1,
                "Standard Post",
                "Basic post for testing",
                40,
                65,
                "cm",
                25,
                25,
                5
        );
        post.getPreCutLengths().addAll(PRE_CUT_LENGTHS);
        return post;
    }

    public static Beam createStandardBeam() {
        Beam beam = new Beam(
                2,
                "Standard Beam",
                "Basic beam for testing",
                90,
                130,
                "cm",
                25,
                25,
                340
        );
        beam.getPreCutLengths().addAll(PRE_CUT_LENGTHS);
        return beam;
    }

    public static Beam createShorterGapBeam() {
        Beam beam = new Beam(
                2,
                "Shorter Beam",
                "Shorter max gap beam",
                70,
                115,
                "cm",
                25,
                25,
                240
        );
        beam.getPreCutLengths().addAll(PRE_CUT_LENGTHS);
        return beam;
    }

    public static Beam createLongerGapBeam() {
        Beam beam = new Beam(
                2,
                "Longer Beam",
                "Longer max distance beam",
                105,
                150,
                "cm",
                25,
                25,
                400
        );
        beam.getPreCutLengths().addAll(PRE_CUT_LENGTHS);
        return beam;
    }

    public static Rafter createStandardRafter() {
        Rafter rafter = new Rafter(
                3,
                "Standard Rafter",
                "Basic rafter for testing",
                25,
                45,
                "cm",
                25,
                25
        );
        rafter.getPreCutLengths().addAll(PRE_CUT_LENGTHS);
        return rafter;
    }

    public static Rafter createShorterRafter() {
        Rafter rafter = new Rafter(
                3,
                "Shorter Rafter",
                "Shorter max length rafter",
                20,
                37.5f,
                "cm",
                25,
                25
        );
        rafter.getPreCutLengths().addAll(SHORT_PRE_CUTS_LENGTHS);
        return rafter;
    }

    public static Rafter createLongerRafter() {
        Rafter rafter = new Rafter(
                3,
                "Longer Rafter",
                "Longer max length rafter",
                32.5f,
                55,
                "cm",
                25,
                25
        );
        rafter.getPreCutLengths().addAll(LONG_PRE_CUTS_LENGTHS);
        return rafter;
    }

    public static Fascia createStandardFascia() {
        Fascia fascia = new Fascia(
                4,
                "Standard Fascia",
                "Basic fascia board for testing",
                15,
                30,
                "cm",
                25,
                25
        );
        fascia.getPreCutLengths().addAll(PRE_CUT_LENGTHS);
        return fascia;
    }
}
