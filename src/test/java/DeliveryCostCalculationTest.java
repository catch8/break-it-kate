import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DeliveryCostCalculationTest {

    @Test
    @Tag("Positive")
    @DisplayName("Минимальная стоимость доставки при расстоянии 1 км, малый груз, базовая нагрузка на обслуживание")
    void testCheapestOrderCost() {
        Delivery delivery = new Delivery(1, CargoDimension.SMALL, false, ServiceWorkload.BASE);
        assertEquals(400, delivery.calculateDeliveryCost());
    }

    @Test
    @Tag("Positive")
    @DisplayName("Стоимость доставки с хрупким грузом на 2 км, базовая нагрузка")
    void testMoreThanMinimalOrderCost() {
        Delivery delivery = new Delivery(2, CargoDimension.SMALL, true, ServiceWorkload.BASE);
        assertEquals(450, delivery.calculateDeliveryCost());
    }

    @Test
    @Tag("Positive")
    @DisplayName("Стоимость доставки на 5 км, большой груз, высокая нагрузка")
    void test5kmDistanceLargeHighWorkloadOrderCost() {
        Delivery delivery = new Delivery(5, CargoDimension.LARGE, false, ServiceWorkload.HIGH);
        assertEquals(420, delivery.calculateDeliveryCost());
    }

    @Test
    @Tag("Positive")
    @DisplayName("Стоимость доставки на 10 км, хрупкий малый груз, повышенная нагрузка")
    void test10kmDistanceFragileIncreasedWorkloadOrderCost() {
        Delivery delivery = new Delivery(10, CargoDimension.SMALL, true, ServiceWorkload.INCREASED);
        assertEquals(600, delivery.calculateDeliveryCost());
    }

    @Test
    @Tag("Positive")
    @DisplayName("Стоимость доставки на 20 км, большой груз, очень высокая нагрузка")
    void test20kmDistanceLargeVeryHighWorkloadOrderCost() {
        Delivery delivery = new Delivery(20, CargoDimension.LARGE, false, ServiceWorkload.VERY_HIGH);
        assertEquals(640, delivery.calculateDeliveryCost());
    }

    @Test
    @Tag("Positive")
    @DisplayName("Стоимость доставки хрупкого малого груза на максимальную дистанцию 30 км, базовая нагрузка")
    void test30kmDistanceFragileBaseWorkloadOrderCost() {
        Delivery delivery = new Delivery(30, CargoDimension.SMALL, true, ServiceWorkload.BASE);
        assertEquals(600, delivery.calculateDeliveryCost());
    }

    @Test
    @Tag("Positive")
    @DisplayName("Стоимость доставки на 35 км, большой груз, очень высокая нагрузка")
    void testLongDistanceVeryHighWorkloadOrderCost() {
        Delivery delivery = new Delivery(35, CargoDimension.LARGE, false, ServiceWorkload.VERY_HIGH);
        assertEquals(800, delivery.calculateDeliveryCost());
    }

    @Test
    @Tag("Positive")
    @DisplayName("Стоимость доставки хрупкого малого груза при очень высокой нагрузке на 2 км")
    void testFragileVeryHighWorkloadOrderCost() {
        Delivery delivery = new Delivery(2, CargoDimension.SMALL, true, ServiceWorkload.VERY_HIGH);
        assertEquals(720, delivery.calculateDeliveryCost());
    }

    @Test
    @Tag("Negative")
    @DisplayName("Доставка хрупкого груза на расстояние >30 км")
    void testFragileOrderOver30KmThrows() {
        Delivery delivery = new Delivery(31, CargoDimension.LARGE, true, ServiceWorkload.BASE);
        Throwable exception = assertThrows(
                UnsupportedOperationException.class,
                delivery::calculateDeliveryCost);
        assertEquals(
                "Fragile cargo cannot be delivered for the distance more than 30", exception.getMessage());
    }

    @Test
    @Tag("Negative")
    @DisplayName("Доставка груза на отрицательное расстояние")
    void testNegativeDistanceThrows() {
        Delivery delivery = new Delivery(-1, CargoDimension.LARGE, true, ServiceWorkload.BASE);
        Throwable exception = assertThrows(
                IllegalArgumentException.class,
                delivery::calculateDeliveryCost);
        assertEquals("destinationDistance should be a positive number!", exception.getMessage());
    }


    @ParameterizedTest
    @CsvSource({
            "0, LARGE, false, VERY_HIGH, 400",
            "29, SMALL, true, INCREASED, 720",
            "15, LARGE, true, HIGH, 980"
    })
    @DisplayName("ДоставОчка")
    void testDeliveryCosts(int distance, String dimension, boolean isFragile, String workload, int expectedCost) {
        Delivery delivery = new Delivery(
                distance, CargoDimension.valueOf(dimension),
                isFragile, ServiceWorkload.valueOf(workload));

        int actualCost = (int) delivery.calculateDeliveryCost();
        assertEquals(expectedCost, actualCost);
    }
}
