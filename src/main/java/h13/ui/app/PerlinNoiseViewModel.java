package h13.ui.app;


import h13.noise.FractalPerlinNoise;
import h13.noise.PerlinNoise;
import h13.noise.SimplePerlinNoise;
import h13.ui.layout.AlgorithmView;
import h13.ui.layout.AlgorithmViewModel;
import h13.util.Cache;
import h13.util.LRUCache;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.Property;
import javafx.scene.paint.Color;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Random;
import java.util.function.DoubleFunction;
import java.util.stream.IntStream;

import static org.tudalgo.algoutils.student.Student.crash;

/**
 * The viw model for {@link AlgorithmView} that defines the behavior of the {@link PerlinNoise}.
 *
 * @author Nhan Huynh
 */
public class PerlinNoiseViewModel extends AlgorithmViewModel {

    /**
     * The first half of the permutation table used by the improved Perlin noise algorithm.
     *
     * @see <a href="https://en.wikipedia.org/wiki/Perlin_noise#Permutation">https://en.wikipedia.org/wiki/Perlin_noise#Permutation</a>
     */
    private static final int[] PERMUTATION_TABLE = {151, 160, 137, 91, 90, 15, 131, 13, 201, 95, 96, 53, 194, 233, 7, 225,
        140, 36, 103, 30, 69, 142, 8, 99, 37, 240, 21, 10, 23, 190, 6, 148,
        247, 120, 234, 75, 0, 26, 197, 62, 94, 252, 219, 203, 117, 35, 11, 32,
        57, 177, 33, 88, 237, 149, 56, 87, 174, 20, 125, 136, 171, 168, 68, 175,
        74, 165, 71, 134, 139, 48, 27, 166, 77, 146, 158, 231, 83, 111, 229, 122,
        60, 211, 133, 230, 220, 105, 92, 41, 55, 46, 245, 40, 244, 102, 143, 54,
        65, 25, 63, 161, 1, 216, 80, 73, 209, 76, 132, 187, 208, 89, 18, 169,
        200, 196, 135, 130, 116, 188, 159, 86, 164, 100, 109, 198, 173, 186, 3, 64,
        52, 217, 226, 250, 124, 123, 5, 202, 38, 147, 118, 126, 255, 82, 85, 212,
        207, 206, 59, 227, 47, 16, 58, 17, 182, 189, 28, 42, 223, 183, 170, 213,
        119, 248, 152, 2, 44, 154, 163, 70, 221, 153, 101, 155, 167, 43, 172, 9,
        129, 22, 39, 253, 19, 98, 108, 110, 79, 113, 224, 232, 178, 185, 112, 104,
        218, 246, 97, 228, 251, 34, 242, 193, 238, 210, 144, 12, 191, 179, 162, 241,
        81, 51, 145, 235, 249, 14, 239, 107, 49, 192, 214, 31, 181, 199, 106, 157,
        184, 84, 204, 176, 115, 121, 50, 45, 127, 4, 150, 254, 138, 236, 205, 93,
        222, 114, 67, 29, 24, 72, 243, 141, 128, 195, 78, 66, 215, 61, 156, 180};

    /**
     * The cache for the {@link SimplePerlinNoise} algorithm used to improve performance when accessing the same
     * algorithm multiple times.
     */
    private final Cache<Long, PerlinNoise> cacheSimpleNoise;

    /**
     * The cache for the {@link FractalPerlinNoise} algorithm used to improve performance when accessing the same
     * algorithm multiple times.
     */
    private final Cache<PerlinNoise, PerlinNoise> cacheImprovedNoise;

    /**
     * Constructs a new {@link PerlinNoiseViewModel} with the given options and parameters to handle and the color
     * function to use.
     *
     * @param options    the options to handle
     * @param parameters the parameters to handle
     * @param color      the color function to use
     * @param cacheSize  the size of the cache used to improve performance when accessing the same algorithm multiple
     */
    public PerlinNoiseViewModel(
        Map<String, BooleanProperty> options,
        Map<String, Property<Number>> parameters,
        DoubleFunction<Color> color,
        int cacheSize
    ) {
        super(options, parameters, color);
        this.cacheSimpleNoise = new LRUCache<>(cacheSize);
        this.cacheImprovedNoise = new LRUCache<>(cacheSize);
    }

    /**
     * Returns the permutation table for the improved Perlin noise algorithm.
     *
     * @param randomGenerator the random generator used to generate the permutation array
     * @return the permutation table for the improved Perlin noise algorithm
     */
    private int[] getPermutationTable(Random randomGenerator) {
        int[] otherHalf = IntStream.range(0, PERMUTATION_TABLE.length).toArray();
        for (int i = 0; i < otherHalf.length; i++) {
            int j = randomGenerator.nextInt(otherHalf.length);
            int temp = otherHalf[i];
            otherHalf[i] = otherHalf[j];
            otherHalf[j] = temp;
        }
        int[] p = new int[PERMUTATION_TABLE.length * 2];
        System.arraycopy(PERMUTATION_TABLE, 0, p, 0, PERMUTATION_TABLE.length);
        System.arraycopy(otherHalf, 0, p, PERMUTATION_TABLE.length, otherHalf.length);
        return p;
    }

    @Override
    protected @Nullable PerlinNoise getAlgorithm() {
        //H6 TODO
        return crash();
    }

    /**
     * Returns the boolean property to the given algorithm.
     *
     * @param algorithm the algorithm to get the boolean property to
     * @return the boolean property to the given algorithm
     */
    private BooleanProperty getAlgorithm(Algorithm algorithm) {
        return options.get(algorithm.toString());
    }

    /**
     * Returns the number property to the given parameter.
     *
     * @param parameter the parameter to get the number property to
     * @return the number property to the given parameter
     */
    private Property<Number> getParameter(Parameter parameter) {
        return parameters.get(parameter.toString());
    }
}

