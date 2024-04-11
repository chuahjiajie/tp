// This file is adapted from FindCOmmandParserTest.java. Full credits to the original
// authors and CS2103T teaching team.
package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FilterCommand;
import seedu.address.model.cca.Cca;
import seedu.address.model.person.CcaContainsKeywordPredicate;

public class FilterCommandParserTest {

    private FilterCommandParser parser = new FilterCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_NOT_FILTER_CCA
                    + "\n" + FilterCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFilterCommand() {
        // no leading and trailing whitespaces
        Set<Cca> keywords = Arrays
                .asList("Alice", "Bob")
                .stream()
                .map(Cca::new)
                .collect(Collectors.toSet());
        FilterCommand expectedFilterCommand =
                new FilterCommand(new CcaContainsKeywordPredicate(keywords, Optional.empty()));
        assertParseSuccess(parser, " c/Alice c/Bob", expectedFilterCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n c/Alice \n \t c/Bob  \t", expectedFilterCommand);
    }

}
