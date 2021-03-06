package seedu.address.logic.commands.task;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.CommandType;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Lists all tasks in Mod Manager to the user.
 */
public class TaskListUnDoneCommand extends TaskCommand {

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.updateFilteredTaskList(task -> !task.isTaskDone());
        return new CommandResult(String.format(Messages.MESSAGE_TASKS_UNDONE_OVERVIEW,
                model.getFilteredTaskList().size()), CommandType.TASK);
    }
}
