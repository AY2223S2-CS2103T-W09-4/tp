package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.AddCommandParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ExecutiveProDb;
import seedu.address.model.Model;
import seedu.address.model.employee.Employee;
import seedu.address.model.employee.EmployeeId;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LIST;

public class BatchAddCommand extends Command{
    public static final String COMMAND_WORD = "batchadd";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds multiple employees into the database from a csv file \n"
            + "Parameters: filename (file must be placed into the data folder of the repository and in CSV format) \n"
            + "Example: " + COMMAND_WORD + " executivepro.csv";
    public static final String MESSAGE_WORKS = "Batch added employees. %d employees were added.";
    private final String fileName;
    private Path filePath;

    /**
     * @param fileName of the input
     */
    public BatchAddCommand(String fileName) {
        requireAllNonNull(fileName);
        this.fileName = fileName;
        this.filePath = Paths.get("data", this.fileName);
    }

    public void setFilePath(Path filePath) {
        this.filePath = filePath;
    }
    public List<AddCommand> getInfo() throws CommandException {
        Path file = this.filePath;
        String line = "";
        String splitBy = ",";
        List<AddCommand> addCommandList = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file.toString()));
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] data = line.split(splitBy);
                String arg = " ";
                for (int i = 0; i < data.length; i++) {
                    if (i == (data.length - 1)) {
                        if (data[i].equals("")) {
                            continue;
                        } else {
                            String[] tags = data[i].split("/");
                            for (String tag : tags) {
                                arg += PREFIX_LIST[i] + tag + " ";
                            }
                        }
                    } else {
                        if (data[i].equals("")) {
                            continue;
                        } else {
                            arg += PREFIX_LIST[i] + data[i] + " ";
                        }
                    }
                }
                System.out.println(arg);
                addCommandList.add(new AddCommandParser().parse(arg));
            }
        } catch (FileNotFoundException exception) {
            throw new CommandException("File Not Found");
        } catch (IOException exception) {
            throw new CommandException(exception.getMessage());
        } catch (ParseException exception) {
            throw new CommandException(exception.getMessage());
        }
        return addCommandList;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        int currEmployeeId = EmployeeId.getCount();
        List<AddCommand> addCommandList = this.getInfo();
        if (addCommandList.isEmpty()) {
            throw new CommandException(String.format("%s does not have any data", this.fileName));
        }
        List<Employee> copyEmployeeList = new ArrayList<>();

        for(Employee employee : model.getExecutiveProDb().getEmployeeList()) {
            copyEmployeeList.add(employee);
        }

        try {
            for (AddCommand item : addCommandList) {
                item.execute(model);
            }
        } catch (CommandException e) {
            ExecutiveProDb database = new ExecutiveProDb();
            database.setEmployees(copyEmployeeList);
            model.setExecutiveProDb(database);
            EmployeeId.setCount(currEmployeeId);
            throw new CommandException("One person in the list is found to be a duplicate. Call aborted");
        }
        return new CommandResult(String.format(MESSAGE_WORKS,addCommandList.size()));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof BatchAddCommand)) {
            return false;
        }

        // state check
        BatchAddCommand e = (BatchAddCommand) other;
        return fileName.equals(e.fileName);
    }
}
