package app.entities.materials.planksAndRoofCovers.planks;

import app.entities.materials.planksAndRoofCovers.PlankAndRoof;

/**
 * Motivation for Specialized Plank Subclasses
 *
 * Originally, all wooden components such as posts, beams, rafters, and fascia boards
 * were represented by a generic Plank class. However, in real-world construction,
 * different types of planks have unique functional requirements and constraints.
 *
 * Therefore, to better model reality and future-proof the system, we introduced
 * specialized subclasses:
 *
 * - Post: Can be pressure-treated for ground contact (treatedForGroundContact field).
 * - Beam: Has a max distance between posts to improve customizability.
 * - Rafter: No immediate extra fields yet, but separated to future-proof the architecture.
 *           May later include properties like supportsHeavyRoof or maxSpanWithoutSupport.
 * - Fascia: Added check to see if Fascia can support gutters (supportsGutters field).
 *
 * Benefits of this approach:
 * - Improved realism: Reflects actual differences between structural elements.
 * - Easier validation: Business rules and engineering rules can now be attached correctly.
 * - Better extensibility: New features (e.g., snow load calculations, structural improvements)
 *   can be added without changing unrelated code.
 * - Clearer domain modeling: Easier for developers and domain experts (engineers, architects)
 *   to understand and extend the system without introducing chaos or bugs.
 *
 * Overall, using targeted subclasses improves maintainability, and scalability.
 */

public abstract class Plank extends PlankAndRoof {
    private final int height;

    public Plank(String name, String description, double costPrice, double salesPrice, String unit, int length, int width, int maxLength, int height) {
        super(name, description, costPrice, salesPrice, unit, length, width, maxLength);
        this.height = height;
    }

    public int getHeight() {
        return height;
    }
}
