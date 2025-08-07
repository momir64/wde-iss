package wedoevents.eventplanner.eventManagement.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class BookingSlotsDTO {
    private EventNameDTO event;
    private HashMap<String, List<String>> timeTable;
}
