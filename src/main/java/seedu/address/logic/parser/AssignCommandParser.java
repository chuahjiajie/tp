// Part of the code is adapted from original AB3 Code. All credits and thanks to the original
// CS2103T teaching team for this.
package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AssignCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.roles.Role;

/**
 * Parses input arguments and creates a new AssignCommand Object
 */
public class AssignCommandParser implements Parser<AssignCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the AssignCommand
     * and returns an AssignCommand object for execution.
     * @throws ParseException  if the user input does not conform the expected format
     */
    @Override
    public AssignCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_ROLE);

        Index index;

        // Make sure all roles are not empty
        if (argMultimap.getAllValues(PREFIX_ROLE).stream().anyMatch(String::isEmpty)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AssignCommand.MESSAGE_USAGE));
        }

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(MESSAGE_INVALID_PERSON_DISPLAYED_INDEX
                + "\n" + AssignCommand.MESSAGE_USAGE, pe);
        }

        AssignCommand.AssignPersonDescriptor assignPersonDescriptor = new AssignCommand.AssignPersonDescriptor();

        parseRolesForAssign(argMultimap.getAllValues(PREFIX_ROLE)).ifPresent(assignPersonDescriptor::setRole);

        if (!assignPersonDescriptor.isAnyFieldNotEdited()) {
            throw new ParseException(AssignCommand.MESSAGE_NOT_ASSIGNED
                + "\n" + AssignCommand.MESSAGE_USAGE);
        }

        return new AssignCommand(index, assignPersonDescriptor);

    }

    /**
     * Parses {@code Collection<String> roles} into a {@code Set<Role>} if {@code roles} is non-empty.
     * If {@code roles} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Role>} containing zero roles.
     */
    private Optional<Set<Role>> parseRolesForAssign(Collection<String> roles) throws ParseException {
        assert roles != null;

        if (roles.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> roleSet = roles.size() == 1 && roles.contains("") ? Collections.emptySet() : roles;
        return Optional.of(ParserUtil.parseRoles(roleSet));
    }
}
