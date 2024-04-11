package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ATTENDANCE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SESSIONS;

import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.SetAttCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SetAttCommand Object
 */
public class SetAttCommandParser implements Parser<SetAttCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the SetAttCommand
     * and returns an SetAttCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public SetAttCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_ATTENDANCE, PREFIX_SESSIONS);

        if (!arePrefixesPresent(argMultimap, PREFIX_ATTENDANCE, PREFIX_SESSIONS)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetAttCommand.MESSAGE_USAGE));
        }
        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(MESSAGE_INVALID_PERSON_DISPLAYED_INDEX
                + "\n" + SetAttCommand.MESSAGE_USAGE, pe);
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_ATTENDANCE, PREFIX_SESSIONS);

        SetAttCommand.SetAttDescriptor setAttDescriptor = new SetAttCommand.SetAttDescriptor();

        setAttDescriptor.setAtt(ParserUtil.parseAtt(argMultimap.getValue(PREFIX_ATTENDANCE).get()));
        setAttDescriptor.setSess(ParserUtil.parseSess(argMultimap.getValue(PREFIX_SESSIONS).get()));

        int attendanceInt = setAttDescriptor.getAtt().get().getValue();
        int sessionsInt = setAttDescriptor.getSess().get().getValue();

        if (attendanceInt > sessionsInt) {
            throw new ParseException(SetAttCommand.MESSAGE_ATT_TOO_LARGE
                + "\n" + SetAttCommand.MESSAGE_USAGE);
        }

        return new SetAttCommand(index, setAttDescriptor);
    }

    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
