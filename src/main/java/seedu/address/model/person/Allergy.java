package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's allergy in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidAllergy(String)}.
 */
public class Allergy {
    public static final int MAX_ALLERGY_LENGTH = 50;
    public static final String MESSAGE_CONSTRAINTS =
            "Allergy should be alphanumeric and spaces, must start with an alphanumeric character, "
                    + "and be at most " + MAX_ALLERGY_LENGTH + " characters long.";
    /**
     * The first character of the allergy name must not be a whitespace,
     * otherwise a blank string (" ") becomes a valid input.
     **/
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    private final String allergyName;

    /**
     * Constructs an {@code Allergy}.
     *
     * @param allergyName A valid allergy name.
     */
    public Allergy(String allergyName) {
        requireNonNull(allergyName);
        checkArgument(isValidAllergy(allergyName), MESSAGE_CONSTRAINTS);
        this.allergyName = allergyName;
    }

    /**
     * Returns true if a given string is a valid allergy.
     */
    public static boolean isValidAllergy(String test) {
        return test.matches(VALIDATION_REGEX) && test.length() <= MAX_ALLERGY_LENGTH;
    }

    @Override
    public String toString() {
        return allergyName;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Allergy)) {
            return false;
        }

        Allergy otherAllergy = (Allergy) other;
        return allergyName.equals(otherAllergy.allergyName);
    }

    @Override
    public int hashCode() {
        return allergyName.hashCode();
    }
}
