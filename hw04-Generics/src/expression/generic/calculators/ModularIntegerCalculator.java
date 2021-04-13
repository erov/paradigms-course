package expression.generic.calculators;

import expression.exceptions.DivideByZeroException;

public class ModularIntegerCalculator extends IntegerCalculator {
    private final Integer MOD = 1009;

    @Override
    public Integer add(Integer a, Integer b) {
        return actualValue(super.add(a, b));
    }

    @Override
    public Integer subtract(Integer a, Integer b) {
        return actualValue(super.subtract(a, b));
    }

    @Override
    public Integer multiply(Integer a, Integer b) {
        return actualValue(super.multiply(a, b));
    }

    @Override
    protected Integer divideImpl(Integer a, Integer b) {
        return multiply(a, pow(b, MOD - 2));
    }


    @Override
    public Integer negate(Integer a) {
        return actualValue(-a);
    }

    @Override
    public Integer mod(Integer a, Integer b) {
        if (b == 0) {
            throw new DivideByZeroException(a);
        }
        return a % (MOD < b ? MOD : b);
    }

    @Override
    public Integer valueOf(int a) {
        return actualValue(a);
    }

    @Override
    public Integer valueOf(String value) {
        return actualValue(super.valueOf(value));
    }

    protected Integer actualValue(Integer a) {
        return (a % MOD + MOD) % MOD;
    }

    protected Integer pow(Integer a, int n) {
        Integer result = valueOf(1);
        while (n > 0) {
            if ((n & 1) == 1) {
                result = multiply(result, a);
                n--;
            }
            a = square(a);
            n >>= 1;
        }
        return result;
    }
}
