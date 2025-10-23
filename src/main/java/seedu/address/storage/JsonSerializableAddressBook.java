package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Person;
import seedu.address.model.subject.Subject;
import seedu.address.model.subject.SubjectRegistry;

/**
 * An Immutable AddressBook that is serializable to JSON format.
 */
@JsonRootName(value = "addressbook")
class JsonSerializableAddressBook {

    public static final String MESSAGE_DUPLICATE_PERSON = "Persons list contains duplicate person(s).";

    private final List<JsonAdaptedPerson> persons = new ArrayList<>();
    private final Map<String, List<JsonAdaptedScoreEntry>> subjectScores = new HashMap<>();

    /**
     * Constructs a {@code JsonSerializableAddressBook} with the given persons.
     */
    @JsonCreator
    public JsonSerializableAddressBook(@JsonProperty("persons") List<JsonAdaptedPerson> persons,
                                       @JsonProperty("subjectScores") Map<String,
                                               List<JsonAdaptedScoreEntry>> subjectScores) {
        if (persons != null) {
            this.persons.addAll(persons);
        }
        if (subjectScores != null) {
            this.subjectScores.putAll(subjectScores);
        }
    }

    /**
     * Converts a given {@code ReadOnlyAddressBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableAddressBook}.
     */
    public JsonSerializableAddressBook(ReadOnlyAddressBook source) {
        persons.addAll(source.getPersonList().stream().map(JsonAdaptedPerson::new).collect(Collectors.toList()));

        // Populate subjectScores map from model Subjects. Only serialize explicit (non-negative) scores.
        for (Subject subject : Subject.values()) {
            Map<Person, Integer> scores = SubjectRegistry.getViewOnlyScores(subject);
            if (scores == null || scores.isEmpty()) {
                continue;
            }
            List<JsonAdaptedScoreEntry> entries = new ArrayList<>();
            for (Map.Entry<Person, Integer> e : scores.entrySet()) {
                Integer sc = e.getValue();
                if (sc != null && sc >= 0) { // only persist concrete scores
                    entries.add(new JsonAdaptedScoreEntry(e.getKey(), sc));
                }
            }
            if (!entries.isEmpty()) {
                this.subjectScores.put(subject.name(), entries);
            }
        }
    }

    /**
     * Converts this address book into the model's {@code AddressBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public AddressBook toModelType() throws IllegalValueException {
        AddressBook addressBook = new AddressBook();
        for (JsonAdaptedPerson jsonAdaptedPerson : persons) {
            Person person = jsonAdaptedPerson.toModelType();
            if (addressBook.hasPerson(person)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
            }
            addressBook.addPerson(person);
        }

        // Build a quick lookup by child name to resolve score entries
        Map<String, Person> nameToPerson = new HashMap<>();
        for (Person p : addressBook.getPersonList()) {
            nameToPerson.put(p.getChildName().toString(), p);
        }

        // Convert subject score DTOs into model Subjects
        for (Map.Entry<String, List<JsonAdaptedScoreEntry>> entry : subjectScores.entrySet()) {
            String subjectName = entry.getKey();
            Subject subject;
            try {
                subject = Subject.valueOf(subjectName);
            } catch (IllegalArgumentException ex) {
                throw new IllegalValueException("Unknown subject: " + subjectName);
            }

            for (JsonAdaptedScoreEntry dto : entry.getValue()) {
                dto.validate();
                Person person = nameToPerson.get(dto.getPersonName());
                if (person == null) {
                    throw new IllegalValueException("Unknown person in scores: " + dto.getPersonName());
                }
                subject.enrollPerson(person);
                Integer sc = dto.getScore();
                if (sc != null && sc >= 0) {
                    subject.setScore(person, sc);
                }
            }
        }
        return addressBook;
    }
}
