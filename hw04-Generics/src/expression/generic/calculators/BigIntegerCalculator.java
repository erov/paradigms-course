package expression.generic.calculators;

import expression.exceptions.InvalidArgumentException;
import expression.exceptions.InvalidConstException;

import java.math.BigInteger;

public class BigIntegerCalculator extends AbstractCalculator<BigInteger> implements IntegerTypeCalculator {
    @Override
    public BigInteger add(BigInteger a, BigInteger b) {
        return a.add(b);
    }

    @Override
    public BigInteger subtract(BigInteger a, BigInteger b) {
        return a.subtract(b);
    }

    @Override
    public BigInteger multiply(BigInteger a, BigInteger b) {
        return a.multiply(b);
    }

    @Override
    protected BigInteger divideImpl(BigInteger a, BigInteger b) {
        return a.divide(b);
    }

    @Override
    public BigInteger negate(BigInteger a) {
        return a.negate();
    }

    @Override
    public BigInteger mod(BigInteger a, BigInteger b) {
        if (b.compareTo(BigInteger.ZERO) <= 0) {
            throw new InvalidArgumentException("Modulus must be positive", b);
        }
        return a.mod(b);
    }

    @Override
    public BigInteger valueOf(int value) {
        return BigInteger.valueOf(value);
    }

    @Override
    public BigInteger valueOf(String value) {
        try {
            return new BigInteger(value);
        } catch (NumberFormatException e) {
            throw new InvalidConstException(value, "BigInteger");
        }
    }

    @Override
    protected int compare(BigInteger a, BigInteger b) {
        return a.compareTo(b);
    }
}
