package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.project.Milestone;

/**
 * Jackson-friendly version of {@link seedu.address.model.project.Milestone}.
 */
class JsonAdaptedMilestone {

    private final String milestone;
    private final String date;

    /**
     * Constructs a {@code JsonAdaptedMilestone} with the given {@code skillName}.
     */
    @JsonCreator
    public JsonAdaptedMilestone(@JsonProperty("milestone") String milestone, @JsonProperty("date") String date) {
        this.milestone = milestone;
        this.date = date;
    }

    /**
     * Converts a given {@code Skill} into this class for Jackson use.
     */
    public JsonAdaptedMilestone(Milestone source) {
        milestone = source.milestone;
        date = source.date;
    }


    /**
     * Converts this Jackson-friendly adapted milestone object into the model's {@code Milestone} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted milestone.
     */
    public Milestone toModelType() throws IllegalValueException {
        if (!Milestone.isValidMilestone(milestone, date)) {
            throw new IllegalValueException(Milestone.MESSAGE_CONSTRAINTS);
        }
        return new Milestone(milestone, date);
    }

}
