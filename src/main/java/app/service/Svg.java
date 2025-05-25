package app.service;

public class Svg {
    private static final String SVG_TEMPLATE = "<svg version=\"1.1\"\n" +
            "     x=\"%d\" y=\"%d\"\n" +
            "     viewBox=\"%s\"  width=\"%s\" \n" +
            "     preserveAspectRatio=\"xMinYMin\">";

    private static final String SVG_ARROW_DEFS = "<defs>\n" +
            "        <marker id=\"beginArrow\" markerWidth=\"12\" markerHeight=\"12\" refX=\"0\" refY=\"6\" orient=\"auto\">\n" +
            "            <path d=\"M0,6 L12,0 L12,12 L0,6\" style=\"fill: #000000;\" />\n" +
            "        </marker>\n" +
            "        <marker id=\"endArrow\" markerWidth=\"12\" markerHeight=\"12\" refX=\"12\" refY=\"6\" orient=\"auto\">\n" +
            "            <path d=\"M0,0 L12,6 L0,12 L0,0 \" style=\"fill: #000000;\" />\n" +
            "        </marker>\n" +
            "    </defs>";

    private static final String SVG_RECT_TEMPLATE = "<rect x=\"%.2f\" y=\"%.2f\" height=\"%f\" width=\"%f\" style=\"%s\" />";

    private StringBuilder svg = new StringBuilder();

    public Svg(int x, int y, String viewBox, String width) {
        svg.append(String.format(SVG_TEMPLATE, x, y, viewBox, width));
        svg.append(SVG_ARROW_DEFS);
    }

    public void addRectangle(double x, double y, double height, double width, String style) {
        svg.append(String.format(SVG_RECT_TEMPLATE, x, y, height, width, style ));
    }


    public void addLine(int x1, int y1, int x2, int y2, String style) {
        svg.append(String.format("<line x1=\"%d\" y1=\"%d\" x2=\"%d\" y2=\"%d\" style=\"%s\" />\n",
                x1, y1, x2, y2, style));
    }

    public void addArrow(int x1, int y1, int x2, int y2, String style) {
        svg.append(String.format(
                "<line x1=\"%d\" y1=\"%d\" x2=\"%d\" y2=\"%d\" style=\"%s\" marker-start=\"url(#beginArrow)\" marker-end=\"url(#endArrow)\" />\n",
                x1, y1, x2, y2, style));
    }

    public void addText(int x, int y, int rotation, String text) {
        svg.append(String.format(
                "<text x=\"%d\" y=\"%d\" transform=\"rotate(%d %d,%d)\" font-family=\"Arial\" font-size=\"12\" text-anchor=\"middle\">%s</text>\n",
                x, y, rotation, x, y, text));
    }

    public void addSvgWithTranslation(Svg innerSvg, int offsetX, int offsetY) {
        svg.append(String.format("<g transform=\"translate(%d,%d)\">", offsetX, offsetY));
        svg.append(innerSvg.toInnerSvg());
        svg.append("</g>");
    }

    public String toInnerSvg() {
        return svg.toString()
                .replaceFirst("(?s)^<svg[^>]*>", "")
                .replaceFirst("</svg>$", "");
    }

    @Override
    public String toString() {
        return svg.append("</svg>").toString();
    }
}
