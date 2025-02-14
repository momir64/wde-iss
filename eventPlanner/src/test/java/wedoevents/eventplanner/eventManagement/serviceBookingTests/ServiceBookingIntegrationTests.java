package wedoevents.eventplanner.eventManagement.serviceBookingTests;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;

public class ServiceBookingIntegrationTests {
    // todo: pogledaj kako sam ja uradio integration testove za proizvode,
    // todo: tamo se nalazi sva logika za ove testove, samo za proizvode
    // todo: samo ne znam rezervaciju servisa, a ovi "moji" testovi zahtevaju to
    // todo: pa ih nisam uradio, note: trebas dodati entitete u bazu
    // todo: koji se poklapaju za ove testne slucajeve
    @Test
    @Transactional
    void testBuyServiceWhileHavingServiceBudgetItemSuccessful() {

    }

    @Test
    @Transactional
    void testBuyServiceWithoutServiceBudgetItemSuccessful() {

    }

    @Test
    @Transactional
    void testBuyServiceWhileHavingServiceBudgetItemButPriceTooMuch() {

    }

    @Test
    @Transactional
    void testDeletePlannedServiceBudgetItemWithReservedService() {

    }
}
