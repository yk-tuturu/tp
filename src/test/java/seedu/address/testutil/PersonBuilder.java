package seedu.address.testutil;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.person.Address;
import seedu.address.model.person.Allergy;
import seedu.address.model.person.AllergyList;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Person objects.
 */
public class PersonBuilder {

    public static final String DEFAULT_CHILD_NAME = "Amy Bee";
    public static final String DEFAULT_PARENT_NAME = "Bees Mom";
    public static final String DEFAULT_PARENT_PHONE = "85355255";
    public static final String DEFAULT_PARENT_EMAIL = "amy@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";
    public static final int DEFAULT_UNIQUE_ID = 0;

    private Name childName;
    private Name parentName;
    private Phone parentPhone;
    private Email parentEmail;
    private AllergyList allergies;
    private Address address;
    private Set<Tag> tags;
    private int uniqueId;

    /**
     * Creates a {@code PersonBuilder} with the default details.
     */
    public PersonBuilder() {
        childName = new Name(DEFAULT_CHILD_NAME);
        parentName = new Name(DEFAULT_PARENT_NAME);
        parentPhone = new Phone(DEFAULT_PARENT_PHONE);
        parentEmail = new Email(DEFAULT_PARENT_EMAIL);
        allergies = new AllergyList(Set.of());
        address = new Address(DEFAULT_ADDRESS);
        uniqueId = DEFAULT_UNIQUE_ID;
        tags = new HashSet<>();
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public PersonBuilder(Person personToCopy) {
        childName = personToCopy.getChildName();
        parentName = personToCopy.getParentName();
        parentPhone = personToCopy.getParentPhone();
        parentEmail = personToCopy.getParentEmail();
        allergies = personToCopy.getAllergies();
        address = personToCopy.getAddress();
        tags = new HashSet<>(personToCopy.getTags());
    }

    /**
     * Sets the {@code childName} of the {@code Person} that we are building.
     */
    public PersonBuilder withChildName(String name) {
        this.childName = new Name(name);
        return this;
    }

    /**
     * Sets the {@code parentName} of the {@code Person} that we are building.
     */
    public PersonBuilder withParentName(String name) {
        this.parentName = new Name(name);
        return this;
    }

    /**
     * Sets the {@code parentPhone} of the {@code Person} that we are building.
     */
    public PersonBuilder withParentPhone(String phone) {
        this.parentPhone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code parentEmail} of the {@code Person} that we are building.
     */
    public PersonBuilder withParentEmail(String email) {
        this.parentEmail = new Email(email);
        return this;
    }

    /**
     * Sets the {@code allergies} of the {@code Person} that we are building.
     */
    public PersonBuilder withAllergies(String... allergyNames) {
        Set<Allergy> allergyList = List.of(allergyNames).stream()
                .map(Allergy::new)
                .collect(Collectors.toSet());
        this.allergies = new AllergyList(allergyList);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Person} that we are building.
     */
    public PersonBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and sets it to the {@code Person} that we are building.
     */
    public PersonBuilder withTags(String... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code uniqueId} of the {@code Person} that we are building.
     *
     * @param uniqueId The unique identifier to assign to the person.
     * @return This {@code PersonBuilder} instance with the updated {@code uniqueId}.
     */
    public PersonBuilder withUniqueId(int uniqueId) {
        this.uniqueId = uniqueId;
        return this;
    }

    public Person build() {
        return new Person(childName, parentName, parentPhone, parentEmail, allergies, address, tags, uniqueId);
    }
}
