package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.person.Person;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalPersons {

    public static final Person ALICE = new PersonBuilder()
            .withChildName("Alice Pauline")
            .withParentName("Paulines Mom")
            .withParentPhone("94351253")
            .withParentEmail("alice@example.com")
            .withAllergies("Peanuts")
            .withAddress("123, Jurong West Ave 6, #08-111")
            .build();

    public static final Person BENSON = new PersonBuilder()
            .withChildName("Benson Meier")
            .withParentName("Bensons Dad")
            .withParentPhone("98765432")
            .withParentEmail("johnd@example.com")
            .withAllergies("Milk", "Shellfish")
            .withAddress("311, Clementi Ave 2, #02-25")
            .build();

    public static final Person CARL = new PersonBuilder()
            .withChildName("Carl Kurz")
            .withParentName("Carls Mom")
            .withParentPhone("95352563")
            .withParentEmail("heinz@example.com")
            .withAllergies("Dust")
            .withAddress("wall street")
            .build();

    public static final Person DANIEL = new PersonBuilder()
            .withChildName("Daniel Meier")
            .withParentName("Daniels Dad")
            .withParentPhone("87652533")
            .withParentEmail("cornelia@example.com")
            .withAllergies("Eggs")
            .withAddress("10th Street")
            .build();

    public static final Person ELLE = new PersonBuilder()
            .withChildName("Elle Meyer")
            .withParentName("Elles Mom")
            .withParentPhone("9482224")
            .withParentEmail("werner@example.com")
            .withAllergies("Pollen")
            .withAddress("Michegan Ave")
            .build();

    public static final Person FIONA = new PersonBuilder()
            .withChildName("Fiona Kunz")
            .withParentName("Fionas Dad")
            .withParentPhone("9482427")
            .withParentEmail("lydia@example.com")
            .withAllergies("Gluten")
            .withAddress("Little Tokyo")
            .build();

    public static final Person GEORGE = new PersonBuilder()
            .withChildName("George Best")
            .withParentName("Georges Mom")
            .withParentPhone("9482442")
            .withParentEmail("anna@example.com")
            .withAllergies() // no allergies
            .withAddress("4th Street")
            .build();

    // Manually added
    public static final Person HOON = new PersonBuilder()
            .withChildName("Hoon Meier")
            .withParentName("Elle Meier")
            .withParentPhone("8482424")
            .withParentEmail("elle@example.com")
            .withAllergies("Soy")
            .withAddress("Little India")
            .build();

    public static final Person IDA = new PersonBuilder()
            .withChildName("Ida Mueller")
            .withParentName("Idas Dad")
            .withParentPhone("8482131")
            .withParentEmail("hans@example.com")
            .withAllergies("Dust")
            .withAddress("Chicago Ave")
            .build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Person AMY = new PersonBuilder()
            .withChildName(VALID_NAME_AMY)
            .withParentName("Amys Mom")
            .withParentPhone(VALID_PHONE_AMY)
            .withParentEmail(VALID_EMAIL_AMY)
            .withAllergies("Eggs")
            .withAddress(VALID_ADDRESS_AMY)
            .build();

    public static final Person BOB = new PersonBuilder()
            .withChildName(VALID_NAME_BOB)
            .withParentName("Bobs Dad")
            .withParentPhone(VALID_PHONE_BOB)
            .withParentEmail(VALID_EMAIL_BOB)
            .withAllergies("Peanuts", "Milk")
            .withAddress(VALID_ADDRESS_BOB)
            .build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalPersons() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical persons.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Person person : getTypicalPersons()) {
            ab.addPerson(person);
        }
        return ab;
    }

    public static List<Person> getTypicalPersons() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE, HOON, IDA, AMY, BOB));
    }
}
