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
     * Constructs a {@code JsonAdaptedTag} with the given {@code tagName}.
     */
    @JsonCreator
    public JsonAdaptedCCA(String tagName) {
        this.CCAName = tagName;
    }

    /**
     * Converts a given {@code Tag} into this class for Jackson use.
     */
    public JsonAdaptedCCA(CCA source) {
        CCAName = source.CCAName;
    }

    @JsonValue
    public String getCCAName() {
        return CCAName;
    }

    /**
     * Converts this Jackson-friendly adapted tag object into the model's {@code Tag} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted tag.
     */
    public CCA toModelType() throws IllegalValueException {
        if (!CCA.isValidTagName(CCAName)) {
            throw new IllegalValueException(CCA.MESSAGE_CONSTRAINTS);
        }
        return new CCA(CCAName);
    }

}
