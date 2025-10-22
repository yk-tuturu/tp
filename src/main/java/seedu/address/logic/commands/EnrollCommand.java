package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.subject.Subject;

public class EnrollCommand extends Command {

    public static final String COMMAND_WORD = "enroll";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Enrolls all students at the specified indexes "
            + "into the listed subjects; or enrolls all students currently listed if the 'all' keyword is used\n"
            + "Parameters: INDEXES (must be positive integers) or 'ALL', s/SUBJECT...\n"
            + "Example: " + COMMAND_WORD + " 1 2 3 s/math"
            + "or: " + COMMAND_WORD + " all s/math s/science";

    public static final String MESSAGE_ENROLL_PERSON_SUCCESS = "Enrolled Person: %1$s in Subject: %2$s";

    private Index[] indexes;
    private boolean enrolAll;
    private Set<Subject> subjectSet;

    public EnrollCommand(Index[] indexes, boolean enrolAll, Set<Subject> subjectSet) {
        this.indexes = indexes;
        this.enrolAll = enrolAll;
        this.subjectSet = subjectSet;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Person> lastShownList = model.getFilteredPersonList();
        String enrolledPersonsResult = "";

        // enroll all currently shown students
        if (enrolAll) {
            for (Person person : lastShownList) {
                for (Subject subject : subjectSet) {
                    subject.enrollPerson(person);
                    enrolledPersonsResult += String.format(MESSAGE_ENROLL_PERSON_SUCCESS,
                            Messages.formatShort(person), subject.toString());
                }
            }
        } else {
            // check for index out of range
            for (Index index : indexes) {
                if (index.getZeroBased() >= lastShownList.size()) {
                    throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
                }
            }

            for (Index index : indexes) {
                for (Subject subject : subjectSet) {
                    Person personToEnroll = lastShownList.get(index.getZeroBased());

                    // check if student is already enrolled, then enrol
                    if (!subject.getStudents().contains(personToEnroll)) {
                        subject.enrollPerson(personToEnroll);
                        enrolledPersonsResult += String.format(MESSAGE_ENROLL_PERSON_SUCCESS,
                                Messages.formatShort(personToEnroll), subject.toString());
                    }
                }
            }
        }

        return new CommandResult(enrolledPersonsResult);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EnrollCommand)) {
            return false;
        }

        EnrollCommand otherCommand = (EnrollCommand) other;
        return indexes.equals(otherCommand.indexes)
                && enrolAll == otherCommand.enrolAll
                && subjectSet.equals(otherCommand.subjectSet);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("indexes", indexes)
                .add("enrolAll", enrolAll)
                .add("subjects", subjectSet)
                .toString();
    }
}
