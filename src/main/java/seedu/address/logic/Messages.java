package seedu.address.logic;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.parser.Prefix;
import seedu.address.model.cca.Cca;
import seedu.address.model.person.CcaContainsKeywordPredicate;
import seedu.address.model.person.Metadata;
import seedu.address.model.person.Person;
import seedu.address.model.roles.Role;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_PERSON_DISPLAYED_INDEX = "The person index provided is invalid";
    public static final String MESSAGE_PERSONS_LISTED_OVERVIEW = "%1$d persons listed!";
    public static final String MESSAGE_DUPLICATE_FIELDS =
                "Multiple values specified for the following single-valued field(s): ";

    /**
     * Returns an error message indicating the duplicate prefixes.
     */
    public static String getErrorMessageForDuplicatePrefixes(Prefix... duplicatePrefixes) {
        assert duplicatePrefixes.length > 0;

        Set<String> duplicateFields =
                Stream.of(duplicatePrefixes).map(Prefix::toString).collect(Collectors.toSet());

        return MESSAGE_DUPLICATE_FIELDS + String.join(" ", duplicateFields);
    }

    /**
     * Formats the {@code person} for display to the user.
     */
    public static String format(Person person) {
        final StringBuilder builder = new StringBuilder();
        builder.append(person.getName())
                .append("; Phone: ")
                .append(person.getPhone())
                .append("; Email: ")
                .append(person.getEmail())
                .append("; Address: ")
                .append(person.getAddress())
                .append("; Roles: ");
        person.getRoles().forEach(builder::append);
        builder.append("; CCA: ");
        person.getCcas().forEach(builder::append);
        builder.append("; Amount: ")
                .append(person.getAmount());
        builder.append("; Attendance: ")
                .append(person.getAtt())
                .append("/")
                .append(person.getSess());
        builder.append("; Metadata: ")
                .append(person.getMetadata()
                    .map(m -> m.metadata).orElse(Metadata.NO_METADATA_STRING));
        return builder.toString();
    }

    /**
     * Formats the {@code CcaContainsKeywordPredicate} for display to the user.
     */
    public static String format(CcaContainsKeywordPredicate p) {
        return "["
            + "CCAs: " + p.getCcas().stream().map(Messages::format).collect(Collectors.joining(", "))
            + p.getRoles().map(rs ->
                ", Roles: "
                + rs.stream().map(Messages::format).collect(Collectors.joining(", ")))
                    .orElse("")
            + "]";
    }

    /**
     * Formats the {@code Cca} for display to the user.
     */
    public static String format(Cca c) {
        return c.ccaName;
    }

    /**
     * Formats the {@code Role} for display to the user.
     */
    public static String format(Role r) {
        return r.roleName;
    }
}
