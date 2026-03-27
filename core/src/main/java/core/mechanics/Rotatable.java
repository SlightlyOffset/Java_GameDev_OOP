package core.mechanics;

/**
 * Interface for objects that can be rotated clockwise or counterclockwise.
 * Implemented by tiles to enable rotation mechanics in the puzzle game.
 */
public interface Rotatable {
    void rotateClockwise();
    void rotateCounterClockwise();
}
