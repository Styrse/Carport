package app.entities.products.carport;

import app.entities.products.materials.Material;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//import static app.entities.products.materials.Material.PREDEFINED_LENGTHS;

public class BillOfMaterial {
    private final Carport carport;
    private final List<BillOfMaterialsItem> lines = new ArrayList<>();

    private final static int OVERHANG = 30;

    public BillOfMaterial(Carport carport) {
        this.carport = carport;
        calculateMaterials();
    }

    public List<BillOfMaterialsItem> getLines() {
        return lines;
    }

    private void calculateMaterials() {
        lines.addAll(getPosts());
        lines.addAll(getBeams());
        lines.addAll(getRafters());
        lines.addAll(getFascia());
        lines.addAll(getRoofCover());
    }

    private List<BillOfMaterialsItem> getPosts() {
        int posts = calcPostsNeededLength() * calcPostCountWidth();
        List<BillOfMaterialsItem> result = new ArrayList<>();
        result.add(new BillOfMaterialsItem(
                carport.getPost().getName(),
                //Todo: Calculate the need post height
                600,
                posts,
                carport.getPost().getUnit(),
                carport.getPost().getDescription()
        ));
        return result;
    }

    public List<BillOfMaterialsItem> getBeams() {
        List<BillOfMaterialsItem> beamList = new ArrayList<>();

        float distanceBetweenPosts = (float) (carport.getLength() - OVERHANG) / calcPostsNeededLength();

        int maxPostPrBeam = (int) Math.floor(Collections.max(carport.getBeam().getPreCutsLengths()) / distanceBetweenPosts);

        int normalBestFit = bestFitLength(carport.getBeam(),maxPostPrBeam * distanceBetweenPosts);
        int endBestFit = bestFitLength(carport.getBeam(),carport.getLength() - ((maxPostPrBeam * distanceBetweenPosts) * (maxPostPrBeam - 1)));

        int normalBeams = (int) Math.floor((double) carport.getLength() / normalBestFit) * calcPostCountWidth();
        int endBeam = calcPostCountWidth();

        beamList.add(new BillOfMaterialsItem(
                carport.getBeam().getName(),
                normalBestFit,
                normalBeams,
                carport.getBeam().getUnit(),
                carport.getBeam().getDescription()
        ));

        beamList.add(new BillOfMaterialsItem(
                carport.getBeam().getName(),
                endBestFit,
                endBeam,
                carport.getBeam().getUnit(),
                carport.getBeam().getDescription()
        ));
        return beamList;
    }

    private List<BillOfMaterialsItem> getRafters() {
        List<BillOfMaterialsItem> rafterList = new ArrayList<>();

        int numberOfRafters = calcRafterCountLength() * calcRafterCountWidth();
        int bestFitLength = bestFitLength(carport.getRafter(), (float) carport.getWidth() / calcRafterCountWidth());

        rafterList.add(new BillOfMaterialsItem(
                carport.getRafter().getName(),
                bestFitLength,
                numberOfRafters,
                carport.getRafter().getUnit(),
                carport.getRafter().getDescription()
        ));
        return rafterList;
    }

    private List<BillOfMaterialsItem> getFascia() {
        List<BillOfMaterialsItem> fasciaList = new ArrayList<>();

        int fasciasCountLength = (int) Math.ceil((double) carport.getLength() / Collections.max(carport.getFascia().getPreCutsLengths()));
        int fasciasCountWidth = (int) Math.ceil((double) carport.getWidth() / Collections.max(carport.getFascia().getPreCutsLengths()));

        int bestFitLength = bestFitLength(carport.getFascia(), (float) carport.getLength() / fasciasCountLength);
        int bestFitWidth = bestFitLength(carport.getFascia(), (float) carport.getWidth() / fasciasCountWidth);

        fasciaList.add(new BillOfMaterialsItem(
                carport.getFascia().getName(),
                bestFitLength,
                fasciasCountLength * 2,
                carport.getFascia().getUnit(),
                carport.getFascia().getDescription()
        ));

        fasciaList.add(new BillOfMaterialsItem(
                carport.getFascia().getName(),
                bestFitWidth,
                fasciasCountWidth * 2,
                carport.getFascia().getUnit(),
                carport.getFascia().getDescription()
        ));
        return fasciaList;
    }

    private List<BillOfMaterialsItem> getRoofCover() {
        List<BillOfMaterialsItem> roofCoverList = new ArrayList<>();

        int totalCovers = calcRoofCoverCountLength() * calcRoofCoverCountWidth();

        int idealRoofLength = Collections.max(carport.getRoofCover().getPreCutsLengths());

        int bestFitLength = bestFitLength(carport.getRoofCover(), (float) idealRoofLength / calcRoofCoverCountLength());

        roofCoverList.add(new BillOfMaterialsItem(
                carport.getRoofCover().getName(),
                bestFitLength,
                totalCovers,
                carport.getRoofCover().getUnit(),
                carport.getRoofCover().getDescription()
        ));
        return roofCoverList;
    }

    // Finds the first length in PREDEFINED_LENGTHS that is greater than the required value or returns the maximum length.
    private int bestFitLength(Material material, float neededLength) {
        return material.getPreCutsLengths().stream()
                .filter(length -> length > neededLength)
                .findFirst()
                .orElse(Collections.max(material.getPreCutsLengths()));
    }

    //================================
    //============= Posts ============
    //================================
    public int calcPostCountWidth() {
        int posts = 2;
        int maxLength = Collections.max(carport.getRafter().getPreCutsLengths());
        if (carport.getWidth() > maxLength) {
            for (int i = maxLength; i < carport.getWidth(); i += maxLength) {
                posts++;
            }
        }
        return posts;
    }

    public int calcPostCountLength() {
        int posts = 2;
        int maxDistanceBetweenPosts = carport.getBeam().getPostGap();
        if (carport.getLength() > maxDistanceBetweenPosts) {
            for (int i = maxDistanceBetweenPosts; i < carport.getLength(); i += maxDistanceBetweenPosts) {
                posts++;
            }
        }
        return posts;
    }

    //1. Calculate the m/2 of the carport (divided by 10.000 to get in m/2 and not cm/2)
    //2. Calculate the total posts needed to carry the roof based on a posts buckling capacity.
    //3. Then get the posts needed based on the beams maxGap.
    //4. Returns the greater of the two.
    public int calcPostsNeededLength() {
        float carportSize = ((float) (carport.getLength() * carport.getWidth()) / 10000);
        int postsByLoad = (int) Math.ceil(carportSize / carport.getPost().getBucklingCapacity());

        int raftersNeededWidth = (int) Math.ceil((double) carport.getWidth() / Collections.max(carport.getRafter().getPreCutsLengths()));

        int postByLoadNeededPrLength = postsByLoad / (raftersNeededWidth + 1);

        int postLengthByBeamMaxGap = calcPostCountLength();

        return Math.max(postByLoadNeededPrLength, postLengthByBeamMaxGap);
    }

    //================================
    //============= Beams ============
    //================================
    public int calcBeamCountLength() {
        int beams = 1;
        int maxLength = Collections.max(carport.getBeam().getPreCutsLengths());
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
    public int calcRafterCountWidth() {
        int rafters = 1;
        int maxLength = Collections.max(carport.getRafter().getPreCutsLengths());
        if (carport.getWidth() > maxLength) {
            for (int i = maxLength; i < carport.getWidth(); i += maxLength) {
                rafters++;
            }
        }
        return rafters;
    }

    public int calcRafterCountLength() {
        int rafters = 2;
        int maxDistance = carport.getRoofCover().getGapRafters();
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
    public int calcRoofCoverCountLength() {
        int covers = 1;
        int maxLength = Collections.max(carport.getRoofCover().getPreCutsLengths());
        int lengthOverlap = carport.getRoofCover().getLengthOverlap();
        if (carport.getLength() > maxLength) {
            for (int i = maxLength; i < carport.getLength(); i += (maxLength - lengthOverlap)) {
                covers++;
            }
        }
        return covers;
    }

    public int calcRoofCoverCountWidth() {
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

    @Override
    public String toString() {
        return "BillOfMaterial{" +
                "carport=" + carport +
                ", lines=" + lines +
                '}';
    }
}
