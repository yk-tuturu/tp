package seedu.address.ui;

import java.util.Comparator;

import javafx.application.Platform;
import javafx.collections.MapChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.person.Person;
import seedu.address.model.subject.Subject;
import seedu.address.model.subject.SubjectRegistry;

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
    private Label parent;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private FlowPane tags;
    @FXML
    private FlowPane allergies;
    @FXML
    private FlowPane subjects;

    private final MapChangeListener<Person, Integer> scoreListener;

    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public PersonCard(Person person, int displayedIndex) {
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + ". ");
        name.setText(person.getChildName().fullName);
        parent.setText("Parent: " + person.getParentName().fullName);
        phone.setText(person.getParentPhone().value);
        address.setText(person.getAddress().value);
        email.setText(person.getParentEmail().value);
        person.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
        person.getAllergyList().stream()
                .sorted(Comparator.comparing(al -> al.toString()))
                .forEach(al -> allergies.getChildren().add(new Label(al.toString())));
        SubjectRegistry.getSubjectsOf(person).stream().sorted(Comparator.comparing(Object::toString))
                .forEach(subject -> {
                    int score = subject.getScore(person);
                    subjects.getChildren().add(new Label(subject + " | " + (score == -1 ? "N/A" : score)));
                });
        scoreListener = change -> {
            if ((change.wasAdded() || change.wasRemoved()) && change.getKey().equals(person)) {
                Platform.runLater(() -> {
                    subjects.getChildren().clear();
                    SubjectRegistry.getSubjectsOf(person).stream().sorted(Comparator.comparing(Object::toString))
                            .forEach(subject -> {
                                int score = subject.getScore(person);
                                subjects.getChildren().add(new Label(subject + " | " + (score == -1 ? "N/A" : score)));
                            });
                });
            }
        };
        for (Subject s : Subject.values()) {
            s.getScoreDict().getObservableScores().addListener(scoreListener);
        }
    }

    /**
     * Detach listeners when this card is disposed.
     */
    public void dispose() {
        for (Subject s : Subject.values()) {
            s.getScoreDict().getObservableScores().removeListener(scoreListener);
        }
    }
}
