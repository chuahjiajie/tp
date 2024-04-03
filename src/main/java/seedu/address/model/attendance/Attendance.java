package seedu.address.model.attendance;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents an Attendance in the address book.
 * Guarantees: immutable; attendance is valid as declared in {@link #isValidAttendance(String)}
 */
public class Attendance {

    public static final String MESSAGE_CONSTRAINTS = "Attendance should be a non-negative integer";
    public static final String MESSAGE_LESS_THAN_CONSTRAINT = "Attendance should be less than sessions";
    public static final String VALIDATION_REGEX = "[0-9]+";

    public final String value;

    /**
     * Constructs a {@code Attendance}.
     *
     * @param attendance A valid attendance number.
     */
    public Attendance(String attendance) {
        requireNonNull(attendance);
        checkArgument(isValidAttendance(attendance), MESSAGE_CONSTRAINTS);
        this.value = attendance;
    }

    public int getValue() {
        return Integer.parseInt(value);
    }

    /**
     * Returns true if a given string is a valid attendance number.
     */
    public static boolean isValidAttendance(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Attendance)) {
            return false;
        }

        Attendance otherAtt = (Attendance) other;
        return value == otherAtt.value;
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
