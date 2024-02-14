package h13.ui.layout;

import h13.noise.PerlinNoise;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.Property;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

import java.util.Map;
import java.util.function.BiFunction;

import static org.tudalgo.algoutils.student.Student.crash;

/**
 * A {@link PerlinNoise} algorithm visualization view.
 *
 * @author Nhan Huynh
 */
public class AlgorithmView extends AbstractView<AlgorithmView, BorderPane> implements View {

    /**
     * The view model of the view for handling the logic.
     */
    private final AlgorithmViewModel viewModel;

    /**
     * The canvas for visualizing the algorithm.
     */
    private final Canvas visualization;

    /**
     * The view for settings that are related to the algorithm configurations.
     */
    private final SettingsView settings;

    /**
     * Creates a new algorithm view with the given root, settings and factory for creating the view model.
     *
     * @param root     the root layout
     * @param settings the settings view
     * @param factory  the factory for creating the view model
     */
    public AlgorithmView(
        BorderPane root,
        SettingsView settings,
        BiFunction<Map<String, BooleanProperty>, Map<String, Property<Number>>, AlgorithmViewModel> factory,
        ViewConfiguration<AlgorithmView> configuration) {
        super(root, configuration);
        this.visualization = new Canvas();
        this.settings = settings;
        this.viewModel = factory.apply(
            settings.getAlgorithms().getValue().selectedProperties(),
            settings.getParameters().getValue().valueProperties()
        );

        initialize();
        config(this);
    }

    @Override
    public void initialize() {
        root.setCenter(visualization);
        root.setRight(settings.getView());
        initializeButtons();
        initializeSize();
    }

    /**
     * Initializes the 'generate' button of the settings view which draws the algorithm on the canvas if it's pressed
     * and the 'save' button which saves the canvas as an image.
     */
    protected void initializeButtons() {
        // H5.2
        Button generate = settings.getGenerate();

        int width = (int)visualization.getWidth();
        int height = (int)visualization.getHeight();

        generate.setOnAction(event -> viewModel.draw(viewModel.getAlgorithm(), visualization.getGraphicsContext2D(), 0, 0, width, height));
    }

    /**
     * Initializes the size of the canvas and binds it to the size of the root layout.
     */
    protected void initializeSize() {
        //5.2
        Button save = settings.getSave();

        int width = (int)root.getWidth();
        int height = (int)root.getHeight();

        save.setOnAction(event -> viewModel.save(width, height));
    }

    /**
     * Returns the canvas for visualizing the algorithm.
     *
     * @return the canvas for visualizing the algorithm
     */
    public Canvas getVisualization() {
        return visualization;
    }

    /**
     * Returns the settings view.
     *
     * @return the settings view
     */
    public SettingsView getSettings() {
        return settings;
    }
}
