package seedu.address.model.CCA;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a CCA in the address book.
 * Guarantees: immutable; name is valid as declared in {@link #isValidTagName(String)}
 */
public class CCA {

    public static final String MESSAGE_CONSTRAINTS = "CCA names should be alphanumeric (but can include " +
            "whitespace)";
    public static final String VALIDATION_REGEX = "[\\p{Alnum}\\s]+";

    public final String CCAName;

    /**
     * Constructs a {@code Tag}.
     *
     * @param CCAName A valid tag name.
     */
    public CCA(String CCAName) {
        requireNonNull(CCAName);
        checkArgument(isValidTagName(CCAName), MESSAGE_CONSTRAINTS);
        this.CCAName = CCAName;
    }

    /**
     * Returns true if a given string is a valid tag name.
     */
    public static boolean isValidTagName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CCA)) {
            return false;
        }

        CCA otherTag = (CCA) other;
        return CCAName.equals(otherTag.CCAName);
    }

    @Override
    public int hashCode() {
        return CCAName.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return "[CCA: " + CCAName + ']';
    }

}
