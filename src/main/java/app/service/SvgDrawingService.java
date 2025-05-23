package app.service;

import app.entities.products.carport.BillOfMaterial;
import app.entities.products.carport.Carport;
import app.entities.products.materials.MaterialRole;

public class SvgDrawingService {

    public static Svg generateCarportSvg(Carport carport) {
        int margin = 100;
        int carportLength = carport.getLength();
        int carportWidth = carport.getWidth();

        int outerWidth = carportLength + margin * 2;
        int outerHeight = carportWidth + margin * 2;

        Svg svg = new Svg(0, 0, "0 0 " + outerWidth + " " + outerHeight, "100%");

        CarportSvg carportSvg = new CarportSvg(carport);
        svg.addSvgWithTranslation(carportSvg.getSvgElement(), margin, margin);

        addMeasurements(svg, carport, margin);

        return svg;
    }

    private static void addMeasurements(Svg svg, Carport carport, int margin) {
        int carportLength = carport.getLength();
        int carportWidth = carport.getWidth();

        // Length arrow
        int xStart = margin;
        int xEnd = margin + carportLength;
        int arrowY = margin + carportWidth + 30;
        svg.addArrow(xStart, arrowY, xEnd, arrowY, "stroke:#000; stroke-width:1.5px");
        svg.addLine(xStart, arrowY - 5, xStart, arrowY + 5, "stroke:#000; stroke-width:1px");
        svg.addLine(xEnd, arrowY - 5, xEnd, arrowY + 5, "stroke:#000; stroke-width:1px");
        svg.addText((xStart + xEnd) / 2, arrowY + 15, 0, String.format("%.2f m", carportLength / 100.0));

        // Width arrow
        int yStart = margin;
        int yEnd = margin + carportWidth;
        int arrowX = margin - 30;
        svg.addArrow(arrowX, yStart, arrowX, yEnd, "stroke:#000; stroke-width:1.5px");
        svg.addLine(arrowX - 5, yStart, arrowX + 5, yStart, "stroke:#000; stroke-width:1px");
        svg.addLine(arrowX - 5, yEnd, arrowX + 5, yEnd, "stroke:#000; stroke-width:1px");
        svg.addText(arrowX - 10, (yStart + yEnd) / 2, -90, String.format("%.2f m", carportWidth / 100.0));

        // Rafters
        int rafterCount = carport.getBillOfMaterial().calcRafterCountLength();
        double rafterWidth = carport.getMaterialMap().get(MaterialRole.RAFTER).getWidth();
        double spacing = (double) (carportLength - rafterWidth) / (rafterCount - 1);

        int tickYTop = margin - 10;
        int labelY = tickYTop - 10;

        for (int i = 0; i < rafterCount; i++) {
            double x = margin + i * spacing;
            svg.addLine((int) x, tickYTop, (int) x, tickYTop + 8, "stroke:#000; stroke-width:1px");
            if (i < rafterCount - 1) {
                double labelX = x + spacing / 2;
                svg.addText((int) labelX, labelY, 0, String.format("%.2f", spacing / 100.0));
            }
        }

        // Beam spacing
        int beamStartY = margin + BillOfMaterial.OVERHANG_SIDE;
        int beamEndY = margin + carportWidth - BillOfMaterial.OVERHANG_SIDE;
        int arrowXX = margin + carportLength + 30;

        svg.addArrow(arrowXX, beamStartY, arrowXX, beamEndY, "stroke:#000; stroke-width:1.5px");
        svg.addLine(arrowXX - 5, beamStartY, arrowXX + 5, beamStartY, "stroke:#000; stroke-width:1px");
        svg.addLine(arrowXX - 5, beamEndY, arrowXX + 5, beamEndY, "stroke:#000; stroke-width:1px");

        int midY = (beamStartY + beamEndY) / 2;
        double beamSpacing = (carportWidth - 2.0 * BillOfMaterial.OVERHANG_SIDE) / 100.0;
        svg.addText(arrowXX + 10, midY, -90, String.format("%.2f m", beamSpacing));

        // Post spacing arrow (horizontal) above the carport
        int postCount = carport.getBillOfMaterial().calcPostsNeededLength();
        double postXStart = margin + BillOfMaterial.OVERHANG_FRONT;
        double postXEnd = margin + carport.getLength() - BillOfMaterial.OVERHANG_END;
        double postSpacing = (postXEnd - postXStart) / (postCount - 1);

        int postArrowY = margin - 40;
        for (int i = 0; i < postCount - 1; i++) {
            double x1 = postXStart + i * postSpacing;
            double x2 = x1 + postSpacing;
            double labelX = (x1 + x2) / 2;

            // Draw arrow between posts
            svg.addArrow((int) x1, postArrowY, (int) x2, postArrowY, "stroke:#000; stroke-width:1.5px");

            // End ticks
            svg.addLine((int) x1, postArrowY - 4, (int) x1, postArrowY + 4, "stroke:#000; stroke-width:1px");
            svg.addLine((int) x2, postArrowY - 4, (int) x2, postArrowY + 4, "stroke:#000; stroke-width:1px");

            // Measurement text
            svg.addText((int) labelX, postArrowY - 8, 0, String.format("%.2f m", postSpacing / 100.0));
        }
    }
}
