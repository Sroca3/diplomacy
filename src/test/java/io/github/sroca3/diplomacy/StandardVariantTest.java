package io.github.sroca3.diplomacy;

import io.github.sroca3.diplomacy.exceptions.CountryOrderMismatchException;
import io.github.sroca3.diplomacy.maps.StandardMapVariant;
import io.github.sroca3.diplomacy.maps.StandardVariantLocation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class StandardVariantTest {

    // Test cases taken from http://web.inter.nl.net/users/L.B.Kruijswijk

    Diplomacy diplomacy = new Diplomacy(StandardMapVariant.getInstance());

    @Test
    @DisplayName("MOVING TO AN AREA THAT IS NOT A NEIGHBOUR")
    public void testCase6_A_1() {
        diplomacy.addUnit(StandardVariantLocation.NORTH_SEA, new Fleet(CountryEnum.ENGLAND));
        diplomacy.beginFirstPhase();
        Order order = diplomacy.parseOrder("F North Sea - Picardy");
        diplomacy.addOrder(order);
        diplomacy.adjudicate();
        assertEquals(OrderStatus.ILLEGAL_ORDER_REPLACED_WITH_HOLD, diplomacy.getPreviousPhase().getOrderById(order.getId()).getStatus());
    }

    @Test
    @DisplayName("MOVE ARMY TO SEA")
    public void testCase6_A_2() {
        diplomacy.addUnit(StandardVariantLocation.LIVERPOOL, new Army(CountryEnum.ENGLAND));
        diplomacy.beginFirstPhase();
        Order order = diplomacy.parseOrder("A Lvp - Irish Sea");
        diplomacy.addOrder(order);
        diplomacy.adjudicate();
        assertEquals(OrderStatus.ILLEGAL_ORDER_REPLACED_WITH_HOLD, diplomacy.getPreviousPhase().getOrderById(order.getId()).getStatus());
    }

    @Test
    @DisplayName("MOVE FLEET TO LAND")
    public void testCase6_A_3() {
        diplomacy.addUnit(StandardVariantLocation.KIEL, new Fleet(CountryEnum.GERMANY));
        diplomacy.beginFirstPhase();
        Order order = diplomacy.parseOrder("F Kiel - Munich");
        diplomacy.addOrder(order);
        diplomacy.adjudicate();
        assertEquals(OrderStatus.ILLEGAL_ORDER_REPLACED_WITH_HOLD, diplomacy.getPreviousPhase().getOrderById(order.getId()).getStatus());
    }

    @Test
    @DisplayName("MOVE TO OWN SECTOR")
    public void testCase6_A_4() {
        diplomacy.addUnit(StandardVariantLocation.KIEL, new Fleet(CountryEnum.GERMANY));
        diplomacy.beginFirstPhase();
        Order order = diplomacy.parseOrder("F Kiel - Kiel");
        diplomacy.addOrder(order);
        diplomacy.adjudicate();
        assertEquals(OrderStatus.ILLEGAL_ORDER_REPLACED_WITH_HOLD, diplomacy.getPreviousPhase().getOrderById(order.getId()).getStatus());
    }

    @Test
    @DisplayName("MOVE TO OWN SECTOR WITH CONVOY")
    public void testCase6_A_5() throws IOException {
        diplomacy.addUnit(StandardVariantLocation.NORTH_SEA, new Fleet(CountryEnum.ENGLAND));
        diplomacy.addUnit(StandardVariantLocation.YORKSHIRE, new Army(CountryEnum.ENGLAND));
        diplomacy.addUnit(StandardVariantLocation.LIVERPOOL, new Army(CountryEnum.ENGLAND));

        diplomacy.addUnit(StandardVariantLocation.LONDON, new Fleet(CountryEnum.GERMANY));
        diplomacy.addUnit(StandardVariantLocation.WALES, new Army(CountryEnum.GERMANY));
        diplomacy.beginFirstPhase();
        List<Order> orders = diplomacy.parseOrders("src/test/resources/test-cases/6_A_5.txt");
        diplomacy.addOrders(orders);
        diplomacy.adjudicate();
        List<Order> englishOrders = diplomacy.getPreviousPhase().getOrdersByCountry(CountryEnum.ENGLAND);
        assertEquals(OrderStatus.ILLEGAL_ORDER_REPLACED_WITH_HOLD, englishOrders.get(0).getStatus());
        assertEquals(OrderStatus.ILLEGAL_ORDER_REPLACED_WITH_HOLD, englishOrders.get(1).getStatus());
        assertEquals(OrderStatus.SUPPORT_FAILED, englishOrders.get(2).getStatus());
        List<Order> germanOrders = diplomacy.getPreviousPhase().getOrdersByCountry(CountryEnum.GERMANY);
        assertEquals(OrderStatus.RESOLVED, germanOrders.get(0).getStatus());
        assertEquals(OrderStatus.RESOLVED, germanOrders.get(1).getStatus());
    }

    @Test
    @DisplayName("ORDERING A UNIT OF ANOTHER COUNTRY")
    public void testCase6_A_6() {
        diplomacy.addUnit(StandardVariantLocation.LONDON, new Fleet(CountryEnum.ENGLAND));
        diplomacy.beginFirstPhase();
        assertThrows(
            CountryOrderMismatchException.class,
            () -> diplomacy.parseOrders("src/test/resources/test-cases/6_A_6.txt")
        );
    }

    @Test
    @DisplayName("ONLY ARMIES CAN BE CONVOYED")
    public void testCase6_A_7() throws IOException {
        diplomacy.addUnit(StandardVariantLocation.LONDON, new Fleet(CountryEnum.ENGLAND));
        diplomacy.addUnit(StandardVariantLocation.NORTH_SEA, new Fleet(CountryEnum.ENGLAND));
        diplomacy.beginFirstPhase();
        List<Order> orders = diplomacy.parseOrders("src/test/resources/test-cases/6_A_7.txt");
        diplomacy.addOrders(orders);
        diplomacy.adjudicate();
        List<Order> englishOrders = diplomacy.getPreviousPhase().getOrdersByCountry(CountryEnum.ENGLAND);
        assertEquals(OrderStatus.ILLEGAL_ORDER_REPLACED_WITH_HOLD, englishOrders.get(0).getStatus());
        assertEquals(OrderStatus.ILLEGAL_ORDER_REPLACED_WITH_HOLD, englishOrders.get(1).getStatus());
    }

    @Test
    @DisplayName("SUPPORT TO HOLD YOURSELF IS NOT POSSIBLE")
    public void testCase6_A_8() throws IOException {
        diplomacy.addUnit(StandardVariantLocation.VENICE, new Army(CountryEnum.ITALY));
        diplomacy.addUnit(StandardVariantLocation.TYROLIA, new Army(CountryEnum.ITALY));
        diplomacy.addUnit(StandardVariantLocation.TRIESTE, new Fleet(CountryEnum.AUSTRIA));
        diplomacy.beginFirstPhase();
        List<Order> orders = diplomacy.parseOrders("src/test/resources/test-cases/6_A_8.txt");
        diplomacy.addOrders(orders);
        diplomacy.adjudicate();
        List<Order> italianOrders = diplomacy.getPreviousPhase().getOrdersByCountry(CountryEnum.ITALY);
        assertEquals(OrderStatus.RESOLVED, italianOrders.get(0).getStatus());
        assertEquals(OrderStatus.RESOLVED, italianOrders.get(1).getStatus());
        List<Order> austrianOrders = diplomacy.getPreviousPhase().getOrdersByCountry(CountryEnum.AUSTRIA);
        assertEquals(OrderStatus.DISLODGED, austrianOrders.get(0).getStatus());
    }

    @Test
    @DisplayName("FLEETS MUST FOLLOW COAST IF NOT ON SEA")
    public void testCase6_A_9() {
        diplomacy.addUnit(StandardVariantLocation.ROME, new Fleet(CountryEnum.GERMANY));
        diplomacy.beginFirstPhase();
        Order order = diplomacy.parseOrder("F Rome - Venice");
        diplomacy.addOrder(order);
        diplomacy.adjudicate();
        assertEquals(OrderStatus.ILLEGAL_ORDER_REPLACED_WITH_HOLD, diplomacy.getPreviousPhase().getOrderById(order.getId()).getStatus());
    }

    @Test
    @DisplayName("SUPPORT ON UNREACHABLE DESTINATION NOT POSSIBLE")
    public void testCase6_A_10() throws IOException {
        diplomacy.addUnit(StandardVariantLocation.APULIA, new Army(CountryEnum.ITALY));
        diplomacy.addUnit(StandardVariantLocation.ROME, new Fleet(CountryEnum.ITALY));
        diplomacy.addUnit(StandardVariantLocation.VENICE, new Army(CountryEnum.AUSTRIA));
        diplomacy.beginFirstPhase();
        List<Order> orders = diplomacy.parseOrders("src/test/resources/test-cases/6_A_10.txt");
        diplomacy.addOrders(orders);
        diplomacy.adjudicate();
        List<Order> italianOrders = diplomacy.getPreviousPhase().getOrdersByCountry(CountryEnum.ITALY);
        assertEquals(OrderStatus.ILLEGAL_ORDER_REPLACED_WITH_HOLD, italianOrders.get(0).getStatus());
        assertEquals(OrderStatus.BOUNCED, italianOrders.get(1).getStatus());
        List<Order> austrianOrders = diplomacy.getPreviousPhase().getOrdersByCountry(CountryEnum.AUSTRIA);
        assertEquals(OrderStatus.RESOLVED, austrianOrders.get(0).getStatus());
    }

    @Test
    @DisplayName("SIMPLE BOUNCE")
    public void testCase6_A_11() {
        diplomacy.addUnit(StandardVariantLocation.VENICE, new Army(CountryEnum.ITALY));
        diplomacy.addUnit(StandardVariantLocation.VIENNA, new Army(CountryEnum.AUSTRIA));
        diplomacy.beginFirstPhase();
        diplomacy.addOrders(
            List.of(
                diplomacy.parseOrder("A Vienna - Tyrolia"),
                diplomacy.parseOrder("A Venice - Tyrolia")
            )
        );
        diplomacy.adjudicate();
        List<Order> italianOrders = diplomacy.getPreviousPhase().getOrdersByCountry(CountryEnum.ITALY);
        assertEquals(OrderStatus.BOUNCED, italianOrders.get(0).getStatus());
        List<Order> austrianOrders = diplomacy.getPreviousPhase().getOrdersByCountry(CountryEnum.AUSTRIA);
        assertEquals(OrderStatus.BOUNCED, austrianOrders.get(0).getStatus());
    }

    @Test
    @DisplayName("BOUNCE OF THREE UNITS")
    public void testCase6_A_12() {
        diplomacy.addUnit(StandardVariantLocation.VENICE, new Army(CountryEnum.ITALY));
        diplomacy.addUnit(StandardVariantLocation.VIENNA, new Army(CountryEnum.AUSTRIA));
        diplomacy.addUnit(StandardVariantLocation.MUNICH, new Army(CountryEnum.GERMANY));
        diplomacy.beginFirstPhase();
        diplomacy.addOrders(
            List.of(
                diplomacy.parseOrder("A Vienna - Tyrolia"),
                diplomacy.parseOrder("A Venice - Tyrolia"),
                diplomacy.parseOrder("A Munich - Tyrolia")
            )
        );
        diplomacy.adjudicate();
        List<Order> italianOrders = diplomacy.getPreviousPhase().getOrdersByCountry(CountryEnum.ITALY);
        assertEquals(OrderStatus.BOUNCED, italianOrders.get(0).getStatus());
        List<Order> austrianOrders = diplomacy.getPreviousPhase().getOrdersByCountry(CountryEnum.AUSTRIA);
        assertEquals(OrderStatus.BOUNCED, austrianOrders.get(0).getStatus());
        List<Order> germanOrders = diplomacy.getPreviousPhase().getOrdersByCountry(CountryEnum.GERMANY);
        assertEquals(OrderStatus.BOUNCED, germanOrders.get(0).getStatus());
    }

    @Test
    @DisplayName("MOVING WITH UNSPECIFIED COAST WHEN COAST IS NECESSARY")
    public void testCase6_B_1() {
        diplomacy.addUnit(StandardVariantLocation.PORTUGAL, new Fleet(CountryEnum.FRANCE));
        diplomacy.beginFirstPhase();
        diplomacy.addOrders(
            List.of(
                diplomacy.parseOrder("F Portugal - Spain")
            )
        );
        diplomacy.adjudicate();
        List<Order> frenchOrders = diplomacy.getPreviousPhase().getOrdersByCountry(CountryEnum.FRANCE);
        assertEquals(OrderStatus.ILLEGAL_ORDER_REPLACED_WITH_HOLD, frenchOrders.get(0).getStatus());
    }

    @Test
    @DisplayName("MOVING WITH UNSPECIFIED COAST WHEN COAST IS NOT NECESSARY")
    public void testCase6_B_2() {
        diplomacy.addUnit(StandardVariantLocation.GASCONY, new Fleet(CountryEnum.FRANCE));
        diplomacy.beginFirstPhase();
        diplomacy.addOrders(
            List.of(
                diplomacy.parseOrder("F Gascony - Spain")
            )
        );
        diplomacy.adjudicate();
        List<Order> frenchOrders = diplomacy.getPreviousPhase().getOrdersByCountry(CountryEnum.FRANCE);
        assertEquals(OrderStatus.RESOLVED, frenchOrders.get(0).getStatus());
    }

    @Test
    @DisplayName("MOVING WITH WRONG COAST WHEN COAST IS NOT NECESSARY")
    public void testCase6_B_3() {
        diplomacy.addUnit(StandardVariantLocation.GASCONY, new Fleet(CountryEnum.FRANCE));
        diplomacy.beginFirstPhase();
        diplomacy.addOrders(
            List.of(
                diplomacy.parseOrder("F Gascony - Spain(sc)")
            )
        );
        diplomacy.adjudicate();
        List<Order> frenchOrders = diplomacy.getPreviousPhase().getOrdersByCountry(CountryEnum.FRANCE);
        assertEquals(OrderStatus.ILLEGAL_ORDER_REPLACED_WITH_HOLD, frenchOrders.get(0).getStatus());
    }

    @Test
    @DisplayName("SUPPORT TO UNREACHABLE COAST ALLOWED")
    public void testCase6_B_4() throws IOException {
        diplomacy.addUnit(StandardVariantLocation.GASCONY, new Fleet(CountryEnum.FRANCE));
        diplomacy.addUnit(StandardVariantLocation.MARSEILLES, new Fleet(CountryEnum.FRANCE));
        diplomacy.addUnit(StandardVariantLocation.WESTERN_MEDITERRANEAN, new Fleet(CountryEnum.ITALY));
        diplomacy.beginFirstPhase();
        List<Order> orders = diplomacy.parseOrders("src/test/resources/test-cases/6_B_4.txt");
        diplomacy.addOrders(orders);
        diplomacy.adjudicate();
        List<Order> frenchOrders = diplomacy.getPreviousPhase().getOrdersByCountry(CountryEnum.FRANCE);
        assertEquals(OrderStatus.RESOLVED, frenchOrders.get(0).getStatus());
        assertEquals(OrderStatus.RESOLVED, frenchOrders.get(1).getStatus());
        List<Order> italianOrders = diplomacy.getPreviousPhase().getOrdersByCountry(CountryEnum.ITALY);
        assertEquals(OrderStatus.BOUNCED, italianOrders.get(0).getStatus());
    }

    @Test
    @DisplayName("SUPPORT FROM UNREACHABLE COAST NOT ALLOWED")
    public void testCase6_B_5() throws IOException {
        diplomacy.addUnit(StandardVariantLocation.MARSEILLES, new Fleet(CountryEnum.FRANCE));
        diplomacy.addUnit(StandardVariantLocation.SPAIN_NC, new Fleet(CountryEnum.FRANCE));
        diplomacy.addUnit(StandardVariantLocation.GULF_OF_LYON, new Fleet(CountryEnum.ITALY));
        diplomacy.beginFirstPhase();
        List<Order> orders = diplomacy.parseOrders("src/test/resources/test-cases/6_B_5.txt");
        diplomacy.addOrders(orders);
        diplomacy.adjudicate();
        List<Order> frenchOrders = diplomacy.getPreviousPhase().getOrdersByCountry(CountryEnum.FRANCE);
        assertEquals(OrderStatus.BOUNCED, frenchOrders.get(0).getStatus());
        assertEquals(OrderStatus.ILLEGAL_ORDER_REPLACED_WITH_HOLD, frenchOrders.get(1).getStatus());
        List<Order> italianOrders = diplomacy.getPreviousPhase().getOrdersByCountry(CountryEnum.ITALY);
        assertEquals(OrderStatus.RESOLVED, italianOrders.get(0).getStatus());
    }

    @Test
    @DisplayName("SUPPORT CAN BE CUT WITH OTHER COAST")
    public void testCase6_B_6() throws IOException {
        diplomacy.addUnit(StandardVariantLocation.IRISH_SEA, new Fleet(CountryEnum.ENGLAND));
        diplomacy.addUnit(StandardVariantLocation.NORTH_ATLANTIC_OCEAN, new Fleet(CountryEnum.ENGLAND));
        diplomacy.addUnit(StandardVariantLocation.SPAIN_NC, new Fleet(CountryEnum.FRANCE));
        diplomacy.addUnit(StandardVariantLocation.MID_ATLANTIC_OCEAN, new Fleet(CountryEnum.FRANCE));
        diplomacy.addUnit(StandardVariantLocation.GULF_OF_LYON, new Fleet(CountryEnum.ITALY));
        diplomacy.beginFirstPhase();
        List<Order> orders = diplomacy.parseOrders("src/test/resources/test-cases/6_B_6.txt");
        diplomacy.addOrders(orders);
        diplomacy.adjudicate();
        List<Order> englishOrder = diplomacy.getPreviousPhase().getOrdersByCountry(CountryEnum.ENGLAND);
        assertEquals(OrderStatus.RESOLVED, englishOrder.get(0).getStatus());
        assertEquals(OrderStatus.RESOLVED, englishOrder.get(1).getStatus());
        List<Order> frenchOrders = diplomacy.getPreviousPhase().getOrdersByCountry(CountryEnum.FRANCE);
        assertEquals(OrderStatus.SUPPORT_CUT, frenchOrders.get(0).getStatus());
        assertEquals(OrderStatus.DISLODGED, frenchOrders.get(1).getStatus());
        List<Order> italianOrders = diplomacy.getPreviousPhase().getOrdersByCountry(CountryEnum.ITALY);
        assertEquals(OrderStatus.BOUNCED, italianOrders.get(0).getStatus());
    }

    @Test
    @DisplayName("SUPPORTING WITH UNSPECIFIED COAST")
    public void testCase6_B_7() throws IOException {
        diplomacy.addUnit(StandardVariantLocation.PORTUGAL, new Fleet(CountryEnum.FRANCE));
        diplomacy.addUnit(StandardVariantLocation.MID_ATLANTIC_OCEAN, new Fleet(CountryEnum.FRANCE));
        diplomacy.addUnit(StandardVariantLocation.GULF_OF_LYON, new Fleet(CountryEnum.ITALY));
        diplomacy.addUnit(StandardVariantLocation.WESTERN_MEDITERRANEAN, new Fleet(CountryEnum.ITALY));
        diplomacy.beginFirstPhase();
        List<Order> orders = diplomacy.parseOrders("src/test/resources/test-cases/6_B_7.txt");
        diplomacy.addOrders(orders);
        diplomacy.adjudicate();
        List<Order> frenchOrders = diplomacy.getPreviousPhase().getOrdersByCountry(CountryEnum.FRANCE);
        assertEquals(OrderStatus.SUPPORT_FAILED, frenchOrders.get(0).getStatus());
        assertEquals(OrderStatus.BOUNCED, frenchOrders.get(1).getStatus());
        List<Order> italianOrders = diplomacy.getPreviousPhase().getOrdersByCountry(CountryEnum.ITALY);
        assertEquals(OrderStatus.SUPPORT_FAILED, italianOrders.get(0).getStatus());
        assertEquals(OrderStatus.BOUNCED, italianOrders.get(1).getStatus());
    }

    @Test
    @DisplayName("SUPPORTING WITH UNSPECIFIED COAST WHEN ONLY ONE COAST IS POSSIBLE")
    public void testCase6_B_8() throws IOException {
        diplomacy.addUnit(StandardVariantLocation.PORTUGAL, new Fleet(CountryEnum.FRANCE));
        diplomacy.addUnit(StandardVariantLocation.GASCONY, new Fleet(CountryEnum.FRANCE));
        diplomacy.addUnit(StandardVariantLocation.GULF_OF_LYON, new Fleet(CountryEnum.ITALY));
        diplomacy.addUnit(StandardVariantLocation.WESTERN_MEDITERRANEAN, new Fleet(CountryEnum.ITALY));
        diplomacy.beginFirstPhase();
        List<Order> orders = diplomacy.parseOrders("src/test/resources/test-cases/6_B_8.txt");
        diplomacy.addOrders(orders);
        diplomacy.adjudicate();
        List<Order> frenchOrders = diplomacy.getPreviousPhase().getOrdersByCountry(CountryEnum.FRANCE);
        assertEquals(OrderStatus.SUPPORT_FAILED, frenchOrders.get(0).getStatus());
        assertEquals(OrderStatus.BOUNCED, frenchOrders.get(1).getStatus());
        List<Order> italianOrders = diplomacy.getPreviousPhase().getOrdersByCountry(CountryEnum.ITALY);
        assertEquals(OrderStatus.SUPPORT_FAILED, italianOrders.get(0).getStatus());
        assertEquals(OrderStatus.BOUNCED, italianOrders.get(1).getStatus());
    }

    @Test
    @DisplayName("SUPPORTING WITH WRONG COAST")
    public void testCase6_B_9() throws IOException {
        diplomacy.addUnit(StandardVariantLocation.PORTUGAL, new Fleet(CountryEnum.FRANCE));
        diplomacy.addUnit(StandardVariantLocation.MID_ATLANTIC_OCEAN, new Fleet(CountryEnum.FRANCE));
        diplomacy.addUnit(StandardVariantLocation.GULF_OF_LYON, new Fleet(CountryEnum.ITALY));
        diplomacy.addUnit(StandardVariantLocation.WESTERN_MEDITERRANEAN, new Fleet(CountryEnum.ITALY));
        diplomacy.beginFirstPhase();
        List<Order> orders = diplomacy.parseOrders("src/test/resources/test-cases/6_B_9.txt");
        diplomacy.addOrders(orders);
        diplomacy.adjudicate();
        List<Order> frenchOrders = diplomacy.getPreviousPhase().getOrdersByCountry(CountryEnum.FRANCE);
        assertEquals(OrderStatus.SUPPORT_FAILED, frenchOrders.get(0).getStatus());
        assertEquals(OrderStatus.BOUNCED, frenchOrders.get(1).getStatus());
        List<Order> italianOrders = diplomacy.getPreviousPhase().getOrdersByCountry(CountryEnum.ITALY);
        assertEquals(OrderStatus.RESOLVED, italianOrders.get(0).getStatus());
        assertEquals(OrderStatus.RESOLVED, italianOrders.get(1).getStatus());
    }

    @Test
    @DisplayName("UNIT ORDERED WITH WRONG COAST")
    public void testCase6_B_10() {
        diplomacy.addUnit(StandardVariantLocation.SPAIN_SC, new Fleet(CountryEnum.FRANCE));
        diplomacy.beginFirstPhase();
        diplomacy.addOrders(List.of(
            diplomacy.parseOrder("F Spain(nc) - Gulf of Lyon")
        ));
        diplomacy.adjudicate();
        List<Order> frenchOrders = diplomacy.getPreviousPhase().getOrdersByCountry(CountryEnum.FRANCE);
        assertEquals(OrderStatus.RESOLVED, frenchOrders.get(0).getStatus());
    }

    @Test
    @DisplayName("COAST CAN NOT BE ORDERED TO CHANGE")
    public void testCase6_B_11() {
        diplomacy.addUnit(StandardVariantLocation.SPAIN_NC, new Fleet(CountryEnum.FRANCE));
        diplomacy.beginFirstPhase();
        diplomacy.addOrders(List.of(
            diplomacy.parseOrder("F Spain(sc) - Gulf of Lyon")
        ));
        diplomacy.adjudicate();
        List<Order> frenchOrders = diplomacy.getPreviousPhase().getOrdersByCountry(CountryEnum.FRANCE);
        assertEquals(OrderStatus.ILLEGAL_ORDER_REPLACED_WITH_HOLD, frenchOrders.get(0).getStatus());
    }

    @Test
    @DisplayName("ARMY MOVEMENT WITH COASTAL SPECIFICATION")
    public void testCase6_B_12() {
        diplomacy.addUnit(StandardVariantLocation.GASCONY, new Army(CountryEnum.FRANCE));
        diplomacy.beginFirstPhase();
        diplomacy.addOrders(List.of(
            diplomacy.parseOrder("A Gascony - Spain(nc)")
        ));
        diplomacy.adjudicate();
        List<Order> frenchOrders = diplomacy.getPreviousPhase().getOrdersByCountry(CountryEnum.FRANCE);
        assertEquals(OrderStatus.RESOLVED, frenchOrders.get(0).getStatus());
    }

    @Test
    @DisplayName("COASTAL CRAWL NOT ALLOWED")
    public void testCase6_B_13() {
        diplomacy.addUnit(StandardVariantLocation.BULGARIA_SC, new Fleet(CountryEnum.TURKEY));
        diplomacy.addUnit(StandardVariantLocation.CONSTANTINOPLE, new Fleet(CountryEnum.TURKEY));
        diplomacy.beginFirstPhase();
        diplomacy.addOrders(List.of(
            diplomacy.parseOrder("F Bulgaria(sc) - Constantinople"),
            diplomacy.parseOrder("F Constantinople - Bulgaria(ec)")
        ));
        diplomacy.adjudicate();
        List<Order> turkishOrders = diplomacy.getPreviousPhase().getOrdersByCountry(CountryEnum.TURKEY);
        assertEquals(OrderStatus.BOUNCED, turkishOrders.get(0).getStatus());
        assertEquals(OrderStatus.BOUNCED, turkishOrders.get(0).getStatus());
    }

    @Test
    @DisplayName("BUILDING WITH UNSPECIFIED COAST")
    public void testCase6_B_14() {
        diplomacy.beginFirstPhase();
        diplomacy.adjudicate();
        diplomacy.adjudicate();
        diplomacy.addOrders(List.of(
            diplomacy.parseOrder("Build F St Petersburg", CountryEnum.RUSSIA)
        ));
        diplomacy.adjudicate();
        List<Order> russianOrders = diplomacy.getPreviousPhase().getOrdersByCountry(CountryEnum.RUSSIA);
        assertEquals(OrderStatus.BUILD_FAILED, russianOrders.get(0).getStatus());
    }

    @Test
    @DisplayName("THREE ARMY CIRCULAR MOVEMENT")
    public void testCase6_C_1() throws IOException {
        diplomacy.addUnit(StandardVariantLocation.ANKARA, new Fleet(CountryEnum.TURKEY));
        diplomacy.addUnit(StandardVariantLocation.CONSTANTINOPLE, new Army(CountryEnum.TURKEY));
        diplomacy.addUnit(StandardVariantLocation.SMYRNA, new Army(CountryEnum.TURKEY));
        diplomacy.beginFirstPhase();
        List<Order> orders = diplomacy.parseOrders("src/test/resources/test-cases/6_C_1.txt");
        diplomacy.addOrders(orders);
        diplomacy.adjudicate();
        List<Order> turkishOrders = diplomacy.getPreviousPhase().getOrdersByCountry(CountryEnum.TURKEY);
        assertEquals(OrderStatus.RESOLVED, turkishOrders.get(0).getStatus());
        assertEquals(OrderStatus.RESOLVED, turkishOrders.get(1).getStatus());
        assertEquals(OrderStatus.RESOLVED, turkishOrders.get(2).getStatus());
    }

    @Test
    @DisplayName("THREE ARMY CIRCULAR MOVEMENT WITH SUPPORT")
    public void testCase6_C_2() throws IOException {
        diplomacy.addUnit(StandardVariantLocation.ANKARA, new Fleet(CountryEnum.TURKEY));
        diplomacy.addUnit(StandardVariantLocation.CONSTANTINOPLE, new Army(CountryEnum.TURKEY));
        diplomacy.addUnit(StandardVariantLocation.SMYRNA, new Army(CountryEnum.TURKEY));
        diplomacy.addUnit(StandardVariantLocation.BULGARIA, new Army(CountryEnum.TURKEY));
        diplomacy.beginFirstPhase();
        List<Order> orders = diplomacy.parseOrders("src/test/resources/test-cases/6_C_2.txt");
        diplomacy.addOrders(orders);
        diplomacy.adjudicate();
        List<Order> turkishOrders = diplomacy.getPreviousPhase().getOrdersByCountry(CountryEnum.TURKEY);
        assertEquals(OrderStatus.RESOLVED, turkishOrders.get(0).getStatus());
        assertEquals(OrderStatus.RESOLVED, turkishOrders.get(1).getStatus());
        assertEquals(OrderStatus.RESOLVED, turkishOrders.get(2).getStatus());
        assertEquals(OrderStatus.RESOLVED, turkishOrders.get(3).getStatus());
    }

    @Test
    @DisplayName("THREE ARMY CIRCULAR MOVEMENT WITH SUPPORT")
    public void testCase6_C_3() throws IOException {
        diplomacy.addUnit(StandardVariantLocation.ANKARA, new Fleet(CountryEnum.TURKEY));
        diplomacy.addUnit(StandardVariantLocation.CONSTANTINOPLE, new Army(CountryEnum.TURKEY));
        diplomacy.addUnit(StandardVariantLocation.SMYRNA, new Army(CountryEnum.TURKEY));
        diplomacy.addUnit(StandardVariantLocation.BULGARIA, new Army(CountryEnum.TURKEY));
        diplomacy.beginFirstPhase();
        List<Order> orders = diplomacy.parseOrders("src/test/resources/test-cases/6_C_3.txt");
        diplomacy.addOrders(orders);
        diplomacy.adjudicate();
        List<Order> turkishOrders = diplomacy.getPreviousPhase().getOrdersByCountry(CountryEnum.TURKEY);
        assertEquals(OrderStatus.BOUNCED, turkishOrders.get(0).getStatus());
        assertEquals(OrderStatus.BOUNCED, turkishOrders.get(1).getStatus());
        assertEquals(OrderStatus.BOUNCED, turkishOrders.get(2).getStatus());
        assertEquals(OrderStatus.BOUNCED, turkishOrders.get(3).getStatus());
    }

    @Test
    @DisplayName("A CIRCULAR MOVEMENT WITH ATTACKED CONVOY")
    public void testCase6_C_4() throws IOException {
        diplomacy.addUnit(StandardVariantLocation.TRIESTE, new Army(CountryEnum.AUSTRIA));
        diplomacy.addUnit(StandardVariantLocation.SERBIA, new Army(CountryEnum.AUSTRIA));
        diplomacy.addUnit(StandardVariantLocation.BULGARIA, new Army(CountryEnum.TURKEY));
        diplomacy.addUnit(StandardVariantLocation.AEGEAN_SEA, new Fleet(CountryEnum.TURKEY));
        diplomacy.addUnit(StandardVariantLocation.IONIAN_SEA, new Fleet(CountryEnum.TURKEY));
        diplomacy.addUnit(StandardVariantLocation.ADRIATIC_SEA, new Fleet(CountryEnum.TURKEY));
        diplomacy.addUnit(StandardVariantLocation.NAPLES, new Fleet(CountryEnum.ITALY));
        diplomacy.beginFirstPhase();
        List<Order> orders = diplomacy.parseOrders("src/test/resources/test-cases/6_C_4.txt");
        diplomacy.addOrders(orders);
        diplomacy.adjudicate();
        List<Order> austrianOrders = diplomacy.getPreviousPhase().getOrdersByCountry(CountryEnum.AUSTRIA);
        assertEquals(OrderStatus.RESOLVED, austrianOrders.get(0).getStatus());
        assertEquals(OrderStatus.RESOLVED, austrianOrders.get(1).getStatus());
        List<Order> turkishOrders = diplomacy.getPreviousPhase().getOrdersByCountry(CountryEnum.TURKEY);
        assertEquals(OrderStatus.RESOLVED, turkishOrders.get(0).getStatus());
        assertEquals(OrderStatus.RESOLVED, turkishOrders.get(1).getStatus());
        assertEquals(OrderStatus.RESOLVED, turkishOrders.get(2).getStatus());
        assertEquals(OrderStatus.RESOLVED, turkishOrders.get(3).getStatus());
        List<Order> italianOrders = diplomacy.getPreviousPhase().getOrdersByCountry(CountryEnum.ITALY);
        assertEquals(OrderStatus.BOUNCED, italianOrders.get(0).getStatus());
    }

    @Test
    @DisplayName("A DISRUPTED CIRCULAR MOVEMENT DUE TO DISLODGED CONVOY")
    public void testCase6_C_5() throws IOException {
        diplomacy.addUnit(StandardVariantLocation.TRIESTE, new Army(CountryEnum.AUSTRIA));
        diplomacy.addUnit(StandardVariantLocation.SERBIA, new Army(CountryEnum.AUSTRIA));
        diplomacy.addUnit(StandardVariantLocation.BULGARIA, new Army(CountryEnum.TURKEY));
        diplomacy.addUnit(StandardVariantLocation.AEGEAN_SEA, new Fleet(CountryEnum.TURKEY));
        diplomacy.addUnit(StandardVariantLocation.IONIAN_SEA, new Fleet(CountryEnum.TURKEY));
        diplomacy.addUnit(StandardVariantLocation.ADRIATIC_SEA, new Fleet(CountryEnum.TURKEY));
        diplomacy.addUnit(StandardVariantLocation.NAPLES, new Fleet(CountryEnum.ITALY));
        diplomacy.addUnit(StandardVariantLocation.TUNIS, new Fleet(CountryEnum.ITALY));
        diplomacy.beginFirstPhase();
        List<Order> orders = diplomacy.parseOrders("src/test/resources/test-cases/6_C_5.txt");
        diplomacy.addOrders(orders);
        diplomacy.adjudicate();
        List<Order> austrianOrders = diplomacy.getPreviousPhase().getOrdersByCountry(CountryEnum.AUSTRIA);
        assertEquals(OrderStatus.BOUNCED, austrianOrders.get(0).getStatus());
        assertEquals(OrderStatus.BOUNCED, austrianOrders.get(1).getStatus());
        List<Order> turkishOrders = diplomacy.getPreviousPhase().getOrdersByCountry(CountryEnum.TURKEY);
        assertEquals(OrderStatus.CONVOY_FAILED, turkishOrders.get(0).getStatus());
        assertEquals(OrderStatus.CONVOY_FAILED, turkishOrders.get(1).getStatus());
        assertEquals(OrderStatus.DISLODGED, turkishOrders.get(2).getStatus());
        assertEquals(OrderStatus.CONVOY_FAILED, turkishOrders.get(3).getStatus());
        List<Order> italianOrders = diplomacy.getPreviousPhase().getOrdersByCountry(CountryEnum.ITALY);
        assertEquals(OrderStatus.RESOLVED, italianOrders.get(0).getStatus());
        assertEquals(OrderStatus.RESOLVED, italianOrders.get(0).getStatus());
    }

    @Test
    @DisplayName("TWO ARMIES WITH TWO CONVOYS")
    public void testCase6_C_6() throws IOException {
        diplomacy.addUnit(StandardVariantLocation.NORTH_SEA, new Fleet(CountryEnum.ENGLAND));
        diplomacy.addUnit(StandardVariantLocation.LONDON, new Army(CountryEnum.ENGLAND));
        diplomacy.addUnit(StandardVariantLocation.ENGLISH_CHANNEL, new Fleet(CountryEnum.FRANCE));
        diplomacy.addUnit(StandardVariantLocation.BELGIUM, new Army(CountryEnum.FRANCE));
        diplomacy.beginFirstPhase();
        List<Order> orders = diplomacy.parseOrders("src/test/resources/test-cases/6_C_6.txt");
        diplomacy.addOrders(orders);
        diplomacy.adjudicate();
        List<Order> englishOrders = diplomacy.getPreviousPhase().getOrdersByCountry(CountryEnum.ENGLAND);
        assertEquals(OrderStatus.RESOLVED, englishOrders.get(0).getStatus());
        assertEquals(OrderStatus.RESOLVED, englishOrders.get(1).getStatus());
        List<Order> frenchOrders = diplomacy.getPreviousPhase().getOrdersByCountry(CountryEnum.FRANCE);
        assertEquals(OrderStatus.RESOLVED, frenchOrders.get(0).getStatus());
        assertEquals(OrderStatus.RESOLVED, frenchOrders.get(1).getStatus());
    }

    @Test
    @DisplayName("DISRUPTED UNIT SWAP")
    public void testCase6_C_7() throws IOException {
        diplomacy.addUnit(StandardVariantLocation.NORTH_SEA, new Fleet(CountryEnum.ENGLAND));
        diplomacy.addUnit(StandardVariantLocation.LONDON, new Army(CountryEnum.ENGLAND));
        diplomacy.addUnit(StandardVariantLocation.ENGLISH_CHANNEL, new Fleet(CountryEnum.FRANCE));
        diplomacy.addUnit(StandardVariantLocation.BELGIUM, new Army(CountryEnum.FRANCE));
        diplomacy.addUnit(StandardVariantLocation.BURGUNDY, new Army(CountryEnum.FRANCE));
        diplomacy.beginFirstPhase();
        List<Order> orders = diplomacy.parseOrders("src/test/resources/test-cases/6_C_7.txt");
        diplomacy.addOrders(orders);
        diplomacy.adjudicate();
        List<Order> englishOrders = diplomacy.getPreviousPhase().getOrdersByCountry(CountryEnum.ENGLAND);
        assertEquals(OrderStatus.RESOLVED, englishOrders.get(0).getStatus());
        assertEquals(OrderStatus.BOUNCED, englishOrders.get(1).getStatus());
        List<Order> frenchOrders = diplomacy.getPreviousPhase().getOrdersByCountry(CountryEnum.FRANCE);
        assertEquals(OrderStatus.RESOLVED, frenchOrders.get(0).getStatus());
        assertEquals(OrderStatus.BOUNCED, frenchOrders.get(1).getStatus());
        assertEquals(OrderStatus.BOUNCED, frenchOrders.get(2).getStatus());
    }

    @Test
    @DisplayName("SUPPORTED HOLD CAN PREVENT DISLODGEMENT")
    public void testCase6_D_1() throws IOException {
        diplomacy.addUnit(StandardVariantLocation.ADRIATIC_SEA, new Fleet(CountryEnum.AUSTRIA));
        diplomacy.addUnit(StandardVariantLocation.TRIESTE, new Army(CountryEnum.AUSTRIA));
        diplomacy.addUnit(StandardVariantLocation.VENICE, new Army(CountryEnum.ITALY));
        diplomacy.addUnit(StandardVariantLocation.TYROLIA, new Army(CountryEnum.ITALY));
        diplomacy.beginFirstPhase();
        List<Order> orders = diplomacy.parseOrders("src/test/resources/test-cases/6_D_1.txt");
        diplomacy.addOrders(orders);
        diplomacy.adjudicate();
        List<Order> austrianOrders = diplomacy.getPreviousPhase().getOrdersByCountry(CountryEnum.AUSTRIA);
        assertEquals(OrderStatus.SUPPORT_FAILED, austrianOrders.get(0).getStatus());
        assertEquals(OrderStatus.BOUNCED, austrianOrders.get(1).getStatus());
        List<Order> italianOrders = diplomacy.getPreviousPhase().getOrdersByCountry(CountryEnum.ITALY);
        assertEquals(OrderStatus.RESOLVED, italianOrders.get(0).getStatus());
        assertEquals(OrderStatus.RESOLVED, italianOrders.get(1).getStatus());
    }

    @Test
    @DisplayName("A MOVE CUTS SUPPORT ON HOLD")
    public void testCase6_D_2() throws IOException {
        diplomacy.addUnit(StandardVariantLocation.ADRIATIC_SEA, new Fleet(CountryEnum.AUSTRIA));
        diplomacy.addUnit(StandardVariantLocation.TRIESTE, new Army(CountryEnum.AUSTRIA));
        diplomacy.addUnit(StandardVariantLocation.VIENNA, new Army(CountryEnum.AUSTRIA));
        diplomacy.addUnit(StandardVariantLocation.VENICE, new Army(CountryEnum.ITALY));
        diplomacy.addUnit(StandardVariantLocation.TYROLIA, new Army(CountryEnum.ITALY));
        diplomacy.beginFirstPhase();
        List<Order> orders = diplomacy.parseOrders("src/test/resources/test-cases/6_D_2.txt");
        diplomacy.addOrders(orders);
        diplomacy.adjudicate();
        List<Order> austrianOrders = diplomacy.getPreviousPhase().getOrdersByCountry(CountryEnum.AUSTRIA);
        assertEquals(OrderStatus.RESOLVED, austrianOrders.get(0).getStatus());
        assertEquals(OrderStatus.RESOLVED, austrianOrders.get(1).getStatus());
        assertEquals(OrderStatus.BOUNCED, austrianOrders.get(2).getStatus());
        List<Order> italianOrders = diplomacy.getPreviousPhase().getOrdersByCountry(CountryEnum.ITALY);
        assertEquals(OrderStatus.DISLODGED, italianOrders.get(0).getStatus());
        assertEquals(OrderStatus.SUPPORT_CUT, italianOrders.get(1).getStatus());
    }

    @Test
    @DisplayName("A MOVE CUTS SUPPORT ON MOVE")
    public void testCase6_D_3() throws IOException {
        diplomacy.addUnit(StandardVariantLocation.ADRIATIC_SEA, new Fleet(CountryEnum.AUSTRIA));
        diplomacy.addUnit(StandardVariantLocation.TRIESTE, new Army(CountryEnum.AUSTRIA));
        diplomacy.addUnit(StandardVariantLocation.VENICE, new Army(CountryEnum.ITALY));
        diplomacy.addUnit(StandardVariantLocation.IONIAN_SEA, new Fleet(CountryEnum.ITALY));
        diplomacy.beginFirstPhase();
        List<Order> orders = diplomacy.parseOrders("src/test/resources/test-cases/6_D_3.txt");
        diplomacy.addOrders(orders);
        diplomacy.adjudicate();
        List<Order> austrianOrders = diplomacy.getPreviousPhase().getOrdersByCountry(CountryEnum.AUSTRIA);
        assertEquals(OrderStatus.SUPPORT_CUT, austrianOrders.get(0).getStatus());
        assertEquals(OrderStatus.BOUNCED, austrianOrders.get(1).getStatus());
        List<Order> italianOrders = diplomacy.getPreviousPhase().getOrdersByCountry(CountryEnum.ITALY);
        assertEquals(OrderStatus.RESOLVED, italianOrders.get(0).getStatus());
        assertEquals(OrderStatus.BOUNCED, italianOrders.get(1).getStatus());
    }

    @Test
    @DisplayName("SUPPORT TO HOLD ON UNIT SUPPORTING A HOLD ALLOWED")
    public void testCase6_D_4() throws IOException {
        diplomacy.addUnit(StandardVariantLocation.BERLIN, new Army(CountryEnum.GERMANY));
        diplomacy.addUnit(StandardVariantLocation.KIEL, new Fleet(CountryEnum.GERMANY));
        diplomacy.addUnit(StandardVariantLocation.BALTIC_SEA, new Fleet(CountryEnum.RUSSIA));
        diplomacy.addUnit(StandardVariantLocation.PRUSSIA, new Army(CountryEnum.RUSSIA));
        diplomacy.beginFirstPhase();
        List<Order> orders = diplomacy.parseOrders("src/test/resources/test-cases/6_D_4.txt");
        diplomacy.addOrders(orders);
        diplomacy.adjudicate();
        List<Order> germanOrders = diplomacy.getPreviousPhase().getOrdersByCountry(CountryEnum.GERMANY);
        assertEquals(OrderStatus.SUPPORT_CUT, germanOrders.get(0).getStatus());
        assertEquals(OrderStatus.RESOLVED, germanOrders.get(1).getStatus());
        List<Order> russianOrders = diplomacy.getPreviousPhase().getOrdersByCountry(CountryEnum.RUSSIA);
        assertEquals(OrderStatus.SUPPORT_FAILED, russianOrders.get(0).getStatus());
        assertEquals(OrderStatus.BOUNCED, russianOrders.get(1).getStatus());
    }

    @Test
    @DisplayName("SUPPORT TO HOLD ON UNIT SUPPORTING A MOVE ALLOWED")
    public void testCase6_D_5() throws IOException {
        diplomacy.addUnit(StandardVariantLocation.BERLIN, new Army(CountryEnum.GERMANY));
        diplomacy.addUnit(StandardVariantLocation.KIEL, new Fleet(CountryEnum.GERMANY));
        diplomacy.addUnit(StandardVariantLocation.MUNICH, new Army(CountryEnum.GERMANY));
        diplomacy.addUnit(StandardVariantLocation.BALTIC_SEA, new Fleet(CountryEnum.RUSSIA));
        diplomacy.addUnit(StandardVariantLocation.PRUSSIA, new Army(CountryEnum.RUSSIA));
        diplomacy.beginFirstPhase();
        List<Order> orders = diplomacy.parseOrders("src/test/resources/test-cases/6_D_5.txt");
        diplomacy.addOrders(orders);
        diplomacy.adjudicate();
        List<Order> germanOrders = diplomacy.getPreviousPhase().getOrdersByCountry(CountryEnum.GERMANY);
        assertEquals(OrderStatus.SUPPORT_CUT, germanOrders.get(0).getStatus());
        assertEquals(OrderStatus.RESOLVED, germanOrders.get(1).getStatus());
        assertEquals(OrderStatus.RESOLVED, germanOrders.get(2).getStatus());
        List<Order> russianOrders = diplomacy.getPreviousPhase().getOrdersByCountry(CountryEnum.RUSSIA);
        assertEquals(OrderStatus.SUPPORT_FAILED, russianOrders.get(0).getStatus());
        assertEquals(OrderStatus.BOUNCED, russianOrders.get(1).getStatus());
    }

    @Test
    @DisplayName("SUPPORT TO HOLD ON CONVOYING UNIT ALLOWED")
    public void testCase6_D_6() throws IOException {
        diplomacy.addUnit(StandardVariantLocation.BERLIN, new Army(CountryEnum.GERMANY));
        diplomacy.addUnit(StandardVariantLocation.BALTIC_SEA, new Fleet(CountryEnum.GERMANY));
        diplomacy.addUnit(StandardVariantLocation.PRUSSIA, new Fleet(CountryEnum.GERMANY));
        diplomacy.addUnit(StandardVariantLocation.LIVONIA, new Fleet(CountryEnum.RUSSIA));
        diplomacy.addUnit(StandardVariantLocation.GULF_OF_BOTHNIA, new Fleet(CountryEnum.RUSSIA));
        diplomacy.beginFirstPhase();
        List<Order> orders = diplomacy.parseOrders("src/test/resources/test-cases/6_D_6.txt");
        diplomacy.addOrders(orders);
        diplomacy.adjudicate();
        List<Order> germanOrders = diplomacy.getPreviousPhase().getOrdersByCountry(CountryEnum.GERMANY);
        assertEquals(OrderStatus.RESOLVED, germanOrders.get(0).getStatus());
        assertEquals(OrderStatus.RESOLVED, germanOrders.get(1).getStatus());
        assertEquals(OrderStatus.RESOLVED, germanOrders.get(2).getStatus());
        List<Order> russianOrders = diplomacy.getPreviousPhase().getOrdersByCountry(CountryEnum.RUSSIA);
        assertEquals(OrderStatus.BOUNCED, russianOrders.get(0).getStatus());
        assertEquals(OrderStatus.SUPPORT_FAILED, russianOrders.get(1).getStatus());
    }

    @Test
    @DisplayName("SUPPORT TO HOLD ON MOVING UNIT NOT ALLOWED")
    public void testCase6_D_7() throws IOException {
        diplomacy.addUnit(StandardVariantLocation.BALTIC_SEA, new Fleet(CountryEnum.GERMANY));
        diplomacy.addUnit(StandardVariantLocation.PRUSSIA, new Fleet(CountryEnum.GERMANY));
        diplomacy.addUnit(StandardVariantLocation.FINLAND, new Army(CountryEnum.RUSSIA));
        diplomacy.addUnit(StandardVariantLocation.LIVONIA, new Fleet(CountryEnum.RUSSIA));
        diplomacy.addUnit(StandardVariantLocation.GULF_OF_BOTHNIA, new Fleet(CountryEnum.RUSSIA));
        diplomacy.beginFirstPhase();
        List<Order> orders = diplomacy.parseOrders("src/test/resources/test-cases/6_D_7.txt");
        diplomacy.addOrders(orders);
        diplomacy.adjudicate();
        List<Order> germanOrders = diplomacy.getPreviousPhase().getOrdersByCountry(CountryEnum.GERMANY);
        assertEquals(OrderStatus.DISLODGED, germanOrders.get(0).getStatus());
        assertEquals(OrderStatus.ILLEGAL_ORDER_REPLACED_WITH_HOLD, germanOrders.get(1).getStatus());
        List<Order> russianOrders = diplomacy.getPreviousPhase().getOrdersByCountry(CountryEnum.RUSSIA);
        assertEquals(OrderStatus.RESOLVED, russianOrders.get(0).getStatus());
        assertEquals(OrderStatus.RESOLVED, russianOrders.get(1).getStatus());
        assertEquals(OrderStatus.BOUNCED, russianOrders.get(2).getStatus());
    }

    @Test
    @DisplayName("FAILED CONVOY CAN NOT RECEIVE HOLD SUPPORT")
    public void testCase6_D_8() throws IOException {
        diplomacy.addUnit(StandardVariantLocation.IONIAN_SEA, new Fleet(CountryEnum.AUSTRIA));
        diplomacy.addUnit(StandardVariantLocation.SERBIA, new Army(CountryEnum.AUSTRIA));
        diplomacy.addUnit(StandardVariantLocation.ALBANIA, new Army(CountryEnum.AUSTRIA));
        diplomacy.addUnit(StandardVariantLocation.GREECE, new Army(CountryEnum.TURKEY));
        diplomacy.addUnit(StandardVariantLocation.BULGARIA, new Army(CountryEnum.TURKEY));
        diplomacy.beginFirstPhase();
        List<Order> orders = diplomacy.parseOrders("src/test/resources/test-cases/6_D_8.txt");
        diplomacy.addOrders(orders);
        diplomacy.adjudicate();
        List<Order> austrianOrders = diplomacy.getPreviousPhase().getOrdersByCountry(CountryEnum.AUSTRIA);
        assertEquals(OrderStatus.RESOLVED, austrianOrders.get(0).getStatus());
        assertEquals(OrderStatus.RESOLVED, austrianOrders.get(1).getStatus());
        assertEquals(OrderStatus.RESOLVED, austrianOrders.get(2).getStatus());
        List<Order> turkishOrders = diplomacy.getPreviousPhase().getOrdersByCountry(CountryEnum.TURKEY);
        assertEquals(OrderStatus.DISLODGED, turkishOrders.get(0).getStatus());
        assertEquals(OrderStatus.ILLEGAL_ORDER_REPLACED_WITH_HOLD, turkishOrders.get(1).getStatus());
    }

    @Test
    @DisplayName("SUPPORT TO MOVE ON HOLDING UNIT NOT ALLOWED")
    public void testCase6_D_9() throws IOException {
        diplomacy.addUnit(StandardVariantLocation.VENICE, new Army(CountryEnum.ITALY));
        diplomacy.addUnit(StandardVariantLocation.TYROLIA, new Army(CountryEnum.ITALY));
        diplomacy.addUnit(StandardVariantLocation.ALBANIA, new Army(CountryEnum.AUSTRIA));
        diplomacy.addUnit(StandardVariantLocation.TRIESTE, new Army(CountryEnum.AUSTRIA));
        diplomacy.beginFirstPhase();
        List<Order> orders = diplomacy.parseOrders("src/test/resources/test-cases/6_D_9.txt");
        diplomacy.addOrders(orders);
        diplomacy.adjudicate();
        List<Order> austrianOrders = diplomacy.getPreviousPhase().getOrdersByCountry(CountryEnum.AUSTRIA);
        assertEquals(OrderStatus.ILLEGAL_ORDER_REPLACED_WITH_HOLD, austrianOrders.get(0).getStatus());
        assertEquals(OrderStatus.DISLODGED, austrianOrders.get(1).getStatus());
        List<Order> italianOrders = diplomacy.getPreviousPhase().getOrdersByCountry(CountryEnum.ITALY);
        assertEquals(OrderStatus.RESOLVED, italianOrders.get(0).getStatus());
        assertEquals(OrderStatus.RESOLVED, italianOrders.get(1).getStatus());
    }

    @Test
    @DisplayName("SELF DISLODGMENT PROHIBITED")
    public void testCase6_D_10() throws IOException {
        diplomacy.addUnit(StandardVariantLocation.BERLIN, new Army(CountryEnum.GERMANY));
        diplomacy.addUnit(StandardVariantLocation.KIEL, new Fleet(CountryEnum.GERMANY));
        diplomacy.addUnit(StandardVariantLocation.MUNICH, new Army(CountryEnum.GERMANY));
        diplomacy.beginFirstPhase();
        List<Order> orders = diplomacy.parseOrders("src/test/resources/test-cases/6_D_10.txt");
        diplomacy.addOrders(orders);
        diplomacy.adjudicate();
        List<Order> germanOrders = diplomacy.getPreviousPhase().getOrdersByCountry(CountryEnum.GERMANY);
        assertEquals(OrderStatus.RESOLVED, germanOrders.get(0).getStatus());
        assertEquals(OrderStatus.BOUNCED, germanOrders.get(1).getStatus());
        assertEquals(OrderStatus.SUPPORT_FAILED, germanOrders.get(2).getStatus());
    }

    @Test
    @DisplayName("NO SELF DISLODGMENT OF RETURNING UNIT")
    public void testCase6_D_11() throws IOException {
        diplomacy.addUnit(StandardVariantLocation.BERLIN, new Army(CountryEnum.GERMANY));
        diplomacy.addUnit(StandardVariantLocation.KIEL, new Fleet(CountryEnum.GERMANY));
        diplomacy.addUnit(StandardVariantLocation.MUNICH, new Army(CountryEnum.GERMANY));
        diplomacy.addUnit(StandardVariantLocation.WARSAW, new Army(CountryEnum.RUSSIA));
        diplomacy.beginFirstPhase();
        List<Order> orders = diplomacy.parseOrders("src/test/resources/test-cases/6_D_11.txt");
        diplomacy.addOrders(orders);
        diplomacy.adjudicate();
        List<Order> germanOrders = diplomacy.getPreviousPhase().getOrdersByCountry(CountryEnum.GERMANY);
        assertEquals(OrderStatus.BOUNCED, germanOrders.get(0).getStatus());
        assertEquals(OrderStatus.BOUNCED, germanOrders.get(1).getStatus());
        assertEquals(OrderStatus.SUPPORT_FAILED, germanOrders.get(2).getStatus());
        List<Order> russianOrders = diplomacy.getPreviousPhase().getOrdersByCountry(CountryEnum.RUSSIA);
        assertEquals(OrderStatus.BOUNCED, russianOrders.get(0).getStatus());
    }

    @Test
    @DisplayName("SUPPORTING A FOREIGN UNIT TO DISLODGE OWN UNIT PROHIBITED")
    public void testCase6_D_12() throws IOException {
        diplomacy.addUnit(StandardVariantLocation.TRIESTE, new Fleet(CountryEnum.AUSTRIA));
        diplomacy.addUnit(StandardVariantLocation.VIENNA, new Army(CountryEnum.AUSTRIA));
        diplomacy.addUnit(StandardVariantLocation.VENICE, new Army(CountryEnum.ITALY));
        diplomacy.beginFirstPhase();
        List<Order> orders = diplomacy.parseOrders("src/test/resources/test-cases/6_D_12.txt");
        diplomacy.addOrders(orders);
        diplomacy.adjudicate();
        List<Order> austrianOrders = diplomacy.getPreviousPhase().getOrdersByCountry(CountryEnum.AUSTRIA);
        assertEquals(OrderStatus.RESOLVED, austrianOrders.get(0).getStatus());
        assertEquals(OrderStatus.SUPPORT_FAILED, austrianOrders.get(1).getStatus());
        List<Order> italianOrders = diplomacy.getPreviousPhase().getOrdersByCountry(CountryEnum.ITALY);
        assertEquals(OrderStatus.BOUNCED, italianOrders.get(0).getStatus());
    }

    @Test
    @DisplayName("SUPPORTING A FOREIGN UNIT TO DISLODGE A RETURNING OWN UNIT PROHIBITED")
    public void testCase6_D_13() throws IOException {
        diplomacy.addUnit(StandardVariantLocation.TRIESTE, new Fleet(CountryEnum.AUSTRIA));
        diplomacy.addUnit(StandardVariantLocation.VIENNA, new Army(CountryEnum.AUSTRIA));
        diplomacy.addUnit(StandardVariantLocation.VENICE, new Army(CountryEnum.ITALY));
        diplomacy.addUnit(StandardVariantLocation.APULIA, new Fleet(CountryEnum.ITALY));
        diplomacy.beginFirstPhase();
        List<Order> orders = diplomacy.parseOrders("src/test/resources/test-cases/6_D_13.txt");
        diplomacy.addOrders(orders);
        diplomacy.adjudicate();
        List<Order> austrianOrders = diplomacy.getPreviousPhase().getOrdersByCountry(CountryEnum.AUSTRIA);
        assertEquals(OrderStatus.BOUNCED, austrianOrders.get(0).getStatus());
        assertEquals(OrderStatus.SUPPORT_FAILED, austrianOrders.get(1).getStatus());
        List<Order> italianOrders = diplomacy.getPreviousPhase().getOrdersByCountry(CountryEnum.ITALY);
        assertEquals(OrderStatus.BOUNCED, italianOrders.get(0).getStatus());
        assertEquals(OrderStatus.BOUNCED, italianOrders.get(1).getStatus());
    }

    @Test
    @DisplayName("NO SUPPORTS DURING RETREAT")
    public void testCase6_H_1() throws IOException {
        diplomacy.addUnit(StandardVariantLocation.TRIESTE, new Fleet(CountryEnum.AUSTRIA));
        diplomacy.addUnit(StandardVariantLocation.SERBIA, new Army(CountryEnum.AUSTRIA));
        diplomacy.addUnit(StandardVariantLocation.GREECE, new Fleet(CountryEnum.TURKEY));
        diplomacy.addUnit(StandardVariantLocation.VENICE, new Army(CountryEnum.ITALY));
        diplomacy.addUnit(StandardVariantLocation.TYROLIA, new Army(CountryEnum.ITALY));
        diplomacy.addUnit(StandardVariantLocation.IONIAN_SEA, new Fleet(CountryEnum.ITALY));
        diplomacy.addUnit(StandardVariantLocation.AEGEAN_SEA, new Fleet(CountryEnum.ITALY));
        diplomacy.beginFirstPhase();
        List<Order> orders = diplomacy.parseOrders("src/test/resources/test-cases/6_H_1.txt");
        diplomacy.addOrders(orders);
        diplomacy.adjudicate();
        List<Order> austrianOrders = diplomacy.getPreviousPhase().getOrdersByCountry(CountryEnum.AUSTRIA);
        assertEquals(OrderStatus.DISLODGED, austrianOrders.get(0).getStatus());
        assertEquals(OrderStatus.RESOLVED, austrianOrders.get(1).getStatus());
        List<Order> turkishOrders = diplomacy.getPreviousPhase().getOrdersByCountry(CountryEnum.TURKEY);
        assertEquals(OrderStatus.DISLODGED, turkishOrders.get(0).getStatus());
        List<Order> italianOrders = diplomacy.getPreviousPhase().getOrdersByCountry(CountryEnum.ITALY);
        assertEquals(OrderStatus.RESOLVED, italianOrders.get(0).getStatus());
        assertEquals(OrderStatus.RESOLVED, italianOrders.get(1).getStatus());
        assertEquals(OrderStatus.RESOLVED, italianOrders.get(2).getStatus());
        assertEquals(OrderStatus.RESOLVED, italianOrders.get(3).getStatus());

        diplomacy.addOrders(List.of(
            diplomacy.parseOrder("F Trieste - Albania"),
            diplomacy.parseOrder("A Serbia Supports F Trieste - Albania"),
            diplomacy.parseOrder("F Greece - Albania")
        ));
        diplomacy.adjudicate();
        austrianOrders = diplomacy.getPreviousPhase().getOrdersByCountry(CountryEnum.AUSTRIA);
        assertEquals(1, austrianOrders.size());
        assertEquals(OrderStatus.BOUNCED, austrianOrders.get(0).getStatus());
        turkishOrders = diplomacy.getPreviousPhase().getOrdersByCountry(CountryEnum.TURKEY);
        assertEquals(1, turkishOrders.size());
        assertEquals(OrderStatus.BOUNCED, turkishOrders.get(0).getStatus());
        assertNull(diplomacy.getCurrentPhase().getResultingUnitLocations().get(StandardVariantLocation.TRIESTE));
        assertNull(diplomacy.getCurrentPhase().getResultingUnitLocations().get(StandardVariantLocation.GREECE));
    }

    @Test
    @DisplayName("NO OTHER MOVES DURING RETREAT")
    public void testCase6_H_4() throws IOException {
        diplomacy.addUnit(StandardVariantLocation.NORTH_SEA, new Fleet(CountryEnum.ENGLAND));
        diplomacy.addUnit(StandardVariantLocation.HOLLAND, new Army(CountryEnum.ENGLAND));
        diplomacy.addUnit(StandardVariantLocation.KIEL, new Fleet(CountryEnum.GERMANY));
        diplomacy.addUnit(StandardVariantLocation.RUHR, new Army(CountryEnum.GERMANY));
        diplomacy.beginFirstPhase();
        diplomacy.addOrders(diplomacy.parseOrders("src/test/resources/test-cases/6_H_4.txt"));
        diplomacy.adjudicate();
        diplomacy.addOrders(List.of(
            diplomacy.parseOrder("A Holland - Belgium", CountryEnum.ENGLAND),
            diplomacy.parseOrder("F North Sea - Norwegian Sea", CountryEnum.ENGLAND)
        ));
        diplomacy.adjudicate();
        List<Order> englishOrders = diplomacy.getPreviousPhase().getOrdersByCountry(CountryEnum.ENGLAND);
        assertEquals(OrderStatus.RESOLVED, englishOrders.get(0).getStatus());
        assertEquals(OrderStatus.ILLEGAL, englishOrders.get(1).getStatus());
        assertNotNull(diplomacy.getPreviousPhase().getResultingUnitLocations().get(StandardVariantLocation.BELGIUM));
    }

    @Test
    @DisplayName("A UNIT MAY NOT RETREAT TO THE AREA FROM WHICH IT IS ATTACKED")
    public void testCase6_H_5() {
        diplomacy.addUnit(StandardVariantLocation.CONSTANTINOPLE, new Fleet(CountryEnum.RUSSIA));
        diplomacy.addUnit(StandardVariantLocation.BLACK_SEA, new Fleet(CountryEnum.RUSSIA));
        diplomacy.addUnit(StandardVariantLocation.ANKARA, new Fleet(CountryEnum.TURKEY));
        diplomacy.beginFirstPhase();
        diplomacy.addOrders(List.of(
            diplomacy.parseOrder("F Constantinople Supports F Black Sea - Ankara", CountryEnum.RUSSIA),
            diplomacy.parseOrder("F Black Sea - Ankara", CountryEnum.RUSSIA),
            diplomacy.parseOrder("F Ankara Hold", CountryEnum.TURKEY)
        ));
        diplomacy.adjudicate();
        diplomacy.addOrders(List.of(
            diplomacy.parseOrder("F Ankara - Black Sea", CountryEnum.TURKEY)
        ));
        diplomacy.adjudicate();
        List<Order> englishOrders = diplomacy.getPreviousPhase().getOrdersByCountry(CountryEnum.TURKEY);
        assertEquals(OrderStatus.DISBANDED, englishOrders.get(0).getStatus());
        Unit unit = diplomacy.getPreviousPhase().getResultingUnitLocations().get(StandardVariantLocation.ANKARA);
        assertEquals(CountryEnum.RUSSIA, unit.getCountry());
    }

    @Test
    @DisplayName("UNIT MAY NOT RETREAT TO A CONTESTED AREA")
    public void testCase6_H_6() throws IOException {
        diplomacy.addUnit(StandardVariantLocation.BUDAPEST, new Army(CountryEnum.AUSTRIA));
        diplomacy.addUnit(StandardVariantLocation.TRIESTE, new Army(CountryEnum.AUSTRIA));
        diplomacy.addUnit(StandardVariantLocation.MUNICH, new Army(CountryEnum.GERMANY));
        diplomacy.addUnit(StandardVariantLocation.SILESIA, new Army(CountryEnum.GERMANY));
        diplomacy.addUnit(StandardVariantLocation.VIENNA, new Army(CountryEnum.ITALY));
        diplomacy.beginFirstPhase();
        diplomacy.addOrders(diplomacy.parseOrders("src/test/resources/test-cases/6_H_6.txt"));
        diplomacy.adjudicate();
        diplomacy.addOrders(List.of(
            diplomacy.parseOrder("A Vienna - Bohemia", CountryEnum.ITALY)
        ));
        diplomacy.adjudicate();
        List<Order> italianOrders = diplomacy.getPreviousPhase().getOrdersByCountry(CountryEnum.ITALY);
        assertEquals(OrderStatus.DISBANDED, italianOrders.get(0).getStatus());
    }

    @Test
    @DisplayName("MULTIPLE RETREAT TO SAME AREA WILL DISBAND UNITS")
    public void testCase6_H_7() throws IOException {
        diplomacy.addUnit(StandardVariantLocation.BUDAPEST, new Army(CountryEnum.AUSTRIA));
        diplomacy.addUnit(StandardVariantLocation.TRIESTE, new Army(CountryEnum.AUSTRIA));
        diplomacy.addUnit(StandardVariantLocation.MUNICH, new Army(CountryEnum.GERMANY));
        diplomacy.addUnit(StandardVariantLocation.SILESIA, new Army(CountryEnum.GERMANY));
        diplomacy.addUnit(StandardVariantLocation.VIENNA, new Army(CountryEnum.ITALY));
        diplomacy.addUnit(StandardVariantLocation.BOHEMIA, new Army(CountryEnum.ITALY));
        diplomacy.beginFirstPhase();
        diplomacy.addOrders(diplomacy.parseOrders("src/test/resources/test-cases/6_H_7.txt"));
        diplomacy.adjudicate();
        diplomacy.addOrders(List.of(
            diplomacy.parseOrder("A Bohemia - Tyrolia", CountryEnum.ITALY),
            diplomacy.parseOrder("A Vienna - Tyrolia", CountryEnum.ITALY)
        ));
        diplomacy.adjudicate();
        List<Order> italianOrders = diplomacy.getPreviousPhase().getOrdersByCountry(CountryEnum.ITALY);
        assertEquals(OrderStatus.BOUNCED, italianOrders.get(0).getStatus());
        assertEquals(OrderStatus.BOUNCED, italianOrders.get(1).getStatus());
    }

    @Test
    @DisplayName("TRIPLE RETREAT TO SAME AREA WILL DISBAND UNITS")
    public void testCase6_H_8() throws IOException {
        diplomacy.addUnit(StandardVariantLocation.LIVERPOOL, new Army(CountryEnum.ENGLAND));
        diplomacy.addUnit(StandardVariantLocation.YORKSHIRE, new Fleet(CountryEnum.ENGLAND));
        diplomacy.addUnit(StandardVariantLocation.NORWAY, new Fleet(CountryEnum.ENGLAND));
        diplomacy.addUnit(StandardVariantLocation.KIEL, new Army(CountryEnum.GERMANY));
        diplomacy.addUnit(StandardVariantLocation.RUHR, new Army(CountryEnum.GERMANY));
        diplomacy.addUnit(StandardVariantLocation.EDINBURGH, new Fleet(CountryEnum.RUSSIA));
        diplomacy.addUnit(StandardVariantLocation.SWEDEN, new Army(CountryEnum.RUSSIA));
        diplomacy.addUnit(StandardVariantLocation.FINLAND, new Army(CountryEnum.RUSSIA));
        diplomacy.addUnit(StandardVariantLocation.HOLLAND, new Fleet(CountryEnum.RUSSIA));
        diplomacy.beginFirstPhase();
        diplomacy.addOrders(diplomacy.parseOrders("src/test/resources/test-cases/6_H_8.txt"));
        diplomacy.adjudicate();
        diplomacy.addOrders(List.of(
            diplomacy.parseOrder("F Norway - North Sea", CountryEnum.ENGLAND),
            diplomacy.parseOrder("F Edinburgh - North Sea", CountryEnum.RUSSIA),
            diplomacy.parseOrder("F Holland - North Sea", CountryEnum.RUSSIA)
        ));
        diplomacy.adjudicate();
        List<Order> russianOrders = diplomacy.getPreviousPhase().getOrdersByCountry(CountryEnum.RUSSIA);
        assertEquals(OrderStatus.BOUNCED, russianOrders.get(0).getStatus());
        assertEquals(OrderStatus.BOUNCED, russianOrders.get(1).getStatus());
        List<Order> englishOrders = diplomacy.getPreviousPhase().getOrdersByCountry(CountryEnum.ENGLAND);
        assertEquals(OrderStatus.BOUNCED, englishOrders.get(0).getStatus());
    }

    @Test
    @DisplayName("DISLODGED UNIT WILL NOT MAKE ATTACKERS AREA CONTESTED")
    public void testCase6_H_9() throws IOException {
        diplomacy.addUnit(StandardVariantLocation.HELGOLAND_BIGHT, new Fleet(CountryEnum.ENGLAND));
        diplomacy.addUnit(StandardVariantLocation.DENMARK, new Fleet(CountryEnum.ENGLAND));
        diplomacy.addUnit(StandardVariantLocation.BERLIN, new Army(CountryEnum.GERMANY));
        diplomacy.addUnit(StandardVariantLocation.KIEL, new Fleet(CountryEnum.GERMANY));
        diplomacy.addUnit(StandardVariantLocation.SILESIA, new Army(CountryEnum.GERMANY));
        diplomacy.addUnit(StandardVariantLocation.PRUSSIA, new Army(CountryEnum.RUSSIA));
        diplomacy.beginFirstPhase();
        diplomacy.addOrders(diplomacy.parseOrders("src/test/resources/test-cases/6_H_9.txt"));
        diplomacy.adjudicate();
        diplomacy.addOrders(List.of(
            diplomacy.parseOrder("F Kiel - Berlin", CountryEnum.GERMANY)
        ));
        diplomacy.adjudicate();
        List<Order> germanOrders = diplomacy.getPreviousPhase().getOrdersByCountry(CountryEnum.GERMANY);
        assertEquals(OrderStatus.RESOLVED, germanOrders.get(0).getStatus());
    }

    @Test
    @DisplayName("TOO MANY BUILD ORDERS")
    public void testCase6_I_1() {
        diplomacy.addStandardStartingUnits();
        diplomacy.beginFirstPhase();
        diplomacy.addOrders(List.of(diplomacy.parseOrder("F Kiel Move Holland")));
        diplomacy.adjudicate();
        diplomacy.adjudicate();
        diplomacy.addOrders(List.of(
            diplomacy.parseOrder("Build A Warsaw", CountryEnum.GERMANY),
            diplomacy.parseOrder("Build A Kiel", CountryEnum.GERMANY),
            diplomacy.parseOrder("Build A Munich", CountryEnum.GERMANY)
        ));
        diplomacy.adjudicate();
        List<Order> germanOrders = diplomacy.getPreviousPhase().getOrdersByCountry(CountryEnum.GERMANY);
        assertEquals(OrderStatus.BUILD_FAILED, germanOrders.get(0).getStatus());
        assertEquals(OrderStatus.RESOLVED, germanOrders.get(1).getStatus());
        assertEquals(OrderStatus.BUILD_FAILED, germanOrders.get(2).getStatus());
    }

    @Test
    @DisplayName("FLEETS CAN NOT BE BUILT IN LAND AREAS")
    public void testCase6_I_2() {
        diplomacy.addStandardStartingUnits();
        diplomacy.setCurrentPhase(new Phase(
            new HashMap<>(),
            diplomacy.getMapVariant(),
            diplomacy.getMapVariant().getMovementGraph(),
            PhaseName.WINTER_BUILD,
            Map.of(StandardVariantLocation.MOSCOW, CountryEnum.RUSSIA),
            1835));
        diplomacy.addOrders(List.of(
            diplomacy.parseOrder("Build F Moscow", CountryEnum.RUSSIA)
        ));
        diplomacy.adjudicate();
        List<Order> russianOrders = diplomacy.getPreviousPhase().getOrdersByCountry(CountryEnum.RUSSIA);
        assertEquals(OrderStatus.BUILD_FAILED, russianOrders.get(0).getStatus());
    }

    @Test
    @DisplayName("SUPPLY CENTER MUST BE EMPTY FOR BUILDING")
    public void testCase6_I_3() {
        diplomacy.addStandardStartingUnits();
        diplomacy.addUnit(StandardVariantLocation.BERLIN, new Army(CountryEnum.GERMANY));
        diplomacy.setCurrentPhase(new Phase(
            Map.of(StandardVariantLocation.BERLIN, new Army(CountryEnum.GERMANY)),
            diplomacy.getMapVariant(),
            diplomacy.getMapVariant().getMovementGraph(),
            PhaseName.WINTER_BUILD,
            Map.of(StandardVariantLocation.BERLIN, CountryEnum.GERMANY,
            StandardVariantLocation.KIEL, CountryEnum.GERMANY),
            1835
            ));
        diplomacy.addOrders(List.of(
            diplomacy.parseOrder("Build A Berlin", CountryEnum.GERMANY)
        ));
        diplomacy.adjudicate();
        List<Order> germanOrders = diplomacy.getPreviousPhase().getOrdersByCountry(CountryEnum.GERMANY);
        assertEquals(OrderStatus.BUILD_FAILED, germanOrders.get(0).getStatus());
    }
}
