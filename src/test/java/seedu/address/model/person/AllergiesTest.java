package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

public class AllergiesTest {

    private static final Allergy PEANUTS = new Allergy("Peanuts");
    private static final Allergy MILK = new Allergy("Milk");
    private static final Allergy DUST = new Allergy("Dust");

    // --- Constructor Tests ---

    @Test
    public void constructor_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AllergyList(null));
    }

    @Test
    public void constructor_listContainsNull_throwsNullPointerException() {
        List<Allergy> listWithNull = new ArrayList<>(Arrays.asList(PEANUTS, null, MILK));
        assertThrows(NullPointerException.class, () -> new AllergyList(listWithNull));
    }

    @Test
    public void constructor_validList_createsImmutableCopy() {
        List<Allergy> mutableList = new ArrayList<>(Arrays.asList(PEANUTS, MILK));
        AllergyList allergies = new AllergyList(mutableList);

        // Modify the original list; Allergies object should remain unaffected
        mutableList.add(DUST);
        assertEquals(2, allergies.getAllergyList().size());
    }

    // --- getAllergyList() Tests ---

    @Test
    public void getAllergyList_unmodifiable_throwsUnsupportedOperationException() {
        AllergyList allergies = new AllergyList(Arrays.asList(PEANUTS, MILK));
        assertThrows(UnsupportedOperationException.class, () -> allergies.getAllergyList().add(DUST));
    }

    // --- isEmpty() Tests ---

    @Test
    public void isEmpty_emptyList_returnsTrue() {
        AllergyList allergies = new AllergyList(Collections.emptyList());
        assertTrue(allergies.isEmpty());
    }

    @Test
    public void isEmpty_nonEmptyList_returnsFalse() {
        AllergyList allergies = new AllergyList(Arrays.asList(PEANUTS));
        assertFalse(allergies.isEmpty());
    }

    // --- contains() Tests ---

    @Test
    public void contains_nullAllergy_throwsNullPointerException() {
        AllergyList allergies = new AllergyList(Arrays.asList(PEANUTS, MILK));
        assertThrows(NullPointerException.class, () -> allergies.contains(null));
    }

    @Test
    public void contains_existingAllergy_returnsTrue() {
        AllergyList allergies = new AllergyList(Arrays.asList(PEANUTS, MILK));
        assertTrue(allergies.contains(new Allergy("Peanuts")));
    }

    @Test
    public void contains_nonExistingAllergy_returnsFalse() {
        AllergyList allergies = new AllergyList(Arrays.asList(PEANUTS, MILK));
        assertFalse(allergies.contains(new Allergy("Dust")));
    }

    // --- toString() Tests ---

    @Test
    public void toString_emptyList_returnsNoKnownAllergies() {
        AllergyList allergies = new AllergyList(Collections.emptyList());
        assertEquals("No known allergies", allergies.toString());
    }

    @Test
    public void toString_nonEmptyList_returnsCommaSeparatedList() {
        AllergyList allergies = new AllergyList(Arrays.asList(PEANUTS, MILK, DUST));
        assertEquals("Peanuts, Milk, Dust", allergies.toString());
    }

    // --- equals() and hashCode() Tests ---

    @Test
    public void equals_sameObject_returnsTrue() {
        AllergyList allergies = new AllergyList(Arrays.asList(PEANUTS, MILK));
        assertTrue(allergies.equals(allergies));
    }

    @Test
    public void equals_null_returnsFalse() {
        AllergyList allergies = new AllergyList(Arrays.asList(PEANUTS, MILK));
        assertFalse(allergies.equals(null));
    }

    @Test
    public void equals_differentType_returnsFalse() {
        AllergyList allergies = new AllergyList(Arrays.asList(PEANUTS, MILK));
        assertFalse(allergies.equals("Peanuts"));
    }

    @Test
    public void equals_sameList_returnsTrue() {
        AllergyList allergies1 = new AllergyList(Arrays.asList(PEANUTS, MILK));
        AllergyList allergies2 = new AllergyList(Arrays.asList(new Allergy("Peanuts"), new Allergy("Milk")));
        assertTrue(allergies1.equals(allergies2));
        assertEquals(allergies1.hashCode(), allergies2.hashCode());
    }

    @Test
    public void equals_differentList_returnsFalse() {
        AllergyList allergies1 = new AllergyList(Arrays.asList(PEANUTS, MILK));
        AllergyList allergies2 = new AllergyList(Arrays.asList(PEANUTS, DUST));
        assertFalse(allergies1.equals(allergies2));
        assertNotEquals(allergies1.hashCode(), allergies2.hashCode());
    }
}
