// Part of the code is adpatated from original AB3 Code. All credits and thanks to the original
// CS2103T teaching team for this.
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CCA;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.cca.Cca;
import seedu.address.model.person.CcaContainsKeywordPredicate;
import seedu.address.model.person.Person;


/**
 * Owes the details of the person identified using the displayed index from the address book.
 */
public class DeleteCcaCommand extends Command {
    public static final String COMMAND_WORD = "cca_delete";

    // MESSAGE_USAGE below is modified from my Teammate's (AlphaJae) code
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes a CCA and all its associated members from your contacts. "
            + "Parameters: "
            + PREFIX_CCA + "CCA\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_CCA + "NUS Cycling ";

    public static final String MESSAGE_NO_CCA = "A CCA should be provided.";
    public static final String MESSAGE_NONEXISTANT_CCA = "No CCA was deleted because "
        + "the CCA `%s` does not exists.";
    private final CcaContainsKeywordPredicate ccas;

    /**
     * @param ccas The matching CCA to delete.
     */
    public DeleteCcaCommand(CcaContainsKeywordPredicate ccas) {
        requireNonNull(ccas);
        this.ccas = ccas;
    }


    // Solution below (equals method) is adapted from original AB3 author lzq.
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteCcaCommand // instanceof handles nulls
                && ccas.equals(((DeleteCcaCommand) other).ccas));
    }
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        // Check if any CCA does not exists
        List<Cca> ccalist = model.getAddressBook().getCcaList();
        List<Cca> nonexistantCcas = this.ccas.getCcas().stream()
            .filter(c1 -> !ccalist.stream().anyMatch(c1::isSameCcaName))
            .collect(Collectors.toList());
        if (!nonexistantCcas.isEmpty()) {
            throw new CommandException(String.format(MESSAGE_NONEXISTANT_CCA,
                nonexistantCcas.stream().map(c -> c.ccaName).collect(Collectors.joining(", "))));
        }

        model.updateFilteredPersonList(this.ccas);
        StringBuilder result = new StringBuilder();
        result.append(String.format("Deleting CCA(s) %s tags from all its members:\n",
            this.ccas.getCcas().stream().map(c -> c.ccaName).collect(Collectors.joining(", "))));

        // We have to essentially clone the list
        // because as `model.setPerson` is called,
        // the ObservableList gets updated.
        // This causes some people to be skipped.
        List<Person> affectedPeople = model
            .getFilteredPersonList()
            .stream()
            .collect(Collectors.toList());

        // Delete their roles
        affectedPeople
            .forEach(affectedPerson -> {
                Set<Cca> updatedCca = affectedPerson
                    .getCcas()
                    .stream()
                    .filter(c -> !ccas.contains(c))
                    .collect(Collectors.toSet());
                model.setPerson(affectedPerson, affectedPerson.replaceCca(updatedCca));
                result.append(String.format("Person affected: %s\n", affectedPerson.getName()));
            });

        // Update filteredlist to display the same people
        model.updateFilteredPersonList(p -> affectedPeople
            .stream()
            .anyMatch(p::isSamePerson)
        );

        return new CommandResult(result.toString());
    }

    @Override
    public String toString() {
        return new StringBuilder().append("DeleteCcaCommand{ ")
                .append("cca: ").append(ccas)
                .append("}").toString();
    }
}
