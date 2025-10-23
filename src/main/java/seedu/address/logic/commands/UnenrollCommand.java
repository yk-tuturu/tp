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

/**
 * Unenrolls students from a subject
 */
public class UnenrollCommand extends Command {
    public static final String COMMAND_WORD = "unenroll";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Unenrolls all students at the specified indexes "
            + "into the listed subjects; or enrolls all students currently listed if the 'all' keyword is used\n"
            + "Parameters: INDEXES (must be positive integers) or 'ALL', s/SUBJECT...\n"
            + "Example: " + COMMAND_WORD + " 1 2 3 s/math "
            + "or: " + COMMAND_WORD + " all s/math s/science";

    public static final String MESSAGE_ENROLL_PERSON_SUCCESS = "Unenrolled Person: %1$s in Subject: %2$s\n";
    public static final String MESSAGE_NO_PERSON_UNENROLLED = "All selected students are already unenrolled";

    private final Index[] indexes;
    private final boolean unenrollAll;
    private final Set<Subject> subjectSet;

    /**
     * Unenrolls an index array of students into one or more subjects
     * The enrollAll flag can be set to true to unenroll all shown students
     * @param indexes the index array
     * @param unenrollAll if true, unenroll all currently shown students
     * @param subjectSet a set of subjects to be unenrolled from
     */
    public UnenrollCommand(Index[] indexes, boolean unenrollAll, Set<Subject> subjectSet) {
        this.indexes = indexes;
        this.unenrollAll = unenrollAll;
        this.subjectSet = subjectSet;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Person> lastShownList = model.getFilteredPersonList();
        StringBuilder sb = new StringBuilder();

        // enroll all currently shown students
        if (unenrollAll) {
            for (Person person : lastShownList) {
                for (Subject subject : subjectSet) {
                    if (subject.getStudents().contains(person)) {
                        subject.enrollPerson(person);
                        sb.append(String.format(MESSAGE_ENROLL_PERSON_SUCCESS,
                                Messages.formatShort(person), subject.toString()));
                    }
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
                    Person personToUnenroll = lastShownList.get(index.getZeroBased());

                    // check if student is actually in the class, then unenroll
                    if (subject.getStudents().contains(personToUnenroll)) {
                        subject.enrollPerson(personToUnenroll);
                        sb.append(String.format(MESSAGE_ENROLL_PERSON_SUCCESS,
                                Messages.formatShort(personToUnenroll), subject.toString()));
                    }
                }
            }
        }

        String unenrolledPersonsResult = sb.toString();

        return new CommandResult(unenrolledPersonsResult.isEmpty() ? MESSAGE_NO_PERSON_UNENROLLED
                : unenrolledPersonsResult);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UnenrollCommand)) {
            return false;
        }

        UnenrollCommand otherCommand = (UnenrollCommand) other;
        return indexes.equals(otherCommand.indexes)
                && unenrollAll == otherCommand.unenrollAll
                && subjectSet.equals(otherCommand.subjectSet);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("indexes", indexes)
                .add("enrolAll", unenrollAll)
                .add("subjects", subjectSet)
                .toString();
    }
}
