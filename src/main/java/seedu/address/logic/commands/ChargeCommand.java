// Part of the code is adpatated from original AB3 Code. All credits and thanks to the original
// CS2103T teaching team for this.
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CCA;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.amount.Amount;
import seedu.address.model.person.CcaContainsKeywordPredicate;
import seedu.address.model.person.Person;


/**
 * Owes the details of the person identified using the displayed index from the address book.
 */
public class ChargeCommand extends Command {
    public static final String COMMAND_WORD = "charge";

    // MESSAGE_USAGE below is modified from my Teammate's (AlphaJae) code
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds a certain amount to how much all matching CCA + optional role members owe. "
            + "Parameters: "
            + PREFIX_AMOUNT + "AMOUNT "
            + "[" + PREFIX_CCA + "CCA]... "
            + "[" + PREFIX_ROLE + "ROLE]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_AMOUNT + "10.00 "
            + PREFIX_CCA + "NUS Cycling "
            + PREFIX_ROLE + "friends";

    public static final String MESSAGE_NO_AMOUNT = "An amount should be provided.";
    public static final String MESSAGE_DO_NOTHING =
        "Nobody was charged as nobody is in any of the following CCAs: [%s] "
        + "AND in any of the following roles: [%s]";
    private final Amount amount;
    private final CcaContainsKeywordPredicate ccas;

    /**
     * @param amount The amount to charge.
     * @param ccas The matching CCA and roles you want to charge.
     */
    public ChargeCommand(Amount amount, CcaContainsKeywordPredicate ccas) {
        requireNonNull(amount);
        requireNonNull(ccas);
        this.amount = amount;
        this.ccas = ccas;
    }

    public Amount getAmount() {
        return amount;
    }

    // Solution below (equals method) is adapted from original AB3 author lzq.
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ChargeCommand // instanceof handles nulls
                && ccas.equals(((ChargeCommand) other).ccas)
                && amount.equals(((ChargeCommand) other).amount));
    }
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> oldList = model.getFilteredPersonList().stream().collect(Collectors.toList());
        model.updateFilteredPersonList(this.ccas);
        List<Person> lastShownList = model.getFilteredPersonList();
        if (lastShownList.isEmpty()) {
            model.updateFilteredPersonList(oldList::contains);
            Collector<CharSequence, ?, String> join = Collectors.joining(", ");
            throw new CommandException(String.format(MESSAGE_DO_NOTHING,
                ccas.getCcas().stream().map(c -> c.ccaName).collect(join),
                ccas.getRoles().map(rs -> rs.stream().map(r -> r.roleName).collect(join)).orElse("")));
        }

        StringBuilder result = new StringBuilder();
        for (Person personToOwe : lastShownList) {
            Amount oldAmount = personToOwe.getAmount();
            Amount newAmount = oldAmount.deduct(this.amount);
            Person owedPerson = OweCommand.createOwedPerson(personToOwe, newAmount);
            model.setPerson(personToOwe, owedPerson);
            result.append(String.format("Owed Person: $%s\n", owedPerson.getAmount().toString()));
        }
        return new CommandResult(result.toString());
    }

    @Override
    public String toString() {
        return new StringBuilder().append("ChargeCommand{ ")
                .append("amount: ").append(amount).append(", ")
                .append("cca&roles: ").append(ccas)
                .append("}").toString();
    }
}
