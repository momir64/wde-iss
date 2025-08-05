package wedoevents.eventplanner.eventManagement.eventFilterTests;

import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import wedoevents.eventplanner.eventManagement.eventFilterTests.seleniumPOMs.*;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EventFilterE2ETests {
    @Autowired
    private WebDriver webDriver;

    @BeforeEach
    public void setUp() {
        webDriver.manage().window().maximize();

        String url = "http://localhost:4200/events";
        webDriver.get(url);

        webDriver.manage().timeouts().implicitlyWait(Duration.ofMillis(2000));
    }

    @Test
    public void testFilterEventsByEventType() {
        AllEventsPage allEventsPage = new AllEventsPage(webDriver);
        allEventsPage.openFilterDialog();

        allEventsPage.selectType("Corporate Event");
        allEventsPage.clickFilterButton();

        List<String> corporateEvents = allEventsPage.getAllEventNames();
        assertTrue(corporateEvents.contains("Tech Career Fair"));
        assertTrue(corporateEvents.contains("Kids Science Fair"));
        assertTrue(corporateEvents.contains("Startup Meetup"));
        assertTrue(corporateEvents.contains("Niš Tech Expo"));
        assertTrue(corporateEvents.contains("Meditation Workshop"));
        assertTrue(corporateEvents.contains("Kids Art Workshop"));
        assertTrue(corporateEvents.contains("Art in the Park"));
        assertTrue(corporateEvents.contains("Corporate Talk"));
        assertEquals(8, corporateEvents.size());
    }

    @Test
    public void testFilterEventsByCity() {
        AllEventsPage allEventsPage = new AllEventsPage(webDriver);
        allEventsPage.openFilterDialog();

        allEventsPage.selectCity("Novi Sad");
        allEventsPage.clickFilterButton();

        List<String> noviSadEvents = allEventsPage.getAllEventsCities();
        assertFalse(noviSadEvents.isEmpty());
        assertTrue(noviSadEvents.stream().allMatch(e -> e.equals("Novi Sad")));
    }

    @Test
    public void testFilterEventsByDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d.M.yyyy.");
        AllEventsPage allEventsPage = new AllEventsPage(webDriver);
        allEventsPage.openFilterDialog();

        allEventsPage.setDateRange("10.1.2025.", "12.1.2025.");
        allEventsPage.clickFilterButton();

        List<String> datedEvents = allEventsPage.getAllEventsDates();
        assertFalse(datedEvents.isEmpty());
        assertTrue(datedEvents.stream().allMatch(e -> {
            LocalDate eventDate = LocalDate.parse(e, formatter);
            LocalDate afterDate = LocalDate.parse("1.10.2025.", formatter);
            LocalDate beforeDate = LocalDate.parse("1.12.2025.", formatter);

            return eventDate.isAfter(afterDate) && eventDate.isBefore(beforeDate);
        }));
    }

    @Test
    public void testFilterEventsByRating() {
        AllEventsPage allEventsPage = new AllEventsPage(webDriver);
        allEventsPage.openFilterDialog();

        allEventsPage.setRatingRange(2, 4);
        allEventsPage.clickFilterButton();

        List<String> ratedEvents = allEventsPage.getAllEventsRatings();
        assertFalse(ratedEvents.isEmpty());
        assertTrue(ratedEvents.stream().allMatch(e -> {
            if (e.equals("No reviews")) return false;
            double rating = Double.parseDouble(e);
            return rating >= 2.0 && rating <= 4.0;
        }));
    }

    @Test
    public void testCombinedCityTypeFilters() {
        AllEventsPage allEventsPage = new AllEventsPage(webDriver);
        allEventsPage.openFilterDialog();

        allEventsPage.selectType("Engagement Party");
        allEventsPage.selectCity("Niš");
        allEventsPage.clickFilterButton();

        List<String> cities = allEventsPage.getAllEventsCities();
        assertFalse(cities.isEmpty());
        assertTrue(cities.stream().allMatch(city -> city.equals("Niš")));

        List<String> eventNames = allEventsPage.getAllEventNames();
        assertTrue(eventNames.contains("Susans Engagement Party"));
        assertEquals(1, eventNames.size());
    }

    @Test
    public void testCombinedCityDateFilters() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d.M.yyyy.");

        AllEventsPage allEventsPage = new AllEventsPage(webDriver);
        allEventsPage.openFilterDialog();

        allEventsPage.selectCity("Niš");
        allEventsPage.setDateRange("10.1.2025.", "12.1.2025.");
        allEventsPage.clickFilterButton();

        List<String> cities = allEventsPage.getAllEventsCities();
        assertFalse(cities.isEmpty());
        assertTrue(cities.stream().allMatch(city -> city.equals("Niš")));

        List<String> dates = allEventsPage.getAllEventsDates();
        LocalDate after = LocalDate.parse("1.10.2025.", formatter);
        LocalDate before = LocalDate.parse("1.12.2025.", formatter);

        assertTrue(dates.stream().allMatch(d -> {
            LocalDate date = LocalDate.parse(d, formatter);
            return (date.isAfter(after) || date.isEqual(after)) && (date.isBefore(before) || date.isEqual(before));
        }));
    }

    @Test
    public void testCombinedCityRatingFilters() {
        AllEventsPage allEventsPage = new AllEventsPage(webDriver);
        allEventsPage.openFilterDialog();

        allEventsPage.selectCity("Beograd");
        allEventsPage.setRatingRange(4.0, 5.0);
        allEventsPage.clickFilterButton();

        List<String> cities = allEventsPage.getAllEventsCities();
        assertFalse(cities.isEmpty());
        assertTrue(cities.stream().allMatch(city -> city.equals("Beograd")));

        List<String> ratings = allEventsPage.getAllEventsRatings();
        assertTrue(ratings.stream().allMatch(r -> {
            if (r.equals("No reviews")) return false;
            double val = Double.parseDouble(r);
            return val >= 4.0 && val <= 5.0;
        }));
    }

    @Test
    public void testCombinedTypeDateFilters() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d.M.yyyy.");

        AllEventsPage allEventsPage = new AllEventsPage(webDriver);
        allEventsPage.openFilterDialog();

        allEventsPage.selectType("Costume Party");
        allEventsPage.setDateRange("10.1.2025.", "11.1.2025.");
        allEventsPage.clickFilterButton();

        List<String> dates = allEventsPage.getAllEventsDates();
        LocalDate after = LocalDate.parse("1.10.2025.", formatter);
        LocalDate before = LocalDate.parse("1.11.2025.", formatter);

        assertTrue(dates.stream().allMatch(d -> {
            LocalDate date = LocalDate.parse(d, formatter);
            return (date.isAfter(after) || date.isEqual(after)) && (date.isBefore(before) || date.isEqual(before));
        }));

        List<String> eventNames = allEventsPage.getAllEventNames();

        assertEquals(1, eventNames.size());
        assertTrue(eventNames.contains("Halloween Costume Party"));
    }

    @Test
    public void testCombinedTypeRatingFilters() {
        AllEventsPage allEventsPage = new AllEventsPage(webDriver);
        allEventsPage.openFilterDialog();

        allEventsPage.selectType("Techno Party");
        allEventsPage.setRatingRange(4.5, 5.0);
        allEventsPage.clickFilterButton();

        List<String> ratings = allEventsPage.getAllEventsRatings();
        assertTrue(ratings.stream().allMatch(r -> {
            if (r.equals("No reviews")) return false;
            double val = Double.parseDouble(r);
            return val >= 4.5 && val <= 5.0;
        }));

        List<String> eventNames = allEventsPage.getAllEventNames();

        assertEquals(2, eventNames.size());
        assertTrue(eventNames.contains("Techno Beats Night"));
        assertTrue(eventNames.contains("Retro Music Night"));
    }

    @Test
    public void testCombinedDateRatingFilters() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d.M.yyyy.");

        AllEventsPage allEventsPage = new AllEventsPage(webDriver);
        allEventsPage.openFilterDialog();

        allEventsPage.setDateRange("10.1.2025.", "12.1.2025.");
        allEventsPage.setRatingRange(2.5, 4.5);
        allEventsPage.clickFilterButton();

        List<String> dates = allEventsPage.getAllEventsDates();
        LocalDate after = LocalDate.parse("1.10.2025.", formatter);
        LocalDate before = LocalDate.parse("1.12.2025.", formatter);

        assertFalse(dates.isEmpty());
        assertTrue(dates.stream().allMatch(d -> {
            LocalDate date = LocalDate.parse(d, formatter);
            return (date.isAfter(after) || date.isEqual(after)) && (date.isBefore(before) || date.isEqual(before));
        }));

        List<String> ratings = allEventsPage.getAllEventsRatings();
        assertTrue(ratings.stream().allMatch(r -> {
            if (r.equals("No reviews")) return false;
            double val = Double.parseDouble(r);
            return val >= 2.5 && val <= 4.5;
        }));
    }

    @Test
    public void testFilterByCityTypeDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d.M.yyyy.");

        AllEventsPage allEventsPage = new AllEventsPage(webDriver);
        allEventsPage.openFilterDialog();

        allEventsPage.selectCity("Beograd");
        allEventsPage.selectType("Corporate Event");
        allEventsPage.setDateRange("7.1.2025.", "8.1.2025.");
        allEventsPage.clickFilterButton();

        List<String> cities = allEventsPage.getAllEventsCities();
        assertFalse(cities.isEmpty());
        assertTrue(cities.stream().allMatch(city -> city.equals("Beograd")));

        List<String> dates = allEventsPage.getAllEventsDates();
        LocalDate after = LocalDate.parse("1.7.2025.", formatter);
        LocalDate before = LocalDate.parse("1.8.2025.", formatter);
        assertTrue(dates.stream().allMatch(d -> {
            LocalDate date = LocalDate.parse(d, formatter);
            return (date.isAfter(after) || date.isEqual(after)) && (date.isBefore(before) || date.isEqual(before));
        }));

        List<String> eventNames = allEventsPage.getAllEventNames();
        assertFalse(eventNames.isEmpty());
        assertTrue(eventNames.contains("Meditation Workshop"));
    }

    @Test
    public void testFilterByCityTypeRating() {
        AllEventsPage allEventsPage = new AllEventsPage(webDriver);
        allEventsPage.openFilterDialog();

        allEventsPage.selectCity("Novi Sad");
        allEventsPage.selectType("Corporate Event");
        allEventsPage.setRatingRange(3.0, 5.0);
        allEventsPage.clickFilterButton();

        List<String> cities = allEventsPage.getAllEventsCities();
        assertFalse(cities.isEmpty());
        assertTrue(cities.stream().allMatch(city -> city.equals("Novi Sad")));

        List<String> ratings = allEventsPage.getAllEventsRatings();
        assertTrue(ratings.stream().allMatch(r -> {
            if (r.equals("No reviews")) return false;
            double val = Double.parseDouble(r);
            return val >= 3.0 && val <= 5.0;
        }));

        List<String> eventNames = allEventsPage.getAllEventNames();
        assertTrue(eventNames.contains("Startup Meetup"));
        assertEquals(1, eventNames.size());
    }

    @Test
    public void testFilterByCityDateRating() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d.M.yyyy.");

        AllEventsPage allEventsPage = new AllEventsPage(webDriver);
        allEventsPage.openFilterDialog();

        allEventsPage.selectCity("Niš");
        allEventsPage.setDateRange("10.1.2025.", "12.1.2025.");
        allEventsPage.setRatingRange(5.0, 5.0);
        allEventsPage.clickFilterButton();

        List<String> cities = allEventsPage.getAllEventsCities();
        assertFalse(cities.isEmpty());
        assertTrue(cities.stream().allMatch(city -> city.equals("Niš")));

        List<String> dates = allEventsPage.getAllEventsDates();
        LocalDate after = LocalDate.parse("1.10.2025.", formatter);
        LocalDate before = LocalDate.parse("1.12.2025.", formatter);
        assertTrue(dates.stream().allMatch(d -> {
            LocalDate date = LocalDate.parse(d, formatter);
            return (date.isAfter(after) || date.isEqual(after)) && (date.isBefore(before) || date.isEqual(before));
        }));

        List<String> ratings = allEventsPage.getAllEventsRatings();
        assertTrue(ratings.stream().allMatch(r -> {
            if (r.equals("No reviews")) return false;
            double val = Double.parseDouble(r);
            return val >= 5.0 && val <= 5.0;
        }));
    }

    @Test
    public void testFilterByTypeDateRating() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d.M.yyyy.");

        AllEventsPage allEventsPage = new AllEventsPage(webDriver);
        allEventsPage.openFilterDialog();

        allEventsPage.selectType("Corporate Event");
        allEventsPage.setDateRange("9.1.2025.", "10.1.2025.");
        allEventsPage.setRatingRange(4.0, 4.0);
        allEventsPage.clickFilterButton();

        List<String> dates = allEventsPage.getAllEventsDates();
        assertFalse(dates.isEmpty());
        LocalDate after = LocalDate.parse("1.9.2025.", formatter);
        LocalDate before = LocalDate.parse("1.10.2025.", formatter);
        assertTrue(dates.stream().allMatch(d -> {
            LocalDate date = LocalDate.parse(d, formatter);
            return (date.isAfter(after) || date.isEqual(after)) && (date.isBefore(before) || date.isEqual(before));
        }));

        List<String> ratings = allEventsPage.getAllEventsRatings();
        assertTrue(ratings.stream().allMatch(r -> {
            if (r.equals("No reviews")) return false;
            double val = Double.parseDouble(r);
            return val >= 4.0 && val <= 4.0;
        }));

        List<String> eventNames = allEventsPage.getAllEventNames();
        assertTrue(eventNames.contains("Startup Meetup"));
        assertEquals(1, eventNames.size());
    }

    @Test
    public void testFilterByAllFields() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d.M.yyyy.");

        AllEventsPage allEventsPage = new AllEventsPage(webDriver);
        allEventsPage.openFilterDialog();

        allEventsPage.selectCity("Ada");
        allEventsPage.selectType("Gala Dinner");
        allEventsPage.setDateRange("12.12.2025.", "12.13.2025.");
        allEventsPage.setRatingRange(3.0, 3.0);
        allEventsPage.clickFilterButton();

        List<String> cities = allEventsPage.getAllEventsCities();
        assertFalse(cities.isEmpty());
        assertTrue(cities.stream().allMatch(city -> city.equals("Ada")));

        List<String> dates = allEventsPage.getAllEventsDates();
        LocalDate after = LocalDate.parse("12.12.2025.", formatter);
        LocalDate before = LocalDate.parse("13.12.2025.", formatter);
        assertTrue(dates.stream().allMatch(d -> {
            LocalDate date = LocalDate.parse(d, formatter);
            return (date.isAfter(after) || date.isEqual(after)) && (date.isBefore(before) || date.isEqual(before));
        }));

        List<String> ratings = allEventsPage.getAllEventsRatings();
        assertTrue(ratings.stream().allMatch(r -> {
            if (r.equals("No reviews")) return false;
            double val = Double.parseDouble(r);
            return val >= 3.0 && val <= 3.0;
        }));

        List<String> names = allEventsPage.getAllEventNames();
        assertTrue(names.contains("Test Event For All Criteria"));
        assertEquals(1, names.size());
    }

    @Test
    public void testSearchSingleEvent() {
        AllEventsPage allEventsPage = new AllEventsPage(webDriver);
        allEventsPage.searchEvents("Spring Carnival");

        List<String> names = allEventsPage.getAllEventNames();
        assertTrue(names.contains("Spring Carnival"));
        assertEquals(1, names.size());
    }

    @Test
    public void testSearchEventsContainingQuery() {
        AllEventsPage allEventsPage = new AllEventsPage(webDriver);
        allEventsPage.searchEvents("Techno");

        List<String> names = allEventsPage.getAllEventNames();
        assertTrue(names.contains("Techno Beats Night"));
        assertTrue(names.contains("Techno Bash 2"));
        assertTrue(names.contains("Techno Bash 3"));
        assertEquals(3, names.size());
    }

    @Test
    public void testCombinedDateCityTypeRatingFiltersAndSearch() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d.M.yyyy.");
        AllEventsPage allEventsPage = new AllEventsPage(webDriver);

        allEventsPage.searchEvents("Techno");

        allEventsPage.openFilterDialog();
        allEventsPage.selectCity("Ada");
        allEventsPage.selectType("Techno Party");
        allEventsPage.setDateRange("10.1.2025.", "1.1.2026.");
        allEventsPage.setRatingRange(4.0, 4.0);
        allEventsPage.clickFilterButton();

        List<String> cities = allEventsPage.getAllEventsCities();
        assertFalse(cities.isEmpty());
        assertTrue(cities.stream().allMatch(city -> city.equals("Ada")));

        List<String> dates = allEventsPage.getAllEventsDates();
        LocalDate after = LocalDate.parse("10.1.2025.", formatter);
        LocalDate before = LocalDate.parse("1.1.2026.", formatter);
        assertTrue(dates.stream().allMatch(d -> {
            LocalDate date = LocalDate.parse(d, formatter);
            return (date.isAfter(after) || date.isEqual(after)) && (date.isBefore(before) || date.isEqual(before));
        }));

        List<String> ratings = allEventsPage.getAllEventsRatings();
        assertTrue(ratings.stream().allMatch(r -> {
            if (r.equals("No reviews")) return false;
            double val = Double.parseDouble(r);
            return val >= 4.0 && val <= 4.0;
        }));

        List<String> names = allEventsPage.getAllEventNames();
        assertTrue(names.contains("Techno Bash 2"));
        assertEquals(1, names.size());
    }

    @Test
    public void testDontShowEventsByBlockedUser() {
        String url = "http://localhost:4200/login";
        webDriver.get(url);

        LoginPage loginPage = new LoginPage(webDriver);
        loginPage.login("madison.green@example.com", "123");

        // madison.green@example.com has blocked jane.smith@example.com,
        // whose events are "Tech Career Fair" and "Fitness Bootcamp"

        SidebarPage sidebarPage = new SidebarPage(webDriver);
        sidebarPage.navigateToAllEvents();

        AllEventsPage allEventsPage = new AllEventsPage(webDriver);

        allEventsPage.searchEvents("Tech Career Fair");
        List<String> names = allEventsPage.getAllEventNames();
        assertTrue(names.isEmpty());

        allEventsPage.searchEvents("Fitness Bootcamp");
        names = allEventsPage.getAllEventNames();
        assertTrue(names.isEmpty());
    }

    @AfterEach
    public void tearDown() {
        //debugging

//        try {
//            Thread.sleep(100000);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }

        webDriver.quit();
    }

    @Autowired
    private DataSource dataSource;

    @BeforeAll
    void runSqlScriptAfterAllTests() throws Exception {
        try (Connection conn = dataSource.getConnection()) {
            List<String> tables = new ArrayList<>();
            try (PreparedStatement ps = conn.prepareStatement("SELECT tablename FROM pg_tables WHERE tableowner = 'admin' AND schemaname = 'public'")) {
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next())
                        tables.add(rs.getString("tablename"));
                }
            }

            if (tables.isEmpty())
                return;

            StringBuilder sql = new StringBuilder("TRUNCATE TABLE ");
            for (int i = 0; i < tables.size(); i++) {
                sql.append("\"").append(tables.get(i)).append("\"");
                if (i < tables.size() - 1)
                    sql.append(", ");
            }
            sql.append(" CASCADE;");

            try (Statement stmt = conn.createStatement()) {
                stmt.execute(sql.toString());
            }

            ScriptUtils.executeSqlScript(conn, new ClassPathResource("entity-insertion.sql"));
        }
    }
}
