// Part of the code is adapted from original AB3 Code. All credits and thanks to the original
// CS2103T teaching team for this.
package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ROLE_HEAD;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AssignCommand;

public class AssignCommandParserTest {

    private AssignCommandParser parser = new AssignCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_ROLE_HEAD, MESSAGE_INVALID_PERSON_DISPLAYED_INDEX
            + "\n" + AssignCommand.MESSAGE_USAGE);

        // no role specified
        assertParseFailure(parser, "1", AssignCommand.MESSAGE_NOT_ASSIGNED
            + "\n" + AssignCommand.MESSAGE_USAGE);

        // no index and no role specified
        assertParseFailure(parser, "", MESSAGE_INVALID_PERSON_DISPLAYED_INDEX
            + "\n" + AssignCommand.MESSAGE_USAGE);
    }

}
