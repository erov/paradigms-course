package expression;

import expression.generic.calculators.AbstractCalculator;

public class Variable implements GenericExpression {
    private final String name;

    public Variable(String name) {
        this.name = name;
    }

    @Override
    public <T extends Number> T evaluate(AbstractCalculator<T> calculator, T x, T y, T z) {
        switch (name) {
            case "x":
                return x;
            case "y":
                return y;
            case "z":
                return z;
        }
        throw new IllegalArgumentException("Unknown variable name");
    }

    @Override
    public String toString() {
        return name;
    }
}
