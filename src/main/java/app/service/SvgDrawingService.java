package app.service;

import app.entities.products.carport.BillOfMaterial;
import app.entities.products.carport.Carport;
import app.entities.products.materials.Material;
import app.entities.products.materials.MaterialRole;
import app.entities.products.materials.planks.Beam;
import app.entities.products.materials.planks.Fascia;

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
        svg.addText(arrowXX + 20, midY, -90, String.format("%.2f m", beamSpacing));

        // Post spacing arrows and overhang indicators
        int postCount = carport.getBillOfMaterial().calcPostsNeededLength();
        double postSize = carport.getMaterialMap().get(MaterialRole.POST).getWidth();

        // Adjusted: OVERHANG_END should start *after* the last post
        double postXStart = margin + BillOfMaterial.OVERHANG_FRONT;
        double postXEnd = margin + carport.getLength() - BillOfMaterial.OVERHANG_END;
        double postSpacing = (postXEnd - postXStart - postSize) / (postCount - 1);

        int postArrowY = margin - 40;

        // Overhang FRONT
        int ohFrontStart = margin;
        int ohFrontEnd = (int) postXStart;
        svg.addArrow(ohFrontStart, postArrowY, ohFrontEnd, postArrowY, "stroke:#000; stroke-width:1.5px");
        svg.addLine(ohFrontStart, postArrowY - 4, ohFrontStart, postArrowY + 4, "stroke:#000; stroke-width:1px");
        svg.addLine(ohFrontEnd, postArrowY - 4, ohFrontEnd, postArrowY + 4, "stroke:#000; stroke-width:1px");
        svg.addText((ohFrontStart + ohFrontEnd) / 2, postArrowY - 8, 0, String.format("%.2f m", BillOfMaterial.OVERHANG_FRONT / 100.0));

        // Post spacings
        for (int i = 0; i < postCount; i++) {
            double x = postXStart + i * postSpacing;
            svg.addRectangle(x, margin + BillOfMaterial.OVERHANG_SIDE, postSize, postSize, "stroke:#000; fill:#888");

            if (i < postCount - 1) {
                double nextX = x + postSpacing;
                double midX = (x + nextX) / 2;
                svg.addArrow((int) x, postArrowY, (int) nextX, postArrowY, "stroke:#000; stroke-width:1.5px");
                svg.addLine((int) x, postArrowY - 4, (int) x, postArrowY + 4, "stroke:#000; stroke-width:1px");
                svg.addLine((int) nextX, postArrowY - 4, (int) nextX, postArrowY + 4, "stroke:#000; stroke-width:1px");
                svg.addText((int) midX, postArrowY - 8, 0, String.format("%.2f m", postSpacing / 100.0));
            }
        }

        // Adjusted: OVERHANG_END starts *after* the last post
        int ohEndStart = (int) (postXStart + (postCount - 1) * postSpacing + postSize);
        int ohEndEnd = margin + carportLength;
        svg.addArrow(ohEndStart, postArrowY, ohEndEnd, postArrowY, "stroke:#000; stroke-width:1.5px");
        svg.addLine(ohEndStart, postArrowY - 4, ohEndStart, postArrowY + 4, "stroke:#000; stroke-width:1px");
        svg.addLine(ohEndEnd, postArrowY - 4, ohEndEnd, postArrowY + 4, "stroke:#000; stroke-width:1px");
        svg.addText((ohEndStart + ohEndEnd) / 2, postArrowY - 8, 0, String.format("%.2f m", BillOfMaterial.OVERHANG_END / 100.0));

        // Cross wire
        double firstPostX = postXStart;
        double firstPostY = margin + BillOfMaterial.OVERHANG_SIDE;

        double lastPostX = postXStart + (postCount - 1) * postSpacing + postSize;
        double lastPostY = margin + carport.getWidth() - BillOfMaterial.OVERHANG_SIDE;

        // Diagonal from top-left to bottom-right
        svg.addLine((int) firstPostX, (int) firstPostY, (int) lastPostX, (int) lastPostY,
                "stroke:#999; stroke-width:1px; stroke-dasharray:5,5");

        // Diagonal from bottom-left to top-right
        svg.addLine((int) firstPostX, (int) lastPostY, (int) lastPostX, (int) firstPostY,
                "stroke:#999; stroke-width:1px; stroke-dasharray:5,5");
    }

    public static Svg generateCarportSideSvg(Carport carport) {
        int length = carport.getLength();
        int height = carport.getHeight();
        int roofAngle = carport.getRoofAngle();

        Material post = carport.getMaterialMap().get(MaterialRole.POST);
        double postWidth = post.getWidth();

        int roofHeight = height;
        if (roofAngle > 0) {
            roofHeight += length / 10;
        }

        int margin = 100;
        int outerWidth = length + margin * 2;
        int outerHeight = roofHeight + margin * 2;

        Svg svg = new Svg(0, 0, "0 0 " + outerWidth + " " + outerHeight, "100%");

        // Ground line
        int groundY = margin + roofHeight;
        svg.addLine(margin, groundY, margin + length, groundY, "stroke:#000; stroke-width:2px");

        // Posts
        int postCount = carport.getBillOfMaterial().calcPostsNeededLength();
        double postXStart = margin + BillOfMaterial.OVERHANG_FRONT;
        double postXEnd = margin + length - BillOfMaterial.OVERHANG_END - postWidth;
        double spacing = (postXEnd - postXStart) / (postCount - 1);
        int postY = groundY - height;

        for (int i = 0; i < postCount; i++) {
            double x = postXStart + i * spacing;
            svg.addRectangle(x, postY, height, postWidth, "stroke:#000; fill:#888");
        }

        // Distance markers between posts
        int markerY = groundY + 20;
        for (int i = 0; i < postCount - 1; i++) {
            double x1 = postXStart + i * spacing;
            double x2 = x1 + spacing;
            double midX = (x1 + x2) / 2;

            // Draw arrow between two posts
            svg.addArrow((int) x1, markerY, (int) x2, markerY, "stroke:#000; stroke-width:1.5px");

            // End ticks
            svg.addLine((int) x1, markerY - 4, (int) x1, markerY + 4, "stroke:#000; stroke-width:1px");
            svg.addLine((int) x2, markerY - 4, (int) x2, markerY + 4, "stroke:#000; stroke-width:1px");

            // Label
            svg.addText((int) midX, markerY + 15, 0, String.format("%.2f m", spacing / 100.0));
        }

        // Roof line
        if (roofAngle == 0) {
            svg.addLine(margin, postY, margin + length, postY, "stroke:#000; stroke-width:2px");
        } else {
            double slopeDrop = length * (roofAngle / 100.0);
            int peakY = (int) (postY - slopeDrop);
            svg.addLine(margin, postY, margin + length, peakY, "stroke:#000; stroke-width:2px");
        }

        // Length arrow
        int arrowY = groundY + 50;
        svg.addArrow(margin, arrowY, margin + length, arrowY, "stroke:#000; stroke-width:1.5px");
        svg.addLine(margin, arrowY - 5, margin, arrowY + 5, "stroke:#000; stroke-width:1px");
        svg.addLine(margin + length, arrowY - 5, margin + length, arrowY + 5, "stroke:#000; stroke-width:1px");
        svg.addText(margin + length / 2, arrowY + 15, 0, String.format("%.2f m", length / 100.0));

        // Height indicator
        int heightArrowX = margin - 40;
        int topY = groundY - height;

        svg.addArrow(heightArrowX, groundY, heightArrowX, topY, "stroke:#000; stroke-width:1.5px");
        svg.addLine(heightArrowX - 5, groundY, heightArrowX + 5, groundY, "stroke:#000; stroke-width:1px");
        svg.addLine(heightArrowX - 5, topY, heightArrowX + 5, topY, "stroke:#000; stroke-width:1px");
        svg.addText(heightArrowX - 10, (groundY + topY) / 2, -90, String.format("%.2f m", height / 100.0));

        // Fascia board
        Fascia fascia = (Fascia) carport.getMaterialMap().get(MaterialRole.FASCIA);
        if (fascia != null) {
            double fasciaHeight = fascia.getHeight();
            int fasciaY = groundY - height;
            svg.addRectangle(margin, fasciaY, fasciaHeight, length,
                    "stroke:#000000; fill:#ccc");
        }

        // Beam
        Beam beam = (Beam) carport.getMaterialMap().get(MaterialRole.BEAM);
        if (beam != null) {
            double beamHeight = beam.getHeight();
            int beamY = groundY - height + (int) fascia.getHeight();
            svg.addRectangle(margin, beamY, beamHeight, length,
                    "stroke:#000000; fill:#bbb");
        }

        return svg;
    }
}
