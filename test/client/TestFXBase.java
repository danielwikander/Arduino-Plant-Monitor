package client;

import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import org.junit.After;
import org.junit.Before;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.util.WaitForAsyncUtils;

import java.util.ResourceBundle;

/**
 * Parent class for testclass.
 * Provides basic functionality
 *
 * @author Daniel Wikander
 * */
public abstract class TestFXBase extends ApplicationTest {

    private Stage primaryStage;
    protected static ResourceBundle bundle;

    /**
     * Setup before each test.
     * */
    @Before
    public void setUp() throws Exception {
        sleep(1000);
        ApplicationTest.launch(Main.class);
    }

    /**
     * Teardown after each test.
     * Hides stage and clears keypresses.
     * */
    @After
    public void tearDown() throws Exception {
        FxToolkit.hideStage();
        release(new KeyCode[]{});
        release(new MouseButton[]{});
    }

    /**
     * Shows stage and inti
     * @param stage
     * @throws Exception
     */
    @Override
    public void start (Stage stage) throws Exception {
        stage.show();
        stage.toFront();
    }

    /**
     * Ensures that the JavaFX eventqueue has completed.
     */
    public void ensureEventQueueComplete(){
        WaitForAsyncUtils.waitForFxEvents(1);
    }

    /**
     * Method to retrieve Java FX GUI components.
     */
    public <T extends Node> T find(final String query) {
        return (T) lookup(query).queryAll().iterator().next();
    }
}
