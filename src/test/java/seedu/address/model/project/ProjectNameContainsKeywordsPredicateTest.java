package seedu.address.model.project;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.ProjectBuilder;

public class ProjectNameContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        ProjectNameContainsKeywordsPredicate firstPredicate = new ProjectNameContainsKeywordsPredicate(
            firstPredicateKeywordList);
        ProjectNameContainsKeywordsPredicate secondPredicate = new ProjectNameContainsKeywordsPredicate(
            secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        ProjectNameContainsKeywordsPredicate firstPredicateCopy = new ProjectNameContainsKeywordsPredicate(
            firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different employee -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_nameContainsKeywords_returnsTrue() {
        // One keyword
        ProjectNameContainsKeywordsPredicate predicate = new ProjectNameContainsKeywordsPredicate(
            Collections.singletonList("Alice"));
        assertTrue(predicate.test(new ProjectBuilder().withProjectName("Alice Project").build()));

        // Multiple keywords
        predicate = new ProjectNameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob"));
        assertTrue(predicate.test(new ProjectBuilder().withProjectName("Alice Bob Project").build()));

        // Only one matching keyword
        predicate = new ProjectNameContainsKeywordsPredicate(Arrays.asList("Bob", "Carol"));
        assertTrue(predicate.test(new ProjectBuilder().withProjectName("Alice Carol Project").build()));

        // Mixed-case keywords
        predicate = new ProjectNameContainsKeywordsPredicate(Arrays.asList("aLIce", "bOB"));
        assertTrue(predicate.test(new ProjectBuilder().withProjectName("Alice Bob Project").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        ProjectNameContainsKeywordsPredicate predicate = new ProjectNameContainsKeywordsPredicate(
            Collections.emptyList());
        assertFalse(predicate.test(new ProjectBuilder().withProjectName("Alice").build()));

        // Non-matching keyword
        predicate = new ProjectNameContainsKeywordsPredicate(Arrays.asList("Carol"));
        assertFalse(predicate.test(new ProjectBuilder().withProjectName("Alice Project").build()));
    }
}
