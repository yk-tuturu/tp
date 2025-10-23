package seedu.address.model.subject;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

// Scores should always be between 0 - 100
// except for when a student is newly enrolled into a class and doesn't have a set grade yet, the score will be initialized to -1
// on the user side, it should not be possible for them to set the score to anything other than 0 - 100
public class Score {
    public static final int UNSET_SCORE = -1;
    public static final String MESSAGE_CONSTRAINTS =
            "Score must be within range 0 - 100";
    public final int value;

    /**
     * Constructs a {@code Score}. Private constructor as we should only use the factory methods
     *
     * @param score A valid score.
     */
    private Score(int score) {
        requireNonNull(score);
        checkArgument(isValidScore(score), MESSAGE_CONSTRAINTS);
        value = score;
    }

    /**
     * For initializing and creating an unset score
     * @return
     */
    public static Score createUnset() {
        return new Score(UNSET_SCORE);
    }

    /**
     * Factory method, creates only defined scores that must be between 0 - 100
     * @param score
     * @return
     */
    public static Score createDefined(int score) {
        requireNonNull(score);
        checkArgument(isValidDefinedScore(score), MESSAGE_CONSTRAINTS);
        return new Score(score);
    }

    /**
     * Returns true if a given string is a valid score, includes the -1 value
     */
    public static boolean isValidScore(int score) {
        return (score >= 0 && score <= 100) || score == UNSET_SCORE;
    }

    /**
     * Retuns true when the score is 0 - 100, set scores cannot have the sentinel value -1
     * @param score any int value to be checked
     * @return true or false
     */
    public static boolean isValidDefinedScore(int score) {
        return (score >= 0 && score <= 100);
    }

    /**
     * Returns the score as a string
     * @return a string
     */
    @Override
    public String toString() {
        return value == UNSET_SCORE ? "N/A" : String.valueOf(value);
    }

    /**
     * Check for equality
     * @param other the other object
     * @return boolean
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Score)) {
            return false;
        }

        Score otherScore = (Score) other;
        return value == otherScore.value;
    }

    /**
     * Creates a hashcode for the class
     * @return
     */
    @Override
    public int hashCode() {
        return value;
    }
}
