package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.CCA.CCA;

/**
 * Jackson-friendly version of {@link CCA}.
 */
class JsonAdaptedCCA {

    private final String CCAName;

    /**
     * Constructs a {@code JsonAdaptedCCA} with the given {@code CCAName}.
     */
    @JsonCreator
    public JsonAdaptedCCA(String CCAName) {
        this.CCAName = CCAName;
    }

    /**
     * Converts a given {@code CCA} into this class for Jackson use.
     */
    public JsonAdaptedCCA(CCA source) {
        CCAName = source.CCAName;
    }

    @JsonValue
    public String getCCAName() {
        return CCAName;
    }

    /**
     * Converts this Jackson-friendly adapted tag object into the model's {@code CCA} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted CCA.
     */
    public CCA toModelType() throws IllegalValueException {
        if (!CCA.isValidCCAName(CCAName)) {
            throw new IllegalValueException(CCA.MESSAGE_CONSTRAINTS);
        }
        return new CCA(CCAName);
    }

}
