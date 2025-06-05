//класс, который работает с дробью, у которой есть числитель-num и знаменатель-den. И реализовать сумму двух чисел

import java.util.Objects;

public class Fraction {
    Integer num;
    Integer den;

    public Integer getNum() {//получаем значения
        return num;
    }

    public Integer getDen() {
        return den;
    }

    public Fraction(Integer num, Integer den) {
        if (num == null || den == null) {
            throw new NullPointerException("Value should be not null");
        }
        if (den == 0) {
            throw new ArithmeticException("Cannot divide to zero");
        }
        this.num = num;//создаем объекты класса
        this.den = den;
    }
// 1/2 + 2/3= 3/6 + 4/6= 7/6

    public static Fraction sum(Fraction first, Fraction second) {
        long bigCommonDen = ((long) first.getDen() * (long) second.getDen());
        if (bigCommonDen > Integer.MAX_VALUE) {
            throw new RuntimeException("Common denominator is too big!");
        }

        Integer commonDen = first.getDen() * second.getDen();
        Integer sumNum = first.getNum() * second.getDen() + second.getNum() * first.getDen();
        return new Fraction(sumNum, commonDen);//выводит н-р 2/4
    }

    @Override
    public String toString() {
        return num + "/" + den;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Fraction fraction = (Fraction) o;
        return Objects.equals(num, fraction.num) && Objects.equals(den, fraction.den);
    }

    @Override
    public int hashCode() {
        return Objects.hash(num, den);
    }

}
