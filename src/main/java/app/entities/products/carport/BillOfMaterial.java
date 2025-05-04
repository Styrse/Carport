package app.entities.products.carport;

import app.entities.products.materials.Material;
import app.entities.products.materials.MaterialRole;
import app.entities.products.materials.planks.Beam;
import app.entities.products.materials.planks.Post;
import app.entities.products.materials.roof.RoofCover;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BillOfMaterial {
    private final Carport carport;
    private final List<BillOfMaterialsItem> lines = new ArrayList<>();

    //TODO: Add front overhang. What can the max be?
    private final static int OVERHANG = 30;

    public BillOfMaterial(Carport carport) {
        this.carport = carport;
        calculateMaterials();
    }

    public double calcTotalPrice() {
        return lines.stream()
                .mapToDouble(item -> item.getQuantity() * item.getSalesPrice())
                .sum();
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
        Material material = carport.getMaterial().get(MaterialRole.POST);
        result.add(new BillOfMaterialsItem(
                material.getName(),
                //Todo: Calculate the needed post height. Remember to check how deep they go and think of the slope even for a flat roof
                Collections.max(material.getPreCutsLengths()),
                posts,
                material.getUnit(),
                material.getDescription(),
                material.getSalesPrice() * Collections.max(material.getPreCutsLengths())
        ));
        return result;
    }

    public List<BillOfMaterialsItem> getBeams() {
        List<BillOfMaterialsItem> beamList = new ArrayList<>();
        Material material = carport.getMaterial().get(MaterialRole.BEAM);

        float distanceBetweenPosts = (float) (carport.getLength() - OVERHANG) / calcPostsNeededLength();

        int maxPostPrBeam = (int) Math.floor(Collections.max(material.getPreCutsLengths()) / distanceBetweenPosts);

        int normalBestFit = bestFitLength(material, maxPostPrBeam * distanceBetweenPosts);
        int endBestFit = bestFitLength(material, carport.getLength() - ((maxPostPrBeam * distanceBetweenPosts) * (maxPostPrBeam - 1)));

        int normalBeams = (int) Math.floor((double) carport.getLength() / normalBestFit) * calcPostCountWidth();
        int endBeam = calcPostCountWidth();

        beamList.add(new BillOfMaterialsItem(
                material.getName(),
                normalBestFit,
                normalBeams,
                material.getUnit(),
                material.getDescription(),
                material.getSalesPrice() * normalBestFit
        ));

        beamList.add(new BillOfMaterialsItem(
                material.getName(),
                endBestFit,
                endBeam,
                material.getUnit(),
                material.getDescription(),
                material.getSalesPrice() * endBestFit
        ));
        return beamList;
    }

    private List<BillOfMaterialsItem> getRafters() {
        List<BillOfMaterialsItem> rafterList = new ArrayList<>();
        Material material = carport.getMaterial().get(MaterialRole.RAFTER);

        int numberOfRafters = calcRafterCountLength() * calcRafterCountWidth();
        int bestFitLength = bestFitLength(material, (float) carport.getWidth() / calcRafterCountWidth());

        rafterList.add(new BillOfMaterialsItem(
                material.getName(),
                bestFitLength,
                numberOfRafters,
                material.getUnit(),
                material.getDescription(),
                material.getSalesPrice() * bestFitLength
        ));
        return rafterList;
    }

    private List<BillOfMaterialsItem> getFascia() {
        List<BillOfMaterialsItem> fasciaList = new ArrayList<>();
        Material material = carport.getMaterial().get(MaterialRole.FASCIA);

        int fasciasCountLength = (int) Math.ceil((double) carport.getLength() / Collections.max(material.getPreCutsLengths()));
        int fasciasCountWidth = (int) Math.ceil((double) carport.getWidth() / Collections.max(material.getPreCutsLengths()));

        int bestFitLength = bestFitLength(material, (float) carport.getLength() / fasciasCountLength);
        int bestFitWidth = bestFitLength(material, (float) carport.getWidth() / fasciasCountWidth);

        fasciaList.add(new BillOfMaterialsItem(
                material.getName(),
                bestFitLength,
                fasciasCountLength * 2,
                material.getUnit(),
                material.getDescription(),
                material.getSalesPrice() * bestFitLength
        ));

        fasciaList.add(new BillOfMaterialsItem(
                material.getName(),
                bestFitWidth,
                fasciasCountWidth * 2,
                material.getUnit(),
                material.getDescription(),
                material.getSalesPrice() * bestFitWidth
        ));
        return fasciaList;
    }

    private List<BillOfMaterialsItem> getRoofCover() {
        List<BillOfMaterialsItem> roofCoverList = new ArrayList<>();
        Material material = carport.getMaterial().get(MaterialRole.ROOF_COVER);

        int totalCovers = calcRoofCoverCountLength() * calcRoofCoverCountWidth();

        int idealRoofLength = Collections.max(material.getPreCutsLengths());

        int bestFitLength = bestFitLength(material, (float) idealRoofLength / calcRoofCoverCountLength());

        roofCoverList.add(new BillOfMaterialsItem(
                material.getName(),
                bestFitLength,
                totalCovers,
                material.getUnit(),
                material.getDescription(),
                material.getSalesPrice() * bestFitLength
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
        int maxLength = Collections.max(carport.getMaterial().get(MaterialRole.POST).getPreCutsLengths());
        if (carport.getWidth() > maxLength) {
            for (int i = maxLength; i < carport.getWidth(); i += maxLength) {
                posts++;
            }
        }
        return posts;
    }

    public int calcPostCountLength() {
        int posts = 2;
        int maxDistanceBetweenPosts = ((Beam) carport.getMaterial().get(MaterialRole.BEAM)).getPostGap();
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

        int postsByLoad = (int) Math.ceil(carportSize / ((Post) carport.getMaterial().get(MaterialRole.POST)).getBucklingCapacity());

        int raftersNeededWidth = (int) Math.ceil((double) carport.getWidth() / Collections.max(carport.getMaterial().get(MaterialRole.RAFTER).getPreCutsLengths()));

        int postByLoadNeededPrLength = postsByLoad / (raftersNeededWidth + 1);

        int postLengthByBeamMaxGap = calcPostCountLength();

        return Math.max(postByLoadNeededPrLength, postLengthByBeamMaxGap);
    }

    //================================
    //============= Beams ============
    //================================
    public int calcBeamCountLength() {
        int beams = 1;
        int maxLength = Collections.max(carport.getMaterial().get(MaterialRole.BEAM).getPreCutsLengths());
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
        int maxLength = Collections.max(carport.getMaterial().get(MaterialRole.RAFTER).getPreCutsLengths());
        if (carport.getWidth() > maxLength) {
            for (int i = maxLength; i < carport.getWidth(); i += maxLength) {
                rafters++;
            }
        }
        return rafters;
    }

    public int calcRafterCountLength() {
        int rafters = 2;
        int maxDistance = ((RoofCover) carport.getMaterial().get(MaterialRole.ROOF_COVER)).getGapRafters();
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
        int maxLength = Collections.max(carport.getMaterial().get(MaterialRole.ROOF_COVER).getPreCutsLengths());
        int lengthOverlap = ((RoofCover) carport.getMaterial().get(MaterialRole.ROOF_COVER)).getLengthOverlap();
        if (carport.getLength() > maxLength) {
            for (int i = maxLength; i < carport.getLength(); i += (maxLength - lengthOverlap)) {
                covers++;
            }
        }
        return covers;
    }

    public int calcRoofCoverCountWidth() {
        int covers = 1;
        int maxWidth = carport.getMaterial().get(MaterialRole.ROOF_COVER).getWidth();
        float sideOverlap = ((RoofCover) carport.getMaterial().get(MaterialRole.ROOF_COVER)).getSideOverlap();
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
