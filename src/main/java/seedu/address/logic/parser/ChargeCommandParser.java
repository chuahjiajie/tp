// This file is adapted from FindCommandParser.java. All credits and thanks to the original
// CS2103T teaching team for this.
package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CCA;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import seedu.address.logic.commands.ChargeCommand;
import seedu.address.logic.commands.FilterCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.amount.Amount;
import seedu.address.model.cca.Cca;
import seedu.address.model.person.CcaContainsKeywordPredicate;
import seedu.address.model.roles.Role;

/**
 * Parses input arguments and creates a new FilterCommand object
 */
public class ChargeCommandParser implements Parser<ChargeCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FilterCommand
     * and returns a FilterCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public ChargeCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argumentMultimap = ArgumentTokenizer.tokenize(args, PREFIX_AMOUNT, PREFIX_CCA, PREFIX_ROLE);

        if (argumentMultimap.getValue(PREFIX_AMOUNT).isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ChargeCommand.MESSAGE_NO_AMOUNT
                    + "\n" + ChargeCommand.MESSAGE_USAGE)
            );
        }

        if (argumentMultimap.getValue(PREFIX_CCA).isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_NOT_FILTER_CCA
                    + "\n" + ChargeCommand.MESSAGE_USAGE)
            );
        }

        Set<Cca> ccas = new HashSet<>(ParserUtil.parseCcas(argumentMultimap.getAllValues(PREFIX_CCA)));

        Optional<Set<Role>> roles = argumentMultimap.getAllValues(PREFIX_ROLE).isEmpty()
                ? Optional.empty()
                : Optional.of(new HashSet<>(ParserUtil.parseRoles(argumentMultimap.getAllValues(PREFIX_ROLE))));

        CcaContainsKeywordPredicate predicate = new CcaContainsKeywordPredicate(ccas, roles);
        Amount amt;
        try {
            amt = new Amount(argumentMultimap.getValue(PREFIX_AMOUNT).get());
        } catch (IllegalArgumentException e) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, e.getMessage())
                + "\n" + ChargeCommand.MESSAGE_USAGE);
        }
        return new ChargeCommand(amt, predicate);
    }

}
