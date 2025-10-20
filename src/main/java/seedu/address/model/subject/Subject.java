package seedu.address.model.subject;

import java.util.HashSet;
import java.util.Set;
import seedu.address.model.person.Person;

/**
 * Represents a fixed set of subjects (Math, English, Science).
 * Each subject maintains a list of enrolled students and their associated scores.
 * Subjects are singletons by nature of the enum.
 */
public enum Subject {
    MATH,
    ENGLISH,
    SCIENCE;

    private final ScoreDict scoreDict = new ScoreDict();
    private final Set<Person> students = new HashSet<>();

    /**
     * Enrolls a person in this subject.
     * Called internally when a person enrolls in a subject.
     * If the person does not already have a score, a default score of 0 is assigned.
     *
     * @param person The person to enroll.
     */
    void enrollPerson(Person person) {
        students.add(person);
        if (!scoreDict.contains(person)) {
            scoreDict.setScore(person, 0); // default score is 0
        }
    }

    /**
     * Unenrolls a person from this subject, removing them from the score dictionary.
     *
     * @param person The person to unenroll.
     */
    void unenrollPerson(Person person) {
        students.remove(person);
        scoreDict.removePerson(person);
    }

    /**
     * Sets the score of a student in this subject.
     *
     * @param person The student whose score is to be set.
     * @param score The score to assign.
     * @throws IllegalStateException If the student is not enrolled in this subject.
     */
    public void setScore(Person person, int score) {
        if (!students.contains(person)) {
            throw new IllegalStateException(
                    person.getChildName() + " is not enrolled in " + name());
        }
        scoreDict.setScore(person, score);
    }

    /**
     * Retrieves the score of a student in this subject.
     *
     * @param person The student whose score is to be retrieved.
     * @return The student's score.
     * @throws IllegalStateException If the student is not enrolled or has no score.
     */
    public int getScore(Person person) {
        return scoreDict.getScore(person).orElseThrow(() ->
                new IllegalStateException(person.getChildName() + " has no score for " + name()));
    }

    /**
     * Returns an unmodifiable set of students enrolled in this subject.
     *
     * @return A set of enrolled students.
     */
    public Set<Person> getStudents() {
        return Set.copyOf(students);
    }

    /**
     * Returns the score dictionary for this subject.
     *
     * @return The {@link ScoreDict} associated with this subject.
     */
    public ScoreDict getScoreDict() {
        return scoreDict;
    }
}
