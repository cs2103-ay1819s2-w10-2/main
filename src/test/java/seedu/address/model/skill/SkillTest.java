package seedu.address.model.skill;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class SkillTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Skill(null));
    }

    @Test
    public void constructor_invalidSkillName_throwsIllegalArgumentException() {
        String invalidSkillName = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Skill(invalidSkillName));
    }

    @Test
    public void isValidSkillName() {
        // null skill name
        Assert.assertThrows(NullPointerException.class, () -> Skill.isValidSkillName(null));
    }

}
