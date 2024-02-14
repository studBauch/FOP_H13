package h13.noise;

import javafx.geometry.Point2D;
import org.tudalgo.algoutils.student.annotation.StudentImplementationRequired;

import java.util.Random;

import static org.tudalgo.algoutils.student.Student.crash;

/**
 * A simple implementation of the Perlin noise algorithm.
 *
 * @author Nhan Huynh
 */
public class SimplePerlinNoise extends AbstractPerlinNoise implements PerlinNoise {

    /**
     * Constructs a simple Perlin noise object with the specified noise domain width, height, frequency and seed.
     *
     * @param width           the width of the noise domain
     * @param height          the height of the noise domain
     * @param frequency       the frequency of the Perlin noise
     * @param randomGenerator the random generator used for generating gradient vectors
     */
    public SimplePerlinNoise(int width, int height, double frequency, Random randomGenerator) {
        super(width, height, frequency, randomGenerator);
    }

    /**
     * Constructs a simple Perlin noise object with the specified noise domain width, height and seed.
     *
     * @param width           the width of the noise domain
     * @param height          the height of the noise domain
     * @param randomGenerator the random generator used for generating gradient vectors
     */
    public SimplePerlinNoise(int width, int height, Random randomGenerator) {
        super(width, height, randomGenerator);
    }

    @Override
    public double compute(int x, int y) {
        double f = getFrequency();
        return compute(x * f, y * f);
    }

    @Override
    @StudentImplementationRequired
    public double compute(double x, double y) {
        // H1.3
        // Bestimmen der Gitterzellen
        int x0 = (int) Math.floor(x);
        int x1 = x0 +1;
        int y0 = (int) Math.floor(y);
        int y1 = y0 +1;

        // Berechnen der Gradienten
        Point2D x0y0 = getGradient(x0, y0);
        Point2D x1y0 = getGradient(x1, y0);
        Point2D x0y1 = getGradient(x0, y1);
        Point2D x1y1 = getGradient(x1, y1);

        // Berechnen des Distanzvektors
        double dx = x - x0;
        double dy = y - y0;

        // Berechnen der Skalarprodukte
        double sx0y0 = new Point2D(dx + 0, dy + 0).dotProduct(x0y0);
        double sx1y0 = new Point2D(dx- 1, dy + 0).dotProduct(x1y0);
        double sx0y1 = new Point2D(dx + 0, dy - 1).dotProduct(x0y1);
        double sx1y1 = new Point2D(dx - 1, dy - 1).dotProduct(x1y1);

        double lx0 = interpolate(sx0y0, sx1y0, fade(dx));
        double lx1 = interpolate(sx0y1, sx1y1, fade(dx));

        return  interpolate(lx0, lx1, fade(dy));

    }

    /**
     * Die Funktion f wird verwendet, um den Einfluss der Gradientenvektoren mit zunehmendem
     * Abstand von der Eckposition zu reduzieren. Dieser Verblassungseffekt stellt sicher, dass der Gradienteneffekt näher an
     * der Ecke stärker ist und sich beim Entfernen von der Ecke abschwächt. Das Ergebnis ist eine insgesamt natürlichere
     * und gleichmäßigere Rauschgenerierung.
     * f(t) = 6t^5 − 15t^4 + 10t^3
     * @param t the value to which the fade function will be applied
     * @return t für den Abstandsfaktor oder die Distanz zum nächsten Gitterpunkt in der
     * Perlin-Noise-Berechnung
     */
    @Override
    @StudentImplementationRequired
    public double fade(double t) {
        // 1.2
        return (6*Math.pow(t, 5) - 15*Math.pow(t, 4) + 10*Math.pow(t, 3));
    }

    /**
     * Performs linear interpolation between two values.
     *
     * @param x1    The first value.
     * @param x2    The second value.
     * @param alpha The interpolation factor, typically in the range [0, 1].
     * @return the interpolated value
     */
    @Override
    @StudentImplementationRequired
    public double interpolate(double x1, double x2, double alpha) {
        // H1.2
        return (x1 + alpha * (x2 - x1));
    }
}
