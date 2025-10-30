package seedu.address.model.subject;

import java.util.HashSet;
import java.util.List;
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

    public static final String MESSAGE_CONSTRAINTS =
            "Subject string should not be empty, and it should be one of the registered subjects "
                    + "MATH, ENGLISH, SCIENCE";
    private static final int DEFAULT_SCORE = -1;

    private final ScoreDict scoreDict = new ScoreDict();
    private final Set<Person> students = new HashSet<>();

    public static int getDefaultScore() {
        return DEFAULT_SCORE;
    }

    /**
     * Gets the correct subject based on string, case insensitive
     * @param name string of subject name
     * @return the subject enum
     */
    public static Subject fromString(String name) {
        for (Subject subject : Subject.values()) {
            if (subject.name().equalsIgnoreCase(name)) {
                return subject;
            }
        }
        throw new IllegalArgumentException("No subject found for name: " + name);
    }

    /**
     * Checks whether the given name corresponds to a valid {@link Subject}.
     * The comparison is case-insensitive.
     *
     * @param name The name of the subject to check.
     * @return {@code true} if the name matches an existing subject; {@code false} otherwise.
     */
    public static boolean contains(String name) {
        try {
            fromString(name);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * Enrolls a person in this subject.
     * Called internally when a person enrolls in a subject.
     * If the person does not already have a score, a default score of -1 is assigned.
     *
     * @param person The person to enroll.
     */
    public Person enrollPerson(Person person) {
        students.add(person);
        if (!scoreDict.contains(person)) {
            scoreDict.setScore(person, DEFAULT_SCORE);
        }

        return person;
    }

    /**
     * Unenrolls a person from this subject, removing them from the score dictionary.
     *
     * @param person The person to unenroll.
     */
    public Person unenrollPerson(Person person) {
        students.remove(person);
        scoreDict.removePerson(person);

        return person;
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
        assert (score == DEFAULT_SCORE || (score >= 0 && score <= 100))
                : "Score must be -1 (unset) or between 0 and 100 inclusive.";
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

    @Override
    public String toString() {
        return this.name();
    }
    /**
     * Returns a list of all defined subjects.
     *
     * @return A list of all subjects.
     */
    public static List<Subject> getAllSubjects() {
        return List.of(Subject.values());
    }
}
