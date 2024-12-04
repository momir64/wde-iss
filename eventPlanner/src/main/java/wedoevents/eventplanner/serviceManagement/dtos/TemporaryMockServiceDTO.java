package wedoevents.eventplanner.serviceManagement.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
// Only used for get latest version of some service, delete when proper endpoint implemented
public class TemporaryMockServiceDTO {
    private     String       name;
    private     String       company;
    private     String       description;
    private     Double       price;
    private     Double       rating;
    private     List<String> images;
    private     Double       salePercentage;
}
