package seedu.address.testutil;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

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
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Person objects.
 */
public class PersonBuilder {

    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "amy@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";
    public static final String DEFAULT_AMOUNT = "0.0";
    public static final String DEFAULT_ATTENDANCE = "0";
    public static final String DEFAULT_SESSIONS = "0";
    public static final String DEFAULT_METADATA = "I like peaches";

    private Name name;
    private Phone phone;
    private Email email;
    private Address address;
    private Set<Role> roles;
    private Set<Cca> ccas;
    private Amount amount;
    private Attendance attendance;
    private Sessions sessions;
    private Optional<Metadata> metadata;

    /**
     * Creates a {@code PersonBuilder} with the default details.
     */
    public PersonBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        roles = new HashSet<>();
        ccas = new HashSet<>();
        amount = new Amount(DEFAULT_AMOUNT);
        attendance = new Attendance(DEFAULT_ATTENDANCE);
        sessions = new Sessions(DEFAULT_SESSIONS);
        metadata = Optional.of(new Metadata(DEFAULT_METADATA));
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public PersonBuilder(Person personToCopy) {
        name = personToCopy.getName();
        phone = personToCopy.getPhone();
        email = personToCopy.getEmail();
        address = personToCopy.getAddress();
        roles = new HashSet<>(personToCopy.getRoles());
        ccas = new HashSet<>(personToCopy.getCcas());
        amount = personToCopy.getAmount();
        attendance = personToCopy.getAtt();
        sessions = personToCopy.getSess();
        metadata = personToCopy.getMetadata();
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public PersonBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code roles} into a {@code Set<Role>} and set it to the {@code Person} that we are building.
     */
    public PersonBuilder withRoles(String... roles) {
        this.roles = SampleDataUtil.getRoleSet(roles);
        return this;
    }

    /**
     * Parses the {@code CCA} into a {@code Set<CCA>} and set it to the {@code Person} that we are building.
     */
    public PersonBuilder withCcas(String... ccas) {
        this.ccas = SampleDataUtil.getCcaSet(ccas);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Person} that we are building.
     */
    public PersonBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Person} that we are building.
     */
    public PersonBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Person} that we are building.
     */
    public PersonBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    /**
     * Sets the {@code Amount} of the {@code Person} that we are building.
     */
    public PersonBuilder withAmount(String amount) {
        this.amount = new Amount(amount);
        return this;
    }

    /**
     * Sets the {@code Attendance} of the {@code Person} that we are building.
     */
    public PersonBuilder withAtt(String attendance) {
        this.attendance = new Attendance(attendance);
        return this;
    }

    /**
     * Sets the {@code Sessions} of the {@code Person} that we are building.
     */
    public PersonBuilder withSess(String sessions) {
        this.sessions = new Sessions(sessions);
        return this;
    }

    /**
     * Sets the {@code Metadata} of the {@code Person} that we are building.
     */
    public PersonBuilder withMetadata(String metadata) {
        this.metadata = Optional.of(new Metadata(metadata));
        return this;
    }

    public Person build() {
        return new Person(name, phone, email, address, roles, ccas, amount, attendance, sessions, metadata);
    }

}
