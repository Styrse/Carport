package app.service;

import app.entities.products.carport.BillOfMaterial;
import app.entities.products.carport.Carport;
import app.entities.products.materials.Material;
import app.entities.products.materials.MaterialRole;

public class CarportSvg {
    private Carport carport;
    private Svg carportSvg;

    public CarportSvg(Carport carport) {
        this.carport = carport;
        String viewBox = "0 0 " + carport.getLength() + " " + carport.getWidth();
        carportSvg = new Svg(0, 0, viewBox, "75%");
        carportSvg.addRectangle(0, 0, carport.getWidth(), carport.getLength(),
                "stroke-width:1px; stroke:#000000; fill: #ffffff");
        addPosts();
        addBeams();
        addRafters();
    }

    private void addPosts() {
        Material post = carport.getMaterialMap().get(MaterialRole.POST);
        double postSize = post.getWidth();
        int postCountLength = carport.getBillOfMaterial().calcPostsNeededLength();
        int postCountWidth = carport.getBillOfMaterial().calcPostCountWidth();
        
        double xEnd = carport.getLength() - BillOfMaterial.OVERHANG_END - postSize;
        double xStart = BillOfMaterial.OVERHANG_FRONT;
        double spacingX = (xEnd - xStart) / (postCountLength - 1);

        double spacingY = (carport.getWidth() - 2 * BillOfMaterial.OVERHANG_SIDE - postSize) / (postCountWidth - 1);

        for (int row = 0; row < postCountWidth; row++) {
            double y = BillOfMaterial.OVERHANG_SIDE + row * spacingY;

            for (int col = 0; col < postCountLength; col++) {
                double x = xStart + col * spacingX;
                carportSvg.addRectangle(x, y, postSize, postSize, "stroke:#000000; fill:#888888");
            }
        }
    }

    private void addBeams() {
        Material beam = carport.getMaterialMap().get(MaterialRole.BEAM);
        if (beam == null) return;

        double beamWidth = beam.getWidth();
        int beamRows = carport.getBillOfMaterial().calcPostCountWidth();

        double spacingY = (double) (carport.getWidth() - 2 * BillOfMaterial.OVERHANG_SIDE - beamWidth) / (beamRows - 1);

        for (int row = 0; row < beamRows; row++) {
            double y = BillOfMaterial.OVERHANG_SIDE + row * spacingY;

            carportSvg.addRectangle(0, y, beamWidth, carport.getLength(),
                    "stroke:#000000; fill:#ffffff");
        }
    }

    private void addRafters() {
        int rafterCount = carport.getBillOfMaterial().calcRafterCountLength();
        double rafterWidth = carport.getMaterialMap().get(MaterialRole.RAFTER).getWidth();

        double spacing = (double) (carport.getLength() - rafterWidth) / (rafterCount - 1);

        for (int i = 0; i < rafterCount; i++) {
            double x = i * spacing;

            carportSvg.addRectangle(x, 0.0, carport.getWidth(), rafterWidth,
                    "stroke:#000000; fill:#ffffff");
        }
    }

    public Svg getSvgElement() {
        return carportSvg;
    }

    @Override
    public String toString() {
        return carportSvg.toString();
    }
}
