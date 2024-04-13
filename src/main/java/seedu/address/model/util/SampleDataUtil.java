package seedu.address.model.util;

import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
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
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                new Address("Blk 30 Geylang Street 29, #06-40"),
                getRoleSet("Treasurer"), getCcaSet("NUS Cycling"), new Amount("0.0"),
                new Attendance("0"), new Sessions("1"),
                new Metadata("Manages finances related to equipment.")),
            new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                getRoleSet("Treasurer", "Logistics"), getCcaSet("NUS Cycling"), new Amount("0.0"),
                new Attendance("0"), new Sessions("1"),
                new Metadata("Manages finances related to events.")),
            new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                getRoleSet("Logistics"), getCcaSet("NUS Cycling"), new Amount("0.0"),
                new Attendance("0"), new Sessions("1"),
                new Metadata("Manages logistics related to weekly trainings.")),
            new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                getRoleSet("President"), getCcaSet("NUS Cycling"), new Amount("0.0"),
                new Attendance("0"), new Sessions("1"),
                new Metadata("Overseas weekly trainings and is main contact for external vendors.")),
            new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                new Address("Blk 47 Tampines Street 20, #17-35"),
                getRoleSet("Outreach", "Logistics"), getCcaSet("NUS Cycling"), new Amount("0.0"),
                new Attendance("0"), new Sessions("1"),
                new Metadata("Manages the CCA's social media accounts")),
            new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                new Address("Blk 45 Aljunied Street 85, #11-31"),
                getRoleSet("Outreach"), getCcaSet("NUS Cycling"), new Amount("0.0"),
                new Attendance("0"), new Sessions("1"),
                new Metadata("Designs the outreach materials.")),
            new Person(new Name("Caleb Hugh"), new Phone("92618217"), new Email("ca.hug@example.com"),
                new Address("Blk 45 Silat Street 85, #01-03"),
                getRoleSet("Member"), getCcaSet("NUS Cycling"), new Amount("0.0"),
                new Attendance("0"), new Sessions("1"),
                Optional.empty())
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        sampleAb.addCca(new Cca("NUS Cycling"));
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
        }
        return sampleAb;
    }

    /**
     * Returns a role set containing the list of strings given.
     */
    public static Set<Role> getRoleSet(String... strings) {
        return Arrays.stream(strings)
                .map(Role::new)
                .collect(Collectors.toSet());
    }

    /**
     * Returns a CCA set containing the list of strings given.
     */
    public static Set<Cca> getCcaSet(String... strings) {
        return Arrays.stream(strings)
                .map(Cca::new)
                .collect(Collectors.toSet());
    }
}
