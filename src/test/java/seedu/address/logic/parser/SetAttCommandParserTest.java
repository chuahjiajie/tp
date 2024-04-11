package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
import static seedu.address.logic.commands.CommandTestUtil.ATTENDANCE_DESC_DEFAULT;
import static seedu.address.logic.commands.CommandTestUtil.SESSIONS_DESC_DEFAULT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.SetAttCommand;

public class SetAttCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetAttCommand.MESSAGE_USAGE);

    private SetAttCommandParser parser = new SetAttCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, ATTENDANCE_DESC_DEFAULT + SESSIONS_DESC_DEFAULT,
            MESSAGE_INVALID_PERSON_DISPLAYED_INDEX
            + "\n" + SetAttCommand.MESSAGE_USAGE);

        // no role specified
        assertParseFailure(parser, "1", MESSAGE_INVALID_FORMAT);

        // no index and no role specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

}
