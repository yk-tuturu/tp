package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SUBJECT_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SUBJECT_2_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SCORE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_SUBJECT_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_SCORE_DESC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBJECT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.SetScoreCommand;
import seedu.address.model.subject.Subject;

public class SetScoreCommandParserTest {

    private SetScoreCommandParser parser = new SetScoreCommandParser();

    @Test
    public void parse_oneStudent_validArgs_returnsSetScoreCommand() {
        String userInput = "1 " + VALID_SUBJECT_DESC + VALID_SCORE_DESC;

        Index[] expectedIndexes = new Index[] { INDEX_FIRST_PERSON };
        Subject expectedSubject = Subject.MATH;
        int expectedScore = 100;

        SetScoreCommand expectedCommand = new SetScoreCommand(expectedIndexes, false, expectedSubject, expectedScore);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleStudents_validArgs_returnsSetScoreCommand() {
        String userInput = "1 2 3 " + VALID_SUBJECT_DESC + VALID_SCORE_DESC;

        Index[] expectedIndexes = new Index[] {
                INDEX_THIRD_PERSON,
                INDEX_SECOND_PERSON,
                INDEX_FIRST_PERSON
        };
        Subject expectedSubject = Subject.MATH;
        int expectedScore = 100;

        SetScoreCommand expectedCommand = new SetScoreCommand(expectedIndexes, false, expectedSubject, expectedScore);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_allStudents_validArgs_returnsSetScoreCommand() {
        String userInput = "all " + VALID_SUBJECT_DESC + VALID_SCORE_DESC;

        Index[] expectedIndexes = new Index[0];
        Subject expectedSubject = Subject.MATH;
        int expectedScore = 100;

        SetScoreCommand expectedCommand = new SetScoreCommand(expectedIndexes, true, expectedSubject, expectedScore);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_withWhitespace_returnsSetScoreCommand() {
        String userInput = PREAMBLE_WHITESPACE + "1 2 " + VALID_SUBJECT_DESC + VALID_SCORE_DESC;

        Index[] expectedIndexes = new Index[] {
                INDEX_SECOND_PERSON,
                INDEX_FIRST_PERSON
        };
        Subject expectedSubject = Subject.MATH;
        int expectedScore = 100;

        SetScoreCommand expectedCommand = new SetScoreCommand(expectedIndexes, false, expectedSubject, expectedScore);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_duplicatePrefixes_throwsParseException() {
        String userInput = "1 " + VALID_SUBJECT_DESC + VALID_SUBJECT_2_DESC + VALID_SCORE_DESC;

        assertParseFailure(parser, userInput,
                String.format(Messages.getErrorMessageForDuplicatePrefixes(PREFIX_SUBJECT)));
    }

    @Test
    public void parse_invalidSubject_throwsParseException() {
        String userInput = "1 " + INVALID_SUBJECT_DESC + VALID_SCORE_DESC;
        assertParseFailure(parser, userInput,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetScoreCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidScore_throwsParseException() {
        String userInput = "1 " + VALID_SUBJECT_DESC + INVALID_SCORE_DESC;
        assertParseFailure(parser, userInput,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetScoreCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingSubject_throwsParseException() {
        String userInput = "1 " + VALID_SCORE_DESC;
        assertParseFailure(parser, userInput,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetScoreCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingScore_throwsParseException() {
        String userInput = "1 " + VALID_SUBJECT_DESC;
        assertParseFailure(parser, userInput,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetScoreCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_emptyInput_throwsParseException() {
        assertParseFailure(parser, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetScoreCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_whitespaceOnly_throwsParseException() {
        assertParseFailure(parser, PREAMBLE_WHITESPACE,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetScoreCommand.MESSAGE_USAGE));
    }
}
