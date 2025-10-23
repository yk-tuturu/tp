package seedu.address.testutil;

import java.util.ArrayList;
import java.util.List;

import seedu.address.model.person.Person;
import seedu.address.model.subject.Subject;

/**
 * Test utilities for managing Subject enum state during tests.
 */
public final class SubjectTestUtil {

    private SubjectTestUtil() {}

    /**
     * Removes all enrolled students from every Subject. Use in @BeforeEach/@AfterEach in tests that
     * mutate Subject enrollment to avoid cross-test pollution.
     */
    public static void resetSubjects() {
        for (Subject s : Subject.values()) {
            List<Person> toUnenroll = new ArrayList<>(s.getStudents());
            for (Person p : toUnenroll) {
                s.unenrollPerson(p);
            }
        }
    }
}

