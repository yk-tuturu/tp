package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ALLERGY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CHILDNAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PARENTEMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PARENTNAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PARENTPHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Address;
import seedu.address.model.person.AllergyList;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.subject.Subject;
import seedu.address.model.tag.Tag;

/**
 * Edits the details of an existing person in the address book.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the child identified "
            + "by the index number used in the displayed child list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_CHILDNAME + "CHILD_NAME] "
            + "[" + PREFIX_PARENTNAME + "PARENT_NAME] "
            + "[" + PREFIX_PARENTPHONE + "PARENT_PHONE_NUMBER] "
            + "[" + PREFIX_PARENTEMAIL + "PARENT_EMAIL] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_ALLERGY + "ALLERGIES]... "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PARENTPHONE + "91234567 "
            + PREFIX_PARENTEMAIL + "johndoe@example.com";

    public static final String MESSAGE_EDIT_CHILD_SUCCESS = "Edited Child: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_CHILD = "This child already exists in the address book.";

    private final Index index;
    private final EditPersonDescriptor editPersonDescriptor;

    /**
     * @param index of the person in the filtered person list to edit
     * @param editPersonDescriptor details to edit the person with
     */
    public EditCommand(Index index, EditPersonDescriptor editPersonDescriptor) {
        requireNonNull(index);
        requireNonNull(editPersonDescriptor);

        this.index = index;
        this.editPersonDescriptor = new EditPersonDescriptor(editPersonDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());
        Person editedPerson = createEditedPerson(personToEdit, editPersonDescriptor);

        if (!personToEdit.isSamePerson(editedPerson) && model.hasPerson(editedPerson)) {
            throw new CommandException(MESSAGE_DUPLICATE_CHILD);
        }

        model.setPerson(personToEdit, editedPerson);
        for (Subject subject : Subject.getAllSubjects()) {
            if (subject.getStudents().contains(personToEdit)) {
                int score;
                try {
                    score = subject.getScore(personToEdit);
                } catch (IllegalStateException e) {
                    // If no score is present, use default unset value -1
                    score = Subject.getDefaultScore();
                }

                subject.unenrollPerson(personToEdit);
                subject.enrollPerson(editedPerson);
                subject.setScore(editedPerson, score);
            }
        }

        return new CommandResult(String.format(MESSAGE_EDIT_CHILD_SUCCESS, Messages.format(editedPerson)));
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Person createEditedPerson(Person personToEdit, EditPersonDescriptor editPersonDescriptor) {
        assert personToEdit != null;
        Name updatedChildName = editPersonDescriptor.getChildName().orElse(personToEdit.getChildName());
        Name updatedParentName = editPersonDescriptor.getParentName().orElse(personToEdit.getParentName());
        Phone updatedParentPhone = editPersonDescriptor.getParentPhone().orElse(personToEdit.getParentPhone());
        Email updatedParentEmail = editPersonDescriptor.getParentEmail().orElse(personToEdit.getParentEmail());
        AllergyList updatedAllergies = editPersonDescriptor.getAllergies().orElse(personToEdit.getAllergies());
        Address updatedAddress = editPersonDescriptor.getAddress().orElse(personToEdit.getAddress());
        Set<Tag> updatedTags = editPersonDescriptor.getTags().orElse(personToEdit.getTags());
        int unchangedUniqueId = personToEdit.getUniqueId();

        return new Person(updatedChildName, updatedParentName, updatedParentPhone,
                updatedParentEmail, updatedAllergies, updatedAddress, updatedTags, unchangedUniqueId);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCommand)) {
            return false;
        }

        EditCommand otherEditCommand = (EditCommand) other;
        return index.equals(otherEditCommand.index)
                && editPersonDescriptor.equals(otherEditCommand.editPersonDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("editPersonDescriptor", editPersonDescriptor)
                .toString();
    }

    /**
     * Stores the details to edit the person with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class EditPersonDescriptor {
        private Name childName;
        private Name parentName;
        private Phone parentPhone;
        private Email parentEmail;
        private AllergyList allergies;
        private Address address;
        private Set<Tag> tags;

        public EditPersonDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditPersonDescriptor(EditPersonDescriptor toCopy) {
            setChildName(toCopy.childName);
            setParentName(toCopy.parentName);
            setParentPhone(toCopy.parentPhone);
            setParentEmail(toCopy.parentEmail);
            setAllergies(toCopy.allergies);
            setAddress(toCopy.address);
            setTags(toCopy.tags);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(childName, parentName, parentPhone,
                    parentEmail, allergies, address, tags);
        }

        public void setChildName(Name childName) {
            this.childName = childName;
        }

        public Optional<Name> getChildName() {
            return Optional.ofNullable(childName);
        }

        public void setParentName(Name parentName) {
            this.parentName = parentName;
        }

        public Optional<Name> getParentName() {
            return Optional.ofNullable(parentName);
        }

        public void setParentPhone(Phone parentPhone) {
            this.parentPhone = parentPhone;
        }

        public Optional<Phone> getParentPhone() {
            return Optional.ofNullable(parentPhone);
        }

        public void setParentEmail(Email parentEmail) {
            this.parentEmail = parentEmail;
        }

        public Optional<Email> getParentEmail() {
            return Optional.ofNullable(parentEmail);
        }

        public void setAllergies(AllergyList allergies) {
            this.allergies = allergies;
        }

        public Optional<AllergyList> getAllergies() {
            return Optional.ofNullable(allergies);
        }

        public void setAddress(Address address) {
            this.address = address;
        }

        public Optional<Address> getAddress() {
            return Optional.ofNullable(address);
        }

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Set<Tag>> getTags() {
            return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditPersonDescriptor)) {
                return false;
            }

            EditPersonDescriptor otherEditPersonDescriptor = (EditPersonDescriptor) other;
            return Objects.equals(childName, otherEditPersonDescriptor.childName)
                    && Objects.equals(parentName, otherEditPersonDescriptor.parentName)
                    && Objects.equals(parentPhone, otherEditPersonDescriptor.parentPhone)
                    && Objects.equals(parentEmail, otherEditPersonDescriptor.parentEmail)
                    && Objects.equals(allergies, otherEditPersonDescriptor.allergies)
                    && Objects.equals(address, otherEditPersonDescriptor.address)
                    && Objects.equals(tags, otherEditPersonDescriptor.tags);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("name", childName)
                    .add("parentName", parentName)
                    .add("phone", parentPhone)
                    .add("email", parentEmail)
                    .add("allergies", allergies)
                    .add("address", address)
                    .add("tags", tags)
                    .toString();
        }
    }
}
