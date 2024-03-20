package seedu.address.model.CCA;

import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class CCATest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new CCA(null));
    }

    @Test
    public void constructor_invalidTagName_throwsIllegalArgumentException() {
        String invalidCCAName = "";
        assertThrows(IllegalArgumentException.class, () -> new CCA(invalidCCAName));
    }

    @Test
    public void isValidCCAName() {
        // null tag name
        assertThrows(NullPointerException.class, () -> CCA.isValidCCAName(null));
    }

}
