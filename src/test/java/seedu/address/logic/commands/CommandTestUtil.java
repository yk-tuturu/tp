package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PARENTEMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CHILDNAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PARENTNAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PARENTPHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ALLERGY;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.testutil.EditPersonDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {
    
    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_AMY_PARENT = "Alice Choo";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_NAME_BOB_PARENT = "Betty Lim";
    public static final String VALID_PHONE_AMY = "11111111";
    public static final String VALID_PHONE_BOB = "22222222";
    public static final String VALID_EMAIL_AMY = "amy@example.com";
    public static final String VALID_EMAIL_BOB = "bob@example.com";
    public static final String VALID_ADDRESS_AMY = "Block 312, Amy Street 1";
    public static final String VALID_ADDRESS_BOB = "Block 123, Bobby Street 3";
    public static final String VALID_ALLERGY_PEANUTS = "peanuts";
    public static final String VALID_ALLERGY_MILK = "milk";
    public static final String VALID_ALLERGY_DUST = "dust";
    public static final String VALID_TAG_SINGLEPARENT = "single parent";
    public static final String VALID_TAG_SPECIALNEEDS = "special needs";
    
    public static final String NAME_DESC_AMY = " " + PREFIX_CHILDNAME + VALID_NAME_AMY;
    public static final String NAME_DESC_AMY_PARENT = " " + PREFIX_PARENTNAME +  VALID_NAME_AMY_PARENT;
    public static final String NAME_DESC_BOB = " " + PREFIX_CHILDNAME + VALID_NAME_BOB;
    public static final String NAME_DESC_BOB_PARENT = " " + PREFIX_PARENTNAME +  VALID_NAME_BOB_PARENT;
    public static final String PHONE_DESC_AMY_PARENT = " " + PREFIX_PARENTPHONE + VALID_PHONE_AMY;
    public static final String PHONE_DESC_BOB_PARENT = " " + PREFIX_PARENTPHONE + VALID_PHONE_BOB;
    public static final String EMAIL_DESC_AMY_PARENT = " " + PREFIX_PARENTEMAIL + VALID_EMAIL_AMY;
    public static final String EMAIL_DESC_BOB_PARENT = " " + PREFIX_PARENTEMAIL + VALID_EMAIL_BOB;
    public static final String ALLERGY_DESC_PEANUTS = " " + PREFIX_ALLERGY + VALID_ALLERGY_PEANUTS;
    public static final String ALLERGY_DESC_MILK = " " + PREFIX_ALLERGY + VALID_ALLERGY_MILK;
    public static final String ALLERGY_DESC_DUST = " " + PREFIX_ALLERGY + VALID_ALLERGY_DUST;
    public static final String ADDRESS_DESC_AMY = " " + PREFIX_ADDRESS + VALID_ADDRESS_AMY;
    public static final String ADDRESS_DESC_BOB = " " + PREFIX_ADDRESS + VALID_ADDRESS_BOB;
    public static final String TAG_DESC_SINGLEPARENT = " " + PREFIX_TAG + VALID_TAG_SINGLEPARENT;
    public static final String TAG_DESC_SPECIALNEEDS = " " + PREFIX_TAG + VALID_TAG_SPECIALNEEDS;
    
    public static final String INVALID_NAME_DESC = " " + PREFIX_CHILDNAME + "James&"; // '&' not allowed in names
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PARENTPHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_PARENTEMAIL + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_ALLERGY_DESC = " " + PREFIX_ALLERGY + "peanut*"; // '*' not allowed in tags
    public static final String INVALID_ADDRESS_DESC = " " + PREFIX_ADDRESS; // empty string not allowed for addresses
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "single parent*"; // '*' not allowed in tags
    
    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";
    
    public static final EditCommand.EditPersonDescriptor DESC_AMY;
    public static final EditCommand.EditPersonDescriptor DESC_BOB;
    
    static {
        DESC_AMY = new EditPersonDescriptorBuilder().withChildName(VALID_NAME_AMY)
                .withParentName(VALID_NAME_AMY_PARENT)
                .withParentPhone(VALID_PHONE_AMY)
                .withParentEmail(VALID_EMAIL_AMY)
                .withAddress(VALID_ADDRESS_AMY)
                .withAllergies()
                .withTags(VALID_TAG_SINGLEPARENT).build();
        DESC_BOB = new EditPersonDescriptorBuilder().withChildName(VALID_NAME_BOB)
                .withParentName(VALID_NAME_BOB_PARENT)
                .withParentPhone(VALID_PHONE_BOB)
                .withParentEmail(VALID_EMAIL_BOB)
                .withAllergies()
                .withAddress(VALID_ADDRESS_BOB)
                .withTags(VALID_TAG_SINGLEPARENT, VALID_TAG_SPECIALNEEDS).build();
    }
    
    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandResult expectedCommandResult,
                                            Model expectedModel) {
        try {
            CommandResult result = command.execute(actualModel);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }
    
    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
                                            Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, expectedCommandResult, expectedModel);
    }
    
    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the address book, filtered person list and selected person in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        AddressBook expectedAddressBook = new AddressBook(actualModel.getAddressBook());
        List<Person> expectedFilteredList = new ArrayList<>(actualModel.getFilteredPersonList());
        
        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedAddressBook, actualModel.getAddressBook());
        assertEquals(expectedFilteredList, actualModel.getFilteredPersonList());
    }
    /**
     * Updates {@code model}'s filtered list to show only the person at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showPersonAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredPersonList().size());
        
        // Find the person at the target index from the filtered list (which initially is unfiltered)
        Person person = model.getFilteredPersonList().get(targetIndex.getZeroBased());
        // Filter by exact child name (and parent name for safety) to avoid matching other persons via parent names
        model.updateFilteredPersonList(p -> p.getChildName().fullName.equals(person.getChildName().fullName)
                && p.getParentName().fullName.equals(person.getParentName().fullName));
        
        assertEquals(1, model.getFilteredPersonList().size());
    }
    
}
