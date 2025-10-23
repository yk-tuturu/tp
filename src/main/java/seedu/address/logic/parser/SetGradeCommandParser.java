//package seedu.address.logic.parser;
//
//import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
//import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
//import static seedu.address.logic.parser.CliSyntax.PREFIX_CHILDNAME;
//import static seedu.address.logic.parser.CliSyntax.PREFIX_PARENTEMAIL;
//import static seedu.address.logic.parser.CliSyntax.PREFIX_PARENTPHONE;
//import static seedu.address.logic.parser.CliSyntax.PREFIX_SCORE;
//import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBJECT;
//
//import java.util.Set;
//import java.util.stream.Stream;
//
//import seedu.address.commons.core.index.Index;
//import seedu.address.logic.commands.SetGradeCommand;
//import seedu.address.logic.commands.UnenrollCommand;
//import seedu.address.logic.parser.exceptions.ParseException;
//import seedu.address.model.subject.Subject;
//
//public class SetGradeCommandParser implements Parser {
//    /**
//     * Parses the given {@code String} of arguments in the context of the EnrolCommand
//     * and returns a EnrolCommand object for execution.
//     * @throws ParseException if the user input does not conform the expected format
//     */
//    public SetGradeCommand parse(String args) throws ParseException {
//        ArgumentMultimap argMultimap =
//                ArgumentTokenizer.tokenize(args, PREFIX_SUBJECT, PREFIX_SCORE);
//
//        if (!arePrefixesPresent(argMultimap, PREFIX_SUBJECT, PREFIX_SCORE)
//                || argMultimap.getPreamble().isEmpty()) {
//            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnenrollCommand.MESSAGE_USAGE));
//        }
//
//        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_SUBJECT, PREFIX_SCORE);
//
//        Index[] indices;
//        Subject subject;
//        int score;
//        boolean setAll = false;
//
//        try {
//            String preamble = argMultimap.getPreamble();
//            if (ParserUtil.checkIsAll(preamble)) {
//                setAll = true;
//                indices = new Index[0];
//            } else {
//                indices = ParserUtil.parseIndexArray(preamble);
//            }
//            subject = ParserUtil.parseSubject(argMultimap.getValue(PREFIX_SUBJECT).get());
//            score = ParserUtil.parseScore(argMultimap.getValue(PREFIX_SCORE).get());
//        } catch (ParseException pe) {
//            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetGradeCommand.MESSAGE_USAGE), pe);
//        }
//
//        return new SetGradeCommand(indices, setAll, subject, score);
//    }
//
//    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
//        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
//    }
//}
