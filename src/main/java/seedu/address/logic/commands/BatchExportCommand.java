package seedu.address.logic.commands;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.employee.Employee;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

public class BatchExportCommand extends Command{
    public static final String COMMAND_WORD = "batchexport";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Exports the database to a CSV file.\n"
            + "Parameters: FILENAME (the exported CSV file will be saved in the data folder)\n"
            + "Example: " + COMMAND_WORD + " exported_database.csv";
    public static final String MESSAGE_WORKS = "Database exported to %s.";

    private final String fileName;
    private final Path filePath;

    public BatchExportCommand(String fileName) {
        requireAllNonNull(fileName);
        this.fileName = fileName;
        this.filePath = Paths.get("data", this.fileName);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        try {
            exportToCsv(model, filePath);
        } catch (IOException e) {
            throw new CommandException("Error exporting data: " + e.getMessage());
        }

        return new CommandResult(String.format(MESSAGE_WORKS, fileName));
    }

    private void exportToCsv(Model model, Path filePath) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath.toFile()));
             CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT
                     .withHeader("Name", "Phone", "Email", "Address", "Tags"))) {

            List<Employee> employeeList = model.getExecutiveProDb().getEmployeeList();

            for (Employee employee : employeeList) {
                csvPrinter.printRecord(
                        employee.getName().fullName,
                        employee.getPhone().value,
                        employee.getEmail().value,
                        employee.getAddress().value,
                        employee.getTags().stream().map(tag -> tag.tagName).collect(Collectors.joining(", "))
                );
            }

            csvPrinter.flush();
        }
    }
}