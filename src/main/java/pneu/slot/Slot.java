package pneu.slot;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import pneu.rack.Rack;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = Tire.class, name = "tire"),
        @JsonSubTypes.Type(value = Hole.class, name = "hole"),
})
public interface Slot {
    int getWidth();

    @JsonBackReference("rackContent")
    Rack getRack();
    @JsonBackReference("rackContent")
    void setRack(Rack rack);
}
