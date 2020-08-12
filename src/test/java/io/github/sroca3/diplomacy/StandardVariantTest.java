package io.github.sroca3.diplomacy;

import io.github.sroca3.diplomacy.maps.StandardMapVariant;
import io.github.sroca3.diplomacy.maps.StandardVariantLocation;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StandardVariantTest {

    // Test cases taken from http://web.inter.nl.net/users/L.B.Kruijswijk

    Diplomacy diplomacy = new Diplomacy(StandardMapVariant.getInstance());

    @Test
    @DisplayName("MOVING TO AN AREA THAT IS NOT A NEIGHBOUR")
    public void testCase6_A_1() {
        diplomacy.addUnit(StandardVariantLocation.NORTH_SEA, new Fleet(Country.ENGLAND));
        diplomacy.beginFirstPhase();
        Order order = diplomacy.parseOrder("F North Sea - Picardy");
        diplomacy.addOrder(order);
        diplomacy.adjudicate();
        assertEquals(OrderStatus.ILLEGAL_ORDER_REPLACED_WITH_HOLD, diplomacy.getPreviousPhase().getOrderById(order.getId()).getStatus());
    }

    @Test
    @DisplayName("MOVE ARMY TO SEA")
    public void testCase6_A_2() {
        diplomacy.addUnit(StandardVariantLocation.LIVERPOOL, new Army(Country.ENGLAND));
        diplomacy.beginFirstPhase();
        Order order = diplomacy.parseOrder("A Lvp - Irish Sea");
        diplomacy.addOrder(order);
        diplomacy.adjudicate();
        assertEquals(OrderStatus.ILLEGAL_ORDER_REPLACED_WITH_HOLD, diplomacy.getPreviousPhase().getOrderById(order.getId()).getStatus());
    }

    @Test
    @DisplayName("MOVE FLEET TO LAND")
    public void testCase6_A_3() {
        diplomacy.addUnit(StandardVariantLocation.KIEL, new Fleet(Country.GERMANY));
        diplomacy.beginFirstPhase();
        Order order = diplomacy.parseOrder("F Kiel - Munich");
        diplomacy.addOrder(order);
        diplomacy.adjudicate();
        assertEquals(OrderStatus.ILLEGAL_ORDER_REPLACED_WITH_HOLD, diplomacy.getPreviousPhase().getOrderById(order.getId()).getStatus());
    }

    @Test
    @DisplayName("MOVE TO OWN SECTOR")
    public void testCase6_A_4() {
        diplomacy.addUnit(StandardVariantLocation.KIEL, new Fleet(Country.GERMANY));
        diplomacy.beginFirstPhase();
        Order order = diplomacy.parseOrder("F Kiel - Kiel");
        diplomacy.addOrder(order);
        diplomacy.adjudicate();
        assertEquals(OrderStatus.ILLEGAL_ORDER_REPLACED_WITH_HOLD, diplomacy.getPreviousPhase().getOrderById(order.getId()).getStatus());
    }

    @Test
    @DisplayName("MOVE TO OWN SECTOR WITH CONVOY")
    public void testCase6_A_5() throws IOException {
        diplomacy.addUnit(StandardVariantLocation.NORTH_SEA, new Fleet(Country.ENGLAND));
        diplomacy.addUnit(StandardVariantLocation.YORKSHIRE, new Army(Country.ENGLAND));
        diplomacy.addUnit(StandardVariantLocation.LIVERPOOL, new Army(Country.ENGLAND));

        diplomacy.addUnit(StandardVariantLocation.LONDON, new Fleet(Country.GERMANY));
        diplomacy.addUnit(StandardVariantLocation.WALES, new Army(Country.GERMANY));
        diplomacy.beginFirstPhase();
        List<Order> orders = diplomacy.parseOrders("src/test/resources/test-cases/6_A_5.txt");
        diplomacy.addOrders(orders);
        diplomacy.adjudicate();
        List<Order> englishOrders = diplomacy.getPreviousPhase().getOrdersByCountry(Country.ENGLAND);
        assertEquals(OrderStatus.ILLEGAL_ORDER_REPLACED_WITH_HOLD, englishOrders.get(0).getStatus());
        assertEquals(OrderStatus.ILLEGAL_ORDER_REPLACED_WITH_HOLD, englishOrders.get(1).getStatus());
        assertEquals(OrderStatus.SUPPORT_FAILED, englishOrders.get(2).getStatus());
        List<Order> germanOrders = diplomacy.getPreviousPhase().getOrdersByCountry(Country.GERMANY);
        assertEquals(OrderStatus.RESOLVED, germanOrders.get(0).getStatus());
        assertEquals(OrderStatus.RESOLVED, germanOrders.get(1).getStatus());
    }

    @Test
    @Disabled
    @DisplayName("ORDERING A UNIT OF ANOTHER COUNTRY")
    public void testCase6_A_6() throws IOException {
        // TODO
    }

    @Test
    @DisplayName("ONLY ARMIES CAN BE CONVOYED")
    public void testCase6_A_7() throws IOException {
        diplomacy.addUnit(StandardVariantLocation.LONDON, new Fleet(Country.ENGLAND));
        diplomacy.addUnit(StandardVariantLocation.NORTH_SEA, new Fleet(Country.ENGLAND));
        diplomacy.beginFirstPhase();
        List<Order> orders = diplomacy.parseOrders("src/test/resources/test-cases/6_A_7.txt");
        diplomacy.addOrders(orders);
        diplomacy.adjudicate();
        List<Order> englishOrders = diplomacy.getPreviousPhase().getOrdersByCountry(Country.ENGLAND);
        assertEquals(OrderStatus.ILLEGAL_ORDER_REPLACED_WITH_HOLD, englishOrders.get(0).getStatus());
        assertEquals(OrderStatus.ILLEGAL_ORDER_REPLACED_WITH_HOLD, englishOrders.get(1).getStatus());
    }

    @Test
    @DisplayName("SUPPORT TO HOLD YOURSELF IS NOT POSSIBLE")
    public void testCase6_A_8() throws IOException {
        diplomacy.addUnit(StandardVariantLocation.VENICE, new Army(Country.ITALY));
        diplomacy.addUnit(StandardVariantLocation.TYROLIA, new Army(Country.ITALY));
        diplomacy.addUnit(StandardVariantLocation.TRIESTE, new Fleet(Country.AUSTRIA));
        diplomacy.beginFirstPhase();
        List<Order> orders = diplomacy.parseOrders("src/test/resources/test-cases/6_A_8.txt");
        diplomacy.addOrders(orders);
        diplomacy.adjudicate();
        List<Order> italianOrders = diplomacy.getPreviousPhase().getOrdersByCountry(Country.ITALY);
        assertEquals(OrderStatus.RESOLVED, italianOrders.get(0).getStatus());
        assertEquals(OrderStatus.RESOLVED, italianOrders.get(1).getStatus());
        List<Order> austrianOrders = diplomacy.getPreviousPhase().getOrdersByCountry(Country.AUSTRIA);
        assertEquals(OrderStatus.DISLODGED, austrianOrders.get(0).getStatus());
    }

    @Test
    @DisplayName("FLEETS MUST FOLLOW COAST IF NOT ON SEA")
    public void testCase6_A_9() {
        diplomacy.addUnit(StandardVariantLocation.ROME, new Fleet(Country.GERMANY));
        diplomacy.beginFirstPhase();
        Order order = diplomacy.parseOrder("F Rome - Venice");
        diplomacy.addOrder(order);
        diplomacy.adjudicate();
        assertEquals(OrderStatus.ILLEGAL_ORDER_REPLACED_WITH_HOLD, diplomacy.getPreviousPhase().getOrderById(order.getId()).getStatus());
    }

    @Test
    @DisplayName("SUPPORT ON UNREACHABLE DESTINATION NOT POSSIBLE")
    public void testCase6_A_10() throws IOException {
        diplomacy.addUnit(StandardVariantLocation.APULIA, new Army(Country.ITALY));
        diplomacy.addUnit(StandardVariantLocation.ROME, new Fleet(Country.ITALY));
        diplomacy.addUnit(StandardVariantLocation.VENICE, new Army(Country.AUSTRIA));
        diplomacy.beginFirstPhase();
        List<Order> orders = diplomacy.parseOrders("src/test/resources/test-cases/6_A_10.txt");
        diplomacy.addOrders(orders);
        diplomacy.adjudicate();
        List<Order> italianOrders = diplomacy.getPreviousPhase().getOrdersByCountry(Country.ITALY);
        assertEquals(OrderStatus.SUPPORT_FAILED, italianOrders.get(0).getStatus());
        assertEquals(OrderStatus.BOUNCED, italianOrders.get(1).getStatus());
        List<Order> austrianOrders = diplomacy.getPreviousPhase().getOrdersByCountry(Country.AUSTRIA);
        assertEquals(OrderStatus.RESOLVED, austrianOrders.get(0).getStatus());
    }

    @Test
    @DisplayName("SIMPLE BOUNCE")
    public void testCase6_A_11() {
        diplomacy.addUnit(StandardVariantLocation.VENICE, new Army(Country.ITALY));
        diplomacy.addUnit(StandardVariantLocation.VIENNA, new Army(Country.AUSTRIA));
        diplomacy.beginFirstPhase();
        diplomacy.addOrders(
            List.of(
                diplomacy.parseOrder("A Vienna - Tyrolia"),
                diplomacy.parseOrder("A Venice - Tyrolia")
            )
        );
        diplomacy.adjudicate();
        List<Order> italianOrders = diplomacy.getPreviousPhase().getOrdersByCountry(Country.ITALY);
        assertEquals(OrderStatus.BOUNCED, italianOrders.get(0).getStatus());
        List<Order> austrianOrders = diplomacy.getPreviousPhase().getOrdersByCountry(Country.AUSTRIA);
        assertEquals(OrderStatus.BOUNCED, austrianOrders.get(0).getStatus());
    }

    @Test
    @DisplayName("BOUNCE OF THREE UNITS")
    public void testCase6_A_12() {
        diplomacy.addUnit(StandardVariantLocation.VENICE, new Army(Country.ITALY));
        diplomacy.addUnit(StandardVariantLocation.VIENNA, new Army(Country.AUSTRIA));
        diplomacy.addUnit(StandardVariantLocation.MUNICH, new Army(Country.GERMANY));
        diplomacy.beginFirstPhase();
        diplomacy.addOrders(
            List.of(
                diplomacy.parseOrder("A Vienna - Tyrolia"),
                diplomacy.parseOrder("A Venice - Tyrolia"),
                diplomacy.parseOrder("A Munich - Tyrolia")
            )
        );
        diplomacy.adjudicate();
        List<Order> italianOrders = diplomacy.getPreviousPhase().getOrdersByCountry(Country.ITALY);
        assertEquals(OrderStatus.BOUNCED, italianOrders.get(0).getStatus());
        List<Order> austrianOrders = diplomacy.getPreviousPhase().getOrdersByCountry(Country.AUSTRIA);
        assertEquals(OrderStatus.BOUNCED, austrianOrders.get(0).getStatus());
        List<Order> germanOrders = diplomacy.getPreviousPhase().getOrdersByCountry(Country.GERMANY);
        assertEquals(OrderStatus.BOUNCED, germanOrders.get(0).getStatus());
    }

    @Test
    @DisplayName("MOVING WITH UNSPECIFIED COAST WHEN COAST IS NECESSARY")
    public void testCase6_B_1() {
        diplomacy.addUnit(StandardVariantLocation.PORTUGAL, new Fleet(Country.FRANCE));
        diplomacy.beginFirstPhase();
        diplomacy.addOrders(
            List.of(
                diplomacy.parseOrder("F Portugal - Spain")
            )
        );
        diplomacy.adjudicate();
        List<Order> frenchOrders = diplomacy.getPreviousPhase().getOrdersByCountry(Country.FRANCE);
        assertEquals(OrderStatus.ILLEGAL_ORDER_REPLACED_WITH_HOLD, frenchOrders.get(0).getStatus());
    }

    @Test
    @DisplayName("MOVING WITH UNSPECIFIED COAST WHEN COAST IS NOT NECESSARY")
    public void testCase6_B_2() {
        diplomacy.addUnit(StandardVariantLocation.GASCONY, new Fleet(Country.FRANCE));
        diplomacy.beginFirstPhase();
        diplomacy.addOrders(
            List.of(
                diplomacy.parseOrder("F Gascony - Spain")
            )
        );
        diplomacy.adjudicate();
        List<Order> frenchOrders = diplomacy.getPreviousPhase().getOrdersByCountry(Country.FRANCE);
        assertEquals(OrderStatus.RESOLVED, frenchOrders.get(0).getStatus());
    }

    @Test
    @DisplayName("MOVING WITH WRONG COAST WHEN COAST IS NOT NECESSARY")
    public void testCase6_B_3() {
        diplomacy.addUnit(StandardVariantLocation.GASCONY, new Fleet(Country.FRANCE));
        diplomacy.beginFirstPhase();
        diplomacy.addOrders(
            List.of(
                diplomacy.parseOrder("F Gascony - Spain(sc)")
            )
        );
        diplomacy.adjudicate();
        List<Order> frenchOrders = diplomacy.getPreviousPhase().getOrdersByCountry(Country.FRANCE);
        assertEquals(OrderStatus.ILLEGAL_ORDER_REPLACED_WITH_HOLD, frenchOrders.get(0).getStatus());
    }

    @Test
    @DisplayName("SUPPORT TO UNREACHABLE COAST ALLOWED")
    public void testCase6_B_4() throws IOException {
        diplomacy.addUnit(StandardVariantLocation.GASCONY, new Fleet(Country.FRANCE));
        diplomacy.addUnit(StandardVariantLocation.MARSEILLES, new Fleet(Country.FRANCE));
        diplomacy.addUnit(StandardVariantLocation.WESTERN_MEDITERRANEAN, new Fleet(Country.ITALY));
        diplomacy.beginFirstPhase();
        List<Order> orders = diplomacy.parseOrders("src/test/resources/test-cases/6_B_4.txt");
        diplomacy.addOrders(orders);
        diplomacy.adjudicate();
        List<Order> frenchOrders = diplomacy.getPreviousPhase().getOrdersByCountry(Country.FRANCE);
        assertEquals(OrderStatus.RESOLVED, frenchOrders.get(0).getStatus());
        assertEquals(OrderStatus.RESOLVED, frenchOrders.get(1).getStatus());
        List<Order> italianOrders = diplomacy.getPreviousPhase().getOrdersByCountry(Country.ITALY);
        assertEquals(OrderStatus.BOUNCED, italianOrders.get(0).getStatus());
    }
}
