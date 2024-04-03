package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ATTENDANCE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SESSIONS;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.SetAttCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.attendance.Attendance;
import seedu.address.model.roles.Role;

/**
 * Parses input arguments and creates a new SetAttCommand Object
 */
public class SetAttCommandParser implements Parser<SetAttCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the SetAttCommand
     * and returns an SetAttCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public SetAttCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_ATTENDANCE, PREFIX_SESSIONS);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetAttCommand.MESSAGE_USAGE), pe);
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_ATTENDANCE, PREFIX_SESSIONS);

        SetAttCommand.SetAttDescriptor setAttDescriptor = new SetAttCommand.SetAttDescriptor();

        setAttDescriptor.setAtt();

        parseRolesForAssign(argMultimap.getAllValues(PREFIX_ROLE)).ifPresent(assignPersonDescriptor::setRole);

        System.out.println(assignPersonDescriptor.toString());

        if (!assignPersonDescriptor.isAnyFieldNotEdited()) {
            throw new ParseException(AssignCommand.MESSAGE_NOT_ASSIGNED);
        }

        return new AssignCommand(index, assignPersonDescriptor);

    }
}
