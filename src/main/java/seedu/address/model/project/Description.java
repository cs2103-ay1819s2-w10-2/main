package seedu.address.model.project;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Description of the project. A summary of what the project is about
 */

public class Description {

    public static final String MESSAGE_CONSTRAINTS = "Description should not be empty or contain only spaces";

    public final String description;

    /**
     * Empty Constructor for when description is not added to the project yet
     */
    public Description() {
        this.description = "";
    }

    /**
     * Constructor for when adding description to the project or milestone
     */
    public Description(String desc) {
        requireNonNull(desc);
        checkArgument(isValidDescription(desc), MESSAGE_CONSTRAINTS);
        this.description = desc;
    }

    public static boolean isValidDescription(String description) {
        return !description.trim().isEmpty();
    }
    /**
     * Returns a clone of this Description object.
     */
    public Description clone() {
        return new Description(this.description);
    }

    @Override
    public String toString() {
        return description;
    }

    @Override
    public boolean equals(Object other) {
        return other == this// short circuit if same object
            || (other instanceof Description // instanceof handles nulls
            && description.equals(((Description) other).description)); // state check
    }

    @Override
    public int hashCode() {
        return description.hashCode();
    }




}
