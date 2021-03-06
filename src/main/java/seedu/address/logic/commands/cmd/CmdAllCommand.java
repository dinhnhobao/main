package seedu.address.logic.commands.cmd;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.CommandType;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Lists all possible command groups.
 */
public class CmdAllCommand extends CmdCommand {

    public static final String MESSAGE_USAGE = COMMAND_GROUP_CMD + " " + COMMAND_WORD_ALL
            + ": Lists all the command groups available in the system.";

    private final String instruction = "Type: " + COMMAND_GROUP_CMD + " " + COMMAND_WORD_GROUP
            + " COMMAND_GROUP to find out about a particular command group.";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        StringBuilder builder = new StringBuilder();
        builder.append("Here are all available command groups: \n");
        Command.ALL_COMMAND_GROUPS.forEach(str -> builder.append(str).append(", "));
        StringBuilder stripedLastComma = new StringBuilder(
                builder.toString().substring(0, builder.toString().length() - 2) + ".");
        stripedLastComma.append("\n").append(instruction);

        return new CommandResult(stripedLastComma.toString(), CommandType.CMD);
    }
}
