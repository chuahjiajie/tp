package seedu.address.model.person;

import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.cca.Cca;
import seedu.address.model.roles.Role;


/**
 * Tests that a {@code Person}'s {@code Cca} matches any of the keywords given.
 */
public class CcaContainsKeywordPredicate implements Predicate<Person> {
    private final Set<Cca> ccas;
    private final Optional<Set<Role>> roles;

    /**
     * Returns a CcaContainsKeywordsPredicate object by taking a list of the Cca names.
     */
    public CcaContainsKeywordPredicate(Set<Cca> keywords, Optional<Set<Role>> roles) {

        this.ccas = keywords;
        this.roles = roles;
    }

    public boolean contains(Cca cca) {
        return ccas.contains(cca);
    }

    public Set<Cca> getCcas() {
        return ccas;
    }

    public Optional<Set<Role>> getRoles() {
        return roles;
    }

    @Override
    public boolean test(Person person) {
        Set<Cca> personCcas = person.getCcas();
        Set<Role> personRoles = person.getRoles();
        boolean matchesCCa = ccas.stream()
                .anyMatch(personCcas::contains);
        boolean matchesRole = this.roles
                .map(realRoles -> realRoles.stream().anyMatch(personRoles::contains))
                .orElse(true);
        return matchesCCa && matchesRole;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CcaContainsKeywordPredicate)) {
            return false;
        }

        CcaContainsKeywordPredicate otherNameContainsKeywordsPredicate = (CcaContainsKeywordPredicate) other;
        return ccas.equals(otherNameContainsKeywordsPredicate.ccas)
                && roles.equals(otherNameContainsKeywordsPredicate.roles);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("ccas", ccas)
                .add("roles", roles)
                .toString();
    }
}
