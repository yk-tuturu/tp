package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.EnrollCommand.MESSAGE_ENROLL_PERSON_SUCCESS;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.subject.Subject;

public class EnrollCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @AfterEach
    public void cleanUp() {
        for (Subject subject : Subject.values()) {
            subject.getStudents().forEach(person -> subject.unenrollPerson(person));
        }
    }

    public void setupTypical() {
        cleanUp();
        Subject.MATH.enrollPerson(ALICE);
        Subject.MATH.enrollPerson(BENSON);
        Subject.SCIENCE.enrollPerson(ALICE);
        Subject.MATH.setScore(BENSON, 100);
        Subject.MATH.setScore(ALICE, 75);
        Subject.SCIENCE.setScore(ALICE, 80);
    }

    @Test
    public void enrol_one_student_and_one_subject_success() {
        Index targetIndex = Index.fromOneBased(1);
        Index[] indexes = new Index[]{targetIndex};

        List<Subject> subjectList = new ArrayList<>();
        subjectList.add(Subject.MATH);

        EnrollCommand command = new EnrollCommand(indexes, false, subjectList);

        Person person = model.getFilteredPersonList().get(targetIndex.getZeroBased());

        String expectedMessage = String.format(MESSAGE_ENROLL_PERSON_SUCCESS,
                Messages.formatShort(person), Subject.MATH);
        Model expectedModel = model;

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertTrue(Subject.MATH.getStudents().contains(person));
        assertEquals(-1, Subject.MATH.getScore(person));
    }

    @Test
    public void enrol_multiple_student_and_one_subject_success() {
        Index[] indexes = new Index[] {
                Index.fromOneBased(1),
                Index.fromOneBased(2),
                Index.fromOneBased(3),
        };

        List<Subject> subjectList = new ArrayList<>();
        subjectList.add(Subject.MATH);

        EnrollCommand command = new EnrollCommand(indexes, false, subjectList);

        // Expected message for all enrolled students
        StringBuilder expectedMessageBuilder = new StringBuilder();
        for (Index index : indexes) {
            Person person = model.getFilteredPersonList().get(index.getZeroBased());
            expectedMessageBuilder.append(String.format(
                    MESSAGE_ENROLL_PERSON_SUCCESS, Messages.formatShort(person), Subject.MATH));
        }

        String expectedMessage = expectedMessageBuilder.toString();
        Model expectedModel = model;

        assertCommandSuccess(command, model, expectedMessage, expectedModel);

        // Verify that each student is enrolled and initialized correctly
        for (Index index : indexes) {
            Person person = model.getFilteredPersonList().get(index.getZeroBased());
            assertTrue(Subject.MATH.getStudents().contains(person),
                    () -> "Expected " + person + " to be enrolled in MATH");
            assertEquals(-1, Subject.MATH.getScore(person),
                    () -> "Expected " + person + " to have an unset score (-1)");
        }
    }

    @Test
    public void enrol_all_student_and_one_subject_success() {
        // put in any index list, the enrol all flag will ignore
        Index[] indexes = new Index[] {
                Index.fromOneBased(1),
                Index.fromOneBased(2),
                Index.fromOneBased(3),
        };

        List<Subject> subjectList = new ArrayList<>();
        subjectList.add(Subject.MATH);

        EnrollCommand command = new EnrollCommand(indexes, true, subjectList);

        List<Person> personList = model.getFilteredPersonList();

        // Expected message for all enrolled students
        StringBuilder expectedMessageBuilder = new StringBuilder();
        for (Person person : personList) {
            expectedMessageBuilder.append(String.format(
                    MESSAGE_ENROLL_PERSON_SUCCESS, Messages.formatShort(person), Subject.MATH));
        }

        String expectedMessage = expectedMessageBuilder.toString();
        Model expectedModel = model;

        assertCommandSuccess(command, model, expectedMessage, expectedModel);

        // Verify that each student is enrolled and initialized correctly
        for (Person person : personList) {
            assertTrue(Subject.MATH.getStudents().contains(person),
                    () -> "Expected " + person + " to be enrolled in MATH");
            assertEquals(-1, Subject.MATH.getScore(person),
                    () -> "Expected " + person + " to have an unset score (-1)");
        }
    }

    @Test
    public void enrol_one_student_and_multiple_subject_success() {
        Index targetIndex = Index.fromOneBased(1);
        Index[] indexes = new Index[] {targetIndex};

        List<Subject> subjectList = new ArrayList<>();
        subjectList.add(Subject.MATH);
        subjectList.add(Subject.SCIENCE);

        EnrollCommand command = new EnrollCommand(indexes, false, subjectList);

        Person person = model.getFilteredPersonList().get(targetIndex.getZeroBased());

        String expectedMessage = "";
        expectedMessage += String.format(MESSAGE_ENROLL_PERSON_SUCCESS,
                Messages.formatShort(person), Subject.MATH);
        expectedMessage += String.format(MESSAGE_ENROLL_PERSON_SUCCESS,
                Messages.formatShort(person), Subject.SCIENCE);
        Model expectedModel = model;

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertTrue(Subject.MATH.getStudents().contains(person));
        assertTrue(Subject.SCIENCE.getStudents().contains(person));
        assertEquals(-1, Subject.MATH.getScore(person));
        assertEquals(-1, Subject.SCIENCE.getScore(person));
    }

    @Test
    public void enrol_multiple_student_and_multiple_subject_success() {
        Index[] indexes = new Index[] {
                Index.fromOneBased(1),
                Index.fromOneBased(2),
                Index.fromOneBased(3),
        };

        List<Subject> subjectList = new ArrayList<>();
        subjectList.add(Subject.MATH);
        subjectList.add(Subject.SCIENCE);

        EnrollCommand command = new EnrollCommand(indexes, false, subjectList);

        // Expected message for all enrolled students
        StringBuilder expectedMessageBuilder = new StringBuilder();
        for (Index index : indexes) {
            Person person = model.getFilteredPersonList().get(index.getZeroBased());
            expectedMessageBuilder.append(String.format(
                    MESSAGE_ENROLL_PERSON_SUCCESS, Messages.formatShort(person), Subject.MATH));
            expectedMessageBuilder.append(String.format(
                    MESSAGE_ENROLL_PERSON_SUCCESS, Messages.formatShort(person), Subject.SCIENCE));
        }

        String expectedMessage = expectedMessageBuilder.toString();
        Model expectedModel = model;

        assertCommandSuccess(command, model, expectedMessage, expectedModel);

        // Verify that each student is enrolled and initialized correctly
        for (Index index : indexes) {
            Person person = model.getFilteredPersonList().get(index.getZeroBased());
            assertTrue(Subject.MATH.getStudents().contains(person),
                    () -> "Expected " + person + " to be enrolled in MATH");
            assertTrue(Subject.SCIENCE.getStudents().contains(person),
                    () -> "Expected " + person + " to be enrolled in SCIENCE");
            assertEquals(-1, Subject.MATH.getScore(person),
                    () -> "Expected " + person + " to have an unset score (-1)");
            assertEquals(-1, Subject.SCIENCE.getScore(person),
                    () -> "Expected " + person + " to have an unset score (-1)");
        }
    }

    @Test
    public void enrol_all_student_and_multiple_subject_success() {
        // put in any index list, the enrol all flag will ignore
        Index[] indexes = new Index[] {
                Index.fromOneBased(1),
                Index.fromOneBased(2),
                Index.fromOneBased(3),
        };

        List<Subject> subjectList = new ArrayList<>();
        subjectList.add(Subject.MATH);
        subjectList.add(Subject.SCIENCE);

        EnrollCommand command = new EnrollCommand(indexes, true, subjectList);

        List<Person> personList = model.getFilteredPersonList();

        // Expected message for all enrolled students
        StringBuilder expectedMessageBuilder = new StringBuilder();
        for (Person person : personList) {
            expectedMessageBuilder.append(String.format(
                    MESSAGE_ENROLL_PERSON_SUCCESS, Messages.formatShort(person), Subject.MATH));
            expectedMessageBuilder.append(String.format(
                    MESSAGE_ENROLL_PERSON_SUCCESS, Messages.formatShort(person), Subject.SCIENCE));
        }

        String expectedMessage = expectedMessageBuilder.toString();
        Model expectedModel = model;

        assertCommandSuccess(command, model, expectedMessage, expectedModel);

        // Verify that each student is enrolled and initialized correctly
        for (Person person : personList) {
            assertTrue(Subject.MATH.getStudents().contains(person),
                    () -> "Expected " + person + " to be enrolled in MATH");
            assertEquals(-1, Subject.MATH.getScore(person),
                    () -> "Expected " + person + " to have an unset score (-1)");
            assertTrue(Subject.SCIENCE.getStudents().contains(person),
                    () -> "Expected " + person + " to be enrolled in SCIENCE");
            assertEquals(-1, Subject.SCIENCE.getScore(person),
                    () -> "Expected " + person + " to have an unset score (-1)");
        }
    }

    @Test
    public void invalid_arguments_out_of_range_index() {
        Index outOfRangeIndex = Index.fromOneBased(100);
        Index[] indexes = new Index[] {outOfRangeIndex};
        List<Subject> subjectList = new ArrayList<>();
        subjectList.add(Subject.MATH);
        EnrollCommand command = new EnrollCommand(indexes, false, subjectList);

        String expectedMessage = MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
        assertCommandFailure(command, model, expectedMessage);
    }

    @Test
    public void already_enrolled_student() {
        setupTypical();
        Index[] indexes = new Index[] {
                Index.fromOneBased(1),
                Index.fromOneBased(2)
        };
        List<Subject> subjectList = new ArrayList<>();
        subjectList.add(Subject.SCIENCE);

        EnrollCommand command = new EnrollCommand(indexes, false, subjectList);

        String expectedMessage = String.format(MESSAGE_ENROLL_PERSON_SUCCESS,
                Messages.formatShort(BENSON), Subject.SCIENCE);
        assertCommandSuccess(command, model, expectedMessage, model);
        assertEquals(2, Subject.MATH.getStudents().size());
        assertEquals(2, Subject.SCIENCE.getStudents().size());
        assertTrue(Subject.MATH.getStudents().contains(ALICE));
        assertTrue(Subject.MATH.getStudents().contains(BENSON));
        assertTrue(Subject.SCIENCE.getStudents().contains(ALICE));
        assertTrue(Subject.SCIENCE.getStudents().contains(ALICE));
        assertTrue(Subject.SCIENCE.getScore(ALICE) == 80);
        assertTrue(Subject.SCIENCE.getScore(BENSON) == -1);
    }
}
