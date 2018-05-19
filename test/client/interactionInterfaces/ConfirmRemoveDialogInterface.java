package client.interactionInterfaces;

import client.TestFXBase;
import org.junit.Test;
import static client.JavaFXids.CONFIRM_REMOVE_BUTTON;

/**
 * The interface to interact with the ConfirmRemoveDialog.
 *
 * @author Daniel Wikander
 */
public class ConfirmRemoveDialogInterface {

    private final TestFXBase driver;

    /**
     * Constructor that sets the base driver for user
     * interactions such as clicking on buttons etc.
     * @param driver    The driver to set.
     */
    public ConfirmRemoveDialogInterface(TestFXBase driver) {
        this.driver = driver;
    }

    /**
     * Clicks on the remove button in the dialog.
     * @return          The action.
     */
    @Test
    public ConfirmRemoveDialogInterface confirmRemove() {
        driver.clickOn(CONFIRM_REMOVE_BUTTON);
        return this;
    }

}
