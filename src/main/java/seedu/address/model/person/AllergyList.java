package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.List;

/**
 * Represents a child's allergies information.
 * Guarantees: immutable; all elements are valid as declared in {@link Allergy#isValidAllergy(String)}.
 */
public class AllergyList {

    public static final String MESSAGE_CONSTRAINTS =
            "Allergy list should not contain null or invalid entries.";

    private final List<Allergy> allergyList;

    /**
     * Constructs an {@code Allergies} object.
     *
     * @param allergyList A valid list of {@code Allergy} objects.
     */
    public AllergyList(List<Allergy> allergyList) {
        requireNonNull(allergyList);
        requireAllNonNull(allergyList);
        this.allergyList = List.copyOf(allergyList); // defensive copy, immutable
    }

    /**
     * Returns an unmodifiable view of the allergy list.
     */
    public List<Allergy> getAllergyList() {
        return Collections.unmodifiableList(allergyList);
    }

    /**
     * Returns true if there are no allergies.
     */
    public boolean isEmpty() {
        return allergyList.isEmpty();
    }

    /**
     * Returns true if the given allergy is contained in this list.
     */
    public boolean contains(Allergy allergy) {
        requireNonNull(allergy);
        return allergyList.contains(allergy);
    }

    @Override
    public String toString() {
        if (allergyList.isEmpty()) {
            return "No known allergies";
        }

        StringBuilder builder = new StringBuilder();
        for (Allergy allergy : allergyList) {
            builder.append(allergy.toString()).append(", ");
        }

        // Remove trailing comma and space
        builder.setLength(builder.length() - 2);
        return builder.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof AllergyList)) {
            return false;
        }

        AllergyList otherAllergies = (AllergyList) other;
        return allergyList.equals(otherAllergies.allergyList);
    }

    @Override
    public int hashCode() {
        return allergyList.hashCode();
    }
}
