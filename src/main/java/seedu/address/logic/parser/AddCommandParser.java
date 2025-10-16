package seedu.address.logic.parser;
// TODO: add in the new imports from CliSyntax and use them
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ALLERGY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CHILDNAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PARENTEMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PARENTNAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PARENTPHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;
import java.util.stream.Stream;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.AllergyList;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_CHILDNAME, PREFIX_PARENTNAME, PREFIX_PARENTPHONE,
                        PREFIX_PARENTEMAIL, PREFIX_ADDRESS, PREFIX_ALLERGY, PREFIX_TAG);

        if (!arePrefixesPresent(argMultimap, PREFIX_CHILDNAME, PREFIX_ADDRESS, PREFIX_PARENTPHONE, PREFIX_PARENTEMAIL)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_CHILDNAME,
                                                 PREFIX_PARENTPHONE,
                                                 PREFIX_PARENTEMAIL,
                                                 PREFIX_ADDRESS);
        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_CHILDNAME).get());
        Name parentName = ParserUtil.parseName(argMultimap.getValue(PREFIX_PARENTNAME).get());
        Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PARENTPHONE).get());
        Email email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_PARENTEMAIL).get());
        Address address = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get());
        AllergyList allergies = ParserUtil.parseAllergies(argMultimap.getAllValues(PREFIX_ALLERGY));
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

        // TODO: For Add command person to fix
        Person person = new Person(name, parentName, phone, email, allergies, address, tagList);

        return new AddCommand(person);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
