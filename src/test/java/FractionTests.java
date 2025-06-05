import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FractionTests {

    @Test
    @DisplayName("Сложение двух положительных чисел")
    void simpleFractionTest() {
        Fraction first = new Fraction(1, 2);
        Fraction second = new Fraction(2, 3);
        Fraction expectedSum = new Fraction(7, 6);
        assertEquals(expectedSum, Fraction.sum(first, second), "Суммы должны быть одинаковыми");
    }

    @Test
    @DisplayName("Сложение положительного и отрицательного числа")
    void negativeFractionTest() {
        Fraction first = new Fraction(1, 2);
        Fraction second = new Fraction(-2, 3);
        Fraction expectedSum = new Fraction(-1, 6);
        assertEquals(expectedSum, Fraction.sum(first, second), "Суммы должны быть одинаковыми");
    }


    @Test
    @DisplayName("Сложение дроби с нулем в числителе")
    void zeroFractionTest() {
        Fraction first = new Fraction(0, 1);
        Fraction second = new Fraction(3, 5);
        Fraction expectedSum = new Fraction(3, 5);
        assertEquals(expectedSum, Fraction.sum(first, second), "Суммы должны быть одинаковыми");
    }

    @Test
    @DisplayName("Слишком большой знаменатель")
    void tooBigDenominatorTest() {
        Fraction first = new Fraction(1, 50000);
        Fraction second = new Fraction(1, 50000); // 50000 * 50000 = 2 500 000 000 > Integer.MAX_VALUE

        assertThrows(RuntimeException.class, () -> Fraction.sum(first, second),
                "Выброшен RuntimeException - Общий знаменатель слишком большой!");
    }

    @Test
    @DisplayName("Дробь с нулевым знаменателем")
    void zeroDenominatorThrowsException() {
        assertThrows(ArithmeticException.class, () -> new Fraction(1, 0),
                "Выброшен ArithmeticException - Нельзя делить на ноль!");
    }

    @Test
    @DisplayName("Не заполнены числитель или знаменатель")
    void nullValuesThrowException() {
        assertThrows(NullPointerException.class, () -> new Fraction(null, null),
                "Должно быть выброшено NullPointerException");
    }

}

