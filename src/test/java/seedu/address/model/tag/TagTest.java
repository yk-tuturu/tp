package seedu.address.model.tag;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class TagTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Tag(null));
    }

    @Test
    public void constructor_invalidTagName_throwsIllegalArgumentException() {
        String invalidTagName = "";
        assertThrows(IllegalArgumentException.class, () -> new Tag(invalidTagName));
    }

    @Test
    public void isValidTagName() {
        // null tag name
        assertThrows(NullPointerException.class, () -> Tag.isValidTagName(null));
    }

    // --- Length boundary tests ---
    @Test
    public void isValidTagName_maxLength_returnsTrue() {
        int max = Tag.MAX_TAG_LENGTH;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < max; i++) {
            sb.append('t');
        }
        String maxLength = sb.toString();
        assertTrue(Tag.isValidTagName(maxLength));
        Tag tag = new Tag(maxLength);
        assertEquals('[' + maxLength + ']', tag.toString());
    }

    @Test
    public void isValidTagName_overMaxLength_returnsFalseAndConstructorThrows() {
        int over = Tag.MAX_TAG_LENGTH + 1;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < over; i++) {
            sb.append('t');
        }
        String overLength = sb.toString();
        assertFalse(Tag.isValidTagName(overLength));
        assertThrows(IllegalArgumentException.class, () -> new Tag(overLength));
    }
}
