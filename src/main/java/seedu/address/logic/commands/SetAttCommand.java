package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ATTENDANCE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SESSIONS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.amount.Amount;
import seedu.address.model.attendance.Attendance;
import seedu.address.model.attendance.Sessions;
import seedu.address.model.cca.Cca;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Metadata;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.roles.Role;

/**
 * Assigns role to the existing person in the CCA Manager
 */
public class SetAttCommand extends Command {

    public static final String COMMAND_WORD = "setatt";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the attendance details of the person identified "
            + "by the index number used in the displayed person list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "" + PREFIX_ATTENDANCE + "ATTENDANCE "
            + "" + PREFIX_SESSIONS + "SESSIONS "
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_ATTENDANCE + "3 "
            + PREFIX_SESSIONS + "10";

    public static final String MESSAGE_SETATT_PERSON_SUCCESS = "Set Attendance for Person: %1$s";
    public static final String MESSAGE_ATT_NOT_SET = "Positive Integer has to be provided after /att and /s";
    public static final String MESSAGE_ATT_TOO_LARGE = "Attendance number exceeds sessions number";
    public static final String MESSAGE_DUPLICATE_PERSON = "Attendance has already been set to that value";

    private final Index index;
    private final SetAttCommand.SetAttDescriptor setAttDescriptor;

    /**
     * @param index of the person in the filtered person list to edit
     * @param setAttDescriptor attendance details to edit the person with
     */
    public SetAttCommand(Index index, SetAttCommand.SetAttDescriptor setAttDescriptor) {
        requireNonNull(index);
        requireNonNull(setAttDescriptor);

        this.index = index;
        this.setAttDescriptor = new SetAttCommand.SetAttDescriptor(setAttDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToSetAtt = lastShownList.get(index.getZeroBased());
        Person setAttPerson = createSetAttPerson(personToSetAtt, setAttDescriptor);

        if (!personToSetAtt.isSamePerson(setAttPerson) && model.hasPerson(setAttPerson)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        model.setPerson(personToSetAtt, setAttPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_SETATT_PERSON_SUCCESS, Messages.format(setAttPerson)));
    }

    /**
     * Creates and returns an person with attendance details
     * @param personToAssign person who will be assigned
     * @param setAttDescriptor details of the attendance to assign the person with
     * @return Person who is assigned with attendance details
     */
    private static Person createSetAttPerson(Person personToAssign,
                                               SetAttCommand.SetAttDescriptor setAttDescriptor) {
        assert personToAssign != null;

        Name updatedName = personToAssign.getName();
        Phone updatedPhone = personToAssign.getPhone();
        Email updatedEmail = personToAssign.getEmail();
        Address updatedAddress = personToAssign.getAddress();
        Set<Cca> updatedCcas = personToAssign.getCcas();
        Set<Role> updatedRoles = personToAssign.getRoles();
        Amount updatedAmount = personToAssign.getAmount();
        Attendance updatedAttendance = setAttDescriptor.getAtt().orElse(personToAssign.getAtt());
        Sessions updatedSessions = setAttDescriptor.getSess().orElse(personToAssign.getSess());
        Optional<Metadata> updatedMetaData = personToAssign.getMetadata();

        return new Person(updatedName, updatedPhone, updatedEmail, updatedAddress,
                updatedRoles, updatedCcas, updatedAmount, updatedAttendance, updatedSessions, updatedMetaData);
    }

    /**
     * Stores the details of the role to assign the person with.
     */
    public static class SetAttDescriptor {
        private Attendance attendance;
        private Sessions sessions;

        public SetAttDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code SetAtt} is used internally.
         */
        public SetAttDescriptor(SetAttCommand.SetAttDescriptor toCopy) {
            setAtt(toCopy.attendance);
            setSess(toCopy.sessions);
        }

        /**
         * Returns true if at least all fields are edited.
         */
        public boolean isAnyFieldNotEdited() {
            return CollectionUtil.isNotNull(attendance, sessions);
        }

        public void setAtt(Attendance attendance) {
            this.attendance = attendance;
        }

        public Optional<Attendance> getAtt() {
            return (attendance != null) ? Optional.of(attendance) : Optional.empty();
        }

        public void setSess(Sessions sessions) {
            this.sessions = sessions;
        }

        public Optional<Sessions> getSess() {
            return (attendance != null) ? Optional.of(sessions) : Optional.empty();
        }


        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof SetAttCommand.SetAttDescriptor)) {
                return false;
            }

            SetAttCommand.SetAttDescriptor otherSetAttDescriptor = (SetAttCommand.SetAttDescriptor) other;
            return Objects.equals(attendance, otherSetAttDescriptor.attendance)
                    && Objects.equals(sessions, otherSetAttDescriptor.sessions);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("attendance", attendance)
                    .add("sessions", sessions)
                    .toString();
        }
    }
}
