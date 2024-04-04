// This file is adapted from NameContainsKeywordsPredicateTest.java. Full credits to the original
// authors and CS2103T teaching team.
package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.model.cca.Cca;
import seedu.address.testutil.PersonBuilder;

public class RoleContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        Set<Cca> firstPredicateKeywordList = Arrays
                .asList("first")
                .stream()
                .map(Cca::new)
                .collect(Collectors.toSet());
        Set<Cca> secondPredicateKeywordList = Arrays
                .asList("first", "second")
                .stream()
                .map(Cca::new)
                .collect(Collectors.toSet());

        CcaContainsKeywordPredicate firstPredicate = new CcaContainsKeywordPredicate(
                firstPredicateKeywordList,
                Optional.empty()
        );
        CcaContainsKeywordPredicate secondPredicate = new CcaContainsKeywordPredicate(
                secondPredicateKeywordList,
                Optional.empty()
        );

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        CcaContainsKeywordPredicate firstPredicateCopy = new CcaContainsKeywordPredicate(
                firstPredicateKeywordList,
                Optional.empty()
        );
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_ccaContainsKeywords_returnsTrue() {
        // One keyword
        Set<Cca> firstPredicateKeywordList = Arrays
                .asList("friends")
                .stream()
                .map(Cca::new)
                .collect(Collectors.toSet());
        CcaContainsKeywordPredicate predicate = new CcaContainsKeywordPredicate(
                firstPredicateKeywordList,
                Optional.empty()
        );
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").withCcas("friends").build()));

        // Multiple keywords
        firstPredicateKeywordList = Arrays
                .asList("friends", "classmates")
                .stream()
                .map(Cca::new)
                .collect(Collectors.toSet());
        predicate = new CcaContainsKeywordPredicate(firstPredicateKeywordList, Optional.empty());
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").withCcas("friends").build()));

        // Only one matching keyword
        predicate = new CcaContainsKeywordPredicate(firstPredicateKeywordList, Optional.empty());
        assertTrue(predicate.test(
                new PersonBuilder().withName("Alice Carol").withCcas("friends", "classmates").build()));
    }

    @Test
    public void test_ccaDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        CcaContainsKeywordPredicate predicate = new CcaContainsKeywordPredicate(
                Collections.emptySet(),
                Optional.empty()
        );
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").build()));

        // Non-matching keyword
        Set<Cca> firstPredicateKeywordList = Arrays
                .asList("Friends")
                .stream()
                .map(Cca::new)
                .collect(Collectors.toSet());
        predicate = new CcaContainsKeywordPredicate(firstPredicateKeywordList, Optional.empty());
        assertFalse(predicate.test(new PersonBuilder().withName("Alice Bob").withRoles("Friend").build()));

    }

    @Test
    public void toStringMethod() {
        Set<Cca> keywords = Arrays
                .asList("keyword1", "keyword2")
                .stream()
                .map(Cca::new)
                .collect(Collectors.toSet());
        CcaContainsKeywordPredicate predicate = new CcaContainsKeywordPredicate(keywords, Optional.empty());

        String expected = CcaContainsKeywordPredicate.class.getCanonicalName() + "{ccas="
                + keywords + ", roles=" + Optional.empty() + "}";
        assertEquals(expected, predicate.toString());
    }
}
