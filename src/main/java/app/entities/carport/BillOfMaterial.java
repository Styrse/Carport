package app.entities.carport;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

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
        lines.addAll(calculateRafters());
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

        Optional<Integer> normalBestFitLength = PREDEFINED_LENGTHS.stream().filter(lenght -> lenght > distanceBetweenPosts).findFirst();
        Optional<Integer> endBestFitLength = PREDEFINED_LENGTHS.stream().filter(lenght -> lenght > distanceBetweenPosts + OVERHANG).findFirst();
        
        int normalBestFit = Collections.max(PREDEFINED_LENGTHS);
        if (normalBestFitLength.isPresent()) {
            normalBestFit = normalBestFitLength.get();
        }

        int endBestFit = Collections.max(PREDEFINED_LENGTHS);
        if (endBestFitLength.isPresent()) {
            endBestFit = endBestFitLength.get();
        }

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

    //    private List<BillOfMaterialsLine> getPosts() {
//        List<BillOfMaterialsLine> postList = new ArrayList<>();
//
//        float distanceBetweenPosts = (float) (carport.getLength() - OVERHANG) / calcPostCountLength();
//        int normalBeams = calcPostCountLength() - 1;
//        int endBeam = 1;
//    }

    private List<BillOfMaterialsLine> calculateRafters() {
        int rafters = calcRafterCountLength() * calcRafterCountWidth();
        List<BillOfMaterialsLine> result = new ArrayList<>();
        result.add(new BillOfMaterialsLine(
                carport.getRafter().getName(),
                carport.getRafter().getLength(),
                rafters,
                carport.getRafter().getUnit(),
                carport.getRafter().getDescription()
        ));
        return result;
    }

    private List<BillOfMaterialsLine> calculateFascia() {
        int fascias = (calcBeamCountLength() + calcRafterCountWidth()) * 2;
        List<BillOfMaterialsLine> result = new ArrayList<>();
        result.add(new BillOfMaterialsLine(
                carport.getFascia().getName(),
                carport.getFascia().getLength(),
                fascias,
                carport.getFascia().getUnit(),
                carport.getFascia().getDescription()
        ));
        return result;
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
