package h13.noise;

import javafx.geometry.Point2D;
import org.tudalgo.algoutils.student.annotation.StudentImplementationRequired;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

import static org.tudalgo.algoutils.student.Student.crash;

/**
 * An abstract base class that provides common functionality for generating Perlin noise using gradient vectors.
 * This class serves as a foundation for implementing Perlin noise algorithms and offers a base implementation.
 *
 * @author Nhan Huynh
 * @see PerlinNoise
 */
public abstract class AbstractPerlinNoise implements PerlinNoise {

    /**
     * The default frequency to use for the Perlin noise.
     */
    public static final double DEFAULT_FREQUENCY = 0.005;

    /**
     * The random generator used for generating gradient vectors.
     */
    private final Random randomGenerator;

    /**
     * The width of the noise domain.
     */
    private final int width;

    /**
     * The height of the noise domain.
     */

    private final int height;
    /**
     * The array of gradient vectors where each vector is associated with a grid cell.
     */
    private final Point2D[] gradients;
    /**
     * The hash code of this Perlin noise object used for caching.
     */
    protected int hashCode = -1;
    /**
     * The frequency of the Perlin noise.
     */
    private double frequency;

    /**
     * Constructs an abstract Perlin noise with the specified noise domain and randomGenerator.
     *
     * @param width           the width of the noise domain
     * @param height          the height of the noise domain
     * @param randomGenerator the random generator used for generating gradient vectors
     * @throws IllegalArgumentException if the width or height is negative
     */
    public AbstractPerlinNoise(int width, int height, Random randomGenerator) {
        this(width, height, DEFAULT_FREQUENCY, randomGenerator);
    }

    /**
     * Constructs an abstract Perlin noise with the specified noise domain, frequency and randomGenerator.
     *
     * @param width           the width of the noise domain
     * @param height          the height of the noise domain
     * @param frequency       the frequency of the Perlin noise
     * @param randomGenerator the random generator used for generating gradient vectors
     * @throws IllegalArgumentException if the width or height is negative, or if the frequency is not between 0 and 1
     */
    public AbstractPerlinNoise(int width, int height, double frequency, Random randomGenerator) {
        if (width < 0) {
            throw new IllegalArgumentException("Width cannot be negative");
        }
        this.width = width;
        if (height < 0) {
            throw new IllegalArgumentException("Width cannot be negative");
        }
        this.height = height;
        setFrequency(frequency);
        this.randomGenerator = randomGenerator;
        this.gradients = createGradients(width + 1, height + 1);
    }

    /**
     * Generates an array of random 2D gradient vectors with dimensions wrapping around on the noise dimension, which
     * means that the width and height of the gradient domain is one unit larger than the noise domain.
     *
     * <p>Each point in the noise domain is associated with four corner gradient vectors.
     *
     * <p>The gradient vectors are stored in a 1D array, where we need to map the 2D coordinates to the 1D index.
     *
     * <p>Visual representation of the gradient domain:
     *
     * <pre>{@code
     *
     *  (xn+1, y0)             ...             (xn+1, yn)
     *              -------------------------
     *              | (xn, y0) ... (xn, yn) |
     *              |    Noise domain ...   |
     *              | (x0, y0) ... (x0, xn) |
     *              -------------------------
     *  (x-1, y-1)             ...            (x-1, yn)
     *
     * }</pre>
     *
     * @param width  the width of the gradient domain, which determines the horizontal dimension of the noise domain.
     * @param height the height of the gradient domain, which determines the vertical dimension of the noise domain.
     * @return random 2D gradient vectors with dimensions wrapping around on the noise dimension
     */
    @StudentImplementationRequired
    protected Point2D[] createGradients(int width, int height) {
        // H1.1
        Point2D[] gradients = new Point2D[(width ) * (height )];
        for (int i=0; i < height; i++){
            for (int j=0; j < width; j++){
                gradients[j * (width ) + i] = createGradient();
            }
        }
        return gradients;
    }

    /**
     * Generates a random 2D gradient vector within the unit circle.
     *
     * @return a random gradient vector within the unit circle
     */
    @StudentImplementationRequired
    protected Point2D createGradient() {
        // H1.1
        double angel = randomGenerator.nextDouble() * Math.PI;
        double radius = Math.nextUp(randomGenerator.nextDouble()); //Vermeidung von Rundungsfehlern
        return new Point2D(Math.cos(angel) * radius, Math.sin(angel) * radius);
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public double getFrequency() {
        return frequency;
    }

    @Override
    public void setFrequency(double frequency) {
        if (frequency < 0 || frequency > 1) {
            throw new IllegalArgumentException("Frequency must be between 0 and 1");
        }
        this.frequency = frequency;
    }

    @Override
    public Random getRandomGenerator() {
        return randomGenerator;
    }

    @Override
    public Point2D[] getGradients() {
        return gradients;
    }

    /**
     * Gibt den Gradienten an der Position (x, y) in der Gradienten-Domän
     * @param x the x coordinate of the gradient domain
     * @param y the y coordinate of the gradient domain
     * @return Der Gradient an der Position (x, y)
     */
    @Override
    @StudentImplementationRequired
    public Point2D getGradient(int x, int y) {
        // H1.1
        return gradients[y * (width + 1) + x];
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AbstractPerlinNoise that = (AbstractPerlinNoise) o;
        return width == that.width
            && height == that.height
            && Double.compare(frequency, that.frequency) == 0
            && Arrays.equals(gradients, that.gradients);
    }

    @Override
    public int hashCode() {
        if (hashCode == -1) {
            int result = Objects.hash(width, height, frequency, Arrays.hashCode(gradients));
            hashCode = 31 * result + Arrays.hashCode(gradients);
        }
        return hashCode;
    }
}
