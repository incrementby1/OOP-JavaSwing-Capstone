package morsel.model.actors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Rider extends Actor {

    @JsonCreator
    public Rider(@JsonProperty("uuid") long uuid) {
        super(Actor.Role.RIDER, uuid);
    }
}