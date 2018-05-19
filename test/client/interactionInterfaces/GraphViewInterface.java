package client.interactionInterfaces;
import static client.JavaFXids.GRAPHVIEW_PLANT_ALIAS_LABEL;
import static client.JavaFXids.GRAPHVIEW_VALUECHART;
import client.TestFXBase;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Label;

/**
 * Interface to interact with the GraphView.
 *
 * @author Daniel Wikander
 */
public class GraphViewInterface {

    private final TestFXBase driver;

    /**
     * Constructor that sets the base driver for user
     * interactions such as clicking on buttons etc.
     * @param driver    The driver to set.
     */
    public GraphViewInterface(TestFXBase driver) {
        this.driver = driver;
    }

    /**
     * Returns the plants linegraph.
     * @return          The linegraph.
     */
    public LineChart getGraph() {
        LineChart chart = driver.find(GRAPHVIEW_VALUECHART);
        return chart;
    }

    /**
     * Returns the plants name label.
     * @return          The plants name label.
     */
    public Label getPlantNameLabel() {
        return driver.find(GRAPHVIEW_PLANT_ALIAS_LABEL);
    }

}
