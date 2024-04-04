package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedPerson.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.BENSON;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.attendance.Attendance;
import seedu.address.model.attendance.Sessions;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;

public class JsonAdaptedPersonTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_ROLE = "#friend";
    private static final String INVALID_CCA = "#NUS Cycling";
    private static final String INVALID_AMOUNT = "10.00.00";
    private static final String INVALID_ATTENDANCE_N = "-1";
    private static final String INVALID_ATTENDANCE = "5";
    private static final String INVALID_SESSIONS_N = "-1";
    private static final String INVALID_SESSIONS = "4";
    private static final String INVALID_METADATA = "#MIENIV";

    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_PHONE = BENSON.getPhone().toString();
    private static final String VALID_EMAIL = BENSON.getEmail().toString();
    private static final String VALID_ADDRESS = BENSON.getAddress().toString();
    private static final JsonAdaptedAmount VALID_AMOUNT = new JsonAdaptedAmount(BENSON.getAmount());
    private static final String VALID_ATTENDANCE = BENSON.getAtt().toString();
    private static final String VALID_SESSIONS = BENSON.getSess().toString();
    private static final List<JsonAdaptedRole> VALID_ROLES = BENSON.getRoles().stream()
            .map(JsonAdaptedRole::new)
            .collect(Collectors.toList());
    private static final List<JsonAdaptedCca> VALID_CCAS = BENSON.getCcas().stream()
            .map(JsonAdaptedCca::new)
            .collect(Collectors.toList());
    private static final String VALID_METADATA = BENSON.getMetadata().toString();

    @Test
    public void toModelType_validPersonDetails_returnsPerson() throws Exception {
        JsonAdaptedPerson person = new JsonAdaptedPerson(BENSON);
        assertEquals(BENSON, person.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(INVALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_ROLES,
                        VALID_CCAS, VALID_AMOUNT, VALID_ATTENDANCE, VALID_SESSIONS, VALID_METADATA);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(null, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                VALID_ROLES, VALID_CCAS, VALID_AMOUNT, VALID_ATTENDANCE, VALID_SESSIONS, VALID_METADATA);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, INVALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_ROLES,
                        VALID_CCAS, VALID_AMOUNT, VALID_ATTENDANCE, VALID_SESSIONS, VALID_METADATA);
        String expectedMessage = Phone.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, null, VALID_EMAIL, VALID_ADDRESS, VALID_ROLES,
                VALID_CCAS, VALID_AMOUNT, VALID_ATTENDANCE, VALID_SESSIONS, VALID_METADATA);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, INVALID_EMAIL, VALID_ADDRESS, VALID_ROLES,
                        VALID_CCAS, VALID_AMOUNT, VALID_ATTENDANCE, VALID_SESSIONS, VALID_METADATA);
        String expectedMessage = Email.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullEmail_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, null, VALID_ADDRESS, VALID_ROLES,
                VALID_CCAS, VALID_AMOUNT, VALID_ATTENDANCE, VALID_SESSIONS, VALID_METADATA);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, INVALID_ADDRESS, VALID_ROLES,
                        VALID_CCAS, VALID_AMOUNT, VALID_ATTENDANCE, VALID_SESSIONS, VALID_METADATA);
        String expectedMessage = Address.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullAddress_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, null, VALID_ROLES,
                VALID_CCAS, VALID_AMOUNT, VALID_ATTENDANCE, VALID_SESSIONS, VALID_METADATA);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidRoles_throwsIllegalValueException() {
        List<JsonAdaptedRole> invalidRoles = new ArrayList<>(VALID_ROLES);
        invalidRoles.add(new JsonAdaptedRole(INVALID_ROLE));
        List<JsonAdaptedCca> invalidCcas = new ArrayList<>(VALID_CCAS);
        invalidRoles.add(new JsonAdaptedRole(INVALID_CCA));
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, invalidRoles,
                        invalidCcas, VALID_AMOUNT, VALID_ATTENDANCE, VALID_SESSIONS, VALID_METADATA);
        assertThrows(IllegalValueException.class, person::toModelType);
    }

    @Test
    public void toModelType_invalidAttendance_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_ROLES,
                        VALID_CCAS, VALID_AMOUNT, INVALID_ATTENDANCE_N, VALID_SESSIONS, VALID_METADATA);
        String expectedMessage = Attendance.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullAttendance_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                VALID_ROLES, VALID_CCAS, VALID_AMOUNT, null, VALID_SESSIONS, VALID_METADATA);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Attendance.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidSessions_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_ROLES,
                        VALID_CCAS, VALID_AMOUNT, VALID_ATTENDANCE, INVALID_SESSIONS_N, VALID_METADATA);
        String expectedMessage = Sessions.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullSessions_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                VALID_ROLES, VALID_CCAS, VALID_AMOUNT, VALID_ATTENDANCE, null, VALID_METADATA);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Sessions.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidSetAtt_throwsParseException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_ROLES,
                        VALID_CCAS, VALID_AMOUNT, INVALID_ATTENDANCE, INVALID_SESSIONS, VALID_METADATA);
        String expectedMessage = Attendance.MESSAGE_LESS_THAN_CONSTRAINT;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }
}
