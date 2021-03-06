package seedu.address.logic.commands.lesson;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE_CODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TYPE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VENUE;

import java.util.Optional;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.CommandType;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.facilitator.ModuleCodesContainKeywordPredicate;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.module.Module;
import seedu.address.model.util.action.DoableActionType;
import seedu.address.model.util.action.LessonAction;

/**
 * Adds a lesson to Mod Manager.
 */
public class LessonAddCommand extends LessonCommand {
    public static final String MESSAGE_USAGE = COMMAND_GROUP_CLASS + " " + COMMAND_WORD_ADD
            + ": Adds a lesson to Mod Manager. \n"
            + "Parameters: "
            + PREFIX_MODULE_CODE + " MOD_CODE "
            + PREFIX_TYPE + " CLASS_TYPE "
            + PREFIX_AT + " DAY START_TIME END_TIME "
            + "[" + PREFIX_VENUE + " VENUE]\n"
            + "Example: " + COMMAND_GROUP_CLASS + " " + COMMAND_WORD_ADD + " "
            + PREFIX_MODULE_CODE + " CS2103T "
            + PREFIX_TYPE + " LEC "
            + PREFIX_AT + " FRIDAY 14:00 16:00 "
            + PREFIX_VENUE + " I3-AUD ";

    public static final String MESSAGE_SUCCESS = "New lesson added: %1$s";
    public static final String MESSAGE_DUPLICATE_LESSON = "This lesson already exists in Mod Manager";
    public static final String MESSAGE_INVALID_MODULE_CODE = "Module does not exist";
    public static final String MESSAGE_INVALID_TIME_RANGE = "The lesson to be added clashes with another lesson";
    private final Lesson toAdd;

    public LessonAddCommand(Lesson lesson) {
        requireNonNull(lesson);
        toAdd = lesson;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (model.hasLesson(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_LESSON);
        }

        Optional<Module> module = model.findModule(toAdd.getModuleCode());
        if (module.isEmpty()) {
            throw new CommandException(MESSAGE_INVALID_MODULE_CODE);
        }

        if (!model.isTimeSlotFree(toAdd, Optional.empty())) {
            throw new CommandException(MESSAGE_INVALID_TIME_RANGE);
        }

        model.updateModule(module);
        model.updateFacilitatorListForModule(new ModuleCodesContainKeywordPredicate(toAdd.getModuleCode().value));
        model.updateTaskListForModule(x -> x.getModuleCode().equals(toAdd.getModuleCode()));

        model.addLesson(toAdd);
        LessonAction addLessonAction = new LessonAction(toAdd, DoableActionType.ADD);
        model.addAction(addLessonAction);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd), CommandType.LESSON);
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof LessonAddCommand
                && toAdd.equals(((LessonAddCommand) other).toAdd));
    }
}
