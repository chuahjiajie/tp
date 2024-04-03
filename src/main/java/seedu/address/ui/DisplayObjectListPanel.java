package seedu.address.ui;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.model.cca.Cca;
import seedu.address.model.person.Person;

/**
 * Represents a union type of all displayable classes
 */
class DisplayObject {

    // Only one of these fields should be non-empty
    private final Optional<Person> person;
    private final Optional<Cca> cca;

    public DisplayObject(Person person) {
        this.person = Optional.of(person);
        this.cca = Optional.empty();
    }

    public DisplayObject(Cca cca) {
        this.person = Optional.empty();
        this.cca = Optional.of(cca);
    }

    public boolean isPerson() {
        return this.person.isPresent();
    }

    public boolean isCca() {
        return this.cca.isPresent();
    }

    public Person getPerson() {
        return this.person.get();
    }

    public Cca getCca() {
        return this.cca.get();
    }
}

/**
 * Panel containing the list of persons.
 */
public class DisplayObjectListPanel extends UiPart<Region> {
    private static final String FXML = "DisplayObjectListPanel.fxml";

    @FXML
    private ListView<DisplayObject> displayObjectListView;

    /**
     * Creates a {@code DisplayObjectListPanel} with the given {@code ObservableList}.
     */
    public DisplayObjectListPanel(
        ObservableList<Person> personList,
        ObservableList<Cca> ccaList,
        ObservableList<Person> allPersons) {
        super(FXML);

        ObservableList<DisplayObject> displayObjects = createDisplayObjectList(personList, ccaList)
            .stream()
            .collect(Collectors.toCollection(FXCollections::observableArrayList));

        displayObjectListView.setItems(displayObjects);
        displayObjectListView.setCellFactory(lsitView ->
            new DisplayObjectListViewCell(allPersons)
        );

        // The ObservableList displayObject is no longer automatically
        // updated on change of personList and ccaList,
        // so we'd have to do it manually.
        personList.addListener(new ListChangeListener<Person>() {
            @Override
            public void onChanged(Change<? extends Person> c) {
                displayObjects.setAll(createDisplayObjectList(personList, ccaList));
            }
        });

        ccaList.addListener(new ListChangeListener<Cca>() {
            @Override
            public void onChanged(Change<? extends Cca> c) {
                displayObjects.setAll(createDisplayObjectList(personList, ccaList));
            }
        });
    }

    private List<DisplayObject> createDisplayObjectList(
        ObservableList<Person> personList, ObservableList<Cca> ccaList) {

        return Stream.concat(
            personList
                .stream()
                .map(p -> new DisplayObject(p)),
            ccaList
                .stream()
                .map(c -> new DisplayObject(c))
        ).collect(Collectors.toList());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a
     * {@code DisplayObject} using a {@code PersonCard} or a {@code CcaCard}.
     */
    class DisplayObjectListViewCell extends ListCell<DisplayObject> {

        private final ObservableList<Person> allPersons;

        DisplayObjectListViewCell(ObservableList<Person> allPersons) {
            super();
            this.allPersons = allPersons;
        }

        @Override
        protected void updateItem(DisplayObject object, boolean empty) {
            super.updateItem(object, empty);

            if (empty || object == null) {
                setGraphic(null);
                setText(null);
            } else {
                Region card;
                if (object.isPerson()) {
                    card = new PersonCard(object.getPerson(), getIndex() + 1).getRoot();
                } else {
                    card = new CcaCard(object.getCca(), allPersons).getRoot();
                    card.getStyleClass().add((getIndex() % 2 == 0) ? "ccaEven" : "ccaOdd");
                }
                setGraphic(card);
            }
        }
    }
}
