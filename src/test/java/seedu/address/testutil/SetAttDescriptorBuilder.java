package seedu.address.testutil;

import seedu.address.logic.commands.SetAttCommand;
import seedu.address.logic.commands.SetAttCommand.SetAttDescriptor;
import seedu.address.model.attendance.Attendance;
import seedu.address.model.attendance.Sessions;

/**
 * A utility class to help with building SetAttDescriptor objects.
 */
public class SetAttDescriptorBuilder {

    private SetAttDescriptor descriptor;

    public SetAttDescriptorBuilder() {
        this.descriptor = new SetAttDescriptor();
    }

    public SetAttDescriptorBuilder(SetAttCommand.SetAttDescriptor descriptor) {
        this.descriptor = new SetAttCommand.SetAttDescriptor(descriptor);
    }

    /**
     * Returns an {@code AssignPersonDescriptor} with fields containing {@code person}'s details
     */
    public SetAttDescriptorBuilder(String attendance, String sessions) {
        descriptor = new SetAttDescriptor();
        descriptor.setAtt(new Attendance(attendance));
        descriptor.setSess(new Sessions(sessions));
    }

    public SetAttCommand.SetAttDescriptor build() {
        return descriptor;
    }
}
