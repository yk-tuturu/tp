package seedu.address.model.util;

import org.junit.jupiter.api.Test;

import seedu.address.model.AddressBook;
import seedu.address.model.person.Person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class SampleDataUtilTest {

    @Test
    public void getSamplePersons_validData_success() {
        Person[] samplePersons = SampleDataUtil.getSamplePersons();
        assertNotNull(samplePersons);
        assertEquals(6, samplePersons.length);
    }

    @Test
    public void getSampleAddressBook_validData_success() {
        AddressBook sampleAddressBook = (AddressBook) SampleDataUtil.getSampleAddressBook();
        assertNotNull(sampleAddressBook);
        assertEquals(6, sampleAddressBook.getPersonList().size());
    }
}