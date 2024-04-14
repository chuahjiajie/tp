// This file is adapted from FindCommandParser.java. All credits and thanks to the original
// CS2103T teaching team for this.
package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CCA;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import seedu.address.logic.commands.DeleteCcaCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.cca.Cca;
import seedu.address.model.person.CcaContainsKeywordPredicate;

/**
 * Parses input arguments and creates a new FilterCommand object
 */
public class DeleteCcaCommandParser implements Parser<DeleteCcaCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FilterCommand
     * and returns a FilterCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public DeleteCcaCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argumentMultimap = ArgumentTokenizer.tokenize(args, PREFIX_CCA);


        if (argumentMultimap.getValue(PREFIX_CCA).isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    DeleteCcaCommand.MESSAGE_NO_CCA
                    + "\n" + DeleteCcaCommand.MESSAGE_USAGE)
            );
        }

        argumentMultimap.verifyNoDuplicatePrefixesFor(PREFIX_CCA);

        Set<Cca> ccas = new HashSet<>(ParserUtil.parseCcas(argumentMultimap.getAllValues(PREFIX_CCA)));

        CcaContainsKeywordPredicate predicate = new CcaContainsKeywordPredicate(ccas, Optional.empty());
        return new DeleteCcaCommand(predicate);
    }

}
