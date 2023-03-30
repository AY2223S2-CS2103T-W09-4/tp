package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_EMPLOYEES;

import seedu.address.commons.core.Messages;
import seedu.address.model.Model;

/**
 * Lists all employees in the address book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all employees";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredEmployeeList(PREDICATE_SHOW_ALL_EMPLOYEES);
        int listSize = model.getFilteredEmployeeList().size();
        if (listSize == 0) {
            return new CommandResult(Messages.MESSAGE_EMPTY_LIST);
        }
        return new CommandResult(
                String.format(Messages.MESSAGE_ALL_EMPLOYEES_LISTED_OVERVIEW, listSize));
    }
}
