// Part of the code is adpatated from original AB3 Code. All credits and thanks to the original
// CS2103T teaching team for this.
package seedu.address.model.amount;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.math.BigDecimal;

/**
 * Represents an Amount in the address book.
 * is valid as declared in {@link #isValidAmount(String)}
 */
public class Amount {
    public static final String MESSAGE_CONSTRAINTS = "Amount should only contain correct numbers "
            + "with no more than 2 decimal places\n"
            + "and it should not be blank or negative";
    public static final String MESSAGE_NUMBER_CONSTRAINTS = "Amount should not be negative";
    private static final String AMOUNT_VALIDATION_REGEX = "(?!0\\d)\\d+(\\.\\d{1,2})?";

    public final BigDecimal value;

    /**
     * Constructs a {@code Amount}.
     *
     * @param amount A valid amount.
     */
    public Amount(String amount) {
        requireNonNull(amount);
        checkArgument(isValidAmount(amount), MESSAGE_CONSTRAINTS);
        this.value = new BigDecimal(amount);
    }

    private Amount(BigDecimal value) {
        this.value = value;
    }

    /**
     * Returns true if a given string is a valid amount.
     */
    public static boolean isValidAmount(String test) {
        boolean isValidAmount = test.matches(AMOUNT_VALIDATION_REGEX);
        return isValidAmount;
    }

    /**
     * Returns true if a given string is a negative amount.
     */
    public static boolean isNegativeAmount(String test) {
        boolean isNegativeAmount = new BigDecimal(test).compareTo(new BigDecimal(0)) < 0;
        return isNegativeAmount;
    }

    /**
     * Returns a new Amount object with the given amount.
     */
    public static Amount setAmount(String amount) {
        return new Amount(amount);
    }

    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Amount // instanceof handles nulls
                        && value.equals(((Amount) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    public int compareTo(Amount other) {
        return this.value.compareTo(other.value);
    }

    /**
     * Returns a new amount by deducting current amount with another amount.
     * @param other The other amount to deduct by
     * @return A new amount.
     */
    public Amount deduct(Amount other) {
        return new Amount(this.value.add(other.value));
    }
}
