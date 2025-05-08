package app.entities.products.materials.roof;

import app.entities.products.materials.Material;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

public class RoofCover extends Material {
    private int lengthOverlap;
    private float sideOverlap;
    private int gapRafters;

    public RoofCover(String name, String description, float costPrice, float salesPrice, float width, String unit, List<Integer> preCutsLengths, int lengthOverlap, float sideOverlap, int gapRafters) {
        super(name, description, costPrice, salesPrice, width, unit, preCutsLengths);
        this.lengthOverlap = lengthOverlap;
        this.sideOverlap = sideOverlap;
        this.gapRafters = gapRafters;
    }

    public RoofCover(int itemId, String name, String description, float costPrice, float salesPrice, String unit, float width, int lengthOverlap, float sideOverlap, int gapRafters) {
        super(itemId, name, description, costPrice, salesPrice, unit, width);
        this.lengthOverlap = lengthOverlap;
        this.sideOverlap = sideOverlap;
        this.gapRafters = gapRafters;
    }

    public int getLengthOverlap() {
        return lengthOverlap;
    }

    public void setLengthOverlap(int lengthOverlap) {
        this.lengthOverlap = lengthOverlap;
    }

    public float getSideOverlap() {
        return sideOverlap;
    }

    public void setSideOverlap(float sideOverlap) {
        this.sideOverlap = sideOverlap;
    }

    public int getGapRafters() {
        return gapRafters;
    }

    public void setGapRafters(int gapRafters) {
        this.gapRafters = gapRafters;
    }

    @Override
    public void prepareStatement(PreparedStatement ps) throws SQLException {
        ps.setString(1, this.getName());
        ps.setString(2, this.getDescription());
        ps.setString(3, this.getUnit());
        ps.setFloat(4, this.getWidth());
        ps.setNull(5, Types.NUMERIC);
        ps.setString(6, this.getClass().getSimpleName());
        ps.setNull(7, Types.NUMERIC);
        ps.setNull(8, Types.NUMERIC);
        ps.setInt(9, this.getLengthOverlap());
        ps.setFloat(10, this.getSideOverlap());
        ps.setInt(11, this.getGapRafters());
    }
}
