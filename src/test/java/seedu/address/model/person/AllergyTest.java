package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;

public class AllergyTest {

    // Valid examples
    private static final String VALID_ALLERGY_1 = "Peanuts";
    private static final String VALID_ALLERGY_2 = "Milk Protein";
    private static final String VALID_ALLERGY_3 = "Dust Mite";
    private static final String VALID_ALLERGY_4 = "Shellfish";

    // Invalid examples
    private static final String INVALID_ALLERGY_EMPTY = "";
    private static final String INVALID_ALLERGY_SPACES = "   ";
    private static final String INVALID_ALLERGY_SYMBOLS = "Egg@123";
    private static final String INVALID_ALLERGY_STARTS_WITH_SPACE = " Dust";

    // --- isValidAllergy() tests ---

    @Test
    public void isValidAllergy_validInputs_returnTrue() {
        assertTrue(Allergy.isValidAllergy(VALID_ALLERGY_1));
        assertTrue(Allergy.isValidAllergy(VALID_ALLERGY_2));
        assertTrue(Allergy.isValidAllergy(VALID_ALLERGY_3));
        assertTrue(Allergy.isValidAllergy(VALID_ALLERGY_4));
    }

    @Test
    public void isValidAllergy_invalidInputs_returnFalse() {
        assertFalse(Allergy.isValidAllergy(INVALID_ALLERGY_EMPTY));
        assertFalse(Allergy.isValidAllergy(INVALID_ALLERGY_SPACES));
        assertFalse(Allergy.isValidAllergy(INVALID_ALLERGY_SYMBOLS));
        assertFalse(Allergy.isValidAllergy(INVALID_ALLERGY_STARTS_WITH_SPACE));
    }

    // --- Constructor tests ---

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Allergy(null));
    }

    @Test
    public void constructor_invalidAllergy_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Allergy(INVALID_ALLERGY_EMPTY));
        assertThrows(IllegalArgumentException.class, () -> new Allergy(INVALID_ALLERGY_SPACES));
        assertThrows(IllegalArgumentException.class, () -> new Allergy(INVALID_ALLERGY_SYMBOLS));
    }

    @Test
    public void constructor_validAllergy_createsSuccessfully() {
        Allergy allergy = new Allergy(VALID_ALLERGY_1);
        assertEquals("Peanuts", allergy.toString());
    }

    // --- Equality tests ---

    @Test
    public void equals_sameObject_returnsTrue() {
        Allergy allergy = new Allergy(VALID_ALLERGY_1);
        assertTrue(allergy.equals(allergy));
    }

    @Test
    public void equals_null_returnsFalse() {
        Allergy allergy = new Allergy(VALID_ALLERGY_1);
        assertFalse(allergy.equals(null));
    }

    @Test
    public void equals_differentType_returnsFalse() {
        Allergy allergy = new Allergy(VALID_ALLERGY_1);
        assertFalse(allergy.equals("Peanuts"));
    }

    @Test
    public void equals_sameValue_returnsTrue() {
        Allergy allergy1 = new Allergy(VALID_ALLERGY_1);
        Allergy allergy2 = new Allergy(VALID_ALLERGY_1);
        assertTrue(allergy1.equals(allergy2));
        assertEquals(allergy1.hashCode(), allergy2.hashCode());
    }

    @Test
    public void equals_differentValue_returnsFalse() {
        Allergy allergy1 = new Allergy(VALID_ALLERGY_1);
        Allergy allergy2 = new Allergy(VALID_ALLERGY_2);
        assertFalse(allergy1.equals(allergy2));
        assertNotEquals(allergy1.hashCode(), allergy2.hashCode());
    }

    // --- toString() tests ---

    @Test
    public void toString_returnsCorrectValue() {
        Allergy allergy = new Allergy("Shellfish");
        assertEquals("Shellfish", allergy.toString());
    }
}
