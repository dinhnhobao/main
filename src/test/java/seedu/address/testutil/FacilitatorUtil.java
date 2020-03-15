package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE_CODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_OFFICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;

import java.util.Set;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.facilitator.FacilEditCommand;
import seedu.address.model.facilitator.Facilitator;
import seedu.address.model.module.ModuleCode;

/**
 * A utility class for Facilitator.
 */
public class FacilitatorUtil {

    /**
     * Returns an add command string for adding the {@code facilitator}.
     */
    public static String getFacilAddCommand(Facilitator facilitator) {
        return Command.COMMAND_WORD_ADD + " " + getFacilitatorDetails(facilitator);
    }

    /**
     * Returns the part of command string for the given {@code facilitator}'s details.
     */
    public static String getFacilitatorDetails(Facilitator facilitator) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + facilitator.getName().fullName + " ");
        sb.append(PREFIX_PHONE + facilitator.getPhone().value + " ");
        sb.append(PREFIX_EMAIL + facilitator.getEmail().value + " ");
        sb.append(PREFIX_OFFICE + facilitator.getOffice().value + " ");
        facilitator.getModuleCodes().stream().forEach(
            s -> sb.append(PREFIX_MODULE_CODE + s.moduleCode + " ")
        );
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditFacilitatorDescriptor}'s details.
     */
    public static String getEditFacilitatorDescriptorDetails(FacilEditCommand.EditFacilitatorDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.fullName).append(" "));
        descriptor.getPhone().ifPresent(phone -> sb.append(PREFIX_PHONE).append(phone.value).append(" "));
        descriptor.getEmail().ifPresent(email -> sb.append(PREFIX_EMAIL).append(email.value).append(" "));
        descriptor.getOffice().ifPresent(office -> sb.append(PREFIX_OFFICE).append(office.value).append(" "));
        if (descriptor.getModuleCodes().isPresent()) {
            Set<ModuleCode> moduleCodes = descriptor.getModuleCodes().get();
            if (moduleCodes.isEmpty()) {
                sb.append(PREFIX_MODULE_CODE);
            } else {
                moduleCodes.forEach(s -> sb.append(PREFIX_MODULE_CODE).append(s.moduleCode).append(" "));
            }
        }
        return sb.toString();
    }
}