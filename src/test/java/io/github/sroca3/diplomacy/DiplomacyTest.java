package io.github.sroca3.diplomacy;


import io.github.sroca3.diplomacy.maps.SouthAmericanSupremacyLocation;
import io.github.sroca3.diplomacy.maps.SouthAmericanSupremacyMapVariant;
import io.github.sroca3.diplomacy.maps.TestMapVariant;
import io.github.sroca3.diplomacy.maps.TestVariantLocation;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DiplomacyTest {

    private static final Army ENGLISH_ARMY = new Army(CountryEnum.ENGLAND);
    private static final Fleet ENGLISH_FLEET = new Fleet(CountryEnum.ENGLAND);
    private static final Army FRENCH_ARMY = new Army(CountryEnum.FRANCE);
    private static final Fleet FRENCH_FLEET = new Fleet(CountryEnum.FRANCE);

    @Test
    public void executeOrderExpectUnitOwnershipException() {
        Diplomacy game = new Diplomacy(TestMapVariant.getInstance());
        game.beginFirstPhase();
        Order order = new Order(
            ENGLISH_ARMY,
            TestVariantLocation.A,
            OrderType.MOVE,
            TestVariantLocation.A,
            TestVariantLocation.B
        );
        assertThrows(UnitOwnershipException.class, () -> game.addOrder(order));
    }

    @Test
    public void executeUncontestedOrder() {
        Diplomacy game = new Diplomacy(TestMapVariant.getInstance());
        game.addUnit(TestVariantLocation.A, ENGLISH_ARMY);
        game.beginFirstPhase();
        Order order = new Order(
            ENGLISH_ARMY,
            TestVariantLocation.A,
            OrderType.MOVE,
            TestVariantLocation.A,
            TestVariantLocation.B
        );
        game.addOrder(order);
        game.adjudicate();
        Phase phase = game.getPreviousPhase();
        assertTrue(phase.getOrderById(order.getId()).getStatus().isResolved());
    }

    @Test
    public void executeIllegalArmyMoveToSea() {
        Diplomacy game = new Diplomacy(TestMapVariant.getInstance());
        game.addUnit(TestVariantLocation.A, ENGLISH_ARMY);
        game.beginFirstPhase();
        Order order = new Order(
            ENGLISH_ARMY,
            TestVariantLocation.A,
            OrderType.MOVE,
            TestVariantLocation.A,
            TestVariantLocation.C
        );
        game.addOrder(order);
        game.adjudicate();
        Phase phase = game.getPreviousPhase();
        assertTrue(phase.getOrderById(order.getId()).getStatus().isIllegal());
    }

    @Test
    public void executeOrderToOccupiedLocationExpectBounce() {
        Diplomacy game = new Diplomacy(TestMapVariant.getInstance());
        game.addUnit(TestVariantLocation.A, ENGLISH_ARMY);
        game.addUnit(TestVariantLocation.B, FRENCH_ARMY);
        game.beginFirstPhase();
        Order order = new Order(
            ENGLISH_ARMY,
            TestVariantLocation.A,
            OrderType.MOVE,
            TestVariantLocation.A,
            TestVariantLocation.B
        );
        game.addOrder(order);
        game.adjudicate();
        Phase phase = game.getPreviousPhase();
        assertTrue(phase.getOrderById(order.getId()).getStatus().isBounced());
        assertTrue(phase.getOrdersByCountry(CountryEnum.FRANCE).get(0).getStatus().isResolved());
    }

    @Test
    public void executeSupportedOrderToOccupiedLocationExpectDislodgement() {
        Diplomacy game = new Diplomacy(TestMapVariant.getInstance());
        game.addUnit(TestVariantLocation.A, ENGLISH_ARMY);
        game.addUnit(TestVariantLocation.B, FRENCH_ARMY);
        game.addUnit(TestVariantLocation.C, ENGLISH_FLEET);
        game.beginFirstPhase();
        Order o1 = new Order(
            ENGLISH_ARMY,
            TestVariantLocation.A,
            OrderType.MOVE,
            TestVariantLocation.A,
            TestVariantLocation.B
        );
        Order o2 = new Order(
            ENGLISH_FLEET,
            TestVariantLocation.C,
            OrderType.SUPPORT,
            TestVariantLocation.A,
            TestVariantLocation.B
        );
        Order o3 = new Order(
            FRENCH_ARMY,
            TestVariantLocation.B
        );
        game.addOrders(List.of(o1,o2,o3));
        game.adjudicate();
        Phase phase = game.getPreviousPhase();
        assertTrue(phase.getOrderById(o1.getId()).getStatus().isResolved());
        assertTrue(phase.getOrderById(o2.getId()).getStatus().isResolved());
        assertTrue(phase.getOrderById(o3.getId()).getStatus().isDislodged());
    }

    @Test
    public void executeOrderExpectDislodgementOfMovingUnit() {
        Diplomacy game = new Diplomacy(TestMapVariant.getInstance());
        game.addUnit(TestVariantLocation.A, ENGLISH_ARMY);
        game.addUnit(TestVariantLocation.B, FRENCH_ARMY);
        game.addUnit(TestVariantLocation.C, ENGLISH_FLEET);
        game.beginFirstPhase();
        Order o1 = new Order(
            ENGLISH_ARMY,
            TestVariantLocation.A,
            OrderType.MOVE,
            TestVariantLocation.A,
            TestVariantLocation.B
        );
        Order o2 = new Order(
            ENGLISH_FLEET,
            TestVariantLocation.C,
            OrderType.SUPPORT,
            TestVariantLocation.A,
            TestVariantLocation.B
        );
        Order o3 = new Order(
            FRENCH_ARMY,
            TestVariantLocation.B,
            OrderType.MOVE,
            TestVariantLocation.B,
            TestVariantLocation.A
        );
        game.addOrders(List.of(o1,o2,o3));
        game.adjudicate();
        Phase phase = game.getPreviousPhase();
        assertTrue(phase.getOrderById(o1.getId()).getStatus().isResolved());
        assertTrue(phase.getOrderById(o2.getId()).getStatus().isResolved());
        assertTrue(phase.getOrderById(o3.getId()).getStatus().isDislodged());
    }

    @Test
    public void executeSupportedOrderExpectStandoffOfUnitsWithSameStrength() {
        Diplomacy game = new Diplomacy(TestMapVariant.getInstance());
        game.addUnit(TestVariantLocation.A, ENGLISH_ARMY);
        game.addUnit(TestVariantLocation.B, FRENCH_ARMY);
        game.addUnit(TestVariantLocation.C, ENGLISH_FLEET);
        game.addUnit(TestVariantLocation.D, FRENCH_FLEET);
        game.beginFirstPhase();
        Order o1 = new Order(
            ENGLISH_ARMY,
            TestVariantLocation.A,
            OrderType.MOVE,
            TestVariantLocation.A,
            TestVariantLocation.B
        );
        Order o2 = new Order(
            ENGLISH_FLEET,
            TestVariantLocation.C,
            OrderType.SUPPORT,
            TestVariantLocation.A,
            TestVariantLocation.B
        );
        Order o3 = new Order(
            FRENCH_ARMY,
            TestVariantLocation.B,
            OrderType.MOVE,
            TestVariantLocation.B,
            TestVariantLocation.A
        );
        Order o4 = new Order(
            FRENCH_FLEET,
            TestVariantLocation.D,
            OrderType.SUPPORT,
            TestVariantLocation.B,
            TestVariantLocation.A
        );
        game.addOrders(List.of(o1,o2,o3, o4));
        game.adjudicate();
        Phase phase = game.getPreviousPhase();
        assertTrue(phase.getOrderById(o1.getId()).getStatus().isBounced());
        assertTrue(phase.getOrderById(o2.getId()).getStatus().isFailed());
        assertTrue(phase.getOrderById(o3.getId()).getStatus().isBounced());
        assertTrue(phase.getOrderById(o4.getId()).getStatus().isFailed());
    }

    @Test
    public void executeCircularOrderExpectAllToResolve() {
        Diplomacy game = new Diplomacy(TestMapVariant.getInstance());
        game.addUnit(TestVariantLocation.A, ENGLISH_ARMY);
        game.addUnit(TestVariantLocation.B, FRENCH_FLEET);
        game.addUnit(TestVariantLocation.C, ENGLISH_FLEET);
        game.beginFirstPhase();
        Order o1 = new Order(
            ENGLISH_ARMY,
            TestVariantLocation.A,
            OrderType.MOVE,
            TestVariantLocation.A,
            TestVariantLocation.B
        );
        Order o2 = new Order(
            ENGLISH_FLEET,
            TestVariantLocation.C,
            OrderType.MOVE,
            TestVariantLocation.C,
            TestVariantLocation.A
        );
        Order o3 = new Order(
            FRENCH_FLEET,
            TestVariantLocation.B,
            OrderType.MOVE,
            TestVariantLocation.B,
            TestVariantLocation.C
        );
        game.addOrders(List.of(o1,o2,o3));
        game.adjudicate();
        Phase phase = game.getPreviousPhase();
        assertTrue(phase.getOrderById(o1.getId()).getStatus().isResolved());
        assertTrue(phase.getOrderById(o2.getId()).getStatus().isResolved());
        assertTrue(phase.getOrderById(o3.getId()).getStatus().isResolved());
    }

    @Test
    public void executeSupportedOrderExpectStandoffOfUnitsWithSameStrengthDueToSupportBeingCut() {
        Diplomacy game = new Diplomacy(TestMapVariant.getInstance());
        game.addUnit(TestVariantLocation.A, ENGLISH_ARMY);
        game.addUnit(TestVariantLocation.B, FRENCH_ARMY);
        game.addUnit(TestVariantLocation.C, ENGLISH_FLEET);
        game.addUnit(TestVariantLocation.D, FRENCH_FLEET);
        game.beginFirstPhase();
        Order o1 = new Order(
            ENGLISH_ARMY,
            TestVariantLocation.A,
            OrderType.SUPPORT,
            TestVariantLocation.C,
            TestVariantLocation.B
        );
        Order o2 = new Order(
            ENGLISH_FLEET,
            TestVariantLocation.C,
            OrderType.MOVE,
            TestVariantLocation.C,
            TestVariantLocation.B
        );
        Order o3 = new Order(
            FRENCH_ARMY,
            TestVariantLocation.B,
            OrderType.HOLD,
            TestVariantLocation.B,
            TestVariantLocation.A
        );
        Order o4 = new Order(
            FRENCH_FLEET,
            TestVariantLocation.D,
            OrderType.MOVE,
            TestVariantLocation.D,
            TestVariantLocation.A
        );
        game.addOrders(List.of(o1,o2,o3, o4));
        game.adjudicate();
        Phase phase = game.getPreviousPhase();
        assertTrue(phase.getOrderById(o1.getId()).getStatus().isCut());
        assertTrue(phase.getOrderById(o2.getId()).getStatus().isBounced());
        assertTrue(phase.getOrderById(o3.getId()).getStatus().isResolved());
        assertTrue(phase.getOrderById(o4.getId()).getStatus().isBounced());
    }

    @Test
    public void executeOrderToSwapUnitsDueToConvoyExpectResolvedOrders() {
        Diplomacy game = new Diplomacy(TestMapVariant.getInstance());
        game.addUnit(TestVariantLocation.A, ENGLISH_ARMY);
        game.addUnit(TestVariantLocation.B, FRENCH_ARMY);
        game.addUnit(TestVariantLocation.C, ENGLISH_FLEET);
        game.beginFirstPhase();
        Order o1 = new Order(
            ENGLISH_ARMY,
            TestVariantLocation.A,
            OrderType.MOVE,
            TestVariantLocation.A,
            TestVariantLocation.B
        );
        Order o2 = new Order(
            ENGLISH_FLEET,
            TestVariantLocation.C,
            OrderType.CONVOY,
            TestVariantLocation.A,
            TestVariantLocation.B
        );
        Order o3 = new Order(
            FRENCH_ARMY,
            TestVariantLocation.B,
            OrderType.MOVE,
            TestVariantLocation.B,
            TestVariantLocation.A
        );
        game.addOrders(List.of(o1,o2,o3));
        game.adjudicate();
        Phase phase = game.getPreviousPhase();
        assertTrue(phase.getOrderById(o1.getId()).getStatus().isResolved());
        assertTrue(phase.getOrderById(o2.getId()).getStatus().isResolved());
        assertTrue(phase.getOrderById(o3.getId()).getStatus().isResolved());
    }

    @Test
    public void parseOrder() {
        Diplomacy game = new Diplomacy(SouthAmericanSupremacyMapVariant.getInstance());
        game.addUnit(SouthAmericanSupremacyLocation.CONCEPCION, new Army(CountryEnum.PARAGUAY));
        String orderString = "A " + SouthAmericanSupremacyLocation.CONCEPCION.getShortName() + " MOVE " + SouthAmericanSupremacyLocation.ASUNCION.name();
        Order order = game.parseOrder(orderString);
        assertEquals(UnitType.ARMY, order.getUnit().getType());
        assertEquals(CountryEnum.PARAGUAY, order.getUnit().getCountry());
        assertEquals(SouthAmericanSupremacyLocation.CONCEPCION, order.getCurrentLocation());
        assertEquals(SouthAmericanSupremacyLocation.CONCEPCION, order.getFromLocation());
        assertEquals(SouthAmericanSupremacyLocation.ASUNCION, order.getToLocation());
    }
}
