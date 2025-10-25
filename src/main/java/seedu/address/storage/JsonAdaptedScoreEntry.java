package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Person;

/**
 * Jackson-friendly version of a single score entry (person + score).
 * Does not attempt to resolve the person object; resolution happens
 * in JsonSerializableAddressBook where the full person list is available.
 */
public class JsonAdaptedScoreEntry {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "ScoreEntry's %s field is missing!";

    private final String personName;
    private final Integer score;

    /** Construct from JSON data when loading. */
    @JsonCreator
    public JsonAdaptedScoreEntry(@JsonProperty("personName") String personName,
                                 @JsonProperty("score") Integer score) {
        this.personName = personName;
        this.score = score;
    }

    /** Construct from model objects when saving. */
    public JsonAdaptedScoreEntry(Person person, int score) {
        this.personName = person.getChildName().toString(); // adapt to your identifier
        this.score = score;
    }

    public String getPersonName() {
        return personName;
    }

    public Integer getScore() {
        return score;
    }

    /** Basic validation; more domain validation can be done during model conversion. */
    public void validate() throws IllegalValueException {
        if (personName == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "personName"));
        }
        if (score == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "score"));
        }
        if (score < -1 || score > 100) {
            throw new IllegalValueException("Score must be between -1 and 100.");
        }
    }
}
