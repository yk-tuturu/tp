package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class PersonContainsKeywordsPredicateTest {

    @Test
    public void equals() {

        PersonContainsKeywordsPredicate firstPredicate =
                new PersonContainsKeywordsPredicate(
                        Collections.singletonList("firstChild"),
                        Collections.singletonList("firstParent"),
                        Collections.singletonList("firstAllergy"),
                        Collections.singletonList("firstTag"));
        PersonContainsKeywordsPredicate secondPredicate =
                new PersonContainsKeywordsPredicate(
                        Collections.singletonList("secondChild"),
                        Collections.singletonList("secondParent"),
                        Collections.singletonList("secondAllergy"),
                        Collections.singletonList("secondTag"));

        // same object -> returns true
        assertEquals(firstPredicate, firstPredicate);

        // same values -> returns true
        PersonContainsKeywordsPredicate firstPredicateCopy =
                new PersonContainsKeywordsPredicate(
                        Collections.singletonList("firstChild"),
                        Collections.singletonList("firstParent"),
                        Collections.singletonList("firstAllergy"),
                        Collections.singletonList("firstTag"));
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
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(
                        Collections.singletonList("Alice"),
                        Collections.emptyList(),
                        Collections.emptyList(),
                        Collections.emptyList());
        assertTrue(predicate.test(new PersonBuilder().withChildName("Alice Bob").build()));

        // Multiple keywords
        predicate = new PersonContainsKeywordsPredicate(
                Arrays.asList("Alice", "Bob"),
                Collections.emptyList(),
                Collections.emptyList(),
                Collections.emptyList());
        assertTrue(predicate.test(new PersonBuilder().withChildName("Alice Bob").build()));

        // Only one matching keyword
        predicate = new PersonContainsKeywordsPredicate(
                Arrays.asList("Bob", "Carol"),
                Collections.emptyList(),
                Collections.emptyList(),
                Collections.emptyList());
        assertTrue(predicate.test(new PersonBuilder().withChildName("Alice Carol").build()));

        // Mixed-case keywords
        predicate = new PersonContainsKeywordsPredicate(
                Arrays.asList("aLIce", "bOB"),
                Collections.emptyList(),
                Collections.emptyList(),
                Collections.emptyList());
        assertTrue(predicate.test(new PersonBuilder().withChildName("Alice Bob").build()));
    }

    @Test
    public void test_nameContainsKeywordsParentName_returnsTrue() {
        // One keyword
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(
                        Collections.emptyList(),
                        Collections.singletonList("Alice"),
                        Collections.emptyList(),
                        Collections.emptyList());
        assertTrue(predicate.test(new PersonBuilder().withParentName("Alice Bob").build()));

        // Multiple keywords
        predicate = new PersonContainsKeywordsPredicate(
                Collections.emptyList(),
                Arrays.asList("Alice", "Bob"),
                Collections.emptyList(),
                Collections.emptyList());
        assertTrue(predicate.test(new PersonBuilder().withParentName("Alice Bob").build()));

        // Only one matching keyword
        predicate = new PersonContainsKeywordsPredicate(
                Collections.emptyList(),
                Arrays.asList("Bob", "Carol"),
                Collections.emptyList(),
                Collections.emptyList());
        assertTrue(predicate.test(new PersonBuilder().withParentName("Alice Carol").build()));

        // Mixed-case keywords
        predicate = new PersonContainsKeywordsPredicate(
                Collections.emptyList(),
                Arrays.asList("aLIce", "bOB"),
                Collections.emptyList(),
                Collections.emptyList());
        assertTrue(predicate.test(new PersonBuilder().withParentName("Alice Bob").build()));
    }

    @Test
    public void test_allergiesContainsKeywordsAllergy_returnsTrue() {
        // One keyword
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(
                        Collections.emptyList(),
                        Collections.emptyList(),
                        Collections.singletonList("Dust"),
                        Collections.emptyList());
        assertTrue(predicate.test(new PersonBuilder().withAllergies("Dust").build()));

        // Multiple keywords
        predicate = new PersonContainsKeywordsPredicate(
                Collections.emptyList(),
                Collections.emptyList(),
                Arrays.asList("Dust", "Peanut"),
                Collections.emptyList());
        assertTrue(predicate.test(new PersonBuilder().withAllergies("Dust", "Peanut").build()));

        // Only one matching keyword
        predicate = new PersonContainsKeywordsPredicate(
                Collections.emptyList(),
                Collections.emptyList(),
                Arrays.asList("Dust", "Peanut"),
                Collections.emptyList());
        assertTrue(predicate.test(new PersonBuilder().withAllergies("Sunlight", "Peanut").build()));

        // Mixed-case keywords
        predicate = new PersonContainsKeywordsPredicate(
                Collections.emptyList(),
                Collections.emptyList(),
                Arrays.asList("duSt", "PeAnut"),
                Collections.emptyList());
        assertTrue(predicate.test(new PersonBuilder().withAllergies("Dust", "Peanut").build()));
    }

    @Test
    public void test_tagsContainsKeywordsTag_returnsTrue() {
        // One keyword
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(
                        Collections.emptyList(),
                        Collections.emptyList(),
                        Collections.emptyList(),
                        Collections.singletonList("ADHD"));
        assertTrue(predicate.test(new PersonBuilder().withTags("ADHD", "Diabetic").build()));

        // Multiple keywords
        predicate = new PersonContainsKeywordsPredicate(
                Collections.emptyList(),
                Collections.emptyList(),
                Collections.emptyList(),
                Arrays.asList("ADHD", "Diabetic"));
        assertTrue(predicate.test(new PersonBuilder().withTags("ADHD", "Diabetic").build()));

        // Only one matching keyword
        predicate = new PersonContainsKeywordsPredicate(
                Collections.emptyList(),
                Collections.emptyList(),
                Collections.emptyList(),
                Arrays.asList("Vegetarian", "ADHD"));
        assertTrue(predicate.test(new PersonBuilder().withTags("ADHD", "Diabetic").build()));

        // Mixed-case keywords
        predicate = new PersonContainsKeywordsPredicate(
                Collections.emptyList(),
                Collections.emptyList(),
                Collections.emptyList(),
                Arrays.asList("diAbeTiC", "adHd"));
        assertTrue(predicate.test(new PersonBuilder().withTags("ADHD", "Diabetic").build()));
    }


    @Test
    public void test_doesNotContainKeywords_returnsFalse() {
        // Zero keywords
        PersonContainsKeywordsPredicate predicate = new PersonContainsKeywordsPredicate(
                Collections.emptyList(),
                Collections.emptyList(),
                Collections.emptyList(),
                Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withChildName("Alice").build()));

        // Non-matching keyword
        predicate = new PersonContainsKeywordsPredicate(
                Collections.singletonList("Carol"),
                Collections.singletonList("Carol"),
                Collections.singletonList("Honey"),
                Collections.singletonList("Diabetic"));
        assertFalse(predicate.test(new PersonBuilder()
                .withChildName("Alice Bob")
                .withParentName("Alice Bob")
                .withAllergies("Dust", "Grass", "Peanut")
                .withTags("ADHD", "Vegetarian")
                .build()));

        // Keywords match phone, email and address, but does not match name
        predicate = new PersonContainsKeywordsPredicate(
                Arrays.asList("12345", "alice@email.com", "Main", "Street"),
                Collections.emptyList(),
                Collections.emptyList(),
                Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withChildName("Alice").withParentPhone("12345")
                .withParentEmail("alice@email.com").withAddress("Main Street").build()));
    }


    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("keyword1", "keyword2");
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(
                        Collections.singletonList("firstChild"),
                        Collections.singletonList("firstParent"),
                        Collections.singletonList("firstAllergy"),
                        Collections.singletonList("firstTag"));

        String expected = PersonContainsKeywordsPredicate.class.getCanonicalName()
                + "{childNameKeywords=[firstChild], "
                + "parentNameKeywords=[firstParent], "
                + "allergyKeywords=[firstAllergy], "
                + "tagKeywords=[firstTag]"
                + "}";
        assertEquals(expected, predicate.toString());
    }
}
