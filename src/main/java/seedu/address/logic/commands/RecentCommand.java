package seedu.address.logic.commands;

import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Shows the most recently added in the current session of the app
 */
public class RecentCommand extends Command {
    public static final String COMMAND_WORD = "recent";
    public static Person recent = null;
    public static void setRecent(Person recent) {
        RecentCommand.recent = recent;
    }

    @Override
    public CommandResult execute(Model model) {
        if (recent != null) {
            return new CommandResult(recent.getName().toString());
        } else {
            return new CommandResult("Recent unavailable for the current session");
        }
    }
}
