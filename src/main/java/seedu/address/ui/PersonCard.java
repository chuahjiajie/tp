package seedu.address.ui;

import java.util.Comparator;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.person.Person;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class PersonCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Person person;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private Label metadata;
    @FXML
    private FlowPane roles;
    @FXML
    private FlowPane ccas;
    @FXML
    private Label owe;
    @FXML
    private Label attendance;


    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public PersonCard(Person person, int displayedIndex) {
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + ". ");
        name.setText(person.getName().fullName);
        phone.setText(person.getPhone().value);
        address.setText(person.getAddress().value);
        email.setText(person.getEmail().value);
        owe.setText("Owe: " + "$" + person.getAmount().value);
        attendance.setText("Attendance: " + person.getAtt().value + "/" + person.getSess());
        metadata.setText(person.getMetadata().map(m -> m.metadata).orElse(""));
        person.getRoles().stream()
                .sorted(Comparator.comparing(role -> role.roleName))
                .forEach(role -> roles.getChildren().add(new Label(role.roleName)));
        person.getCcas().stream()
                .sorted(Comparator.comparing(cca -> cca.ccaName))
                .forEach(cca -> ccas.getChildren().add(new Label(cca.ccaName)));

        List.of(name, id, phone, address, email, owe, attendance, metadata)
            .forEach(r -> r.setWrapText(true));
        List.of(roles, ccas).stream()
            .flatMap(f -> f.getChildren().stream())
            .forEach(r -> {
                Label l = (Label) r;
                l.setWrapText(true);
                l.setMaxWidth(500);
            });
    }
}
