package client;

import java.util.Random;

/**
 * Class containing references to all JavaFX IDs
 * (elements such as buttons or labels) that are used in testing.
 *
 * @author Daniel Wikander
 */
public class JavaFXids {

    // LoginView
    public final static String USERNAME_FIELD_ID = "#emailTextField";
    public final static String PASSWORD_FIELD_ID = "#passwordPasswordField";
    public final static String LOGIN_BUTTON_ID = "#loginButton";
    public final static String NEW_USER_BUTTON_ID = "#newUserButton";
    public final static String REMEMBER_USER_CHECKBOX_ID = "#rememberUserCheckBox";
    public final static String LOGIN_ERROR_LABEL = "#loginErrorLabel";

    // NewUserView
    public final static String NEW_USER_EMAIL_FIELD = "#emailTextField";
    public final static String NEW_USER_PASSWORD_FIELD = "#passwordPasswordField";
    public final static String NEW_USER_FIRST_NAME_FIELD = "#firstNameTextField";
    public final static String NEW_USER_LAST_NAME_FIELD = "#lastNameTextField";
    public final static String NEW_USER_EMAIL_ERROR_FIELD = "#emailErrorLabel";
    public final static String NEW_USER_SUBMIT_BUTTON = "#newUserButton";
    public final static String NEW_USER_BACK_BUTTON = "#backButton";

    // MainView
    public final static String MAIN_ADD_BUTTON = "#addButtonIcon";
    public final static String MAIN_SETTINGS_BUTTON = "#settingsButtonIcon";
    public final static String MAIN_PLANT_LIST= "#plantList";

    // StartView
    public final static String STARTVIEW_LABEL = "#startViewText";
    public final static String STARTVIEW_HEADER_LABEL = "#startViewHeader";

    // GraphView
    public final static String GRAPHVIEW_VALUECHART = "#valueChart";
    public final static String GRAPHVIEW_PLANT_ALIAS_LABEL = "#plantAliasLabel";

    // ChangeView
    public final static String CHANGEVIEW_PLANT_ALIAS_FIELD = "#plantAliasTextField";
    public final static String CHANGEVIEW_MAC_FIELD = "#macAddressTextField";
    public final static String CHANGEVIEW_SAVE_BUTTON = "#saveButton";
    public final static String CHANGEVIEW_CANCEL_BUTTON = "#cancelButton";
    public final static String CHANGEVIEW_REMOVE_BUTTON = "#removeButton";
    public final static String CHANGEVIEW_SETTINGS_FOR_LABEL = "#settingsForLabel";

    // AddView
    public final static String ADDVIEW_SAVE_BUTTON = "#saveButton";
    public final static String ADDVIEW_MAC_ADDRESS_FIELD = "#macAddressTextField";
    public final static String ADDVIEW_PLANT_ALIAS_FIELD = "#plantAliasTextField";
    public final static String ADDVIEW_PLANTSPECIES_CHOICEBOX = "#speciesChoiceBox";

    // Confirm remove dialog
    public final static String CONFIRM_REMOVE_BUTTON = "#removeButton";

    // Random generators
    public final static String RANDOM_NEW_PLANTNAME = "randomNewPlantName" + new Random().nextInt(100000);
    public final static String RANDOM_NEW_MAC_ADDRESS = "randomNewMacAddress" + new Random().nextInt(100000);
    public final static String RANDOM_NEW_USERNAME = "randomNewUser" + new Random().nextInt(100000);
    public final static String RANDOM_NEW_PASSWORD = "randomNewPassword" + new Random().nextInt(100000);

    // Username & Password
    public final static String VALID_USERNAME = "validTestUser";
    public final static String VALID_PASSWORD = "validTestPassword";
    public final static String INVALID_USERNAME = "invalidUser";
    public final static String INVALID_PASSWORD = "invalidPassword";

    // Username & Password for newUserView tests
    public final static String NEW_USER_INVALID_USERNAME = "daniel.wikander@gmail.com"; // Invalid because it already exists in database.
    public final static String NEW_USER_FIRST_NAME = "randomNewUserLastName";
    public final static String NEW_USER_LAST_NAME = "randomNewUserFirstName";


    private JavaFXids() {} // Not supposed to be instantiated.

}
