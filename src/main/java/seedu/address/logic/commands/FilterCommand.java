// This file is adapted from FindCommand.java. All credits and thanks to the original
// CS2103T teaching team for this.
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CCA;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.person.CcaContainsKeywordPredicate;

/**
 * Filters all persons in address book by their cca (case-sensitive).
 * Serves the purpose of grouping people together..
 */
public class FilterCommand extends Command {

    public static final String COMMAND_WORD = "filter";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Filters a person by their CCA "
            + "(case-sensitive) and optionally their roles. "
            + "Exact word matches are required. "
            + "Displays them as a list with index numbers.\n"
            + "Parameters: "
            + "[" + PREFIX_CCA + "CCA]... "
            + "[" + PREFIX_ROLE + "ROLE]...\n"
            + "Example: " + COMMAND_WORD + " c/NUS Cycling r/Member";

    public static final String MESSAGE_NOT_FILTER_CCA = "A CCA should be provided.";
    public static final String MESSAGE_ROLE_EMPTY = "Role field is empty.";

    private final CcaContainsKeywordPredicate cca;

    public FilterCommand(CcaContainsKeywordPredicate cca) {
        this.cca = cca;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(cca);
        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FilterCommand)) {
            return false;
        }

        FilterCommand otherFilterCommand = (FilterCommand) other;
        return cca.equals(otherFilterCommand.cca);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("cca", cca)
                .toString();
    }
}
