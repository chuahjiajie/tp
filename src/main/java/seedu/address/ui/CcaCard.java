package seedu.address.ui;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.model.cca.Cca;
import seedu.address.model.person.Person;
import seedu.address.model.roles.Role;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class CcaCard extends UiPart<Region> {

    private static final String FXML = "CcaListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Cca cca;

    @FXML
    private HBox cardPane;
    @FXML
    private Label ccaName;
    @FXML
    private VBox ccaRoleList;

    /**
     * Creates a {@code CcaCard} with the given {@code Cca} and index to display.
     */
    public CcaCard(Cca cca, ObservableList<Person> allPersons) {
        super(FXML);
        this.cca = cca;
        ccaName.setText(cca.ccaName);

        FilteredList<Person> ccaPersonel = allPersons.filtered(p -> !p
            .getCcas()
            .stream()
            .noneMatch(c -> c.ccaName.equals(cca.ccaName))
        );
        List<Role> allRoles = ccaPersonel
            .stream()
            .flatMap(p -> p.getRoles().stream())
            .distinct()
            .sorted((a, b) -> a.roleName.compareTo(b.roleName))
            .collect(Collectors.toList());

        allRoles.forEach(r -> ccaRoleList
            .getChildren()
            .add(generateRoleUi(r.roleName, ccaPersonel
                .filtered(p -> !p
                .getRoles()
                .stream()
                .noneMatch(o -> o.roleName.equals(r.roleName))
                )
                .stream()
                .sorted((a, b) -> a.getName().fullName.compareTo(b.getName().fullName))
                .collect(Collectors.toList())
            ))
        );

        List<Person> noRoles = ccaPersonel
            .stream()
            .filter(p -> p.getRoles().isEmpty())
            .collect(Collectors.toList());

        if (noRoles.size() != 0) {
            ccaRoleList
                .getChildren()
                .add(generateRoleUi("No roles", noRoles));
        }
    }

    private HBox generateRoleListElementUi(int idx, Person p) {
        Label pl = new Label();
        pl.getStyleClass().add("ccaRolePersonIndex");
        pl.setText(Integer.valueOf(idx + 1).toString() + ". ");
        Label nl = new Label();
        nl.getStyleClass().add("ccaRolePersonName");
        nl.setText(p.getName().fullName);

        HBox hb = new HBox();
        hb.getChildren().add(pl);
        hb.getChildren().add(nl);
        return hb;
    }

    private VBox generateRoleUi(String roleString, List<Person> rolePersonel) {

        VBox rb = new VBox();
        rb.getStyleClass().add("ccaRoleListBox");

        // Add the role label
        Label l = new Label();
        l.getStyleClass().add("ccaRoleName");
        l.setText(roleString + ":");
        rb.getChildren().add(l);

        // Create box for rolePersonel list
        VBox b = new VBox();
        b.getStyleClass().add("ccaRolePersonList");
        IntStream
            .range(0, rolePersonel.size())
            .forEach(idx -> {
                Person p = rolePersonel.get(idx);
                b.getChildren().add(generateRoleListElementUi(idx, p));
            });
        rb.getChildren().add(b);
        return rb;
    }
}
