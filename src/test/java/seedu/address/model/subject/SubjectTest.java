package seedu.address.model.subject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;

import java.util.Set;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.person.Person;

public class SubjectTest {

    @AfterEach
    public void cleanup() {
        for (Subject subject : Subject.values()) {
            subject.getStudents().forEach(person -> subject.unenrollPerson(person));
        }
    }

    @Test
    public void enrollPerson_success() {
        Subject.MATH.enrollPerson(ALICE);

        assertTrue(Subject.MATH.getStudents().contains(ALICE));
        assertEquals(-1, Subject.MATH.getScore(ALICE));
    }

    @Test
    public void enrollPerson_multipleStudents_success() {
        Subject.ENGLISH.enrollPerson(ALICE);
        Subject.ENGLISH.enrollPerson(BENSON);
        Subject.ENGLISH.enrollPerson(CARL);

        Set<Person> students = Subject.ENGLISH.getStudents();
        assertEquals(3, students.size());
        assertTrue(students.contains(ALICE));
        assertTrue(students.contains(BENSON));
        assertTrue(students.contains(CARL));
    }

    @Test
    public void enrollPerson_duplicateEnrollment_doesNotDuplicateStudent() {
        Subject.SCIENCE.enrollPerson(ALICE);
        Subject.SCIENCE.enrollPerson(ALICE);

        assertEquals(1, Subject.SCIENCE.getStudents().size());
    }

    @Test
    public void unenrollPerson_success() {
        Subject.MATH.enrollPerson(ALICE);
        Subject.MATH.enrollPerson(BENSON);

        Subject.MATH.unenrollPerson(ALICE);

        assertFalse(Subject.MATH.getStudents().contains(ALICE));
        assertTrue(Subject.MATH.getStudents().contains(BENSON));
    }

    @Test
    public void unenrollPerson_notEnrolled_noEffect() {
        Subject.MATH.unenrollPerson(ALICE);

        assertFalse(Subject.MATH.getStudents().contains(ALICE));
    }

    @Test
    public void setScore_enrolledStudent_success() {
        Subject.MATH.enrollPerson(ALICE);

        Subject.MATH.setScore(ALICE, 85);

        assertEquals(85, Subject.MATH.getScore(ALICE));
    }

    @Test
    public void setScore_notEnrolled_throwsException() {
        assertThrows(IllegalStateException.class, () -> Subject.MATH.setScore(ALICE, 90));
    }

    @Test
    public void setScore_updateExistingScore_success() {
        Subject.ENGLISH.enrollPerson(BENSON);
        Subject.ENGLISH.setScore(BENSON, 70);

        Subject.ENGLISH.setScore(BENSON, 95);

        assertEquals(95, Subject.ENGLISH.getScore(BENSON));
    }

    @Test
    public void getScore_enrolledStudent_success() {
        Subject.SCIENCE.enrollPerson(CARL);
        Subject.SCIENCE.setScore(CARL, 88);

        assertEquals(88, Subject.SCIENCE.getScore(CARL));
    }

    @Test
    public void getScore_notEnrolled_throwsException() {
        assertThrows(IllegalStateException.class, () -> Subject.MATH.getScore(ALICE));
    }

    @Test
    public void getScore_enrolledButNoScoreSet_returnsDefaultScore() {
        Subject.MATH.enrollPerson(ALICE);

        assertEquals(-1, Subject.MATH.getScore(ALICE));
    }

    @Test
    public void getStudents_emptySubject_returnsEmptySet() {
        Set<Person> students = Subject.MATH.getStudents();

        assertTrue(students.isEmpty());
    }

    @Test
    public void getStudents_returnsUnmodifiableSet() {
        Subject.ENGLISH.enrollPerson(ALICE);
        Set<Person> students = Subject.ENGLISH.getStudents();

        // Attempting to modify the returned set should throw an exception
        assertThrows(UnsupportedOperationException.class, () -> students.add(BENSON));
    }

    @Test
    public void getScoreDict_returnsCorrectScoreDict() {
        Subject.MATH.enrollPerson(ALICE);
        Subject.MATH.setScore(ALICE, 75);

        ScoreDict scoreDict = Subject.MATH.getScoreDict();

        assertTrue(scoreDict.contains(ALICE));
        assertEquals(75, scoreDict.getScore(ALICE).orElse(-1));
    }

    @Test
    public void multipleSubjects_independentEnrollment() {
        Subject.MATH.enrollPerson(ALICE);
        Subject.ENGLISH.enrollPerson(BENSON);
        Subject.SCIENCE.enrollPerson(CARL);

        assertEquals(1, Subject.MATH.getStudents().size());
        assertEquals(1, Subject.ENGLISH.getStudents().size());
        assertEquals(1, Subject.SCIENCE.getStudents().size());

        assertTrue(Subject.MATH.getStudents().contains(ALICE));
        assertFalse(Subject.MATH.getStudents().contains(BENSON));
    }

    @Test
    public void multipleSubjects_sameStudent_independentScores() {
        Subject.MATH.enrollPerson(ALICE);
        Subject.ENGLISH.enrollPerson(ALICE);
        Subject.SCIENCE.enrollPerson(ALICE);

        Subject.MATH.setScore(ALICE, 90);
        Subject.ENGLISH.setScore(ALICE, 85);
        Subject.SCIENCE.setScore(ALICE, 95);

        assertEquals(90, Subject.MATH.getScore(ALICE));
        assertEquals(85, Subject.ENGLISH.getScore(ALICE));
        assertEquals(95, Subject.SCIENCE.getScore(ALICE));
    }

    @Test
    public void subjectEnum_correctValues() {
        Subject[] subjects = Subject.values();

        assertEquals(3, subjects.length);
        assertEquals(Subject.MATH, Subject.valueOf("MATH"));
        assertEquals(Subject.ENGLISH, Subject.valueOf("ENGLISH"));
        assertEquals(Subject.SCIENCE, Subject.valueOf("SCIENCE"));
    }
}
