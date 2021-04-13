package expression;

import expression.generic.calculators.AbstractCalculator;

public interface GenericExpression {
    <T extends Number> T evaluate(AbstractCalculator<T> calculator, T x, T y, T z);
}
