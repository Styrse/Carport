package app.entities.carport;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static app.entities.materials.planksAndRoofCovers.PlankAndRoof.PREDEFINED_LENGTHS;

public class BillOfMaterial {
    private final Carport carport;
    private final List<BillOfMaterialsLine> lines = new ArrayList<>();

    private final static int OVERHANG = 30;

    public BillOfMaterial(Carport carport) {
        this.carport = carport;
        calculateMaterials();
    }

    public List<BillOfMaterialsLine> getLines() {
        return lines;
    }

    private void calculateMaterials() {
        lines.addAll(calculatePosts());
        lines.addAll(getBeams());
        lines.addAll(getRafters());
        lines.addAll(calculateFascia());
        lines.addAll(getRoofCoverMaterials());
    }

    private List<BillOfMaterialsLine> calculatePosts() {
        int posts = calcPostCountLength() * calcPostCountWidth();
        List<BillOfMaterialsLine> result = new ArrayList<>();
        result.add(new BillOfMaterialsLine(
                carport.getPost().getName(),
                carport.getPost().getLength(),
                posts,
                carport.getPost().getUnit(),
                carport.getPost().getDescription()
        ));
        return result;
    }

    private List<BillOfMaterialsLine> getBeams() {
        List<BillOfMaterialsLine> beamList = new ArrayList<>();

        float distanceBetweenPosts = (float) (carport.getLength() - OVERHANG) / calcPostCountLength();
        int normalBeams = (calcPostCountLength() - 1) * calcPostCountWidth();
        int endBeam = calcPostCountWidth();

        int normalBestFit = bestFitLength(distanceBetweenPosts);
        int endBestFit = bestFitLength(distanceBetweenPosts + OVERHANG);

        beamList.add(new BillOfMaterialsLine(
                carport.getBeam().getName(),
                normalBestFit,
                normalBeams,
                carport.getBeam().getUnit(),
                carport.getBeam().getDescription()
        ));

        beamList.add(new BillOfMaterialsLine(
                carport.getBeam().getName(),
                endBestFit,
                endBeam,
                carport.getBeam().getUnit(),
                carport.getBeam().getDescription()
        ));
        return beamList;
    }

    private List<BillOfMaterialsLine> getRafters() {
        List<BillOfMaterialsLine> rafterList = new ArrayList<>();

        int numberOfRafters = calcRafterCountLength() * calcRafterCountWidth();
        int bestFitLength = bestFitLength(carport.getWidth());

        rafterList.add(new BillOfMaterialsLine(
                carport.getRafter().getName(),
                bestFitLength,
                numberOfRafters,
                carport.getRafter().getUnit(),
                carport.getRafter().getDescription()
        ));
        return rafterList;
    }

    // Finds the first length in PREDEFINED_LENGTHS that is greater than the required value or returns the maximum length.
    private int bestFitLength(float neededLength) {
        return PREDEFINED_LENGTHS.stream()
                .filter(length -> length > neededLength)
                .findFirst()
                .orElse(Collections.max(PREDEFINED_LENGTHS));
    }

    private List<BillOfMaterialsLine> getFascia() {
        List<BillOfMaterialsLine> fasciaList = new ArrayList<>();

        int fasciasCountLength = (int) Math.ceil((double) carport.getLength() / Collections.max(PREDEFINED_LENGTHS));
        int fasciasCountWidth = (int) Math.ceil((double) carport.getWidth() / Collections.max(PREDEFINED_LENGTHS));

        int bestFitLength = bestFitLength((float) carport.getLength() / fasciasCountLength);
        int bestFitWidth = bestFitLength((float) carport.getWidth() / fasciasCountWidth);

        fasciaList.add(new BillOfMaterialsLine(
                carport.getFascia().getName(),
                bestFitLength,
                fasciasCountLength * 2,
                carport.getFascia().getUnit(),
                carport.getFascia().getDescription()
        ));

        fasciaList.add(new BillOfMaterialsLine(
                carport.getFascia().getName(),
                bestFitWidth,
                fasciasCountWidth * 2,
                carport.getFascia().getUnit(),
                carport.getFascia().getDescription()
        ));
        return fasciaList;
    }

    private List<BillOfMaterialsLine> getRoofCoverMaterials() {
        int roofCovers = calcRoofCoverCountLength() * calcRoofCoverCountWidth();
        List<BillOfMaterialsLine> result = new ArrayList<>();
        result.add(new BillOfMaterialsLine(
                carport.getRoofCover().getName(),
                carport.getRoofCover().getLength(),
                roofCovers,
                carport.getRoofCover().getUnit(),
                carport.getRoofCover().getDescription()
        ));
        return result;
    }

    //================================
    //============= Posts ============
    //================================
    int calcTotalPosts() {
        return calcPostCountLength() * calcPostCountWidth();
    }

    int calcPostCountWidth() {
        int posts = 2;
        int maxLength = carport.getRafter().getMaxLength();
        if (carport.getWidth() > maxLength) {
            for (int i = maxLength; i < carport.getWidth(); i += maxLength) {
                posts++;
            }
        }
        return posts;
    }

    int calcPostCountLength() {
        int posts = 2;
        int maxDistanceBetweenPosts = carport.getBeam().getMaxLengthBetweenPosts();
        if (carport.getLength() > maxDistanceBetweenPosts) {
            for (int i = maxDistanceBetweenPosts; i < carport.getLength(); i += maxDistanceBetweenPosts) {
                posts++;
            }
        }
        return posts;
    }

    //================================
    //============= Beams ============
    //================================
    int calcTotalBeams() {
        return calcBeamCountLength() * calcPostCountWidth();
    }

    int calcBeamCountLength() {
        int beams = 1;
        int maxLength = carport.getBeam().getMaxLength();
        if (carport.getLength() > maxLength) {
            for (int i = maxLength; i < carport.getLength(); i += maxLength) {
                beams++;
            }
        }
        return beams;
    }

    //================================
    //============ Rafters ===========
    //================================
    int calcTotalRafters() {
        return calcRafterCountLength() * calcRafterCountWidth();
    }

    int calcRafterCountWidth() {
        int rafters = 1;
        int maxLength = carport.getRafter().getMaxLength();
        if (carport.getWidth() > maxLength) {
            for (int i = maxLength; i < carport.getWidth(); i += maxLength) {
                rafters++;
            }
        }
        return rafters;
    }

    int calcRafterCountLength() {
        int rafters = 2;
        int maxDistance = carport.getRoofCover().getMaxLengthBetweenRafters();
        if (carport.getLength() > maxDistance) {
            for (int i = maxDistance; i < carport.getLength(); i += maxDistance) {
                rafters++;
            }
        }
        return rafters;
    }

    //================================
    //========== Roof Covers =========
    //================================
    int calcTotalRoofCovers() {
        return calcRoofCoverCountLength() * calcRoofCoverCountWidth();
    }

    int calcRoofCoverCountLength() {
        int covers = 1;
        int maxLength = carport.getRoofCover().getMaxLength();
        int lengthOverlap = carport.getRoofCover().getLengthOverlap();
        if (carport.getLength() > maxLength) {
            for (int i = maxLength; i < carport.getLength(); i += (maxLength - lengthOverlap)) {
                covers++;
            }
        }
        return covers;
    }

    int calcRoofCoverCountWidth() {
        int covers = 1;
        int maxWidth = carport.getRoofCover().getWidth();
        float sideOverlap = carport.getRoofCover().getSideOverlap();
        if (carport.getWidth() > maxWidth) {
            for (float i = maxWidth; i < carport.getWidth(); i += (maxWidth - sideOverlap)) {
                covers++;
            }
        }
        return covers;
    }
}
