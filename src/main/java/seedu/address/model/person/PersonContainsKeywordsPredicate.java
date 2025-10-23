package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person}'s {@code Name}, {@code Allergy} or {@code Tag} matches any of the keywords given.
 */
public class PersonContainsKeywordsPredicate implements Predicate<Person> {
    private final List<String> childNameKeywords;
    private final List<String> parentNameKeywords;
    private final List<String> allergyKeywords;
    private final List<String> tagKeywords;

    /**
     * Constructs a {@code PersonContainsKeywordsPredicate} with the given keywords for
     * child name, parent name, allergies, and tags.
     *
     * @param childNameKeywords list of keywords to match against child names, must not be null
     * @param parentNameKeywords list of keywords to match against parent names, must not be null
     * @param allergyKeywords list of keywords to match against allergies, must not be null
     * @param tagKeywords list of keywords to match against tags, must not be null
     */
    public PersonContainsKeywordsPredicate(List<String> childNameKeywords, List<String> parentNameKeywords,
                                           List<String> allergyKeywords, List<String> tagKeywords) {

        assert childNameKeywords != null : "childNameKeywords should not be null";
        assert parentNameKeywords != null : "parentNameKeywords should not be null";
        assert allergyKeywords != null : "allergyKeywords should not be null";
        assert tagKeywords != null : "tagKeywords should not be null";

        this.childNameKeywords = childNameKeywords;
        this.parentNameKeywords = parentNameKeywords;
        this.allergyKeywords = allergyKeywords;
        this.tagKeywords = tagKeywords;
    }

    /**
     * Compares the given {@code keywords} against the child's name, parent's name, allergies and tags,
     * and returns a boolean indicating if it matches any of the respective keywords.
     */
    @Override
    public boolean test(Person person) {
        boolean matchesChildName = childNameKeywords
                .stream()
                .anyMatch(keyword -> (
                        StringUtil.containsWordIgnoreCase(person.getChildName().fullName, keyword)));

        boolean matchesParentName = parentNameKeywords
                .stream()
                .anyMatch(keyword -> (
                        StringUtil.containsWordIgnoreCase(person.getParentName().fullName, keyword)));

        boolean matchesAllergy = allergyKeywords.stream()
                .anyMatch(keyword -> person.getAllergyList().stream()
                        .anyMatch(allergy -> allergy.toString().equalsIgnoreCase(keyword)));

        boolean matchesTag = tagKeywords.stream()
                .anyMatch(keyword -> person.getTags().stream()
                        .anyMatch(tag -> tag.toString().equalsIgnoreCase("[" + keyword + "]")));

        return matchesChildName || matchesParentName || matchesAllergy || matchesTag;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PersonContainsKeywordsPredicate)) {
            return false;
        }

        PersonContainsKeywordsPredicate otherPersonContainsKeywordsPredicate = (PersonContainsKeywordsPredicate) other;
        return childNameKeywords.equals(otherPersonContainsKeywordsPredicate.childNameKeywords)
                && parentNameKeywords.equals(otherPersonContainsKeywordsPredicate.parentNameKeywords)
                && allergyKeywords.equals(otherPersonContainsKeywordsPredicate.allergyKeywords)
                && tagKeywords.equals(otherPersonContainsKeywordsPredicate.tagKeywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("childNameKeywords", childNameKeywords)
                .add("parentNameKeywords", parentNameKeywords)
                .add("allergyKeywords", allergyKeywords)
                .add("tagKeywords", tagKeywords)
                .toString();
    }
}
