package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class NameContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        NameContainsKeywordsPredicate firstPredicate = new NameContainsKeywordsPredicate(firstPredicateKeywordList);
        NameContainsKeywordsPredicate secondPredicate = new NameContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertEquals(firstPredicate, firstPredicate);

        // same values -> returns true
        NameContainsKeywordsPredicate firstPredicateCopy = new NameContainsKeywordsPredicate(firstPredicateKeywordList);
        assertEquals(firstPredicate, firstPredicateCopy);

        // different types -> returns false
        assertNotEquals(1, firstPredicate);

        // null -> returns false
        assertNotEquals(null, firstPredicate);

        // different person -> returns false
        assertNotEquals(firstPredicate, secondPredicate);
    }

    @Test
    public void test_nameContainsKeywordsChildName_returnsTrue() {
        // One keyword
        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate(Collections.singletonList("Alice"));
        assertTrue(predicate.test(new PersonBuilder().withChildName("Alice Bob").build()));

        // Multiple keywords
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob"));
        assertTrue(predicate.test(new PersonBuilder().withChildName("Alice Bob").build()));

        // Only one matching keyword
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("Bob", "Carol"));
        assertTrue(predicate.test(new PersonBuilder().withChildName("Alice Carol").build()));

        // Mixed-case keywords
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("aLIce", "bOB"));
        assertTrue(predicate.test(new PersonBuilder().withChildName("Alice Bob").build()));
    }

    @Test
    public void test_nameContainsKeywordsParentName_returnsTrue() {
        // One keyword
        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate(Collections.singletonList("Alice"));
        assertTrue(predicate.test(new PersonBuilder().withParentName("Alice Bob").build()));

        // Multiple keywords
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob"));
        assertTrue(predicate.test(new PersonBuilder().withParentName("Alice Bob").build()));

        // Only one matching keyword
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("Bob", "Carol"));
        assertTrue(predicate.test(new PersonBuilder().withParentName("Alice Carol").build()));

        // Mixed-case keywords
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("aLIce", "bOB"));
        assertTrue(predicate.test(new PersonBuilder().withParentName("Alice Bob").build()));
    }

    @Test
    public void test_nameContainsKeywordsBothNames_returnsTrue() {
        // One keyword in child name
        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate(Collections.singletonList("Alice"));
        assertTrue(predicate.test(new PersonBuilder().withChildName("Alice").withParentName("Bob").build()));

        // One keyword in parent name
        predicate = new NameContainsKeywordsPredicate(Collections.singletonList("Bob"));
        assertTrue(predicate.test(new PersonBuilder().withChildName("Alice").withParentName("Bob").build()));

        // Multiple keywords across both names
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob"));
        assertTrue(predicate.test(new PersonBuilder().withChildName("Alice").withParentName("Charlie Bob").build()));

        // Mixed-case keywords across both names
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("aLIce", "bOB"));
        assertTrue(predicate.test(new PersonBuilder().withChildName("Alice").withParentName("Charlie Bob").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withChildName("Alice").build()));

        // Non-matching keyword
        predicate = new NameContainsKeywordsPredicate(List.of("Carol"));
        assertFalse(predicate.test(new PersonBuilder().withChildName("Alice Bob").build()));

        // Keywords match phone, email and address, but does not match name
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("12345", "alice@email.com", "Main", "Street"));
        assertFalse(predicate.test(new PersonBuilder().withChildName("Alice").withParentPhone("12345")
                .withParentEmail("alice@email.com").withAddress("Main Street").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywordsInEitherName_returnsFalse() {
        // Zero keywords
        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withChildName("Alice").withParentName("Bob").build()));

        // Non-matching keyword
        predicate = new NameContainsKeywordsPredicate(List.of("Carol"));
        assertFalse(predicate.test(new PersonBuilder().withChildName("Alice").withParentName("Bob").build()));

        // Keywords match phone, email and address, but does not match either name
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("12345", "alice@email.com", "Main", "Street"));
        assertFalse(predicate.test(new PersonBuilder().withChildName("Alice").withParentName("Bob")
                .withParentPhone("12345").withParentEmail("alice@email.com").withAddress("Main Street").build()));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("keyword1", "keyword2");
        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate(keywords);

        String expected = NameContainsKeywordsPredicate.class.getCanonicalName() + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}
