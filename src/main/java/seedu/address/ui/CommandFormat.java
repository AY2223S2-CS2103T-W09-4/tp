package seedu.address.ui;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Format and usage for an ExecutivePro command.
 * Used in {@code HelpWindow} for the help command.
 */
public class CommandFormat {

    // All profile commands below
    // Sequence: NAME, PHONE, EMAIL, ADDRESS, DEPARTMENT, TAGS
    // Compulsory ones: NAME, PHONE, DEPARTMENT
    private static final CommandFormat ADD_COMMAND_FORMAT = new CommandFormat(
            "add",
            "Adds a person to ExecutivePro.",
            "add [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [d/DEPARTMENT] [t/TAG]…");

    private static final CommandFormat EDIT_COMMAND_FORMAT = new CommandFormat(
            "edit",
            "Edits an employee's details.",
            "edit EMPLOYEE_ID [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [d/DEPARTMENT] [t/TAG]…");

    private static final CommandFormat DELETE_COMMAND_FORMAT = new CommandFormat(
            "delete",
            "Deletes the specified person from ExecutivePro, given the Employee ID.",
            "delete EMPLOYEE_ID");

    private static final CommandFormat BATCH_ADD_COMMAND_FORMAT = new CommandFormat(
            "batchadd",
            "Adds multiple employees all at once.",
            "batchadd FILENAME(Example: executivepro.csv)");

    private static final CommandFormat BATCH_EXPORT_COMMAND_FORMAT = new CommandFormat(
            "batchexport",
            "Export the database into a csv file.",
            "batchexport FILENAME(Example: executivepro.csv)");


    private static final CommandFormat LIST_COMMAND_FORMAT = new CommandFormat(
            "list",
            "Shows a list of all persons in the company.",
            "list");

    private static final CommandFormat FIND_COMMAND_FORMAT = new CommandFormat(
            "find",
            "Find employees with a search filter.",
            "find [*] KEYWORD [MORE KEYWORDS...]");

    // All misc commands below
    private static final CommandFormat HELP_COMMAND_FORMAT = new CommandFormat(
            "help",
            "Opens the help window.",
            "help");

    private static final CommandFormat EXIT_COMMAND_FORMAT = new CommandFormat(
            "exit",
            "Exits the program.",
            "exit");

    private static final CommandFormat CLEAR_COMMAND_FORMAT = new CommandFormat(
            "clear",
            "Clears the data stored in the database.",
            "clear");

    private SimpleStringProperty command;
    private SimpleStringProperty usage;

    /**
     * Creates a {@code CommandFormat} object with a specified command and corresponding description, format.
     *
     * @param command The specified command.
     * @param description The corresponding description.
     * @param format The corresponding format.
     */
    public CommandFormat(String command, String description, String format) {
        this.command = new SimpleStringProperty(command);
        this.usage = new SimpleStringProperty(String.format("%s\nFormat: %s", description, format));
    }

    public static ObservableList<CommandFormat> getProfileCommands() {
        return FXCollections.observableArrayList(
                ADD_COMMAND_FORMAT,
                BATCH_ADD_COMMAND_FORMAT,
                BATCH_EXPORT_COMMAND_FORMAT,
                EDIT_COMMAND_FORMAT,
                DELETE_COMMAND_FORMAT,
                LIST_COMMAND_FORMAT,
                FIND_COMMAND_FORMAT
        );
    }

    public static ObservableList<CommandFormat> getMiscCommands() {
        return FXCollections.observableArrayList(
                HELP_COMMAND_FORMAT,
                EXIT_COMMAND_FORMAT,
                CLEAR_COMMAND_FORMAT
        );
    }
    public String getCommand() {
        return this.command.get();
    }

    public String getUsage() {
        return this.usage.get();
    }

}
