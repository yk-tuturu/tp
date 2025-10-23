package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.AddressBook;
import seedu.address.model.person.Person;
import seedu.address.model.subject.Subject;
import seedu.address.testutil.SubjectTestUtil;
import seedu.address.testutil.TypicalPersons;

public class JsonSerializableAddressBookTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableAddressBookTest");
    private static final Path TYPICAL_PERSONS_FILE = TEST_DATA_FOLDER.resolve("typicalPersonsAddressBook.json");
    private static final Path INVALID_PERSON_FILE = TEST_DATA_FOLDER.resolve("invalidPersonAddressBook.json");
    private static final Path DUPLICATE_PERSON_FILE = TEST_DATA_FOLDER.resolve("duplicatePersonAddressBook.json");

    private static final Path TYPICAL_WITH_SCORES_FILE = TEST_DATA_FOLDER
            .resolve("typicalPersonsAddressBookWithScores.json");
    private static final Path INVALID_SCORE_FILE = TEST_DATA_FOLDER.resolve("invalidScoreAddressBook.json");
    private static final Path UNKNOWN_PERSON_SCORE_FILE = TEST_DATA_FOLDER
            .resolve("unknownPersonScoreAddressBook.json");
    private static final Path UNKNOWN_SUBJECT_FILE = TEST_DATA_FOLDER
            .resolve("unknownSubjectAddressBook.json");

    @BeforeEach
    public void setUp() {
        SubjectTestUtil.resetSubjects();
    }

    @AfterEach
    public void tearDown() {
        SubjectTestUtil.resetSubjects();
    }

    @Test
    public void toModelType_typicalPersonsFile_success() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(TYPICAL_PERSONS_FILE,
                JsonSerializableAddressBook.class).get();
        AddressBook addressBookFromFile = dataFromFile.toModelType();
        AddressBook typicalPersonsAddressBook = TypicalPersons.getTypicalAddressBook();
        assertEquals(addressBookFromFile, typicalPersonsAddressBook);
    }

    @Test
    public void toModelType_invalidPersonFile_throwsIllegalValueException() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(INVALID_PERSON_FILE,
                JsonSerializableAddressBook.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicatePersons_throwsIllegalValueException() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(DUPLICATE_PERSON_FILE,
                JsonSerializableAddressBook.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableAddressBook.MESSAGE_DUPLICATE_PERSON,
                dataFromFile::toModelType);
    }

    // ---- New tests for subject score serialization/deserialization ----

    @Test
    public void toModelType_typicalPersonsFileWithScores_success() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(TYPICAL_WITH_SCORES_FILE,
                JsonSerializableAddressBook.class).get();
        AddressBook addressBookFromFile = dataFromFile.toModelType();

        // Resolve persons by child name
        Person hoon = addressBookFromFile.getPersonList().stream()
                .filter(p -> p.getChildName().toString().equals(TypicalPersons.HOON.getChildName().toString()))
                .findFirst().orElseThrow();
        Person ida = addressBookFromFile.getPersonList().stream()
                .filter(p -> p.getChildName().toString().equals(TypicalPersons.IDA.getChildName().toString()))
                .findFirst().orElseThrow();

        // Verify scores were restored onto Subjects
        assertEquals(88, Subject.MATH.getScore(hoon));
        assertEquals(92, Subject.SCIENCE.getScore(ida));
    }

    @Test
    public void toModelType_invalidScore_throwsIllegalValueException() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(INVALID_SCORE_FILE,
                JsonSerializableAddressBook.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_unknownPersonInScores_throwsIllegalValueException() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(UNKNOWN_PERSON_SCORE_FILE,
                JsonSerializableAddressBook.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_unknownSubject_throwsIllegalValueException() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(UNKNOWN_SUBJECT_FILE,
                JsonSerializableAddressBook.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

}
