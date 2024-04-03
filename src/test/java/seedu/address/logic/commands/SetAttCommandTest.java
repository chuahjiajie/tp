package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ATTENDANCE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SESSIONS;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.SetAttDescriptorBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for SetAttCommand.
 */
public class SetAttCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_assignRole_success() {
        Index indexLastPerson = Index.fromOneBased(model.getFilteredPersonList().size());
        Person lastPerson = model.getFilteredPersonList().get(indexLastPerson.getZeroBased());

        PersonBuilder personInList = new PersonBuilder(lastPerson);
        Person setAttPerson = personInList.withAtt(VALID_ATTENDANCE).withSess(VALID_SESSIONS).build();
        SetAttCommand.SetAttDescriptor descriptor = new SetAttDescriptorBuilder(VALID_ATTENDANCE, VALID_SESSIONS)
                .build();
        SetAttCommand setAttCommand = new SetAttCommand(indexLastPerson, descriptor);

        String expectedMessage = String.format(SetAttCommand.MESSAGE_SETATT_PERSON_SUCCESS,
                Messages.format(setAttPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(lastPerson, setAttPerson);

        assertCommandSuccess(setAttCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        SetAttCommand.SetAttDescriptor descriptor = new SetAttDescriptorBuilder(VALID_ATTENDANCE, VALID_SESSIONS)
                .build();
        SetAttCommand setAttCommand = new SetAttCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(setAttCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }


}
