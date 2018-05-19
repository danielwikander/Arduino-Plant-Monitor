package client.interactionInterfaces;

import client.TestFXBase;
import javafx.scene.control.Label;
import static client.JavaFXids.STARTVIEW_LABEL;

/**
 * Interface for interacting with the StartView.
 *
 * @author Daniel Wikander
 */
public class StartViewInterface {

    private final TestFXBase driver;

    /**
     * Constructor that sets the base driver for user
     * interactions such as clicking on buttons etc.
     * @param driver    The driver to set.
     */
    public StartViewInterface(TestFXBase driver) {
        this.driver = driver;
    }

    /**
     * Returns the welcome label that is presented to
     * users after they have successfully logged in.
     * @return  The welcome label.
     */
    public Label getStartViewLabel() {
        return driver.find(STARTVIEW_LABEL);
    }
}