package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_SINGLEPARENT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class PersonTest {

    @Test
    public void getTags_unmodifiable_throwsUnsupportedOperationException() {
        Person person = new PersonBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> person.getTags().remove(0));
    }

    @Test
    public void isSamePerson() {
        // same object -> returns true
        assertTrue(ALICE.isSamePerson(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSamePerson(null));

        // same child and parent name, all other attributes different -> returns true
        Person editedAlice = new PersonBuilder(ALICE)
                .withParentPhone(VALID_PHONE_BOB)
                .withParentEmail(VALID_EMAIL_BOB)
                .withAddress(VALID_ADDRESS_BOB)
                .withTags(VALID_TAG_SINGLEPARENT)
                .build();
        assertTrue(ALICE.isSamePerson(editedAlice));

        // different child name -> returns false
        editedAlice = new PersonBuilder(ALICE).withChildName(VALID_NAME_BOB).build();
        assertFalse(ALICE.isSamePerson(editedAlice));

        // different parent name -> returns false
        editedAlice = new PersonBuilder(ALICE).withParentName("Different Parent").build();
        assertFalse(ALICE.isSamePerson(editedAlice));

        // same names but differing case -> still false
        Person editedBob = new PersonBuilder(BOB)
                .withChildName(VALID_NAME_BOB.toLowerCase())
                .withParentName(BOB.getParentName().toString().toLowerCase())
                .build();
        assertTrue(BOB.isSamePerson(editedBob));

        // name with trailing spaces -> false
        String nameWithTrailingSpaces = VALID_NAME_BOB + " ";
        editedBob = new PersonBuilder(BOB)
                .withChildName(nameWithTrailingSpaces)
                .build();
        assertFalse(BOB.isSamePerson(editedBob));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Person aliceCopy = new PersonBuilder(ALICE).build();
        assertTrue(ALICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE.equals(ALICE));

        // null -> returns false
        assertFalse(ALICE.equals(null));

        // different type -> returns false
        assertFalse(ALICE.equals(5));

        // different person -> returns false
        assertFalse(ALICE.equals(BOB));

        // same Person descriptor attributes, different unique Id -> returns false
        Person editedAlice = new PersonBuilder(ALICE).withUniqueId(546).build();
        assertFalse(ALICE.equals(editedAlice));

        // different child name, same unique Id -> returns true
        editedAlice = new PersonBuilder(ALICE).withChildName(VALID_NAME_BOB).build();
        assertTrue(ALICE.equals(editedAlice));

        // different parent name,same unique Id -> returns true
        editedAlice = new PersonBuilder(ALICE).withParentName("Different Parent").build();
        assertTrue(ALICE.equals(editedAlice));

        // different parent phone, same unique Id -> returns true
        editedAlice = new PersonBuilder(ALICE).withParentPhone(VALID_PHONE_BOB).build();
        assertTrue(ALICE.equals(editedAlice));

        // different parent email, same unique Id -> returns true
        editedAlice = new PersonBuilder(ALICE).withParentEmail(VALID_EMAIL_BOB).build();
        assertTrue(ALICE.equals(editedAlice));

        // different address, same unique Id -> returns true
        editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).build();
        assertTrue(ALICE.equals(editedAlice));

        // different tags, same unique Id -> returns true
        editedAlice = new PersonBuilder(ALICE).withTags(VALID_TAG_SINGLEPARENT).build();
        assertTrue(ALICE.equals(editedAlice));

        // different allergies, same unique Id -> returns true
        editedAlice = new PersonBuilder(ALICE).withAllergies("Peanuts", "Dust").build();
        assertTrue(ALICE.equals(editedAlice));
    }

    @Test
    public void toStringMethod() {
        String expected = Person.class.getCanonicalName()
                + "{child=" + ALICE.getChildName()
                + ", parent=" + ALICE.getParentName()
                + ", parentPhone=" + ALICE.getParentPhone()
                + ", parentEmail=" + ALICE.getParentEmail()
                + ", allergies=" + ALICE.getAllergies()
                + ", address=" + ALICE.getAddress()
                + ", tags=" + ALICE.getTags() + "}";
        assertEquals(expected, ALICE.toString());
    }

    @Test
    public void editAllergies_updatesCorrectly() {
        // start with ALICE having a default allergy list
        Person originalAlice = new PersonBuilder(ALICE).withAllergies("Dust").build();

        // simulate editing her allergies
        Person editedAlice = new PersonBuilder(originalAlice)
                .withAllergies("Peanuts", "Shellfish")
                .build();

        // ensure allergies actually changed
        assertFalse(originalAlice.getAllergies().equals(editedAlice.getAllergies()));
        assertEquals(List.of("Peanuts", "Shellfish"),
                editedAlice.getAllergies().getAllergyList().stream()
                        .map(Allergy::toString)
                        .toList());

        // all other attributes should remain unchanged
        assertEquals(originalAlice.getChildName(), editedAlice.getChildName());
        assertEquals(originalAlice.getParentName(), editedAlice.getParentName());
        assertEquals(originalAlice.getParentPhone(), editedAlice.getParentPhone());
        assertEquals(originalAlice.getParentEmail(), editedAlice.getParentEmail());
        assertEquals(originalAlice.getAddress(), editedAlice.getAddress());
        assertEquals(originalAlice.getTags(), editedAlice.getTags());
    }
}
