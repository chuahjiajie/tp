// This file is adapted from FindCommand.java. All credits and thanks to the original
// CS2103T teaching team for this.
package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.cca.Cca;
import seedu.address.model.person.CcaContainsKeywordPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FilterCommand}.
 */
public class FilterCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        Set<Cca> firstPredicateKeywordList = Arrays
                .asList("first")
                .stream()
                .map(Cca::new)
                .collect(Collectors.toSet());
        Set<Cca> secondPredicateKeywordList = Arrays
                .asList("second")
                .stream()
                .map(Cca::new)
                .collect(Collectors.toSet());
        CcaContainsKeywordPredicate firstPredicate =
                new CcaContainsKeywordPredicate(firstPredicateKeywordList, Optional.empty());
        CcaContainsKeywordPredicate secondPredicate =
                new CcaContainsKeywordPredicate(secondPredicateKeywordList, Optional.empty());

        FilterCommand findFirstCommand = new FilterCommand(firstPredicate);
        FilterCommand findSecondCommand = new FilterCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FilterCommand findFirstCommandCopy = new FilterCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        CcaContainsKeywordPredicate predicate = preparePredicate("hi");
        FilterCommand command = new FilterCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_multipleKeywords_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 4);
        CcaContainsKeywordPredicate predicate = preparePredicate("NUS Bakes, NUS Cycling");
        FilterCommand command = new FilterCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE, BENSON, CARL, DANIEL), model.getFilteredPersonList());
    }

    @Test
    public void toStringMethod() {
        Set<Cca> predicateKeywordsList = Arrays
                .asList("friends")
                .stream()
                .map(Cca::new)
                .collect(Collectors.toSet());
        CcaContainsKeywordPredicate predicate = new CcaContainsKeywordPredicate(
                predicateKeywordsList,
                Optional.empty()
        );
        FilterCommand filterCommand = new FilterCommand(predicate);
        String expected = FilterCommand.class.getCanonicalName() + "{cca=" + predicate + "}";
        assertEquals(expected, filterCommand.toString());
    }

    /**
     * Parses {@code userInput} into a {@code CcaContainsKeywordPredicate}.
     */
    private CcaContainsKeywordPredicate preparePredicate(String userInput) {
        return new CcaContainsKeywordPredicate(Arrays.stream(userInput.split(","))
                .map(String::trim).map(Cca::new).collect(Collectors.toSet()), Optional.empty());
    }
}
