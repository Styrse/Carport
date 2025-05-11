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
        for (BillOfMaterialsItem item : lines) {
            System.out.println("Name: " + item.getName());
            System.out.println("Quantity: " + item.getQuantity());
            System.out.println("Length: " + item.getLength());
            System.out.println("Price pr meter: " + item.getMeterSalesPrice());
            System.out.println("Total price: " + item.getSalesPrice());
            System.out.println();
            System.out.println();
            System.out.println();
        }
    }

    public double calcTotalPrice() {
        return lines.stream().mapToDouble(BillOfMaterialsItem::getSalesPrice).sum();
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
                Collections.max(material.getPreCutLengths()),
                posts,
                material.getUnit(),
                material.getDescription(),
                material.getSalesPrice()
        ));
        return result;
    }

    public List<BillOfMaterialsItem> getBeams() {
        List<BillOfMaterialsItem> beamList = new ArrayList<>();
        Material material = carport.getMaterial().get(MaterialRole.BEAM);

        float distanceBetweenPosts = (float) (carport.getLength() - OVERHANG) / calcPostsNeededLength();

        int maxPostPrBeam = (int) Math.floor(Collections.max(material.getPreCutLengths()) / distanceBetweenPosts);

        float normalBeamLength = carport.getLength() <= Collections.max(material.getPreCutLengths()) ? carport.getLength() : (maxPostPrBeam * distanceBetweenPosts);

        int normalBestFit = bestFitLength(material, normalBeamLength);
        int normalBeams = (int) Math.floor((double) carport.getLength() / normalBestFit) * calcPostCountWidth();

        int beamsPerRow = (int) Math.floor(carport.getLength() / normalBestFit);
        float leftoverLength = carport.getLength() - (beamsPerRow * normalBestFit);
        int endBestFit = bestFitLength(material, leftoverLength);

        int endBeam = calcPostCountWidth();

        if (normalBeams > 0) {
            beamList.add(new BillOfMaterialsItem(
                    material.getName(),
                    normalBestFit,
                    normalBeams,
                    material.getUnit(),
                    material.getDescription(),
                    material.getSalesPrice()
            ));
        }

        beamList.add(new BillOfMaterialsItem(
                material.getName(),
                endBestFit,
                endBeam,
                material.getUnit(),
                material.getDescription(),
                material.getSalesPrice()
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
                material.getSalesPrice()
        ));
        return rafterList;
    }

    private List<BillOfMaterialsItem> getFascia() {
        List<BillOfMaterialsItem> fasciaList = new ArrayList<>();
        Material material = carport.getMaterial().get(MaterialRole.FASCIA);

        int fasciasCountLength = (int) Math.ceil((double) carport.getLength() / Collections.max(material.getPreCutLengths()));
        int fasciasCountWidth = (int) Math.ceil((double) carport.getWidth() / Collections.max(material.getPreCutLengths()));

        int bestFitLength = bestFitLength(material, (float) carport.getLength() / fasciasCountLength);
        int bestFitWidth = bestFitLength(material, (float) carport.getWidth() / fasciasCountWidth);

        if (bestFitWidth == bestFitLength) {
            fasciaList.add(new BillOfMaterialsItem(
                    material.getName(),
                    bestFitLength,
                    (fasciasCountLength + fasciasCountWidth) * 2,
                    material.getUnit(),
                    material.getDescription(),
                    material.getSalesPrice()
            ));
            return fasciaList;
        } else {
            fasciaList.add(new BillOfMaterialsItem(
                    material.getName(),
                    bestFitLength,
                    fasciasCountLength * 2,
                    material.getUnit(),
                    material.getDescription(),
                    material.getSalesPrice()
            ));

            fasciaList.add(new BillOfMaterialsItem(
                    material.getName(),
                    bestFitWidth,
                    fasciasCountWidth * 2,
                    material.getUnit(),
                    material.getDescription(),
                    material.getSalesPrice()
            ));
            return fasciaList;
        }
    }

    private List<BillOfMaterialsItem> getRoofCover() {
        List<BillOfMaterialsItem> roofCoverList = new ArrayList<>();
        Material material = carport.getMaterial().get(MaterialRole.ROOF_COVER);

        int totalCovers = calcRoofCoverCountLength() * calcRoofCoverCountWidth();

        int idealRoofLength = Collections.max(material.getPreCutLengths());

        int bestFitLength = bestFitLength(material, (float) idealRoofLength / calcRoofCoverCountLength());

        roofCoverList.add(new BillOfMaterialsItem(
                material.getName(),
                bestFitLength,
                totalCovers,
                material.getUnit(),
                material.getDescription(),
                material.getSalesPrice()
        ));
        return roofCoverList;
    }

    // Finds the first length in PREDEFINED_LENGTHS that is greater than the required value or returns the maximum length.
    private int bestFitLength(Material material, float neededLength) {
        return material.getPreCutLengths().stream()
                .filter(length -> length >= neededLength)
                .findFirst()
                .orElse(Collections.max(material.getPreCutLengths()));
    }

    //================================
    //============= Posts ============
    //================================
    public int calcPostCountWidth() {
        int posts = 2;
        int maxLength = Collections.max(carport.getMaterial().get(MaterialRole.RAFTER).getPreCutLengths());
        if (carport.getWidth() > maxLength) {
            for (int i = maxLength; i < carport.getWidth(); i += maxLength) {
                posts++;
            }
        }
        return posts;
    }

    public int calcPostCountLength() {
        int posts = 2;
        int postGap = ((Beam) carport.getMaterial().get(MaterialRole.BEAM)).getPostGap();
        if (carport.getLength() > postGap) {
            for (int i = postGap; i < carport.getLength(); i += postGap) {
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

        int raftersNeededWidth = (int) Math.ceil((double) carport.getWidth() / Collections.max(carport.getMaterial().get(MaterialRole.RAFTER).getPreCutLengths()));

        int postByLoadNeededPrLength = postsByLoad / (raftersNeededWidth + 1);

        int postLengthByBeamMaxGap = calcPostCountLength();

        return Math.max(postByLoadNeededPrLength, postLengthByBeamMaxGap);
    }

    //================================
    //============= Beams ============
    //================================
    public int calcBeamCountLength() {
        int beams = 1;
        int maxLength = Collections.max(carport.getMaterial().get(MaterialRole.BEAM).getPreCutLengths());
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
        int maxLength = Collections.max(carport.getMaterial().get(MaterialRole.RAFTER).getPreCutLengths());
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
        int maxLength = Collections.max(carport.getMaterial().get(MaterialRole.ROOF_COVER).getPreCutLengths());
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
        float maxWidth = carport.getMaterial().get(MaterialRole.ROOF_COVER).getWidth();
        float sideOverlap = ((RoofCover) carport.getMaterial().get(MaterialRole.ROOF_COVER)).getSideOverlap();
        if (carport.getWidth() > maxWidth) {
            for (float i = maxWidth; i < carport.getWidth(); i += (maxWidth - sideOverlap)) {
                covers++;
            }
        }
        return covers;
    }
}
