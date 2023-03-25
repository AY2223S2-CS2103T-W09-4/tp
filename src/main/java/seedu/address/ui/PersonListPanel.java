package seedu.address.ui;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Logger;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.Logic;
import seedu.address.model.employee.Employee;




/**
 * Panel containing the list of employees.
 */
public class PersonListPanel extends UiPart<Region> {
    private static final String FXML = "PersonListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(PersonListPanel.class);

    @FXML
    private ListView<Employee> personListView;
    @FXML
    private Label nameCard;

    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label employeeId;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private Label department;
    @FXML
    private ImageView imageView;
    @FXML
    private FlowPane tags;


    /**
     * Creates a {@code PersonListPanel} with the given {@code ObservableList}.
     */
    public PersonListPanel(ObservableList<Employee> employeeList, Logic logic) {
        super(FXML);
        personListView.setItems(employeeList);
        personListView.setCellFactory(listView -> new PersonListViewCell());
        //nameCard.setText("Welcome to ExecutivePro v1.2");
        setClick();
        logic.getFilteredEmployeeList().addListener(new ListChangeListener<Employee>() {
            @Override
            public void onChanged(Change<? extends Employee> change) {
                Employee employee = personListView.getSelectionModel().getSelectedItem();
                setInformation(employee);
            }
        });
    }
    public void setClick() {
        personListView.setOnMouseClicked(mouseEvent -> {
            Employee person = personListView.getSelectionModel().getSelectedItem();
            setInformation(person);
        });
    }
    public void setInformation(Employee employee) {
        name.setText("Name: " + employee.getName().fullName);
        employeeId.setText("EmployeeId: " + employee.getEmployeeId().value);
        phone.setText("Phone: " + employee.getPhone().value);
        address.setText("Address: " + employee.getAddress().value);
        email.setText("Email: " + employee.getEmail().value);
        department.setText("Department: " + employee.getDepartment().value);

        File img = new File(employee.getPicturePath().value);
        try {
            InputStream isImage = (InputStream) new FileInputStream(img);
            imageView.setImage(new Image(isImage));
            isImage.close();
        } catch (FileNotFoundException e) {
            // default image will be src/main/resources/employeepictures/default.png
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Person} using a {@code PersonCard}.
     */
    class PersonListViewCell extends ListCell<Employee> {
        @Override
        protected void updateItem(Employee employee, boolean empty) {
            super.updateItem(employee, empty);

            if (empty || employee == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new PersonCard(employee, getIndex() + 1).getRoot());
                setInformation(employee);
            }
        }
    }

}
