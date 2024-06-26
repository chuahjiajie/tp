package seedu.address.model.attendance;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents an Sessions in the address book.
 * Guarantees: immutable; attendance is valid as declared in {@link #isValidSessions(String)}
 */
public class Sessions {

    public static final String MESSAGE_CONSTRAINTS = "Sessions should be a non-negative integer";
    public static final String VALIDATION_REGEX = "[0-9]+";

    public final String value;

    /**
     * Constructs a {@code Sessions}.
     *
     * @param sessions A valid session number.
     */
    public Sessions(String sessions) {
        requireNonNull(sessions);
        checkArgument(isValidSessions(sessions), MESSAGE_CONSTRAINTS);
        this.value = sessions;
    }

    public int getValue() {
        return Integer.parseInt(value);
    }

    /**
     * Returns true if a given string is a valid session number.
     */
    public static boolean isValidSessions(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Sessions)) {
            return false;
        }

        Sessions otherSess = (Sessions) other;
        return value.equals(otherSess.value);
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return value;
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
