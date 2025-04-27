package app.entities.carport;

import java.util.ArrayList;
import java.util.List;

public class BillOfMaterial {
    private final Carport carport;
    private final List<BillOfMaterialsLine> lines = new ArrayList<>();

    public BillOfMaterial(Carport carport) {
        this.carport = carport;
        calculateMaterials();
    }

    public List<BillOfMaterialsLine> getLines() {
        return lines;
    }

    private void calculateMaterials() {
        lines.addAll(calculatePosts());
        lines.addAll(calculateBeams());
        lines.addAll(calculateRafters());
        lines.addAll(calculateFascia());
        lines.addAll(calculateRoofing());
    }

    private List<BillOfMaterialsLine> calculatePosts() {
        int posts = calcPostCountLength() * calcPostCountWidth();
        List<BillOfMaterialsLine> result = new ArrayList<>();
        result.add(new BillOfMaterialsLine(
                "Posts",
                posts,
                carport.getPostMaterial().getUnit(),
                carport.getPostMaterial().getDescription()
        ));
        return result;
    }

    private List<BillOfMaterialsLine> calculateBeams() {
        int beams = calcBeamCountLength() * calcPostCountWidth();
        List<BillOfMaterialsLine> result = new ArrayList<>();
        result.add(new BillOfMaterialsLine(
                "Beams",
                beams,
                carport.getBeamMaterial().getUnit(),
                carport.getBeamMaterial().getDescription()
        ));
        return result;
    }

    private List<BillOfMaterialsLine> calculateRafters() {
        int rafters = calcRafterCountLength() * calcRafterCountWidth();
        List<BillOfMaterialsLine> result = new ArrayList<>();
        result.add(new BillOfMaterialsLine(
                "Rafters",
                rafters,
                carport.getRafterMaterial().getUnit(),
                carport.getRafterMaterial().getDescription()
        ));
        return result;
    }

    private List<BillOfMaterialsLine> calculateFascia() {
        int fascias = (calcBeamCountLength() + calcRafterCountWidth()) * 2;
        List<BillOfMaterialsLine> result = new ArrayList<>();
        result.add(new BillOfMaterialsLine(
                "Fascia Boards",
                fascias,
                carport.getFasciaMaterial().getUnit(),
                carport.getFasciaMaterial().getDescription()
        ));
        return result;
    }

    private List<BillOfMaterialsLine> calculateRoofing() {
        int roofCovers = calcRoofCoverCountLength() * calcRoofCoverCountWidth();
        List<BillOfMaterialsLine> result = new ArrayList<>();
        result.add(new BillOfMaterialsLine(
                "Roof Covers",
                roofCovers,
                carport.getRoofingMaterial().getUnit(),
                carport.getRoofingMaterial().getDescription()
        ));
        return result;
    }

    //Calculation helper methods
    private int calcPostCountWidth() {
        int posts = 2;
        int maxLength = carport.getPostMaterial().getMaxLength();
        if (carport.getWidth() > maxLength) {
            for (int i = maxLength; i < carport.getWidth(); i += maxLength) {
                posts++;
            }
        }
        return posts;
    }

    private int calcPostCountLength() {
        int posts = 2;
        int maxDistance = carport.getPostMaterial().getMaxLengthBetweenPosts();
        if (carport.getLength() > maxDistance) {
            for (int i = maxDistance; i < carport.getLength(); i += maxDistance) {
                posts++;
            }
        }
        return posts;
    }

    private int calcBeamCountLength() {
        int beams = 1;
        int maxLength = carport.getBeamMaterial().getMaxLength();
        if (carport.getLength() > maxLength) {
            for (int i = maxLength; i < carport.getLength(); i += maxLength) {
                beams++;
            }
        }
        return beams;
    }

    private int calcRafterCountWidth() {
        int rafters = 1;
        int maxLength = carport.getRafterMaterial().getMaxLength();
        if (carport.getWidth() > maxLength) {
            for (int i = maxLength; i < carport.getWidth(); i += maxLength) {
                rafters++;
            }
        }
        return rafters;
    }

    private int calcRafterCountLength() {
        int rafters = 2;
        int maxDistance = carport.getRoofingMaterial().getMaxLengthBetweenRafters();
        if (carport.getLength() > maxDistance) {
            for (int i = maxDistance; i < carport.getLength(); i += maxDistance) {
                rafters++;
            }
        }
        return rafters;
    }

    private int calcRoofCoverCountLength() {
        int covers = 1;
        int maxLength = carport.getRoofingMaterial().getMaxLength();
        int overlap = carport.getRoofingMaterial().getSideOverlap();
        if (carport.getLength() > maxLength) {
            for (int i = maxLength; i < carport.getLength(); i += (maxLength - overlap)) {
                covers++;
            }
        }
        return covers;
    }

    private int calcRoofCoverCountWidth() {
        int covers = 1;
        int maxWidth = carport.getRoofingMaterial().getWidth();
        if (carport.getWidth() > maxWidth) {
            for (int i = maxWidth; i < carport.getWidth(); i += maxWidth) {
                covers++;
            }
        }
        return covers;
    }
}
