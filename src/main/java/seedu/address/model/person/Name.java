package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's name in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class Name {

    public static final String MESSAGE_CONSTRAINTS =
            "Names should only contain English letters, spaces, and allowed symbols "
                    + "(. , ' ’ - ( ) /), and must contain at least one letter or digit.";

    /*
     * This regex ensures:
     *  - Only English letters, numbers, spaces, and the listed punctuation are allowed.
     *  - There must be at least one alphanumeric character (prevents whitespace-only names).
     */
    public static final String VALIDATION_REGEX =
            "^(?=.*[A-Za-z])[A-Za-z .,'’\\-()/]+$";

    public final String fullName;

    /**
     * Constructs a {@code Name}.
     *
     * @param name A valid name.
     */
    public Name(String name) {
        requireNonNull(name);
        String trimmed = name.trim(); // trim whitespace from both ends
        checkArgument(isValidName(trimmed), MESSAGE_CONSTRAINTS);
        fullName = trimmed.replaceAll("\\s+", " ");
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return fullName;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Name)) {
            return false;
        }

        Name otherName = (Name) other;
        return fullName.equalsIgnoreCase(otherName.fullName);
    }

    @Override
    public int hashCode() {
        return fullName.hashCode();
    }
}
