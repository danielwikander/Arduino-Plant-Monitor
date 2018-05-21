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

/**
 * Parent class for all test classes.
 * Provides basic functionality like finding FX elements.
 * Also has a parent setUp() and tearDown() method which
 * is run before and after each test in a test class.
 * These classes start up the application in preparation
 * for the tests.
 *
 * @author Daniel Wikander
 * */
public abstract class TestFXBase extends ApplicationTest {

    /**
     * Setup before each test.
     * */
    @Before
    public void setUp() throws Exception {
        sleep(500);
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
     * Shows stage and places it in front.
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
